package ca.gc.hc.nhpd.webservice.handler;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * JAX-WS Handler resolver.  Provides a hook into the WsseClientHandler
 * implmentation for any JAX-WS client.
 * 
 * @author MRABE
 *
 */
public class WsseClientHandlerResolver implements HandlerResolver {
    
public List<Handler> getHandlerChain(PortInfo portInfo) {
      List<Handler> handlerChain = new ArrayList<Handler>();

      WsseClientHandler clientHandler = new WsseClientHandler();
      clientHandler.setUserId("System");
      clientHandler.setPassword("system");
      handlerChain.add(clientHandler);

      return handlerChain;
   }
}

 


