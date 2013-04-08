package asriik.trak2;

/**
 * Hash table implementation for TRAK2 course project. Does not support satellite data for the sake of simplicity.
 * Implementation is based on arrays to avoid use of Java collections, and uses only primitive integer values to avoid
 * autoboxing messing with performance testing.
 * Supports both modulo and multiplication based hashing. Size doubles whenever a load factor threshold is
 * exceeded. Downsizing is not implemented to keep the implementation simple and to avoid trashing. Buckets are
 * initialized in lazy manner, to avoid unnecessary object creation.
 * Created by: Antti Riikonen
 * Date and time: 30.3.2013, 22:29
 */
public class HashTable {
    // Types of hash functions this implementation supports
    public enum HashType {modulo, mult}
    // Defines the type of this particular hashtable instance
    private final HashType type;
    // Constant used by the multiplication hash function.
    // This value is recommended by Knuth in The Art Of Computer Programming, but it's unclear whether it works as well
    // for arrays whose capacity isn't a power of two.
    private static final double multiplierConstant = (java.lang.Math.sqrt(5) - 1) / 2;
    // Load factor threshold that, when exceeded, triggers capacity doubling and rehash.
    private final double loadFactor;
    // Initial capacity
    private final int initialSize;
    // Current number of keys in hash table
    private int size = 0;
    // hash table buckets
    private BucketList[] buckets;

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return buckets.length;
    }

    /**
     * Constructs a new HashTable instance with given parameters.
     * @param initialSize Initial capacity, or number of hash table buckets
     * @param loadFactor Exceeding this load factor will trigger hash table size doubling and rehashing of keys
     * @param type Indicates whether this hash table uses modulo or multiplication based hashing
     */
    public HashTable(int initialSize, double loadFactor, HashType type) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.type = type;
        buckets = new BucketList[initialSize];
    }

    /**
     * Helper method that checks whether load factor is within acceptable limits, and if not, doubles the bucket
     * array size and rehashes current keys into it.
     */
    private void resizeIfNeeded() {
        if (getCurrentLoadFactor() > loadFactor) {
            BucketList[] oldBuckets = buckets;
            buckets = new BucketList[getCapacity()*2];
            size = 0;
            rehash(oldBuckets);
            if (Testrun.paramVerbose) System.out.println("Resize triggered by exceeding load factor of " + loadFactor
                    + ". Keys: " + size + "; New capacity: " + getCapacity() + "; New load factor: " + getCurrentLoadFactor());
        }


    }

    /**
     * @return Current load factor
     */
    public double getCurrentLoadFactor() {
        return (double) size / getCapacity();
    }

    /**
     * Finds out whether the value needle exists in  hash table.
     * @param needle Value to search for
     * @return True if needle is found, otherwise false
     */
    public final boolean contains(int needle) {
        int hash = hash(needle, getCapacity());
        if (buckets[hash] == null) {
            return false;
        } else {
            return buckets[hash].contains(needle);
        }
    }

    /**
     * Inserts value into the hash table. Duplicates are allowed.
     * @param value Value to insert
     */
    public final void add(int value) {
        int hash = hash(value, getCapacity());
        if (buckets[hash] == null) {
            buckets[hash] = new BucketList();
        }
        buckets[hash].add(value);
        size++;
        resizeIfNeeded();
    }

    /**
     * Removes the latest added instance of this value from hashtable.
     * @param value
     * @return
     */
    public final boolean remove(int value) {
        int hash = hash(value, getCapacity());
        if (buckets[hash] == null) {
            return false;
        } else if (buckets[hash].remove(value) == true) {
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hashes the given value using the hash function defined when the hash table was created.
     * @param value Value to hash
     * @param capacity Number of slots we are hashing into
     * @return Hashed value
     */
    public int hash(int value, int capacity) {
        if (type == HashType.modulo) {
            return hashModulo(value, capacity);
        } else {
            return hashMultiplication(value, capacity);
        }
    }

    /**
     * Hash function using the modulo method.
     * @param value Value to hash
     * @param capacity Number of slots we are hashing into
     * @return Hashed value
     */
    public static int hashModulo(int value, int capacity) {
        return java.lang.Math.abs(value % capacity);
    }

    /**
     * Hash function using the multiplication by constant 0 < a < 1 method.
     * @param value Value to hash
     * @param capacity Number of slots we are hashing into
     * @return Hashed value
     */
    public static int hashMultiplication(int value, int capacity) {
        return (int)java.lang.Math.floor(capacity * (java.lang.Math.abs(value) * multiplierConstant % 1d));
    }

    /**
     * Empties the hash table of all values.
     */
    public final void clean() {
        buckets = new BucketList[initialSize];
        size = 0;
    }

    /**
     * Helper method for rehashing all current keys into hash table.
     * @param oldBuckets Array containing table's current keys
     */
    private void rehash(BucketList[] oldBuckets) {
        for (BucketList b: oldBuckets) {
            if (b != null) {
                for (int i: b.values()) {
                   add(i);
                }
            }
        }
    }

    /**
     * Finds the length of longest collision chain in the hash table.
     * @return Length of longest collision chain
     */
    public int longestChain() {
        int longest = 0;
        for (BucketList b: buckets) {
            if (b != null && b.values().length > longest) {
                longest = b.values().length;
            }
        }
        return longest;
    }

    /**
     * @return List of all collision chain lengths in the hash table, including chains of length 0.
     */
    public int[] chainLengths() {
        int[] lengths = new int[10000];
        for (BucketList b: buckets) {
            if (b == null) {
                lengths[0]++;
            } else {
                lengths[b.values().length]++;
            }
        }
        return lengths;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (BucketList bucket: buckets) {
            if (bucket != null)
                result.append(bucket.toString()).append("\n");
        }
        return result.toString();
    }
}
