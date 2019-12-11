package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographSourceIngredient;

public class MonographSourceIngredientWS implements Comparable<MonographSourceIngredientWS>{
    // The wrapped MonographSourceIngredient
    private MonographSourceIngredient monographSourceIngredient;

    public MonographSourceIngredientWS(
            MonographSourceIngredient monographSourceIngredient) {
        this.monographSourceIngredient = monographSourceIngredient;
    }

    public MonographSourceIngredientWS() {
        monographSourceIngredient = new MonographSourceIngredient();
    }

    public String getSourceMaterialName() {
        return monographSourceIngredient.getSourceMaterialName();
    }

    public void setSourceMaterialName(String sourceMaterialName) {
        //
    }
    
    public MonographDependenciesWS getMonographDependenciesWS() {
        MonographDependenciesWS monographDependenciesWS = new MonographDependenciesWS(monographSourceIngredient);
        if (monographDependenciesWS.hasDependency()) {
            return monographDependenciesWS;
        }
        return null;
    }
    
    public void setMonographDependenciesWS(MonographDependenciesWS monographDependencies) {
        //
    }
    
    public int compareTo(MonographSourceIngredientWS o) {
        return this.monographSourceIngredient
                .compareTo(o.monographSourceIngredient);
    }

}
