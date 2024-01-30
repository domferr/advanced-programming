package part1;

public class Main {

    public static void main(String[] args) {
        CountAnagramsStrategy countAnagramsStrategy = new CountAnagramsStrategy();
        JobSchedulerContext<String, String> jobSchedulingContext = new JobSchedulerContext<>(countAnagramsStrategy);
        jobSchedulingContext.runScheduling();
    }
}
