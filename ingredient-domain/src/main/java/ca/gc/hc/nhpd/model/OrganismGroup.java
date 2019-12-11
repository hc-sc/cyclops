package ca.gc.hc.nhpd.model;

import java.util.Date;
import java.util.Set;

/*******************************************************************************
 * An Interface that represents an organism group. This only exists for Hibernate
 * binding purposes - Hibernate does not support a subclassing hierarchy where
 * the top class can be instantiated - it requires the top to be abstract or an
 * interface.
 */
public interface OrganismGroup {
    
    public static String SIMPLEORGANISMGROUP = "SimpleOrganismGroup";
    public static String NAMEDORGANISMGROUP = "NamedOrganismGroup";
    
       public Long getId();
       public void setId(Long aLong);

       public UserAccount getLastUpdateAccount();
       public void setLastUpdateAccount(UserAccount aUserAccount);

       public Date getLastUpdateDate();
       public void setLastUpdateDate(Date aDate);
        
       public Date getCreationDate();
       public void setCreationDate(Date aDate);

   /***************************************************************************
     * Gets the Organisms that are included in this OrganismGroup.
     * @see #setIncludedOrganisms()
     */
    public Set<Organism> getIncludedOrganisms();

   /***************************************************************************
     * Sets the Organisms that are included in this OrganismGroup.
     * @see #getIncludedOrganisms()
     * @param newVal
     */
    public void setIncludedOrganisms(Set<Organism> newVal);
    
    /***************************************************************************
     * Retrieves a Set of included Organisms that have distinct Citations
     * @see #getIncludedOrganisms()
     * @param newVal
     */
    public Set<Organism> getDistinctCitationOrganisms();
    
    
}
