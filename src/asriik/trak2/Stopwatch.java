package asriik.trak2;

/**
 * Created by: Antti Riikonen
 * Date and time: 1.4.2013, 16:42
 */
public class Stopwatch {
    private long start;
    private long result;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        result = System.nanoTime() - start;
    }

    public long getResult() {
        return result;
    }

    public float getResultInSeconds() {
        return (float)result/1000000000;
    }

    @Override
    public String toString() {
        return (float)result/1000000000 + "s";
    }
}