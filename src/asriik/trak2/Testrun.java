package asriik.trak2;

/**
 * A user interface class for running various tests on the HashTable and its hash functions.
 * Created by: Antti Riikonen
 * Date and time: 8.4.2013, 18:38
 */
public class Testrun {
    // Variables for command line parameters
    // Test mode: can be either insert or distribution
    private static TestMode paramMode;
    // Number of random keys to be generated and used in the test run.
    private static int paramNumberOfKeys;
    // Initial capacity (number of buckets in the hashtable).
    private static int paramCapacity;
    // Load factor that, when exceeded, triggers hashtable resize and rehash.
    private static double paramLoadFactor;
    // If true, modulo hashing is used. If false, division hashing is used.
    private static HashTable.HashType paramHashType;
    // Parameter controlling output verbosity
    public static boolean paramVerbose;

    // Internal variables
    // Enum indicating test mode
    public static enum TestMode {insert, distribution, hashtime}
    // Stopwatch for timing program execution.
    private static final Stopwatch watch = new Stopwatch();
    // Array for simulating the buckets of a hash table in distribution and hashtime modes.
    private static int[] buckets;


    /**
     * Main method that runs the actual tests.
     */
    public static void main(String[] args) {
        /* Commet this back in if you need time to attach a profiler
        try {
            System.out.println("Waiting for 10 seconds, attach a profiler now!");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // Read command line parameters and set up the test run settings accordingly
        setupFromParams(args);

        int[] keys = Utility.generateIntegers(paramNumberOfKeys);

        // Run the selected test and output results
        switch (paramMode) {

            // Test: calculate hash function running time on given number of keys.
            case hashtime:
                buckets = new int[paramCapacity];

                switch (paramHashType) {
                    case modulo:
                        watch.start();
                        for (int i: keys) {
                            buckets[HashTable.hashModulo(i, paramCapacity)]++;
                        }
                        break;
                    case mult:
                        watch.start();
                        for (int i: keys) {
                            buckets[HashTable.hashMultiplication(i, paramCapacity)]++;
                        }
                        break;
                }
                watch.stop();

                System.out.println("Hash calculation time: " + watch.getResult() + " ns");
                break;

            // Test: hash keys into buckets and output how many hashes there are per bucket, line per bucket.
            case distribution:
                buckets = new int[paramCapacity];

                switch (paramHashType) {
                    case modulo:
                        for (int i: keys) {
                            buckets[HashTable.hashModulo(i, paramCapacity)]++;
                        }
                        break;
                    case mult:
                        for (int i: keys) {
                            buckets[HashTable.hashMultiplication(i, paramCapacity)]++;
                        }
                        break;
                }

                // Print statistics showing number distribution into buckets
                System.out.println("Bucket\tCount");
                for (int i = 0; i < buckets.length; i++) {
                    if (buckets[i] != 0) {
                        System.out.println(i + "\t\t" + buckets[i]);
                    }
                }
                break;

            // Test: time the insertion of given amount of random generated keys into the table.
            case insert:
                HashTable table = new HashTable(paramCapacity, paramLoadFactor, paramHashType);

                watch.start();
                for (int i: keys) {
                    table.add(i);
                }
                watch.stop();
                System.out.println("Time\t\tKeys\t\tCapacity\t\tLoad factor\t\tLongest chain length");
                System.out.println(watch.toString() + ".\t" + table.getSize() + "\t" + table.getCapacity() + "\t" + table.getCurrentLoadFactor() + "\t" + table.longestChain());

                int[] chainLengths = table.chainLengths();
                System.out.println("Chain length\tCount");
                for (int i = 0; i < chainLengths.length; i++) {
                    if (chainLengths[i] != 0) {
                        System.out.println(i + "\t\t" + chainLengths[i]);
                    }
                }
                break;
        }
    }


    /**
     * Examine the parameters passed to the program and configure the environment accordingly.
     * @param args arguments passed to the program
     */
    private static void setupFromParams(String[] args) {
        // Sanity check command line arguments and set up the test environment based on them.
        // If sanity check fails, print usage instructions in the catch block.
        try {
            paramMode = Enum.valueOf(TestMode.class, args[0]);
            paramNumberOfKeys = Integer.decode(args[1]);
            paramCapacity = Integer.decode(args[2]);
            paramLoadFactor = Double.valueOf(args[3]);
            paramHashType = HashTable.HashType.valueOf(args[4]);
            paramVerbose = args[args.length - 1].equals("verbose");

            // Print parameters for user reference
            System.out.println("Mode\tNumberOfKeys\tCapacity\tLoadFactor\tHashType\tVerbose");
            System.out.println(paramMode + "\t" + paramNumberOfKeys + "\t\t" + paramCapacity + "\t" + paramLoadFactor + "\t" + paramHashType + "\t" + paramVerbose);

        } catch (Exception e) {
            System.out.println("Command line parameters: mode numOfKeys capacity loadFactor hashType verbose\n" +
            "mode:       Test mode. One of \"distribution\", \"insert\", \"hashtime\"." +
            "numOfKeys:  Number of random keys to use for test run." +
            "capacity:   Hash table capacity (number of buckets)." +
            "loadFactor: Load factor that triggers resizing, only used in distribution mode." +
            "hashType:   Hash function type. Either \"modulo\" or \"mult\"." +
            "verbose:    If last parameter is \"verbose\", program output will be more verbose." +
            "\nExamples:" +
            "java asriik.trak2.Testrun distribution 15000 13 0 modulo verbose" +
            "java asriik.trak2.Testrun insert 20000000 28 0.75 mult"
            );
            e.printStackTrace();
            System.exit(1);
        }
    }
}
