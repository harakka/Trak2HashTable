package asriik.trak2;

/**
 * Created by: Antti Riikonen
 * Date and time: 31.3.2013, 21:47
 */
@Deprecated
public class TestrunInsertions {
    // Parameter controlling output verbosity
    public static boolean paramVerbose;
    // Stopwatch for timing program execution
    private static Stopwatch watch = new Stopwatch();
    // Array of keys to be used as input
    private static int[] keys;
    // Hash table the values are inserted into
    private static HashTable table;

    public static void main(String[] args) {

        // Read command line parameters and config everything accordingly
        setupFromParams(args);

        // Execute the test and time it
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
    }

    /**
     * Examine the parameters passed to the program and configure the environment accordingly.
     * @param args arguments passed to the program
     */
    public static void setupFromParams(String[] args) {
        // Sanity check command line arguments and set up the test environment based on them
        // If sanity check fails, print usage instructions in the catch block.
        try {
            int paramInitCap;
            double paramLoadFactor;

            paramInitCap = Integer.decode(args[0]);
            paramLoadFactor = Double.valueOf(args[1]);
            switch (args[2]) {
                case "modulo":
                    table = new HashTable(paramInitCap, paramLoadFactor, HashTable.HashType.modulo);
                    break;
                case "multiplication":
                    table = new HashTable(paramInitCap, paramLoadFactor, HashTable.HashType.mult);
                    break;
                default:
                    throw(new IllegalArgumentException("Invalid parameter: " + args[2] + ", should be either \"modulo\" or \"multiplication\""));
            }
            if (args.length == 5 && args[4].equals("verbose")) {
                paramVerbose = true;
            } else paramVerbose = false;

            keys = Utility.generateIntegers(Integer.decode(args[3]));

            System.out.println("Parameters: initial capacity " + paramInitCap + "; load factor " + paramLoadFactor
                    + "; table type " + args[2] + "; number of random keys " + Integer.decode(args[3]));

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Usage: java asriik.trak2.TestrunInsertions initialCapacity loadFactor modulo|multiplication numberOfKeys [verbose]");
            System.out.println("Example: java asriik.trak2.TestrunInsertions 15 0.75 modulo 5000");
            System.out.println("Note: when using large numbers of keys, you may need to allocate more memory");
            System.out.println("to the JVM with -Xms and -Xmx switches. 20 000 000 Keys will need around 2.5GB.");
            System.exit(1);
        }
    }
}
