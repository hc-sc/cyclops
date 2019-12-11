package ca.gc.hc.nhpd.webservice.handler;

import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * WSSE client handler which enables a JAX_WS web service client to add WSSE 
 * credentials to the SOAP header.  Based on sample code at: 
 *   http://www.javadb.com/using-a-message-handler-to-alter-the-soap-header-in-a-web-service-client 
 * 
 * TODO: revisit; don't need to send password on every request.  There will be a login dialog; 
 * look at SAML, token based auth, etc.
 *  
 * @author MRABE
 */
public class WsseClientHandler implements SOAPHandler<SOAPMessageContext> {
	
	private String userId;
	private String userPassword;
	

	public void setPassword(String password) {
		this.userPassword = password;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
    public boolean handleMessage(SOAPMessageContext smc) {

        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        //we are only interested in outbound messages 
        if (outboundProperty.booleanValue()) {

            SOAPMessage message = smc.getMessage();

            try {

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.addHeader();

                SOAPElement security =
                        header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

                SOAPElement usernameToken =
                        security.addChildElement("UsernameToken", "wsse");
                usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

                SOAPElement username =
                        usernameToken.addChildElement("Username", "wsse");
                username.addTextNode(userId);

                SOAPElement password =
                        usernameToken.addChildElement("Password", "wsse");
                password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                password.addTextNode(userPassword);

                //Print out the outbound SOAP message to System.out
                /*
                message.writeTo(System.out);
                System.out.println("");
                */
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return outboundProperty;

    }

    public Set<QName> getHeaders() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    public boolean handleFault(SOAPMessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    public void close(MessageContext context) {
    	//throw new UnsupportedOperationException("Not supported yet.");
    }
 
}

