package westbank.ws.business.loanfile._2009._11;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-06-19T08:30:31.981+10:00
 * Generated source version: 3.2.4
 *
 */
@WebService(targetNamespace = "urn:westbank:ws:business:LoanFile:2009:11", name = "LoanFile")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LoanFile {

    @WebMethod
    @WebResult(name = "LoanFileResponse", targetNamespace = "urn:westbank:ws:business:LoanFile:2009:11", partName = "response")
    public LoanFileResponse update(
        @WebParam(partName = "request", name = "LoanFileRequest", targetNamespace = "urn:westbank:ws:business:LoanFile:2009:11")
        LoanFileRequest request
    );
}
