package ca.gc.hc.nhpd.webservice.handler;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NodeList;

import ca.gc.hc.nhpd.util.ThreadContext;

public class SoapHeaderHandler implements SOAPHandler<SOAPMessageContext> {
	private static final Log log = LogFactory.getLog(SoapHeaderHandler.class);
	private static final String languageTag = "xml:lang";
	private static final String versionTag = "xml:version";

	public SoapHeaderHandler() {
	}

	/*
	 * This "handleMessage" method is called for both inbound and outbound messages.
	 * We need to intercept inbound messages on the server side to extract the
	 * language from the SOAP header.
	 */
	public boolean handleMessage(SOAPMessageContext messageContext) {
		log.debug("In SoapHeaderHandler.handleMessage");

		if (isInboundMessage(messageContext)) {

			// get the User principal from the WSSE fragment in the SOAP header
			String userId = extractUserId(messageContext);
			// set the user's language for this thread.
			ThreadContext.getInstance().setUserId(userId);

			// get the xml:lang attribute from the SOAP header
			String userLanguage = extractHeaderLanguage(messageContext);

			// set the user's language for this thread.
			ThreadContext.getInstance().setUserLanguage(userLanguage);

			// get the xml:version attribute from the SOAP header
			String versionString = extractHeaderVersion(messageContext);

			// set the user's version for this thread.
			ThreadContext.getInstance().setVersion(versionString);

		}

		return true;
	}

	/*
	 * determines if message is inbound or outbound
	 */
	private boolean isInboundMessage(SOAPMessageContext messageContext) {

		Boolean isOutbound = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		// log.debug ( isOutbound.booleanValue() ? "Outbound msg" : "Inbound msg");

		return (!isOutbound.booleanValue());
	}

	/**
	 * Extracts the language from the SOAP Header. The language is set according to
	 * XML I18N standards with the "xml:lang" header attribute. For example:
	 * 
	 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
	 * xmlns:mon="http://monograph.webservice.nhpd.hc.gc.ca/">
	 * <soapenv:Header xml:lang="fra"> </soapenv:Header> <soapenv:Body>
	 * <mon:getMwsConfigProperties/> </soapenv:Body> </soapenv:Envelope>
	 * 
	 * NB. the current WebSphere 6.1 handler requires that the Header fragment be
	 * very well formed; the following styles do not work: 1.
	 * <soapenv:Header xml:lang="fr"/> (handler does not see the header) 2.
	 * <soapenv:Header xml:lang="fr"></soapenv:Header> (there must be some content
	 * in the header, a single space will do)
	 * 
	 * The following styles work: 1. <soapenv:Header xml:lang="fra">
	 * </soapenv:Header> 2. <soapenv:Header xml:lang="fra"> </soapenv:Header>
	 * 
	 */
	private String OFF_extractHeaderLanguage(SOAPMessageContext msgContext) {

		String language = null;
		try {

			SOAPMessage soapMessage = msgContext.getMessage();
			// this provides a SOAP message trace facility
			// log("MSG=" + getMsgAsString(soapMessage));
			System.out.println("MSG=" + getMsgAsString(soapMessage));
			SOAPHeader header = soapMessage.getSOAPHeader();

			if (header == null) {
				log.warn("SOAP header is null ...returning default language (English)");
				return ThreadContext.LANG_EN;
			}
			language = header.getAttribute(languageTag);
			log.debug("SoapHeaderHandler: Language specified in SOAP Header: " + language);
		}

		catch (SOAPException ex) {
			log.warn("SOAPException occurred in extractHeaderLanguage - returning default language EN");
			ex.printStackTrace();
			// throw new WebServiceException(ex);
			return ThreadContext.LANG_EN;
		} catch (Throwable t) {
			log.warn("Unexpected exception occurred in extractHeaderLanguage - returning default language EN");
			t.printStackTrace();
			return ThreadContext.LANG_EN;
		}

		return language;
	}

	private String extractHeaderLanguage(SOAPMessageContext msgContext) {

		String language = null;
		try {

			SOAPMessage soapMessage = msgContext.getMessage();
			// this provides a SOAP message trace facility
			// log("MSG=" + getMsgAsString(soapMessage));
			// System.out.println("MSG=" + getMsgAsString(soapMessage));
			SOAPBody soapBody = soapMessage.getSOAPBody();
			if (soapBody == null) {
				log.warn("SOAP Body is null ...returning default language (English)");
				return ThreadContext.LANG_EN;
			}
			language = soapBody.getAttribute(languageTag);
			log.debug("SoapHeaderHandler: Language specified in SOAP Body: " + language);
		}

		catch (SOAPException ex) {
			log.warn("SOAPException occurred in extractHeaderLanguage - returning default language EN");
			ex.printStackTrace();
			// throw new WebServiceException(ex);
			return ThreadContext.LANG_EN;
		} catch (Throwable t) {
			log.warn("Unexpected exception occurred in extractHeaderLanguage - returning default language EN");
			t.printStackTrace();
			return ThreadContext.LANG_EN;
		}

		return language;
	}

	/**
	 * Extracts the version from the SOAP Header. The version is set using the
	 * "xml:version" header attribute. For example:
	 * 
	 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
	 * xmlns:mon="http://monograph.webservice.nhpd.hc.gc.ca/">
	 * <soapenv:Header xml:version="1.4"> </soapenv:Header> <soapenv:Body>
	 * <mon:getMwsConfigProperties/> </soapenv:Body> </soapenv:Envelope>
	 */
	private String extractHeaderVersion(SOAPMessageContext msgContext) {

		String version = null;
		try {

			SOAPMessage soapMessage = msgContext.getMessage();
			SOAPHeader header = soapMessage.getSOAPHeader();
			if (header == null) {
				log.warn("SOAP header is null ...returning default version (" + ThreadContext.VERSION_DEFAULT + ")");
				return ThreadContext.VERSION_DEFAULT;
			}
			version = header.getAttribute(versionTag);
			log.debug("SoapHeaderHandler: Version specified in SOAP Header: " + version);
		}

		catch (SOAPException ex) {
			log.warn("SOAPException occurred in extractHeaderVersion - returning default version ("
					+ ThreadContext.VERSION_DEFAULT + ")");
			ex.printStackTrace();
			return ThreadContext.VERSION_DEFAULT;
		} catch (Throwable t) {
			log.warn("Unexpected exception occurred in extractHeaderVersion - returning default version ("
					+ ThreadContext.VERSION_DEFAULT + ")");
			t.printStackTrace();
			return ThreadContext.VERSION_DEFAULT;
		}

		return version;
	}

	/**
	 * Extracts the user id from the WSSE security header. For now, this is
	 * unencrypted data; just need to get the value out of the SOAP Header WSSE
	 * fragment.
	 * 
	 */
	private String extractUserId(SOAPMessageContext msgContext) {

		String userName = null;
		try {

			SOAPEnvelope envelope = msgContext.getMessage().getSOAPPart().getEnvelope();
			SOAPHeader header = envelope.getHeader();

			/*
			 * Get the username Soap element. We extract the named element directly. An
			 * alternate mechanism to ensure that the the WSSE header is properly formed
			 * would be to ensure that the parent elements (Security, UsernameToken) also
			 * exist, but I'm just doing a quick and dirty impl for now.
			 */

			// NodeList list1 = header.getElementsByTagName("wsse:Security");
			// log ("list1 size: " + list1.getLength());

			NodeList list2 = header.getElementsByTagName("wsse:Username");

			// log ("Username nodelist size: " + list2.getLength());
			userName = list2.item(0).getTextContent();
			// log ("Value" + userName);

		} catch (Exception ex) {
			// ignore for now...user id passing is currently optional
			// log.error("Could not extract user principal from SOAP header");
		}
		return userName;

	}

	public String getMsgAsString(SOAPMessage message) {

		String msg = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			message.writeTo(baos);
			msg = baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}

	/*
	 * Required method when implementing SOAPHandler. Although I don't see why this
	 * is required if the headers are accessible as implemented above... I suspect
	 * the next SOAP version gets rid of this weirdness.
	 * 
	 * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
	 */
	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleFault(SOAPMessageContext mc) {
		log.warn("In SoapHeaderHandler.handleFault");
		return true;
	}

	public void close(MessageContext mc) {
	}

}
