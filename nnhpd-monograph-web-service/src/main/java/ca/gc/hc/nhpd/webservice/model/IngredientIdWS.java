/**

 */

package ca.gc.hc.nhpd.webservice.model;

public class IngredientIdWS {
    private Float version;

    private String ingredientName;

    private Long id;

    public IngredientIdWS() {
    }
    
    public IngredientIdWS(Long id, String ingredientName) {
        this.id = id;
        this.ingredientName = ingredientName;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
