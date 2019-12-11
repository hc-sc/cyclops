package ca.gc.hc.nhpd.dto;

import ca.gc.hc.nhpd.model.MonographType;
import ca.gc.hc.nhpd.util.LocaleSensitiveString;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
* Lighweight DTO (Data Transfer Object) which encapsulates attributes of 
* a Monograph search result.  Used by both IDB web app and MWS (web service).
* Enables performant queries against the denormalized Monograph_Synonyms_MV
* Materialized View without instantiating a large Monograph object graph.
* 
* @author MRABE  
*/
public class MonographSearchResult implements Comparable {

	private static final String synonymSeparator = "<br/>";
    private static final String ONE = "1";
    private static final String GENERATED_CLASS_NAME = "GeneratedMonograph";
	
	private long 			monographId;
	private String 			monographName;
	private Date 			monographDate;
	private String 	    	primaryIngredientName;
	private Set<MonographType> monographTypes;
	private String          monographClassName;
	private String 			url;
	private List<String>	synonyms;
	private boolean         generated = false;
    private long            type_a;
    private long            type_c;
    private long            type_p;
    private long            type_t;
    private long            type_i;
    private long            pdfAttachmentId;
    private String          pdfAttachmentFileName;
    private String          pdfAttachmentDescription;
    private Integer			pdfAttachmentSize;
    
	public MonographSearchResult( ) {
		//default constructor
	}
	
	/* 
	 * constructor which creates a MonographSearchResult instance from
	 * a single row of the database search result
	 */
	public MonographSearchResult(Object[] searchResult ) {
	    monographId              = ((BigDecimal)searchResult[0]).longValue();
        monographName            = (String)searchResult[1];
        url                      = (String)searchResult[2];
        synonyms                 = parseSynonyms((String)searchResult[3]);
        monographClassName       = (String)searchResult[4];
        pdfAttachmentId          = bigDecimalConversion(searchResult[5]);
        pdfAttachmentFileName    = (String) searchResult[6];
        pdfAttachmentDescription = (String) searchResult[7];
        if (searchResult[8] != null) {
        	pdfAttachmentSize		 = Integer.parseInt(searchResult[8].toString());
        } else {
        	pdfAttachmentSize = 0;
        }
        type_a                   = bigDecimalConversion(searchResult[9]);
        type_c                   = bigDecimalConversion(searchResult[10]);
        type_p                   = bigDecimalConversion(searchResult[11]);
        type_t                   = bigDecimalConversion(searchResult[12]);
        type_i                   = bigDecimalConversion(searchResult[13]);
        if (GENERATED_CLASS_NAME.equals(monographClassName)) {
            generated = true;
        }
        setMonographTypes();
	}

    private long bigDecimalConversion(Object object) {
        if (object == null) {
            return 0;
        }
        return ((BigDecimal)object).longValue();
    }
    
    /* 
     * Converts the type numbers into appropriate Monograph Type objects.
     */
    private void setMonographTypes() {
        if (monographTypes == null) {
            monographTypes = new TreeSet<MonographType>();
        }
        if (type_a==1) monographTypes.add(MonographType.MONOGRAPH_TYPE_ABLS);
        if (type_c==1) monographTypes.add(MonographType.MONOGRAPH_TYPE_COMPENDIAL);
        if (type_p==1) monographTypes.add(MonographType.MONOGRAPH_TYPE_PRODUCT);
        if (type_t==1) monographTypes.add(MonographType.MONOGRAPH_TYPE_TPDCAT4);
        if (type_i==1) monographTypes.add(MonographType.MONOGRAPH_TYPE_ABLS_INTERNAL);
    }
    
	/*
	 * Extracts valid synonyms.  We use the LocaleSensitiveString to provide
	 * proper sort order, which comprises case insensitive and Locale aware sorting.
     * TODO - this method could be moved to a new superclass "SearchResult"
	 */
    private List<String> parseSynonyms(String synonyms)
	{
		
		TreeSet<LocaleSensitiveString> sortedSynonyms = new TreeSet<LocaleSensitiveString>();
		ArrayList<String> retSynonyms = new ArrayList<String>();
		String[] tempSynonyms = null;
		
		if (synonyms != null && synonyms.trim().length() > 0)
		{	
			tempSynonyms = synonyms.split(synonymSeparator);
			for (int x=0; x< tempSynonyms.length; x++)
			{	
				//NA's have now been filtered out of the MV, but we'll 
				//keep this around for a bit just in case... 
				if (!tempSynonyms[x].equalsIgnoreCase("NA"))
				{	
					sortedSynonyms.add(new LocaleSensitiveString(tempSynonyms[x]));
				}
			}
		}
		
		//translate into String List,which maintains order.   
		Iterator iter = sortedSynonyms.iterator();
		LocaleSensitiveString localeString = null;
		while (iter.hasNext()) {
			localeString = (LocaleSensitiveString)iter.next(); 
			retSynonyms.add(localeString.getString()); 
		}
		
		return retSynonyms;
	
	}	
	
    public Set<MonographType> getMonographTypes() {
    	return monographTypes;
    }
    
    public void setMonographTypes(Set<MonographType> monographTypes) {
    	this.monographTypes = monographTypes;
    }
    
    public void addMonographType(MonographType monographType) {
        monographTypes.add(monographType);
    }
    
    public String getMonographClassName() {
    	return monographClassName;
    }
    
    public void setMonographClassName(String monographClassName) {
    	this.monographClassName = monographClassName;
    }
    
	public Date getMonographDate() {
		return monographDate;
	}

	public void setMonographDate(Date monographDate) {
		this.monographDate = monographDate;
	}

	public long getMonographId() {
		return monographId;
	}

	public void setMonographId(long monographId) {
		this.monographId = monographId;
	}

    public String getPdfAttachmentFileName() {
        return pdfAttachmentFileName;
    }
    
    public void setPdfAttachmentFileName(String pdfAttachmentFileName) {
        this.pdfAttachmentFileName = pdfAttachmentFileName;
    }
    
    public String getPdfAttachmentDescription() {
        return pdfAttachmentDescription;
    }
    
    public void setPdfAttachmentDescripiton(String pdfAttachmentDescription) {
        this.pdfAttachmentDescription = pdfAttachmentDescription;
    }
    
    public Integer getPdfAttachmentSizeInKilobytes() {
    	return pdfAttachmentSize / 1024;
    }
    public Integer getPdfAttachmentSize() {
        return pdfAttachmentSize;
    }
    
    public void setPdfAttachmentSize(Integer pdfAttachmentSize) {
        this.pdfAttachmentSize = pdfAttachmentSize;
    }
    
	public String getMonographName() {
		return monographName;
	}

	public void setMonographName(String monographName) {
		this.monographName = monographName;
	}

    public long getPdfAttachmentId() {
        return pdfAttachmentId;
    }
    
    public void setPdfAttachmentId(long pdfAttachmentId) {
        this.pdfAttachmentId = pdfAttachmentId;
    }

	public String getPrimaryIngredientName() {
		return primaryIngredientName;
	}
	
	public void setPrimaryIngredientName(String primaryIngredientName) {
		this.primaryIngredientName = primaryIngredientName;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int compareTo(Object obj) {
		if (obj == null) return -1;
		MonographSearchResult msr = (MonographSearchResult)obj;
		
		String thisName = monographName;
		String passedName = msr.getMonographName(); 
	   /*
		* special handling for 5-HTP, where we want to sort alphabetically,
		* ignoring the leading numeric and dash.  
		*/ 
		if (thisName != null && Character.isDigit(thisName.charAt(0))) {
			thisName = StringUtils.removeNumbersAndDashes(thisName);
		}
		if (passedName != null && Character.isDigit(passedName.charAt(0))) {
			passedName = StringUtils.removeNumbersAndDashes(passedName);
		}
		
		return StringComparator.compare(thisName,passedName);
		
	}
    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  DOCUMENT ME!
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("MonographId: ");
        buffer.append(getMonographId());
        buffer.append(", monographName: ");
        buffer.append(getMonographName());
        buffer.append(", monographDate: ");
        buffer.append(getMonographDate());
        buffer.append(", primaryIngredientName: ");
        buffer.append(getPrimaryIngredientName());
        buffer.append(", monographClassName: ");
        buffer.append(getMonographClassName());
        buffer.append(", monographTypes: ");
        if (getMonographTypes() == null) {
            buffer.append("null");
        } else {
            for (MonographType monographType : getMonographTypes()) {
                buffer.append(monographType.toString());
            }
        }

        return buffer.toString();
    }
    
	public boolean isGenerated() {
        return generated;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public boolean isAbls() {
		if (getMonographTypes() != null 
                && getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS)) { 
			return true;
		}
		return false;
	}

	public boolean isProductAbls() {
		if (getMonographTypes() != null 
                && getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS)
                && getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_PRODUCT)) { 
			return true;
		}
		return false;
	}
	
    public boolean isAblsInternal() {
        if (getMonographTypes() != null 
                && getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS_INTERNAL)) { 
            return true;
        }
        return false;
    }
    
	public void setAbls(boolean abls) {
		//no impl
	}
    
    public boolean isProduct() {
        if (getMonographTypes() != null
                && getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_CODE_PRODUCT)
                && !getMonographTypes().contains(MonographType.MONOGRAPH_TYPE_ABLS)) {
            return true;
        }
        return false;
    }

    public boolean isSingle() {
        if (isAbls() || isProduct()) {
            return false;
        }
        return true;
    }
    
   
}
