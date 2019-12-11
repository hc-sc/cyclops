package ca.gc.hc.nhpd.dto;

// import ca.gc.hc.nhpd.layout.render.DisplayColumn;
import ca.gc.hc.nhpd.model.PersistentObject;
import ca.gc.hc.nhpd.util.StringComparator;

import java.math.BigDecimal;

import java.util.Comparator;
import java.util.List;


/**
 * The PersistentObjectSearchResult was created to return a generic simplified
 * object for use when returning large result sets. By using this simplified
 * object we are able to return "value objects" or pointers to the original
 * hibernate objects. The intention is not to replace hibernate, but to simplify
 * and accelerate the retrieval of common lists.
 *
 * <p>The implementation requires that the custom SQL code that returns the
 * result set be implemented in the appropriate DAO object. See
 * searchResultSQLStatement in the DAO objects where this process has been
 * implemented.
 *
 * @author  GEBRUNET
 */

public class PersistentObjectSearchResult extends PersistentObject
    implements Comparable {

    private static final String KEY_ID = "id";

    private static final String KEY_OBJECT_CLASS_NAME = "objectName";

    private static final String KEY_SHORT_DESCRIPTION = "shortDescription";

    private String column1;

    private String column2;

    private String column3;

    private String column4;

    private String column5;

    private String column6;

    private String[] columns = new String[10];

    /*
     * Provides the class name of the model object this search result
     * represents. By using the class name and id we can uniquely identify the
     * appropriate model class object and retreive it.
     */
    private String simpleClassName;

    public PersistentObjectSearchResult(Object[] searchResult,
            boolean isFrench) {

        this.id               = ((BigDecimal) searchResult[0]).longValue();
        this.simpleClassName  = (String) searchResult[1];
        this.shortDescription = (String) searchResult[2];

        if (searchResult.length > 3) {
            this.column1 = (String) searchResult[3];
        }

        if (searchResult.length > 4) {
            this.column2 = (String) searchResult[4];
        }

        if (searchResult.length > 5) {
            this.column3 = (String) searchResult[5];
        }

        if (searchResult.length > 6) {
            this.column4 = (String) searchResult[6];
        }
    }

    /*
     * Constructor which creates an PersistentObjectSearchResult instance from a
     * single row of the database search result.
     */
    public PersistentObjectSearchResult(Object[] searchResult,
            String simpleClassName, boolean isFrench) {

        this.id               = ((BigDecimal) searchResult[0]).longValue();
        this.shortDescription = (String) searchResult[1];
        this.simpleClassName  = simpleClassName;
    }

    /*
    public PersistentObjectSearchResult(Object[] searchResult,
            boolean isFrench, List<DisplayColumn> displayColumns,
            String objectName) {

        if ((objectName != null) && !EMPTY_STRING.equals(objectName)) {
            this.simpleClassName = objectName;
        }

        int columnCounter       = 0;
        int searchResultCounter = 0;

        for (DisplayColumn displayColumn : displayColumns) {

            if (displayColumn != null) {


                if (KEY_ID.equals(displayColumn.getColumnName())) {
                    this.id = ((BigDecimal) searchResult[0]).longValue();
                } else if (
                    KEY_OBJECT_CLASS_NAME.equals(displayColumn.getColumnName())
                        && (objectName == null)) {
                    this.simpleClassName = (String) searchResult[1];
                } else if (KEY_SHORT_DESCRIPTION.equals(
                            displayColumn.getColumnName())) {
                    this.shortDescription = (String) searchResult[2];
                }

                if (displayColumn.isVisible()) {

                    Object object = (Object) searchResult[searchResultCounter];

                    try {

                        if (object instanceof String) {
                            columns[columnCounter] = (String) object;
                        } else if (object instanceof BigDecimal) {
                            columns[columnCounter] = ((BigDecimal) object)
                                    .toString();
                        } else {
                            columns[columnCounter] = object.toString();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    columnCounter++;

                }

            }

            searchResultCounter++;

        }

        this.column1 = columns[0];
        this.column2 = columns[1];
        this.column3 = columns[2];
        this.column4 = columns[3];
        this.column5 = columns[4];
        this.column6 = columns[5];

    }
    */

    /*
     * For sorting purpose, we will sort on the short description.
     */
    public int compareTo(PersistentObject obj) {
        return StringComparator.compare(getShortDescription(),
                ((PersistentObject) obj).getShortDescription());
    }

    /*
     * Since results sets are all object specific, we have simplified the
     * equality opperator by looking at the object id.
     */
    public boolean equals(Object object) {
        return this.id == ((PersistentObjectSearchResult) object).id;
    }

    public String getColumn1() {
        return column1;
    }

    public String getColumn2() {
        return column2;
    }

    public String getColumn3() {
        return column3;
    }

    public String getColumn4() {
        return column4;
    }

    public String getColumn5() {
        return column5;
    }

    public String getColumn6() {
        return column6;
    }

    /*
     * Returns the short description implemented in this object.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /*
     * Accessor that retreives the simple class name. This overrides the
     * getSimpleClassName implementation in the PersistentObject, and is
     * normally set by the constructor.
     *
     * @see ca.gc.hc.nhpd.model.PersistentObject#getSimpleClassName()
     */
    public String getSimpleClassName() {
        return simpleClassName;
    }

    /*
     * Setter for the simple class name.
     */
    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    /**
     * An implementation of Comparator that allows these objects to be sorted
     * using their French names.
     */
    public static class ShortDescriptionComparator implements Comparator {

        /**
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         *
         * @param  o1  the first object to be compared.
         * @param  o2  the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {

            if ((o1 == null)
                    || (((PersistentObjectSearchResult) o1)
                        .getShortDescription() == null)) {
                return 1;
            }

            if ((o2 == null)
                    || (((PersistentObjectSearchResult) o2)
                        .getShortDescription() == null)) {
                return -1;
            }

            return ((PersistentObjectSearchResult) o1).getShortDescription()
                                                      .toLowerCase().compareTo(
                                                          ((PersistentObjectSearchResult)
                                                              o2)
                                                                  .getShortDescription()
                            .toLowerCase());
        }
    }

}
