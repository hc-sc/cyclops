package ca.gc.hc.nhpd.webservice;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.gc.hc.nhpd.webservice.ingredient.IngredientService;

@WebService(serviceName = "CombinedWebService")
@HandlerChain(file = "handlers.xml")
public class CombinedWebService extends IngredientService {

	private static final Log log = LogFactory.getLog(CombinedWebService.class);

}
