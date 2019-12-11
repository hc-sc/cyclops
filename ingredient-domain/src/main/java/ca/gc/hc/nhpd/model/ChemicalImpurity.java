package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An interface that defines the methods required by chemical substances that
 * are considered impurities.
 */
public interface ChemicalImpurity {
    /***************************************************************************
     */
	public abstract String getComment();

    /***************************************************************************
     */
	public abstract String getDescription();

    /***************************************************************************
     */
	public abstract String getAuthorizedName();

    /***************************************************************************
     */
	public abstract Set<TestMethod> getPurityTestMethods();

    /***************************************************************************
     */
	public abstract BasicRestriction getToxicityRestriction();
}