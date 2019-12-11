package ca.gc.hc.handler;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HandlerBase implements SOAPHandler<SOAPMessageContext> {

	// private static final Log log = LogFactory.getLog(HandlerBase.class);
	private static final String languageTag = "xml:lang";
	private static final String versionTag = "xml:version";
	private String lang = "en";
	private String version = "2.0";

	public HandlerBase(String language, String version) {
		this.lang = language;
		this.version = version;
	}

	/*
	 * This "handleMessage" method is called for both inbound and outbound messages.
	 * We need to intercept inbound messages on the server side to extract the
	 * language from the SOAP header.
	 */
	public boolean handleMessage(SOAPMessageContext messageContext) {
		// log.debug("In SoapHeaderHandler.handleMessage");

		if (isOutboundMessage(messageContext)) {

			setHeaderLanguage(messageContext);
			setHeaderVersion(messageContext);
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

	/*
	 * determines if message is inbound or outbound
	 */
	private boolean isOutboundMessage(SOAPMessageContext messageContext) {

		Boolean isOutbound = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		// log.debug ( isOutbound.booleanValue() ? "Outbound msg" : "Inbound msg");

		return (isOutbound.booleanValue());
	}

	/**
	 * Set the language from the SOAP Header. The language is set according to XML
	 * I18N standards with the "xml:lang" header attribute. For example:
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
	private boolean setHeaderLanguage(SOAPMessageContext msgContext) {

		String language = null;
		try {

			SOAPMessage soapMsg = msgContext.getMessage();

			// SOAPHeader soapHeader = soapMsg.getSOAPHeader();
			SOAPBody soapHeader = soapMsg.getSOAPBody();

			soapHeader.setAttribute(languageTag, this.lang);

			language = soapHeader.getAttribute(languageTag);

			if (language == null) {
				// log.warn("SOAP header language is null ...returning false");
				return false;
			}
			// log.debug("SoapHeaderHandler: Language specified in SOAP Header: " +
			// language);
		}

		catch (SOAPException ex) {
			// log.warn("SOAPException occurred in setHeaderLanguage");
			ex.printStackTrace();
			// throw new WebServiceException(ex);
		} catch (Throwable t) {
			// log.warn("Unexpected exception occurred in setHeaderLanguage");
			t.printStackTrace();
		}

		return true;
	}

	/**
	 * sets the version from the SOAP Header. The version is set using the
	 * "xml:version" header attribute. For example:
	 * 
	 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
	 * xmlns:mon="http://monograph.webservice.nhpd.hc.gc.ca/">
	 * <soapenv:Header xml:version="1.4"> </soapenv:Header> <soapenv:Body>
	 * <mon:getMwsConfigProperties/> </soapenv:Body> </soapenv:Envelope>
	 */
	private boolean setHeaderVersion(SOAPMessageContext msgContext) {

		String version = null;
		try {
			SOAPMessage soapMsg = msgContext.getMessage();

			SOAPHeader soapHeader = soapMsg.getSOAPHeader();

			soapHeader.setAttribute(versionTag, this.version);

			version = soapHeader.getAttribute(versionTag);
			if (version == null) {
				// log.warn("SOAP header version is null ...returning false");
				return false;
			}
			// log.debug("SoapHeaderHandler: Version specified in SOAP Header: " + version);
		}

		catch (SOAPException ex) {
			// log.warn("SOAPException occurred in setHeaderVersion ");
			ex.printStackTrace();
			// throw new WebServiceException(ex);
		} catch (Throwable t) {
			// log.warn("Unexpected exception occurred in setHeaderVersion ");
			t.printStackTrace();
		}

		return true;
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
		// log.warn("In SoapHeaderHandler.handleFault");
		return true;
	}

	public void close(MessageContext mc) {
	}

}
