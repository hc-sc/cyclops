package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.ProvinceState;
import ca.gc.hc.nhpd.util.StringComparator;

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
public class BilingualProvinceStateWS implements Comparable<BilingualProvinceStateWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private ProvinceState provinceState;
    
    // ~ Constructors
    // -----------------------------------------------------------

    public BilingualProvinceStateWS() {}
    
    /*
     * NB. This constructor initializes the wrapped objects. We can do this once
     * at construction time, since the web service is a read only service.
     */
    public BilingualProvinceStateWS(ProvinceState provinceState) {
        if (provinceState == null) {
            throw new IllegalArgumentException("provinceStcountry is null!");
        }
        this.provinceState = provinceState;
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    public String getCode() {
        return provinceState.getCode();
    }
    
    public void setCode(String code) {
        // Not Implemented.
    }
    
    public String getName() {
        return provinceState.getName();
    }
    
    public void setName(String name) {
        // Not Implemented.
    }
    
    public String getNameE() {
        return provinceState.getNameE();
    }
    
    public void setNameE(String nameE) {
        // Not Implemented.
    }

    public String getNameF() {
        return provinceState.getNameF();
    }
    
    public void setNameF(String nameF) {
        // Not Implemented.
    }

    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(BilingualProvinceStateWS o) throws ClassCastException {
        return StringComparator.compare(provinceState.getCode(),
                ((BilingualProvinceStateWS) o).getCode());

    }
}