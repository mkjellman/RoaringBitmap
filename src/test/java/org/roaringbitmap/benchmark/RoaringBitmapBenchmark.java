package org.roaringbitmap.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Main class for running all classes containing JMH benchmarks
 * for RoaringBitmap
 */
public class RoaringBitmapBenchmark {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(".*" + SerializationBenchmark.class.getSimpleName() + ".*")
                .include(".*" + NewBitmapBenchmark.class.getSimpleName() + ".*")
                .include(".*" + BitmapOperationsBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(5)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
