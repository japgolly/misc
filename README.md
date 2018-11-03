See https://github.com/graalvm/graaljs/issues/67

To execute, run:

```
sbt run
```

Sample output:

```
[info] ================================================================================
[info] Warming up (10000) ...
[info] Benchmarking (100) ...
[info] p50 =   1 ms
[info] p90 =   1 ms
[info] p95 =   1 ms
[info] p98 =   2 ms
[info] p99 =   2 ms
[info] ================================================================================
[info] Benchmarking (100) ...
[info] p50 =  18 ms
[info] p90 =  32 ms
[info] p95 =  35 ms
[info] p98 =  45 ms
[info] p99 = 303 ms
[info] ================================================================================
[success] Total time: 64 s, completed 03/11/2018 11:06:18 PM
```
