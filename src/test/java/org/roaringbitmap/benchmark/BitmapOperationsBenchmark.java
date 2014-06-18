package org.roaringbitmap.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.roaringbitmap.RoaringBitmap;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Benchmarks various bitmap operations such as OR/AND/XOR
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BitmapOperationsBenchmark {

    @State(Scope.Thread)
    public static class ThreadState {
        HashMap<Integer, RoaringBitmap> generatedBenchmarkData = new HashMap<>();
        int[] sizesToGenerate = new int[]{1, 12, 24, 128, 256, 1024, 5012, 10024, 20048, 40096, 80192, 160384};

        public ThreadState() {
            for(int size : sizesToGenerate) {
                RoaringBitmap rr = new RoaringBitmap();
                for (int k = 0; k < size; ++k) {
                    rr.add(k);
                }

                generatedBenchmarkData.put(size, rr);
            }
        }
    }

    private void and(int reps, RoaringBitmap rr1, RoaringBitmap rr2) throws Exception {
        for (int i = 0; i < reps; i++) {
            RoaringBitmap.and(rr1, rr2);
        }
    }

    private void or(int reps, RoaringBitmap rr1, RoaringBitmap rr2) throws Exception {
        for (int i = 0; i < reps; i++) {
            RoaringBitmap.or(rr1, rr2);
        }
    }

    private void xor(int reps, RoaringBitmap rr1, RoaringBitmap rr2) throws Exception {
        for (int i = 0; i < reps; i++) {
            RoaringBitmap.xor(rr1, rr2);
        }
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void and_12_with_128(ThreadState state) throws Exception{
        and(10, state.generatedBenchmarkData.get(12), state.generatedBenchmarkData.get(128));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void and_128_with_128(ThreadState state) throws Exception{
        and(10, state.generatedBenchmarkData.get(128), state.generatedBenchmarkData.get(128));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void and_24_with_160384(ThreadState state) throws Exception{
        and(10, state.generatedBenchmarkData.get(24), state.generatedBenchmarkData.get(160384));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void or_12_with_128(ThreadState state) throws Exception{
        or(10, state.generatedBenchmarkData.get(12), state.generatedBenchmarkData.get(128));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void or_128_with_128(ThreadState state) throws Exception{
        or(10, state.generatedBenchmarkData.get(128), state.generatedBenchmarkData.get(128));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void or_24_with_160384(ThreadState state) throws Exception{
        or(10, state.generatedBenchmarkData.get(24), state.generatedBenchmarkData.get(160384));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void xor_12_with_128(ThreadState state) throws Exception{
        xor(10, state.generatedBenchmarkData.get(12), state.generatedBenchmarkData.get(128));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void xor_128_with_128(ThreadState state) throws Exception{
        xor(10, state.generatedBenchmarkData.get(128), state.generatedBenchmarkData.get(128));
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void xor_24_with_160384(ThreadState state) throws Exception{
        xor(10, state.generatedBenchmarkData.get(24), state.generatedBenchmarkData.get(160384));
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(".*" + BitmapOperationsBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(5)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
