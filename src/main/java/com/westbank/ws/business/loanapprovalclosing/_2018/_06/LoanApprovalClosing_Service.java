package com.westbank.ws.business.loanapprovalclosing._2018._06;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-10-16T13:28:53.487+11:00
 * Generated source version: 3.2.4
 *
 */
@WebServiceClient(name = "LoanApprovalClosing",
                  targetNamespace = "urn:com:westbank:ws:business:LoanApprovalClosing:2018:06")
public class LoanApprovalClosing_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:com:westbank:ws:business:LoanApprovalClosing:2018:06", "LoanApprovalClosing");
    public final static QName LoanApprovalClosingPort = new QName("urn:com:westbank:ws:business:LoanApprovalClosing:2018:06", "LoanApprovalClosingPort");
    static {
        WSDL_LOCATION = null;
    }

    public LoanApprovalClosing_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public LoanApprovalClosing_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LoanApprovalClosing_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public LoanApprovalClosing_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public LoanApprovalClosing_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public LoanApprovalClosing_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns LoanApprovalClosing
     */
    @WebEndpoint(name = "LoanApprovalClosingPort")
    public LoanApprovalClosing getLoanApprovalClosingPort() {
        return super.getPort(LoanApprovalClosingPort, LoanApprovalClosing.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LoanApprovalClosing
     */
    @WebEndpoint(name = "LoanApprovalClosingPort")
    public LoanApprovalClosing getLoanApprovalClosingPort(WebServiceFeature... features) {
        return super.getPort(LoanApprovalClosingPort, LoanApprovalClosing.class, features);
    }

}
