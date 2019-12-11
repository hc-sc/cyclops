package ca.gc.hc.nhpd.model;

import java.util.Date;

/*******************************************************************************
 * An interface required by Hibernate so that it can support subclassing -
 * Hibernate requires the base to be an interface or abstract class, since
 * instances cannot be created.
 * @see GenericText
 */
public interface Text {
    public Date getCreationDate();
    public void setCreationDate(Date aDate);

    public Long getId();
    public void setId(Long aLong);

    public UserAccount getLastUpdateAccount();
    public void setLastUpdateAccount(UserAccount aUserAccount);

    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date aDate);

    public String getTextE();
    public void setTextE( String value );

    public String getTextF();
    public void setTextF( String value );
}
