import time
import functools
import threading
import json
import statistics

def just_wait(n):
    time.sleep(n * 0.01)

def grezzo(n):
    for i in range(2**n):
        pass

def fork_join(n_threads, func, *args, **kwargs):
    # save all the threads created to join them later
    threads = [] 

    # fork n_threads
    for _ in range(n_threads):
        t = threading.Thread(target=func, args=args, kwargs=kwargs)
        threads.append(t)
        t.start() # run the thread

    # join all the threads. Blocking call
    for t in threads:
        t.join()

def bench(n_threads=1, seq_iter=1, iter=1):
    """Benchmark the decorated function"""
    
    def decorator_bench(func):
        @functools.wraps(func)
        def wrapper_bench(*args, **kwargs):
            def runner(*args, **kwargs):
                """Wrapper function to run func seq_iter times"""
                for _ in range(seq_iter):
                    func(*args, **kwargs)

            # save all the #iter results in a list
            run_times = []

            for _ in range(iter):
                start_time = time.perf_counter()

                fork_join(n_threads, runner, *args, **kwargs)

                end_time = time.perf_counter()
                run_times.append(end_time - start_time)

            # compute mean and variance
            mean = statistics.mean(run_times)
            variance = statistics.variance(run_times, xbar=mean) if len(run_times) > 1 else 0

            return {
                "fun": func.__name__,
                "args": args,
                "n_threads": n_threads,
                "seq_iter": seq_iter,
                "iter": iter,
                "mean": mean,
                "variance": variance,
            }
        return wrapper_bench
    return decorator_bench

@bench(n_threads=2)
def decorator_example():
    """An example that demonstrates that it is possible to use the @bench decorator with some arguments"""
    just_wait(20)

def test(fun, *args, iter=1):
    for seq_iter in [16, 8, 4, 2]:
        n_threads = int(16 / seq_iter)
        res = bench(n_threads=n_threads, seq_iter=seq_iter, iter=iter)(fun)(*args)
        
        # save res to json file
        args_str = f"{str(args).lstrip('(').rstrip('),').replace(',','-')}"
        filename = f"{fun.__name__}_{args_str}_{n_threads}_{seq_iter}.json"
        with open(filename, 'w') as file:
            json.dump(res, file)

if __name__ == "__main__":
    test(just_wait, 10, iter=8)
    test(grezzo, 18, iter=8)

"""
The experiment's results highlight two distinct behaviors:

- The test applied to the "just_wait" function demonstrates that when the number 
  of threads doubles, the execution time halves.
- The test applied to the "grezzo" function indicates that when the number of 
  threads doubles, the execution time remains nearly the same, with a negligible 
  degree of error.

These findings validate the claim that "Two threads calling a function may take 
twice as much time as a single thread calling the function twice" when considering 
the results related to the "grezzo" function. However, the results with the 
"just_wait" function suggest that the claim may not hold true in all scenarios.
The crucial distinction is that the "grezzo" function is CPU-intensive, while the 
"just_wait" function is I/O-intensive. As a consequence, when the "just_wait" 
function calls the "sleep" function, its thread releases the GIL, allowing the next 
thread to be scheduled and acquire the GIL.

In summary, experiment's results highlight that, while the claim holds for 
CPU-intensive functions like "grezzo", it may not hold for I/O-intensive functions 
like "just_wait" due to the different behaviors influenced by GIL release during 
I/O operations.
"""