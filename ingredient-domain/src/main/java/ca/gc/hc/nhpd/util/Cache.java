package ca.gc.hc.nhpd.util;

import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * This Class is used to hold a collection of Objects. An extension of
 * LinkedHashMap, it expires entries based on when they were inserted or when
 * they were last accessed. It can be size-based, set to a maximum size; or
 * time-based, with a maximum cache life.
 * 
 * The containsKey(), containsValue(), get(), put(), putAll(), remove(), and
 * squeeze() methods are synchronized to be thread safe. It has limited support
 * for values(), and entrySet() returns map entries who's values are wrapped in
 * a CachedItem.
 */
public class Cache extends LinkedHashMap implements Serializable {
    public static final long serialVersionUID = 3693500215986308797L;
    private static final Log LOG = LogFactory.getLog(Cache.class);
    private boolean accessOrdering = false;
    private boolean autoSqueeze = false;
    private boolean caching = true;
    private int cacheLife = -1;
    private int maxCacheSize = 2000;
    private String name;

    /***************************************************************************
     * Gets a Cache that has previously been saved to disk. This automatically
     * calls squeeze after the Cache has been reconstituted so that expired
     * items are automatically removed.
     * @param fileName the path / file name of the file containing the Cache.
     * @param autoUpdate true if the Cache should be saved back to disk after it
     *        has been squeezed so the disk image reflects the change.
     * @return the Cache stored in the file.
     * @see #save
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Cache getCacheFromFile(String fileName, boolean autoUpdate)
                        throws ClassNotFoundException, IOException {
        Cache cache;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                                                      fileName));
        Object obj = ois.readObject();

        ois.close();
        cache = (Cache) obj;
        cache.squeeze();
        if (autoUpdate) {
            cache.save(fileName);
        }
        return cache;
    }

    /***************************************************************************
     * Constructs a generic size-based Cache with no name, a maximum size of
     * 2000 entries with access ordering.
     */
    public Cache() {
        this("", 2000, true);
    }

    /***************************************************************************
     * Constructs a size-based Cache with the passed parameters.
     * @param aName the name of this Cache, used mainly for console messages
     *        when there is more than one Cache present.
     * @param maxCacheSize the maximum number of items this cache will hold.
     * @param accessOrdering true if items should be moved to the top when they
     *        are accessed using get().
     */
    public Cache(String aName, int maxCacheSize, boolean accessOrdering) {
        super(16, 0.75f, accessOrdering);
        setName(aName);
        setMaxCacheSize(maxCacheSize);
        this.accessOrdering = accessOrdering;
    }

    /***************************************************************************
     * Constructs a generic time-based Cache with no name, a maximum size of
     * 2000 entries with insertion ordering.
     * @param cacheLife the number of seconds that an entry remains valid in the
     *        cache.
     * @param autoSqueeze true if this Cache should remove all expired items
     *        whenever get() and put() are called (minimizes size but causes
     *        extra processing).
     */
    public Cache(int cacheLife, boolean autoSqueeze) {
        this("", cacheLife, 2000, false, autoSqueeze);
    }

    /***************************************************************************
     * Constructs a time-based Cache with the passed parameters.
     * @param aName the name of this Cache, used mainly for console messages
     *       when there is more than one Cache present.
     * @param cacheLife the number of seconds that an entry remains valid in the
     *        cache.
     * @param maxCacheSize the maximum number of entries this cache will hold.
     * @param accessOrdering true if items should be moved to the top when they
     *        are accessed using get().
     * @param autoSqueeze true if this Cache should remove all expired items
     *        whenever get() and put() are called (minimizes size but causes
     *        extra processing).
     */
    public Cache(String aName, int cacheLife, int maxCacheSize,
                 boolean accessOrdering, boolean autoSqueeze) {
        super(16, 0.75f, accessOrdering);
        setName(aName);
        setCacheLife(cacheLife);
        setMaxCacheSize(maxCacheSize);
        this.accessOrdering = accessOrdering;
        setAutoSqueeze(autoSqueeze);
    }

    /***************************************************************************
     * Gets the number of seconds that a CachedItem remains valid in the cache.
     * Typically autoSqueeze is turned on when this is used, otherwise expired
     * items will only be removed when the cache size exceeds maxCacheSize.
     * Items never expire if this is set to -1.
     * @return the number of seconds that an entry remains valid in the cache.
     * @see #setCacheLife
     */
    public int getCacheLife() {
        return cacheLife;
    }

    /***************************************************************************
     * Gets the number of items this cache will hold. When the cache is full,
     * adding new entries will cause the oldest ones to be dropped. DEFAULT =
     * 1000.
     * @return the number of items this cache will hold.
     * @see #setMaxCacheSize
     */
    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    /***************************************************************************
     * Gets the name of this Cache. This is used mainly for console messages
     * when there is more than one Cache present to tell which one the message
     * is for.
     * @return the name of this Cache.
     * @see #setName
     */
    public String getName() {
        return name;
    }

    /***************************************************************************
     * Gets whether an item in this Cache should automatically be moved to the
     * top when it is accessed using get(). It also resets the item's timestamp.
     * When set to true, items will expire or be removed based on when they were
     * last accessed rather than when they were originally inserted. DEFAULT =
     * false
     * @return true if items should be moved to the top when they are accessed
     *         using get().
     * @see #setAccessOrdering
     */
    public boolean isAccessOrdering() {
        return accessOrdering;
    }

    /***************************************************************************
     * Gets whether this Cache should automatically remove expired items
     * whenever get() and put() are called. This will minimize the size of a
     * time-based cache, but cause extra processing. If false, squeeze will only
     * be called on a time-based cache when containsKey() or containsValue() are
     * called. Note that this has no effect on size-based caches.
     * @return true if this Cache should regularly remove expired items.
     * @see #setAutoSqueeze
     */
    public boolean isAutoSqueeze() {
        return autoSqueeze;
    }

    /***************************************************************************
     * Gets whether caching is to occur. If true, then caching is turned on. If
     * false, put() will not add anything to the cache and get() will always
     * return null. DEFAULT = true
     * @return true if caching is turned on.
     * @see #setCaching
     */
    public boolean isCaching() {
        return caching;
    }

    /***************************************************************************
     * Gets whether this is a time-based cache.
     * @return true if this Cache is time-based.
     */
    public boolean isTimeBased() {
        return (cacheLife >= 0);
    }

    /***************************************************************************
     * Sets whether an item in this Cache should automatically be moved to the
     * top when it is accessed using get(). It also resets the item's timestamp.
     * When set to true, items will expire or be removed based on when they were
     * last accessed rather than when they were originally inserted. DEFAULT =
     * false
     * @param aBoolean true if items should be moved to the top when they are
     *        accessed using get().
     * @see #isAccessOrdering
     */
    public void setAccessOrdering(boolean aBoolean) {
        this.accessOrdering = aBoolean;
    }

    /***************************************************************************
     * Sets whether this Cache should automatically remove expired items
     * whenever get() and put() are called. This will minimize the size of a
     * time-based cache, but cause extra processing. If false, squeeze will only
     * be called on a time-based cache when containsKey() or containsValue() are
     * called. Note that this has no effect on size-based caches.
     * @param aBoolean true if this Cache should regularly remove expired items.
     * @see #isAutoSqueeze
     */
    public void setAutoSqueeze(boolean aBoolean) {
        this.autoSqueeze = aBoolean;
    }

    /***************************************************************************
     * Sets whether caching is to occur. If true, then caching is turned on. If
     * false, put() will not add anything to the cache and get() will always
     * return null. DEFAULT = true
     * @param aBoolean true if caching is turned on.
     * @see #isCaching
     */
    public void setCaching(boolean aBoolean) {
        this.caching = aBoolean;
    }

    /***************************************************************************
     * Sets the number of seconds that a CachedItem remains valid in the cache.
     * Typically autoSqueeze is turned on when this is used, otherwise expired
     * items will only be removed when the cache size exceeds maxCacheSize.
     * Items never expire if this is set to -1.
     * @param anInt the number of seconds that an entry remains valid in the
     *        cache.
     * @see #getCacheLife
     */
    public void setCacheLife(int anInt) {
        this.cacheLife = anInt;
    }

    /***************************************************************************
     * Sets the number of items this cache will hold. When the cache is full,
     * adding new entries will cause the oldest ones to be dropped. This
     * automatically calls squeeze(). DEFAULT = 1000
     * @param anInt the number of items this cache will hold.
     * @see #getMaxCacheSize
     */
    public void setMaxCacheSize(int anInt) {
        if (anInt >= 0) {
            this.maxCacheSize = anInt;
            squeeze();
        } else {
            LOG.error("Cache '" + getName() + "'.setMaxCacheSize() -"
                      + " cannot be set to a negative number.");
        }
    }

    /***************************************************************************
     * Sets the name of this Cache. This is used mainly for console messages
     * when there is more than one Cache present to tell which one the message
     * is for.
     * @param aString the name of this Cache.
     * @see #getName
     */
    public void setName(String aString) {
        this.name = aString;
    }

    /***************************************************************************
     * Gets whether this map contains a mapping for the specified key.
     * Overridden to make sure that squeeze is run first.
     * @param key the key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key.
     */
    @Override
    public synchronized boolean containsKey(Object key) {
        if (isTimeBased()) {
            squeeze();
        }
        return super.containsKey(key);
    }

    /***************************************************************************
     * Gets whather this map maps one or more keys to the specified value.
     * Overridden to make sure that squeeze is run first, and to wrap the value
     * in a CachedItem before testing it.
     * @param obj value whose presence in this map is to be tested.
     * @return true if this map maps one or more keys to the specified value.
     */
    @Override
    public synchronized boolean containsValue(Object obj) {
        if (isTimeBased()) {
            squeeze();
        }
        return (super.containsValue(new CachedItem(obj)));
    }

    /***************************************************************************
     * Gets an Object that was stored in this Cache. Over-ridden so that it can
     * retrieve the requested Object from the CachedItem that it was wrapped in.
     * Note that this will not return expired items, even if they are still
     * present in the cache. Returns null if caching is turned off.
     * @param key the key that was assigned to the Object when it was added to
     *        the Cache.
     * @return the Object that was stored in this Cache using the passed key.
     *         Null if caching is turned off.
     */
    @Override
    public synchronized Object get(Object key) {
        if (caching) {
            if (autoSqueeze) {
                squeeze();
            }
            CachedItem item = (CachedItem) super.get(key);
            if (item != null && isAccessOrdering()) {
                item.setCachedTimestamp(Calendar.getInstance());
            }

            // May need to expire individual items if cacheLife is not -1:
            if (item != null && !autoSqueeze && isTimeBased()) {
                Calendar cutoff = Calendar.getInstance();
                cutoff.add(Calendar.SECOND, -cacheLife); // Set to the expiry time.
                // Remove the item if it has expired:
                if (item.hasExpired(cutoff)) {
                    remove(key);
                    item = null;
                }
            }

            if (item != null) {
                LOG.debug("Cache '" + getName() + "'.get() - key: " + key 
                          + ", value: " + item.getValue());
                return item.getValue();
            } else {
                LOG.debug("Cache '" + getName() + "'.get() - key: " + key 
                          + " - item not found.");
                return null;
            }

        } else { // Not caching at this time:
            LOG.debug("Cache '" + getName() + "'.get() - key: " + key
                      + " - Caching turned off.");
            return null;
        }
    }

    /***************************************************************************
     * Puts an Object into this Cache. Over-ridden so that it can wrapper the
     * passed Object in a CachedItem before adding it to the Cache. Does nothing
     * if caching is turned off.
     * @param key the key that is assigned to the Object when adding it to the
     *        Cache.
     * @param value the Object that is to be stored in this Cache using the
     *        passed key.
     * @return the Object that was previously stored in this Cache with that
     *         key, otherwise null.
     * @see #put(Object)
     */
    @Override
    public synchronized Object put(Object key, Object value) {
        if (caching) {
            Object result;
            LOG.debug("Cache '" + getName() + "'.put() - key: " + key 
                      + ", value: " + value);

            if (value != null && value instanceof CachedItem) {
                result = super.put(key, value);
            } else {
                result = super.put(key, new CachedItem(value));
            }

            if (autoSqueeze) {
                squeeze();
            }
            return result;

        } else { // Not caching at this time:
            LOG.debug("Cache '" + getName() + "'.put() - key: " + key 
                      + ", value: " + value + " - Caching turned off.");
            return null;
        }
    }

    /***************************************************************************
     * Copies all of the mappings from the specified map to this cache. These
     * mappings will replace any mappings that this cache had for any of the
     * keys currently in the specified map. Does nothing if caching is turned
     * off.
     * @param m the mappings to be stored in this cache.
     * @throws NullPointerException if the passed map is null.
     */
    @Override
    public synchronized void putAll(Map m) throws NullPointerException {
        if (caching) {
            Iterator entryIter = m.entrySet().iterator();
            Map.Entry entry;

            while (entryIter.hasNext()) {
                entry = (Map.Entry) entryIter.next();
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /***************************************************************************
     * Removes an Object that was stored in this Cache. Over-ridden so that it
     * can return the object wrappered in the CachedItem.
     * @param key the key of the Object to be removed.
     * @return the Object that was removed.
     */
    @Override
    public synchronized Object remove(Object key) {
        CachedItem removedItem = (CachedItem) super.remove(key);

        if (removedItem != null) {
            LOG.debug("Cache '" + getName() + "'.remove() - key: " + key 
                      + ", value: " + removedItem.getValue());
            return removedItem.getValue();

        } else {
            LOG.debug("Cache '" + getName() + "'.remove() - key: " + key 
                      + ", item not found.");
            return null;
        }
    }

    /***************************************************************************
     * Overridden for debugging purposes.
     */
    @Override
    public String toString() {
        return "Cache< Name: " + getName() + ", MaxCacheSize: "
                + getMaxCacheSize() + ", CacheLife: " + getCacheLife()
                + ", AccessOrdering: " + isAccessOrdering() + ", AutoSqueeze: "
                + isAutoSqueeze() + ", Caching: " + isCaching() + ", Content:\n"
                + super.toString() + " >\n";
    }

    /***************************************************************************
     * Returns a collection view of the values contained in this map. In this
     * case, the collection is not backed by the map, so changes to the map are
     * NOT reflected in the collection, and vice-versa. As a result, the
     * returned collection is immutable.
     * @return a collection view of the values contained in this map.
     */
    @Override
    public Collection values() {
        Iterator valueIter = super.values().iterator();
        ArrayList<Object> valueList = new ArrayList<Object>();

        while (valueIter.hasNext()) {
            valueList.add(((CachedItem) valueIter.next()).getValue());
        }
        return Collections.unmodifiableCollection(valueList);
    }

    /***************************************************************************
     * Gets whether this map should remove its eldest entry. This method is
     * invoked by put and putAll after inserting a new entry into the map.
     * Over-ridden so that it can do necessary house keeping.
     * @param eldest the eldest entry in the cache.
     * @return true if the eldest entry should be removed.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        if (autoSqueeze) {
            return false; // put() will be calling squeeze() itself.

        } else {// This is size-based, so let it do the maintenance:
            return (size() > getMaxCacheSize());
        }
    }

    /***************************************************************************
     * Checks whether this Cache contains the passed Object. In some cases there
     * is no need for storing both a key and value. For example, a cache may be
     * used to sense whether an item has already been processed/received to
     * avoid duplicates. This is used in those cases. The object is first stored
     * using put(), then this is used to see if it's there. Primarily used with
     * time- based caches. Note that squeeze is run first.
     * @param anObject the Object to look for in this Cache.
     * @return true if this Cache contains the passed Object.
     * @see #put(Object)
     */
    public synchronized boolean contains(Object anObject) {
        return containsKey(anObject);
    }

    /***************************************************************************
     * Returns a collection view of the expired map entries. Note that their
     * values are wrapped in a CachedItem. In this case, the collection is not
     * backed by the map, so changes to the map are NOT reflected in the
     * collection, and vice-versa. As a result, the returned collection is
     * immutable.
     * @return a collection view of the expired map entries.
     */
    public Collection getExpiredEntries() {
        Calendar cutoff = Calendar.getInstance();
        Map.Entry entry;
        Iterator entryIter = super.entrySet().iterator();
        ArrayList<Map.Entry> entryList = new ArrayList<Map.Entry>();

        if (isTimeBased()) {
            cutoff.add(Calendar.SECOND, -getCacheLife()); // Set to the expiry time.
            while (entryIter.hasNext()) {
                entry = (Map.Entry) entryIter.next();
                if (((CachedItem) entry.getValue()).hasExpired(cutoff)) {
                    entryList.add(entry);
                }
            }
        }
        return Collections.unmodifiableCollection(entryList);
    }

    /***************************************************************************
     * Returns a collection view of the expired values contained in this map. In
     * this case, the collection is not backed by the map, so changes to the map
     * are NOT reflected in the collection, and vice-versa. As a result, the
     * returned collection is immutable.
     * @return a collection view of the expired values contained in this map.
     */
    public Collection getExpiredValues() {
        Calendar cutoff = Calendar.getInstance();
        CachedItem item;
        Iterator valueIter = super.values().iterator();
        ArrayList<Object> valueList = new ArrayList<Object>();

        if (isTimeBased()) {
            cutoff.add(Calendar.SECOND, -getCacheLife()); // Set to the expiry time.
            while (valueIter.hasNext()) {
                item = (CachedItem) valueIter.next();
                if (item.hasExpired(cutoff)) {
                    valueList.add(item.getValue());
                }
            }
        }
        return Collections.unmodifiableCollection(valueList);
    }

    /***************************************************************************
     * Puts an Object into this Cache. In some cases there is no need for
     * storing both a key and value. For example, a cache may be used to sense
     * whether an item has already been processed/received to avoid duplicates.
     * This is used in those cases. Subsequently use contains() to see if it's
     * there. Primarily used with time-based caches. Does nothing if caching is
     * turned off.
     * @param anObject the Object to add to this Cache.
     * @see #contains(Object)
     * @see #put(Object, Object)
     */
    public synchronized void put(Object anObject) {
        put(anObject, null);
    }

    /***************************************************************************
     * Saves this Cache to disk. This requires all items in the cache to be
     * Serializable. Note that this calls squeeze() to minimize the size of the
     * file to be stored on disk.
     * @param fileName the path / file name of the file to contain the Cache.
     * @see #getCacheFromFile
     * @throws IOException
     */
    public synchronized void save(String fileName) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                                                        fileName));
        squeeze();
        oos.writeObject(this);
        oos.close();
    }

    /***************************************************************************
     * Removes any expired items from the cache. If the Cache is still too large
     * (it may have had its maxCacheSize reduced), it deletes the oldest records
     * until it is the correct size.
     */
    public synchronized void squeeze() {
        Map.Entry[] entries = (Map.Entry[]) super.entrySet().toArray(
                                                             new Map.Entry[0]);
        int index = 0;

        LOG.debug("Cache '" + getName() + "' Squeezing Cache...");

        // Remove items that have expired if the cache is time-based:
        if (isTimeBased()) {
            Calendar cutoff = Calendar.getInstance();
            cutoff.add(Calendar.SECOND, -getCacheLife()); // Set to the expiry time.
            // Remove expired items:
            while (index < entries.length &&
                   ((CachedItem) entries[index].getValue()).hasExpired(cutoff)) {
                super.entrySet().remove(entries[index++]);
            }
        }

        // Trim off extra items if maxCacheSize was reduced:
        while ((super.entrySet().size() - getMaxCacheSize()) > 0) {
            super.entrySet().remove(entries[index++]);
        }
    }

    //+++++ INNER CLASSES ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * This Class is used to wrap an Object to be stored in a Cache. It includes
     * a timestamp so the cache can support time-based entry expiry.
     */
    public static class CachedItem implements Serializable {
        public static final long serialVersionUID = -2748430846386219297L;
        private Calendar cachedTimestamp;
        private Object value;

        /***********************************************************************
         * Constructs a CachedItem with the passed object using the current
         * system time as its timestamp.
         * @param anObject the object that is being stored in the Cache.
         */
        public CachedItem(Object anObject) {
            this(anObject, Calendar.getInstance());
        }

        /***********************************************************************
         * Constructs a CachedItem with the passed parameters.
         * @param anObject the object that is being stored in the Cache.
         * @param timestamp the time this CachedItem was stored in the Cache.
         */
        public CachedItem(Object anObject, Calendar timestamp) {
            setValue(anObject);
            setCachedTimestamp(timestamp);
        }

        /***********************************************************************
         * Gets the time this CachedItem was stored in the Cache. This is used
         * to determine when it should expire.
         * @return the time this CachedItem was stored in the Cache.
         * @see #setCachedTimestamp
         * @see #hasExpired
         */
        public Calendar getCachedTimestamp() {
            return cachedTimestamp;
        }

        /***********************************************************************
         * Gets the value of the object that is being stored in the Cache.
         * @return the object that is being stored in the Cache.
         * @see #setValue
         */
        public Object getValue() {
            return value;
        }

        /***********************************************************************
         * Sets the time this CachedItem was stored in the Cache. This is used
         * to determine when it should expire.
         * @param aCalendar the time this CachedItem was stored in the Cache.
         * @see #getCachedTimestamp
         * @see #hasExpired
         */
        public void setCachedTimestamp(Calendar aCalendar) {
            this.cachedTimestamp = aCalendar;
        }

        /***********************************************************************
         * Sets the value of the object that is being stored in the Cache.
         * @param anObject the object that is being stored in the Cache.
         * @see #getValue
         */
        public void setValue(Object anObject) {
            this.value = anObject;
        }

        /***********************************************************************
         * Compares this item to CachedItems for equality. This compares their
         * "value" objects.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CachedItem) {
                if (getValue() != null) {
                    return (getValue().equals(((CachedItem) obj).getValue()));
                } else {
                    return (((CachedItem) obj).getValue() == null);
                }

            } else {
                return false;
            }
        }

        /***********************************************************************
         * Gets whether this CachedItem has expired. It compares the
         * cachedTimestamp to the cutoff time passed in. It has expired if it
         * was cached before the cutoff time.
         * @param cutoff the time that the Cache is willing to keep items after.
         * @return true if this CachedItem has expired.
         */
        public boolean hasExpired(Calendar cutoff) {
            return cachedTimestamp.before(cutoff);
        }

        /***********************************************************************
         * Overridden for debugging purposes.
         */
        @Override
        public String toString() {
            return "CachedItem[Value: " + getValue() + ", Timestamp: "
                    + getCachedTimestamp().getTime() + " ]\n";
        }
    }
}
