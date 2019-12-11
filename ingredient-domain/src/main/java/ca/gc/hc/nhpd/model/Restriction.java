package ca.gc.hc.nhpd.model;

import java.util.Date;
import java.util.Set;

/*******************************************************************************
 * An interface required by Hibernate so that it can support subclassing -
 * Hibernate requires the base to be an interface or abstract class, since
 * instances cannot be created.
 * @see BasicRestriction
 */
public interface Restriction {
    public Date getCreationDate();
    public void setCreationDate(Date aDate);

    public Long getId();
    public void setId(Long aLong);

    public UserAccount getLastUpdateAccount();
    public void setLastUpdateAccount(UserAccount aUserAccount);

    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date aDate);

    public Set<Citation> getCitations();
    public void setCitations(Set<Citation> value );
    
    public String getDetailE();
    public void setDetailE( String value );
    
    public void setDetailF( String value );
    public String getDetailF();
       
    public Double getLowerLimit();
    public void setLowerLimit(Double newVal);

    public Reference getReference();
    public void setReference(Reference newVal);

    public RouteOfAdministration getRouteOfAdministration();
    public void setRouteOfAdministration(RouteOfAdministration newVal);

    public RestrictionType getType();
    public void setType(RestrictionType newVal);

    public Units getUnits();
    public void setUnits(Units newVal);

    public Double getUpperLimit();
    public void setUpperLimit(Double newVal);
}