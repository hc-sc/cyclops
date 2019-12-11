package ca.gc.hc.nhpd.model;
/*
* Business Identifier for Ingredient objects.   
* 
* Author: M. Rabe
* Date: May 2007
*
*/
public class IngredientId implements java.io.Serializable {

	private String ingredientName;  //approved name
	private Float  version;
	
   /* 
	* Note: this is the Hibernate id.  This attribute has been added to 
	* this class to simplify the Ingredient web service retrieval of
	* a single Ingredient.  If/when we transition to the "Business Id"
	* concept, this attribute can be removed from here in favor of
	* another attribute that uniquely identifies the business entity 
	* (e.g.  GUID)  Be aware that there are Ingredient types that 
	* do not have names. 
	*/
	private Long   id;
	
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public Float getVersion() {
		return version;
	}
	public void setVersion(Float version) {
		this.version = version;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}