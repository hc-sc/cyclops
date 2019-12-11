package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TextMonographSourceMaterial extends PersistentObject 
	implements MonographSourceMaterial, Comparable {
    
    private QualifiedAssertion qualifiedAssertion;
     
    public TextMonographSourceMaterial( ) {
    }
	
    public TextMonographSourceMaterial(QualifiedAssertion qualifiedAssertion) {
    	this.qualifiedAssertion = qualifiedAssertion;
    }

    public QualifiedAssertion getQualifiedAssertion() {
        return qualifiedAssertion;
    }

    public void setQualifiedAssertion(QualifiedAssertion qualifiedAssertion) {
        this.qualifiedAssertion = qualifiedAssertion;
    }
    
    public String getSourceMaterialName() {
    	if (qualifiedAssertion == null)
			return null;
        return qualifiedAssertion.getText();
    }
   
    public String getSourceMaterialNameE() {
		if (qualifiedAssertion == null)
			return null;
    	return qualifiedAssertion.getTextE();
	}

	public String getSourceMaterialNameF() {
		if (qualifiedAssertion == null)
			return null;
		return qualifiedAssertion.getTextF();
	}
	
    /*
     * returns the Citations contained by the wrapped QualifiedAssertion  
     */
    public Set<Citation> getCitations() {
        if (qualifiedAssertion == null)
            return null;
        
        return qualifiedAssertion.getCitations();  
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
    
    /***************************************************************************
     * 
     */
    public int compareTo(Object o) throws ClassCastException {
        if (o == null) return -1; 
        
        return StringComparator.compare(getSourceMaterialName(),
                                        ((MonographSourceMaterial)o).getSourceMaterialName());
    }

	public void setSourceMaterialNameE(String sourceMaterialNameE) {
		if (qualifiedAssertion != null)
			qualifiedAssertion.setTextE(sourceMaterialNameE);
	}

	public void setSourceMaterialNameF(String sourceMaterialNameF) {
		if (qualifiedAssertion != null)
			qualifiedAssertion.setTextF(sourceMaterialNameF);
	}

	/*
	public void setCitations(Set<Citation> citations) {
		if (qualifiedAssertion != null)
			if (isLanguageFrench()) {
				qualifiedAssertion.setCitationsF(citations);
			}
			else {
				qualifiedAssertion.setCitationsE(citations); 
	}
	}
	*/
    
}
