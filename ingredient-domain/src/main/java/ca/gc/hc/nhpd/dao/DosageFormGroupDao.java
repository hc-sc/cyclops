package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.DosageFormGroup;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;

/*******************************************************************************
 * An object used to retrieve instances of DosageFormGroup from persistent store.
 * @see ca.gc.hc.nhpd.model.DosageFormGroup
 */
public class DosageFormGroupDao extends AbstractDao {
	
	public static boolean LOAD_INTO_MEMORY = true;
		
    /***************************************************************************
     * Generic Constructor.
     */
    public DosageFormGroupDao() {
        super(DosageFormGroup.class);
    }

	/***************************************************************************
	 * Flag determining if the list of items for this DAO object should be
	 * loaded into the ApplicationGlobal's resident memory.  This is used for
	 * system pick lists, and other code list objects.  This method returns the
	 * static value stored in the LOAD_INTO_MEMORY that should be located at
	 * the top of the individual DAO classes.  By default, the AbstractDAO
	 * ensures that none of the individual DAO classes are loaded unless 
	 * specifically set.
	 * @return LOAD_INTO_MEMORY static variable.
	 */
	public boolean loadIntoMemory() {
		return LOAD_INTO_MEMORY;
	}
    
    /***************************************************************************
     * Gets a collection of all the DosageFormGroups in the persistent
     * store. Note that these are sorted by their English names.
     * @return all instances of the DosageForms that are in the
     *         persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("nameE"));
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the DosageFormGroup with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the DosageForm in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the DosageForm, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
    }
    
    /**
     * Getter that returns the SQL discriminator used by this object.  This method
     * exposes the discriminator for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getDiscriminator() {
        return null;
    }

    /**
     * Getter that returns the SQL English column used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getEnglishColumnName() {
        return null;
    }

    /**
     * Getter that returns the SQL French column used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getFrenchColumnName() {
        return null;
    }

    /**
     * Getter that returns the SQL id column used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getIdColumnName() {
        return null;
    }
    
    /**
     * Getter that returns the SQL table name used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getTableName() {
        return null;
    }

}