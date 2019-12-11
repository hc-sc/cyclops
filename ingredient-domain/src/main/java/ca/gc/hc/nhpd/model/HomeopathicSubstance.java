package ca.gc.hc.nhpd.model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.gc.hc.nhpd.util.StringUtils;


/**
 * An object that represents a homeopathic ingredient.  These ingredients are
 * created as place holders when the homeopathic role can not be associted with
 * another existing ingredient.
 *
 * <p>Business rules:  Homeopathic ingredients can only be given homeopathic
 * roles.  If we try to give them another role, then we have to define them as
 * another class of ingredients.  (Defined Organism Substance or Chemical
 * Substance.)
 */
public class HomeopathicSubstance extends Ingredient {

	private static final Log LOG = LogFactory.getLog(HomeopathicSubstance.class);
	
    public static final String TYPE_E        = "Homeopathic Substance";
    public static final String TYPE_F        = "Substance homéopatique";
    public static final String HMPN_CATEGORY = "HMN";

    private String                     authorizedNameE;
    private String                     authorizedNameF;
    private Set<HomeopathicGenericText> homeopathicCommonNames;
    private Set<HomeopathicGenericText> homeopathicProperNames;
    private Reference                  reference;

    /* massages the proper names for display purposes.  We reuse the Synonym 
     * class here as a container of id and name attributes.  
     */ 
    public Set<Synonym> getDisplayProperNames() {
        
        Set<Synonym> displayProperNames = new TreeSet<Synonym>();
        Synonym retSynonym = null;
        String retName = null;
        try {
            if (homeopathicProperNames != null) {
                Iterator iter = homeopathicProperNames.iterator();
                while (iter.hasNext()) {
                    HomeopathicGenericText hpn = (HomeopathicGenericText) iter.next();
                    String name = hpn.getText();
                    
                    if (name == null) { 
                        LOG.warn("null homeopathic proper name!");
                    }
                    else {
                        retSynonym = new Synonym();
                        retSynonym.setId(hpn.getId());
                        retName = hpn.getText();
                        //gets rid of unbreakable sequences
                        if (retName.length() > 40) {
                            retName = StringUtils.breakText(retName,40);
                        }
                        retSynonym.setNameF(retName);
                        retSynonym.setNameE(retName);
                        displayProperNames.add(retSynonym);
                    }
                    
                }
            }
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
        
        return displayProperNames;
    
    }

    
    /**
     * massages the common names for display purposes.  We reuse the Synonym 
     * class here as a container of id and name attributes.  
     */ 
    public Set<Synonym> getDisplayCommonNames() {
        
        TreeSet<Synonym> displayCommonNames = new TreeSet<Synonym>();
        Synonym retSynonym = null;
        String retName = null;
        try {
            if (homeopathicCommonNames != null) {
                Iterator<HomeopathicGenericText> iter = homeopathicCommonNames.iterator();
                while (iter.hasNext()) {
                    HomeopathicGenericText hpn = (HomeopathicGenericText) iter.next();
                    String name = hpn.getText();
                    
                    if (name == null) { 
                        LOG.warn("null homeopathic common name!");
                    }
                    else {
                        retSynonym = new Synonym();
                        retSynonym.setId(hpn.getId());
                        retName = hpn.getText();
                        //gets rid of unbreakable sequences
                        if (retName.length() > 40) {
                            retName = StringUtils.breakText(retName,40);
                        }
                        retSynonym.setNameF(retName);
                        retSynonym.setNameE(retName);
                        displayCommonNames.add(retSynonym);
                    }
                    
                }
            }
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
        
        return displayCommonNames;
    
    }
    
    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getAuthorizedName() {

        if (isLanguageFrench()) {
            return getAuthorizedNameF();
        }

        return getAuthorizedNameE();
    }

    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getAuthorizedNameE() {
        return authorizedNameE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getAuthorizedNameF() {
        return authorizedNameF;
    }

    /**
     * Gets the display name in the language appropriate for the Locale.
     *
     * @return  the locale-specific display name.
     */
    public String getDisplayableString() {
        return getAuthorizedName();
    }

    /**
     * Getter that returns the set of homeopathic Common Names associated to
     * this homeopathic substance.
     *
     * @return  a set of homeopathic common names
     */
    public Set<HomeopathicGenericText> getHomeopathicCommonNames() {
        return homeopathicCommonNames;
    }
    
    /**
     * Getter that returns the set of homeopathic Proper Names associated to
     * this homeopathic substance.
     *
     * @return  a set of homeopathic proper names
     */
    public Set<HomeopathicGenericText> getHomeopathicProperNames() {
        return homeopathicProperNames;
    }

    /**
     */
    public Reference getReference() {
        return reference;
    }

    /**
     * Returns the Source Material name.  Implements SourceMaterial method
     *
     * @return  the source material name
     */
    public String getSourceMaterialName() {
        return getAuthorizedName();
    }


    /**
     * Specific implementation of the getProperName that returns the following
     * information: 1. Proper Name if available.  (Implemented by Ingredients)
     * 2. Name in the case of a AHN 3. Name of the organism in the case of all
     * other.
     */
    /*
     * public Set getProperNames() {
     *
     *  // Try returning the NHPDProperNames.
     *  if ( super.getProperNames() != null && super.getProperNames().size() !=
     * 0 ) {
     *          return super.getProperNames();
     *  }
     *
     *  Set properNames = new TreeSet();
     *
     *  if ( super.getCategory().getCode().equals(AHN_CATEGORY)) {
     *          // If an AHN, then return the approved name.
     *          properNames.add( getAuthorizedName() );
     *  } else {
     *          // For all else, return the organism names.
     *          if ((getOrganismGroup() != null) &&
     *                  (getOrganismGroup().getIncludedOrganisms() != null)) {
     *                  Iterator i =
     * getOrganismGroup().getIncludedOrganisms().iterator();
     *                  while ( i.hasNext() ) {
     *                          Organism o = (Organism) i.next();
     *                          properNames.add( o.getName() );
     *                  }
     *          }
     *  }
     *
     *  return properNames;
     *
     * }
     */

    /**
     * Gets the type in the language appropriate for the Locale.
     *
     * @return  the locale-specific type.
     */
    public String getType() {

        if (isLanguageFrench()) {
            return TYPE_F;
        }

        return TYPE_E;
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", nameE: ");
        buffer.append(getAuthorizedNameE());
        buffer.append(", nameF: ");
        buffer.append(getAuthorizedNameF());
        buffer.append(", type: ");
        buffer.append(getType());
        buffer.append(", homeopathicCommonNames: ");
        if (getHomeopathicCommonNames() != null) {
        	int counter = 0;
        	Iterator<HomeopathicGenericText> i = getHomeopathicCommonNames().iterator();
        	while (i.hasNext()) {
                HomeopathicGenericText homeopathicCommonName = (HomeopathicGenericText) i.next();
        		if (counter != 0) {
        			buffer.append(", ");
        		}
        		buffer.append(homeopathicCommonName.getText());
        		counter++;
        	}
        } else {
        	buffer.append("null");
        }
        buffer.append(", homeopathicProperNames: ");
        if (getHomeopathicProperNames() != null) {
        	int counter = 0;
        	Iterator<HomeopathicGenericText> i = getHomeopathicProperNames().iterator();
        	while (i.hasNext()) {
                HomeopathicGenericText homeopathicProperName = (HomeopathicGenericText) i.next();
        		if (counter != 0) {
        			buffer.append(", ");
        		}
        		buffer.append(homeopathicProperName.getText());
        		counter++;
        	}
        } else {
        	buffer.append("null");
        }
        buffer.append(", reference: ");
        if (getReference() != null) {
        	buffer.append(getReference().toString());
        } else {
        	buffer.append("null");
        }

        return buffer.toString();
    }

    /**
     * Sets the name in English.
     *
     * @param  newVal  the name in English
     *
     * @see    #getNameE()
     */
    public void setAuthorizedNameE(String newVal) {
        authorizedNameE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setAuthorizedNameF(String newVal) {
        authorizedNameF = newVal;
    }

    /**
     * Getter that returns the set of homeopathic Common Names associated to
     * this homeopathic substance.
     *
     * @return  a set of homeopathic common names
     */
    public void setHomeopathicCommonNames(
            Set<HomeopathicGenericText> homeopathicCommonNames) {
        this.homeopathicCommonNames = homeopathicCommonNames;
    }

    /**
     * Getter that returns the set of homeopathic Proper Names associated to
     * this homeopathic substance.
     *
     * @return  a set of homeopathic proper names
     */
    public void setHomeopathicProperNames(
            Set<HomeopathicGenericText> homeopathicProperNames) {
        this.homeopathicProperNames = homeopathicProperNames;
    }

    /**
     * @param  newVal
     */
    public void setReference(Reference newVal) {
        reference = newVal;
    }
}
