package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/*
 * Introduced to model situations where Ingredients act as sources and have 
 * a dependency on subpopulation.  For example, according to the NHPD Calcium
 * monograph, if an applicant selects one of the Calcium chelates as a source 
 * for Calcium, then the applicant's e-PLA must restrict the choices in the 
 * subpopulations drop-down to only an adult subpopulation and nothing else.
 */
public class MonographSourceIngredient extends DependantObject 
                                       implements MonographSourceMaterial, Comparable {
    
    private Ingredient ingredient;
    private Set<Citation> citations;
 
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    
    public String getSourceMaterialName() {
        return ingredient.getAuthorizedName();
    }
    
    public String getSourceMaterialNameE() {
        return ingredient.getAuthorizedName();
    }
    
    //TODO - need to drill down into subclasses to get E/F names
    public String getSourceMaterialNameF() {
        return ingredient.getAuthorizedName();
    }
    
    public int compareTo(Object obj) throws ClassCastException {
		 if (obj == null) return -1;
	     
	     return StringComparator.compare
	     	(getSourceMaterialName(),((MonographSourceMaterial) obj).getSourceMaterialName());
	     
	}
    
    public Set<Citation> getCitations() {
    	return citations;
    }

    /**
     * Gets the citations associated with this Object ordered by the citation
     * date.
     * @return the citations Set for this Object ordered by date.
     */
    public Set<Citation> getCitationsByDate() {
        Comparator<Citation> citationComparator = new Citation.CitationComparator();
        Set<Citation> citationsByDate = new TreeSet<Citation>(citationComparator);
        citationsByDate.addAll(getCitations());
        return citationsByDate;
    }

	public void setCitations(Set<Citation> citations) {
		this.citations = citations;
	}
    
    
}
