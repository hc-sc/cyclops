package ca.gc.hc.nhpd.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

/*******************************************************************************
 * A List that supports "paging" - cutting the overall (master) List into
 * pre-sized sub-Lists (pages) with the "current" one being used for display.
 * This also provides the supporting methods to allow the user to navigate
 * through the pages.
 * Note that this simply wraps the master List that it is passed - it does not
 * unload or otherwise process its contents. 
 */
public class PagedList implements List {
    private int linkRange = 20;
    private int linkRangeMidpoint = 10;
    private List masterList = new ArrayList();
    private int pageIndex = 1;
    private int pageSize = 20;
    private boolean wrap = true;
    
    /***************************************************************************
     * Generic Constructor.
     */
    public PagedList() {
    }

    /***************************************************************************
     * Constructs a PagedList containing the elements of the specified
     * collection, in the order they are returned by the collection's iterator.
     */
    public PagedList(List aList) {
        setMasterList(aList);
    }

	/***************************************************************************
	 * Constructs a PagedList containing the elements of the specified
	 * collection, in the order they are returned by the collection's iterator.
	 */
	public PagedList(LinkedHashMap map) {
		List list = Arrays.asList( map.values().toArray() );
		setMasterList( list );
	}

    /***************************************************************************
     * Gets the lowest page index to be displayed in the link area. Used when a
     * display similar to Google is to be supported.
     * @return the lowest page index to be displayed in the link area.
     * @see #getLastLinkIndex()
     * @see #getNextLinkIndex()
     * @see #getPreviousLinkIndex()
     */
    public int getFirstLinkIndex() {
        return Math.max(getPageIndex() - linkRangeMidpoint, 1);
    }

    /***************************************************************************
     * Gets the highest page index to be displayed in the link area. Used when a
     * display similar to Google is to be supported.
     * @return the highest page index to be displayed in the link area.
     * @see #getFirstLinkIndex()
     * @see #getNextLinkIndex()
     * @see #getPreviousLinkIndex()
     */
    public int getLastLinkIndex() {
        return Math.min(getPageIndex() + linkRangeMidpoint - 1,
                        getNumberOfPages());
    }

    /***************************************************************************
     * Gets the number of page indices to be displayed in the link area. Used
     * when a display similar to Google is to be supported. Default = 20
     * @return the number of page indices to be displayed in the link area.
     * @see #getFirstLinkIndex()
     * @see #getLastLinkIndex()
     * @see #setLinkRange()
     */
    public int getLinkRange() {
        return linkRange;
    }

    /***************************************************************************
     * Sets the number of page indices to be displayed in the link area. Used
     * when a display similar to Google is to be supported. Default = 20
     * @param aRange the number of page indices to be displayed in the link area.
     * @see #getLinkRange()
     * @see #getFirstLinkIndex()
     * @see #getLastLinkIndex()
     */
    public void setLinkRange(int aRange) {
        linkRange = aRange;
        linkRangeMidpoint = aRange / 2;
    }

    /***************************************************************************
     * Sets the master list that this gets all of its values from. Note that
     * there is no accessor to get the master list. Instead, use this class's
     * List implementation methods to access its values.
     * @return a subList that contains the items in the current page.
     */
    public void setMasterList(List aList) {
        if (aList != null) {
            masterList = aList;
        } else {
            clear();
        }
        if (isEmpty()) {
            pageIndex = 0;
        } else {
            setPageIndex(1);
        }
    }

    /***************************************************************************
     * Gets the page index that the Next link should take the user to. Used when
     * a stateless display similar to Google is to be supported.
     * @return the page index of the next page.
     * @see #getFirstLinkIndex()
     * @see #getLastLinkIndex()
     * @see #getPreviousLinkIndex()
     */
    public int getNextLinkIndex() {
        return Math.min(getPageIndex() + 1, getNumberOfPages());
    }

    /***************************************************************************
     * Gets the number of pages available.
     * @return the number of pages available.
     */
    public int getNumberOfPages() {
        if (size() % getPageSize() > 0) {
            return (size() / getPageSize()) + 1;
        }
        return size() / getPageSize();
    }

    /***************************************************************************
     * Gets the current page. Returns an empty list if there are no items in
     * this list.
     * @return a subList that contains the items in the current page.
     */
    public List getPage() {
        if (size() > 0) {
            int fromIndex = (getPageIndex() - 1) * getPageSize();
            int toIndex = Math.min(fromIndex + getPageSize(), size());
            if (fromIndex <= toIndex) {
                return subList(fromIndex, toIndex);
            }
        }
        return new ArrayList();
    }

    /***************************************************************************
     * Gets the index of the current page. Note that this index starts at 1, not
     * 0. Default = 1
     * @return the index of the current page.
     * @see #setPageIndex()
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /***************************************************************************
     * Gets the page index that the Previous link should take the user to. Used
     * when a stateless display similar to Google is to be supported.
     * @return the page index of the previous page.
     * @see #getFirstLinkIndex()
     * @see #getLastLinkIndex()
     * @see #getNextLinkIndex()
     */
    public int getPreviousLinkIndex() {
        return Math.max(getPageIndex() - 1, 1);
    }

    /***************************************************************************
     * Sets the index of the current page. This is used to navigate to a new
     * page. Note that this index starts at 1, not 0. Default = 1
     * @param newIndex the index of the page to move to.
     * @see #getPageIndex()
     */
    public void setPageIndex(int newIndex) {
        if (newIndex < 1) {
            if (wrap) {
                pageIndex = getNumberOfPages();
            } else {
                pageIndex = 1;
            }
        } else if (newIndex > getNumberOfPages()) {
            if (wrap) {
                pageIndex = 1;
            } else {
                pageIndex = getNumberOfPages();
            }
        } else {
            pageIndex = newIndex;
        }
    }

    /***************************************************************************
     * Gets the number of items in a page. Default = 20
     * @return the number of items in a page.
     * @see #setPageSize()
     */
    public int getPageSize() {
        return pageSize;
    }

    /***************************************************************************
     * Sets the number of items in a page. Default = 20
     * @param anInt the number of items in a page.
     * @see #getPageSize()
     */
    public void setPageSize(int anInt) {
        if (anInt > 0) {        
            pageSize = anInt;
            if (isEmpty()) {
                pageIndex = 0;
            } else {
                setPageIndex(1);
            }
        }
    }

    /***************************************************************************
     * Gets the total number of items in the list. A wrapper around size() to
     * support this being used as a bean.
     */
    public int getSize() {
        return size();
    }

    /***************************************************************************
     * Gets whether the current page is the first one.
     * @see #isLastPage()
     */
    public boolean isFirstPage() {
        return getPageIndex() == 1;
    }

    /***************************************************************************
     * Gets whether the current page is the last one.
     * @see #isFirstPage()
     */
    public boolean isLastPage() {
        return getPageIndex() == getNumberOfPages();
    }

    /***************************************************************************
     * Gets whether the pages should automatically wrap when one is asked for
     * off either end of the List. Default = true
     * @return true if pages should wrap on the ends.
     * @see #setWrap()
     */
    public boolean isWrap() {
        return wrap;
    }

    /***************************************************************************
     * Sets whether the pages should automatically wrap when one is asked for
     * off either end of the List. Default = true
     * @param aBoolean true if pages should wrap on the ends.
     * @see #isWrap()
     */
    public void setWrap(boolean aBoolean) {
        wrap = aBoolean;
    }

    /***************************************************************************
     * Moves to the first page by adjusting the page index.
     * @see #gotoLastPage()
     * @see #gotoNextPage()
     * @see #gotoPreviousPage()
     */
    public void gotoFirstPage() {
        setPageIndex(1);
    }

    /***************************************************************************
     * Moves to the last page by adjusting the page index.
     * @see #gotoFirstPage()
     * @see #gotoNextPage()
     * @see #gotoPreviousPage()
     */
    public void gotoLastPage() {
        setPageIndex(getNumberOfPages());
    }

    /***************************************************************************
     * Moves to the next page by adjusting the page index. Wraps if appropriate.
     * @see #gotoFirstPage()
     * @see #gotoLastPage()
     * @see #gotoPreviousPage()
     */
    public void gotoNextPage() {
        setPageIndex(getPageIndex() + 1);
    }

    /***************************************************************************
     * Moves to the previous page by adjusting the page index. Wraps if
     * appropriate.
     * @see #gotoFirstPage()
     * @see #gotoLastPage()
     * @see #gotoNextPage()
     */
    public void gotoPreviousPage() {
        setPageIndex(getPageIndex() - 1);
    }

    /***************************************************************************
     * Used for debugging.
     */
    public String getState() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("{size: ");
        buffer.append(size());
        buffer.append(", numberOfPages: ");
        buffer.append(getNumberOfPages());
        buffer.append(", pageIndex: ");
        buffer.append(getPageIndex());
        buffer.append(", pageSize: ");
        buffer.append(getPageSize());
        buffer.append(", itemsOnPage: ");
        buffer.append(getPage().size());
        if (isWrap()) {
            buffer.append(", wrapping");
        }
        buffer.append("}");
        return buffer.toString();
    }
    
    /** sorts the contained collection with an instance of the passed comparator */ 
    public void sort(Comparator sortComparator) {
    	Collections.sort(masterList, sortComparator);
    }
    
    //+++++ List Implementation ++++++++++++++++++++++++++++++++++++++++++++++++
    public boolean add(Object o) {return masterList.add(o);}
    public void add(int index, Object element) {masterList.add(index, element);}
    public boolean addAll(Collection c) {return masterList.addAll(c);}
    public boolean addAll(int index, Collection c) {return masterList.addAll(index, c);}
    public void clear() {
        masterList.clear();
        setPageIndex(1);
    }
    public boolean contains(Object o) {return masterList.contains(o);}
    public boolean containsAll(Collection c) {return masterList.containsAll(c);}
    public Object get(int index) {return masterList.get(index);}
    public int indexOf(Object o) {return masterList.indexOf(o);}
    public boolean isEmpty() {return masterList.isEmpty();}
    public Iterator iterator() {return masterList.iterator();}
    public int lastIndexOf(Object o) {return masterList.lastIndexOf(o);}
    public ListIterator listIterator() {return masterList.listIterator();}
    public ListIterator listIterator(int index) {return masterList.listIterator(index);}
    public Object remove(int index) {return masterList.remove(index);}
    public boolean remove(Object o) {return masterList.remove(o);}
    public boolean removeAll(Collection c) {return masterList.removeAll(c);}
    public boolean retainAll(Collection c) {return masterList.retainAll(c);}
    public Object set(int index, Object element) {return masterList.set(index, element);}
    public int size() {return masterList.size();}
    public List subList(int fromIndex, int toIndex) {return masterList.subList(fromIndex, toIndex);}
    public Object[] toArray(Object[] a) {return masterList.toArray(a);}
    public Object[] toArray() {return masterList.toArray();}
    public String toString() {return masterList.toString();}
}
