package part1;

import part1.aux_files.AJob;
import part1.aux_files.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JobSchedulerContext<K, V> {
    private JobSchedulerStrategy<K, V> strategy;

    public JobSchedulerContext(JobSchedulerStrategy<K, V> strategy) {
        this.strategy = strategy;
    }

    public final void runScheduling() {
        strategy.output(collect(compute(strategy.emit())));
    }

    public void setStrategy(JobSchedulerStrategy<K, V> newStrategy) {
        this.strategy = newStrategy;
    }

    private Stream<Pair<K,V>> compute(Stream<AJob<K,V>> jobs) {
        return jobs.flatMap(AJob::execute);
    }

    private Stream<Pair<K, List<V>>> collect(Stream<Pair<K,V>> pairs) {
        return pairs
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())))
                .entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
    }
}