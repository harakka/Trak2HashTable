package asriik.trak2;

import java.util.Random;

/**
 * Created by: harakka
 * Date and time: 1.4.2013, 23:27
 */
public class Utility {
    // Random number generator for generating keys
    private static final Random rng = new Random();

    /**
     * Fill an array of given size with random integers distributed evenly across the full range of Java integers.
     * Uses java.util.Random.
     * @param size size of array
     * @return array full of random integers
     */
    public static int[] generateIntegers(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = rng.nextInt();
        }
        return result;
    }


}
