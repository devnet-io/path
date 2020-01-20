package core.tests.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for implementing performance tests. Run all using TestRunner. Expects that the configured system is running.
 *
 * @author jesposito
 * @version 2/15/18
 */

public abstract class PerformanceTest {
    protected static final int ITERATIONS = 1000;

    private List<Long> times;
    private double average;

    protected PerformanceTest() {
        this.times = new ArrayList<>();
    }

    public void run() {
        this.printHeader();
        this.before();

        //run 1000 times
        for (int i = 0; i < ITERATIONS; i++) {
            long time = System.currentTimeMillis();

            this.runTest();

            this.times.add(System.currentTimeMillis() - time);
        }

        this.after();
        this.printTimes();
    }

    protected void before() {}

    protected abstract void runTest();

    protected void after() {}

    protected abstract String getName();

    private void printHeader() {
        int l = this.getName().length() + 4;

        System.err.print("\n");
        printSeparator(l);
        System.err.println("= " + this.getName() + " =");
        printSeparator(l);

        System.err.println("Running...");
    }

    private void printTimes() {
        Collections.sort(times);

        times.forEach(t -> average += t);
        average = average / times.size();

        System.err.println("Complete\n");

        System.err.println("Average time: " + average + " ms");
        System.err.println("Median time: " + times.get((times.size() - 1) / 2) + " ms");
    }

    private void printSeparator(int n) {
        for (int i = 0; i < n; i++) {
            System.err.print("=");
        }
        System.err.print("\n");
    }
}
