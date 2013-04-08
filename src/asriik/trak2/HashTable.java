package asriik.trak2;

/**
 * Created by: Antti Riikonen
 * Date and time: 30.3.2013, 22:29
 */
public class HashTable {
    public enum HashType {modulo, mult}
    private final HashType type;
    // This value is recommended by Knuth in The Art Of Computer Programming, but it's unclear whether it works as well
    // for arrays whose capacity isn't a power of two.
    private static double multiplierConstant = (java.lang.Math.sqrt(5) - 1) / 2;
    private final double loadFactor;
    private final int initialSize;
    private int size = 0;
    private BucketList[] buckets;

    public double getLoadFactor() {
        return loadFactor;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return buckets.length;
    }

    public HashTable(int initialSize, double loadFactor, HashType type) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.type = type;
        buckets = new BucketList[initialSize];

        // We pre-initialize buckets because this saves us the trouble of having to perform null checks for safety whenever buckets are accessed
        initializeBuckets();
    }

    private void initializeBuckets() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new BucketList();
        }
    }

    private void resizeIfNeeded() {
        if (getCurrentLoadFactor() > loadFactor) {
            BucketList[] oldBuckets = buckets;
            buckets = new BucketList[getCapacity()*2];
            size = 0;
            initializeBuckets();
            rehash(oldBuckets);
            if (Testrun.paramVerbose) System.out.println("Resize triggered by exceeding load factor of " + loadFactor
                    + ". Keys: " + size + "; New capacity: " + getCapacity() + "; New load factor: " + getCurrentLoadFactor());
        }


    }

    public double getCurrentLoadFactor() {
        return (double) size / getCapacity();
    }

    public final boolean contains(int needle) {
        return buckets[hash(needle, getCapacity())].contains(needle);
    }

    public final void add(int value) {
        int hash = hash(value, getCapacity());
        buckets[hash].add(value);
        size++;
        resizeIfNeeded();
    }

    public final boolean remove(int value) {
        if (buckets[hash(value, getCapacity())].remove(value) == true) {
            size--;
            return true;
        } else {
            return false;
        }
    }

    public int hash(int value, int capacity) {
        if (type == HashType.modulo) {
            return hashModulo(value, capacity);
        } else {
            return hashMultiplication(value, capacity);
        }
    }

    public static int hashModulo(int value, int capacity) {
        return java.lang.Math.abs(value % capacity);
    }

    public static int hashMultiplication(int value, int capacity) {
        return (int)java.lang.Math.floor(capacity * (java.lang.Math.abs(value) * multiplierConstant % 1d));
    }

    public final void clean() {
        buckets = new BucketList[initialSize];
        initializeBuckets();
        size = 0;
    }

    private void rehash(BucketList[] oldBuckets) {
        for (BucketList b: oldBuckets) {
            for (int i: b.values()) {
                add(i);
            }
        }
    }

    public int longestChain() {
        int longest = 0;
        for (BucketList b: buckets) {
            if (b.values().length > longest) {
                longest = b.values().length;
            }
        }
        return longest;
    }

    public int[] chainLengths() {
        int[] lengths = new int[10000];
        for (BucketList b: buckets) {
            lengths[b.values().length]++;
        }
        return lengths;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (BucketList bucket: buckets) {
            result.append(bucket.toString()).append("\n");
        }
        return result.toString();
    }
}
