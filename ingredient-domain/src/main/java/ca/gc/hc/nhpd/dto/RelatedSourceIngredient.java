package ca.gc.hc.nhpd.dto;
import ca.gc.hc.nhpd.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;
/**
 * DTO which models a flattened, performant RelatedSourceIngredient.
 * Used in conjunction with RelatedSourceIngredients_mv materialized view
 * to increase response time for the Ingredient Web Service. 
 * 
 * @author MRABE
 */

public class RelatedSourceIngredient {
	
	private boolean french;
	private Long ingredientId;
	//subingredient parent id 
	private Long relatedIngredientId;
	private String ingredientNameEnglish;
	private String ingredientNameFrench;
	private List<String> synonymsEnglish;
	private List<String> synonymsFrench;
	
	private static final String separator = "<br/>";
	
	
	public RelatedSourceIngredient() {
	}
            
   /* 
    * constructor which creates a RelatedSourceIngredient instance from
    * a single row of the database search result
    */
    public RelatedSourceIngredient(Object[] searchResult,
    							   boolean isFrench) {
                                  
        ingredientId = ((BigDecimal)searchResult[0]).longValue();
        relatedIngredientId = ((BigDecimal)searchResult[1]).longValue();
        ingredientNameEnglish = (String)searchResult[2];
        ingredientNameFrench = (String)searchResult[3];
        synonymsEnglish = StringUtils.extractDelimitedNames
        	((String) searchResult[4],separator,ingredientNameEnglish);
        synonymsFrench = StringUtils.extractDelimitedNames
        	((String) searchResult[5],separator,ingredientNameFrench);
        french = isFrench;
    }
    
	

	public Long getRelatedIngredientId() {
		return relatedIngredientId;
	}

	public void setRelatedIngredientId(Long relatedIngredientId) {
		this.relatedIngredientId = relatedIngredientId;
	}

	public String getIngredientNameEnglish() {
		return ingredientNameEnglish;
	}

	public void setIngredientNameEnglish(String ingredientNameEnglish) {
		this.ingredientNameEnglish = ingredientNameEnglish;
	}

	public String getIngredientNameFrench() {
		return ingredientNameFrench;
	}

	public void setIngredientNameFrench(String ingredientNameFrench) {
		this.ingredientNameFrench = ingredientNameFrench;
	}

	public List<String> getSynonymsEnglish() {
		return synonymsEnglish;
	}

	public void setSynonymsEnglish(List<String> synonymsEnglish) {
		this.synonymsEnglish = synonymsEnglish;
	}

	public List<String> getSynonymsFrench() {
		return synonymsFrench;
	}

	public void setSynonymsFrench(List<String> synonymsFrench) {
		this.synonymsFrench = synonymsFrench;
	}

	public Long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}
	
	public boolean isFrench() {
		return french;
	}

	public void setFrench(boolean french) {
		this.french = french;
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RelatedSourceIngredient [french=");
        builder.append(french);
        builder.append(", ");
        if (ingredientId != null) {
            builder.append("ingredientId=");
            builder.append(ingredientId);
            builder.append(", ");
        }
        if (relatedIngredientId != null) {
            builder.append("relatedIngredientId=");
            builder.append(relatedIngredientId);
            builder.append(", ");
        }
        if (ingredientNameEnglish != null) {
            builder.append("ingredientNameEnglish=");
            builder.append(ingredientNameEnglish);
            builder.append(", ");
        }
        if (ingredientNameFrench != null) {
            builder.append("ingredientNameFrench=");
            builder.append(ingredientNameFrench);
            builder.append(", ");
        }
        if (synonymsEnglish != null) {
            builder.append("synonymsEnglish=");
            builder.append(synonymsEnglish);
            builder.append(", ");
        }
        if (synonymsFrench != null) {
            builder.append("synonymsFrench=");
            builder.append(synonymsFrench);
        }
        builder.append("]");
        return builder.toString();
    }
	
}
