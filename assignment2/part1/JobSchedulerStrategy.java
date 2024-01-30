package part1;

import part1.aux_files.AJob;
import part1.aux_files.Pair;

import java.util.List;
import java.util.stream.Stream;

public interface JobSchedulerStrategy<K, V> {
    Stream<AJob<K,V>> emit();
    void output(Stream<Pair<K, List<V>>> stream);
}
