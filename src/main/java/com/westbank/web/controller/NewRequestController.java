package com.westbank.web.controller;

import com.westbank.domain.Customer;
import com.westbank.service.CustomerService;
import com.westbank.web.Constants;
import com.westbank.web.form.ApplicationForm;
import com.westbank.proxy.LoanApprovalProcessProxy;
import com.westbank.web.validator.NewRequestValidator;
import com.westbank.web.validator.SessionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A controller for handling a new loan request issued after the customer already
 * logged in.
 */
@Controller
@RequestMapping("/newrequest.html")
public class NewRequestController {

	static Logger log = LoggerFactory.getLogger(NewRequestController.class);

	static final String LOGIN_VIEW = "redirect:/login.html";
	static final String THIS_VIEW = "customer/newrequest";
	static final String NEW_REQUEST_INFO = "customer/info";

	@Autowired(required = false)
	protected ApplicationForm applicationForm;

	@Autowired
	protected NewRequestValidator validator;

	@Autowired
	protected LoanApprovalProcessProxy processProxy;

	@Autowired
	private CustomerService customerService;

	@ModelAttribute("applicationForm")
	public ApplicationForm setupApplicationForm() {
		log.info("Initialize a new request form");
		if (applicationForm != null) {
			return applicationForm;
		} else {
			return new ApplicationForm();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	protected String prepare(HttpSession session) {
		final Object sessionId = SessionValidator.validateSession(session, Constants.SESSION_CUSTOMER_ID);
		if (sessionId != null) {
			log.info("Return the new loan request view");
			session.setAttribute(Constants.SESSION_NAV, Constants.NAV_NEW_REQUEST);
			session.removeAttribute(Constants.SESSION_PROCESS_STATUS);
			session.removeAttribute(Constants.SESSION_PROCESS_STATUS_KEY);
			session.removeAttribute(Constants.SESSION_CUSTOMER_TITLE);
			session.removeAttribute(Constants.SESSION_CUSTOMER_NAME);
			session.removeAttribute(Constants.SESSION_CUSTOMER_EMAIL);
			return THIS_VIEW;
		} else {
			log.info("Session ID is invalid. Customer must log-in first");
			session.setAttribute(Constants.SESSION_NAV, Constants.NAV_LOGIN);
			return LOGIN_VIEW;
		}
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(false));
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final CustomDateEditor editor = new CustomDateEditor(df, false);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmission(@ModelAttribute ApplicationForm form, BindingResult result, HttpSession session) {

		session.removeAttribute(Constants.SESSION_PROCESS_STATUS);
		session.removeAttribute(Constants.SESSION_PROCESS_STATUS_KEY);
		session.removeAttribute(Constants.SESSION_CUSTOMER_TITLE);
		session.removeAttribute(Constants.SESSION_CUSTOMER_NAME);
		session.removeAttribute(Constants.SESSION_CUSTOMER_EMAIL);

		final Object sessionId = SessionValidator.validateSession(session, Constants.SESSION_CUSTOMER_ID);
		log.info("Process the new loan request");
		if (validator != null) {
			validator.validate(form, result);
			if (result.hasFieldErrors()) {
				log.info("Form validation failed. Stay!");
				return THIS_VIEW;
			} else {
				if (sessionId != null) {
					session.setAttribute(Constants.SESSION_NAV, Constants.NAV_NEW_REQUEST);
					if (processProxy != null) {
						log.info("Deliver the new request to the process");
						// customer ID must be set to be properly handled
						form.setBorrowerCustomerId(String.valueOf(sessionId));
						Customer borrower = customerService.findCustomerById((Integer) sessionId);
						boolean isOK = processProxy.startProcess(form);
						if (isOK) {
							session.setAttribute(Constants.SESSION_CUSTOMER_TITLE, borrower.getTitle());
							session.setAttribute(Constants.SESSION_CUSTOMER_NAME,
									borrower.getFirstName() + " " + borrower.getLastName());
							session.setAttribute(Constants.SESSION_CUSTOMER_EMAIL, borrower.getEmail());
							return NEW_REQUEST_INFO;

						} else {
							session.setAttribute(Constants.SESSION_PROCESS_STATUS, Constants.PROCESS_STATUS_ERROR);
							session.setAttribute(Constants.SESSION_PROCESS_STATUS_KEY, Constants.MSG_INVOCATION_ERR);
						}
					}
				} else {
					log.info("Session ID is invalid. Customer must log-in first");
					session.setAttribute(Constants.SESSION_NAV, Constants.NAV_LOGIN);
					return LOGIN_VIEW;
				}
			}
		}
		return THIS_VIEW;
	}
}