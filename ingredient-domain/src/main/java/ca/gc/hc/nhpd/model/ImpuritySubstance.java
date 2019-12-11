package ca.gc.hc.nhpd.model;

import java.util.Date;
import java.util.Set;

/*******************************************************************************
 * An Interface that represents a substance that only exists in the system as an
 * impurity. This only exists for Hibernate binding purposes - Hibernate does
 * not support a subclassing hierarchy where the top class can be instantiated -
 * it requires the top to be abstract or an interface.
 */
public interface ImpuritySubstance {
    public String getCommentE();
    public void setCommentE(String newVal);

    public String getCommentF();
    public void setCommentF(String newVal);

    public Date getCreationDate();
    public void setCreationDate(Date aDate);

    public String getDescriptionE();
    public void setDescriptionE(String newVal);

    public String getDescriptionF();
    public void setDescriptionF(String newVal);

    public Long getId();
    public void setId(Long aLong);

    public UserAccount getLastUpdateAccount();
    public void setLastUpdateAccount(UserAccount aUserAccount);

    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date aDate);

    public String getAuthorizedNameE();
    public void setAuthorizedNameE(String newVal);

    public String getAuthorizedNameF();
    public void setAuthorizedNameF(String newVal);

    public Set<TestMethod> getPurityTestMethods();
    public void setPurityTestMethods(Set<TestMethod> newVal);
    
}
