/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import java.util.List;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WebServiceWrapperWS {

	List<DosageFormWS> dosageForms;
	List<IngredientWS> ingredients;
	List<RouteOfAdministrationWS> routesOfAdministration;
	List<SolventWS> solvents;
	List<UnitsWS> units;
	
	public WebServiceWrapperWS(){};
	
	public WebServiceWrapperWS(
			List<DosageFormWS> dosageForms,
			List<IngredientWS> ingredients,
			List<RouteOfAdministrationWS> routesOfAdministration,
			List<SolventWS> solvents,
			List<UnitsWS> units
	) {
		this.dosageForms = dosageForms;
		this.ingredients = ingredients;
		this.routesOfAdministration = routesOfAdministration;
		this.solvents = solvents;
		this.units = units;
	}
	
	public List<DosageFormWS> getDosageForms() {
		return dosageForms;
	}
	
	public void setDosageForms(List<DosageFormWS> dosageForms) {
		// Not Implemented.
	}
	
	public List<IngredientWS> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<IngredientWS> ingredients) {
		// Not Implemented.
	}
	
	public List<RouteOfAdministrationWS> getRoutesOfAdministration() {
		return routesOfAdministration;
	}
	
	public void setRoutesOfAdministration(List<RouteOfAdministrationWS> routesOfAdministration) {
		// Not Implemented.
	}
	
    public List<SolventWS> getSolvents() {
    	return solvents;
    }

    public void setSolvents(List<SolventWS> solvents) {
        // Not Implemented.
    }
    
    public List<UnitsWS> getUnits() {
    	return units;
    }
    
    public void setUnits(List<UnitsWS> units) {
    	// Not Implemented.
    }

}
