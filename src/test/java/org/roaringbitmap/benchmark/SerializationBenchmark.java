package org.roaringbitmap.benchmark;

import org.apache.commons.math3.util.Pair;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.roaringbitmap.RoaringBitmap;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Benchmarks for Serialization/Deserialization of RoaringBitmap Objects
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SerializationBenchmark {

    @State(Scope.Thread)
    public static class ThreadState {
        HashMap<Integer, Pair<RoaringBitmap, ByteArrayOutputStream>> generatedBenchmarkData = new HashMap<>();
        int[] sizesToGenerate = new int[]{1, 12, 24, 128, 256, 1024, 5012, 10024, 20048, 40096, 80192, 160384};

        public ThreadState() {
            for(int size : sizesToGenerate) {
                RoaringBitmap rr = new RoaringBitmap();
                for (int k = 0; k < size; ++k) {
                    rr.add(k);
                }

                ByteArrayOutputStream bos;
                try {
                    bos = new ByteArrayOutputStream();
                    ObjectOutputStream oo = new ObjectOutputStream(bos);
                    rr.writeExternal(oo);
                    oo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                generatedBenchmarkData.put(size, new Pair<>(rr, bos));
            }
        }
    }

    private void serialize(int reps, RoaringBitmap rr) throws IOException {
        for (int i = 0; i < reps; i++) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bos);
            rr.writeExternal(oo);
            oo.close();
        }
    }

    private void deserialize(int reps, ByteArrayOutputStream bos) throws Exception {
        for (int i = 0; i < reps; i++) {
            RoaringBitmap rrback = new RoaringBitmap();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            rrback.readExternal(new ObjectInputStream(bis));
        }
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_1(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(1).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_1(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(1).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_12(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(12).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_12(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(12).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_24(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(24).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_24(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(24).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_128(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(128).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_128(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(128).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_256(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(256).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_256(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(256).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_1024(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(1024).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_1024(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(1024).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_5012(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(5012).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_5012(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(5012).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_10024(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(10024).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_10024(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(10024).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_20048(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(20048).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_20048(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(20048).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_40096(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(40096).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_40096(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(40096).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_80192(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(80192).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_80192(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(80192).getSecond());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void serialize_160384(ThreadState state) throws Exception{
        serialize(10, state.generatedBenchmarkData.get(160384).getFirst());
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public void deserialize_160384(ThreadState state) throws Exception{
        deserialize(10, state.generatedBenchmarkData.get(160384).getSecond());
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(".*" + SerializationBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(5)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
