package asriik.trak2;

/**
 * Helper class for timing execution of code blocks. In theory has nanosecond
 * accuracy, in practice depends on platform timing accuracy.
 * Created by: Antti Riikonen
 * Date and time: 1.4.2013, 16:42
 */
public class Stopwatch {
    private long start;
    private long result;

    /**
     * Start timekeeping.
     */
    public void start() {
        start = System.nanoTime();
    }

    /**
     * Stop timekeeping.
     */
    public void stop() {
        result = System.nanoTime() - start;
    }

    /**
     * @return Result of timekeeping in nanoseconds.
     */
    public long getResult() {
        return result;
    }

    /**
     * @return Result of timekeeping in seconds.
     */
    public float getResultInSeconds() {
        return (float)result/1000000000;
    }

    @Override
    public String toString() {
        return (float)result/1000000000 + "s";
    }
}