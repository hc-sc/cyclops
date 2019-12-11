package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.ProvinceState;
import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An abstract class intended to be extended by different types of bilingual
 * ingredients.
 * 
 * Note: Following attributes are the WS equivalents of the Model objects. WS
 * equivalents are provided to: a) limit the number of attributes exposed by the
 * web service b) hide attributes that are not supported by JAX-WS (e.g.
 * java.util.Locale is not supported because it does not have a no-arg
 * constructor.
 * 
 * Author: Germain Brunet Date: 2009-11-17
 */
public class BilingualCountryWS implements Comparable<BilingualCountryWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private Country country;
    
    // ~ Constructors
    // -----------------------------------------------------------
    
    public BilingualCountryWS() {}
    
    /*
     * NB. This constructor initializes the wrapped objects. We can do this once
     * at construction time, since the web service is a read only service.
     */
    public BilingualCountryWS(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("country is null!");
        }
        this.country = country;
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    public String getCode() {
        return country.getCode();
    }
    
    public void setCode(String code) {
        // Not Implemented.
    }
    
    public String getName() {
        return country.getName();
    }
    
    public void setName(String name) {
        // Not Implemented.
    }
    
    public String getNameE() {
        return country.getNameE();
    }
    
    public void setNameE(String nameE) {
        // Not Implemented.
    }

    public String getNameF() {
        return country.getNameF();
    }
    
    public void setNameF(String nameF) {
        // Not Implemented.
    }
    
    public Set<BilingualProvinceStateWS> getProvinceStateWS() {
        TreeSet<BilingualProvinceStateWS> provincesStates = new TreeSet<BilingualProvinceStateWS>();
        if (country != null &&
                country.getProvincesStates() != null) {
            for (ProvinceState provinceState : country.getProvincesStates()) {
                provincesStates.add(new BilingualProvinceStateWS(provinceState));
            }
        }
        // Prevent the empty tag from being displayed.
        if (provincesStates.size() == 0) {
            return null;
        }
        return provincesStates;
    }
    
    public void setProvinceStateWS(Set<BilingualProvinceStateWS> bilingualProvinceStateWS) {
        // Not Imlemented.
    }
    
    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(BilingualCountryWS o) throws ClassCastException {
        return StringComparator.compare(country.getCode(),
                ((BilingualCountryWS) o).getCode());
    }

}