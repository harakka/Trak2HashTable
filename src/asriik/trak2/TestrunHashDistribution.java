package asriik.trak2;

import java.util.Random;

/**
 * Program for testing performance differences between modulo and multiplication based hash functions, and for examining
 * key-bucket distribution via collision chain lengths.
 * Created by: Antti Riikonen
 * Date and time: 1.4.2013, 21:39
 */
@Deprecated
public class TestrunHashDistribution {

    // Stopwatch for timing program execution
    private static Stopwatch watch = new Stopwatch();
    // Array of keys to be used as input
    private static int[] keys;
    // Array of buckets, simulating the buckets of a hashtable
    private static int[] buckets;
    // Number of buckets
    private static int paramBuckets;
    // Parameter indicating whether modulo or multiplication hashing is used
    private static boolean paramModuloHash;
    // Parameter for whether to print the number distribution statistics, row per bucket
    private static boolean paramPrintDistribution;

    public static void main(String[] args) {
        // Read command line parameters and config everything accordingly
        setupFromParams(args);

        // Perform the test run according to parameters and time it
        watch.start();
        if (paramModuloHash) {
            for (int i: keys) {
                    buckets[HashTable.hashModulo(i, paramBuckets)]++;
            }
        } else {
            for (int i: keys) {
                buckets[HashTable.hashMultiplication(i, paramBuckets)]++;
            }
        }
        watch.stop();

        System.out.println("Hash calculation time: " + watch.getResult());

        // Print statistics showing number distribution into buckets
        if (paramPrintDistribution) {
            System.out.println("Bucket\tCount");
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != 0) {
                    System.out.println(i + "\t\t" + buckets[i]);
                }
            }
        }
    }

    /**
     * Examine the parameters passed to the program and configure the environment accordingly.
     * @param args arguments passed to the program
     */
    public static void setupFromParams(String[] args) {
        // Sanity check command line arguments and set up the test environment based on them
        // If sanity check fails, print usage instructions in the catch block.
        try {
            keys = Utility.generateIntegers(Integer.decode(args[0]));
            paramBuckets = Integer.decode(args[1]);
            switch (args[2]) {
                case "modulo":
                    paramModuloHash = true;
                    break;
                case "multiplication":
                    paramModuloHash = false;
                    break;
                default:
                    throw(new IllegalArgumentException("Invalid parameter: " + args[2] + ", should be either \"modulo\" or \"multiplication\""));
            }
            paramPrintDistribution = Boolean.valueOf(args[2]);
            buckets = new int[paramBuckets];

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Usage: java asriik.trak2.TestrunHashDistribution numberOfKeys numberOfBuckets modulo|multiplication true|false");
            System.out.println("Example: java asriik.trak2.TestrunHashDistribution 5000 100 modulo true");
            System.exit(1);
        }
    }
}
