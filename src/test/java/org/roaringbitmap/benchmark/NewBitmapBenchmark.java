package org.roaringbitmap.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.roaringbitmap.RoaringBitmap;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Basic benchmarks on the speed of creating a new RoaringBitmap
 * and populating it with various amounts of data
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class NewBitmapBenchmark {
    final Random random = new Random();

    private void createRandom(int reps, int numElmsToAdd) throws Exception {
        for (int i = 0; i < reps; i++) {
            RoaringBitmap rr = new RoaringBitmap();
            for(int k = 0; k < numElmsToAdd; k++) {
                rr.add(random.nextInt(300000));
            }
        }
    }

    private void create(int reps, int numElmsToAdd, int skip) throws Exception {
        if (skip > 0) {
            numElmsToAdd *= skip;
        } else {
            skip = 1;
        }

        for (int i = 0; i < reps; i++) {
            RoaringBitmap rr = new RoaringBitmap();
            for(int k = 0; k < numElmsToAdd; k += skip) {
                rr.add(k);
            }
        }
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_1_with_skip_0() throws Exception{
        create(10, 1, 0);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_100_with_skip_0() throws Exception{
        create(10, 100, 0);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_100_with_skip_10() throws Exception{
        create(10, 100, 10);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_25000_with_skip_5() throws Exception{
        create(10, 25000, 5);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_100000_with_skip_100() throws Exception{
        create(10, 100000, 100);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_100_random() throws Exception{
        createRandom(10, 100);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_2500_random() throws Exception{
        createRandom(10, 2500);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void create_and_add_100000_random() throws Exception{
        createRandom(10, 100000);
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(".*" + NewBitmapBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(5)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
