[![Build Status](https://travis-ci.org/danpersa/anaconda.svg?branch=master)](https://travis-ci.org/danpersa/anaconda)

## Basic Auth

To access the next endpoints you need to use Basic Auth

Username: user
Password printed when the app starts. Eg:

    Using default security password: 2c7388ec-d1c3-4620-9a4e-0b4306dbf122

## Hystrix

- [The Turbine Stream](http://localhost:8080/hystrix.stream)
- [The Dashboard](http://localhost:8080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8080%2Fhystrix.stream&title=Content)

## Metrics

- [The Metrics](http://localhost:8080/metrics)
- [The Health](http://localhost:8080/health)

## Config Properties

- [The Config Properties](http://localhost:8080/configprops)
- [The Env](http://localhost:8080/env)
- [The Routes](http://localhost:8080/mappings)

## Performance

Start the service in dev mode and try:

     wrk -t 8 -c 200 -d 10s http://localhost:8080/quote-cache

After some warm up (a few runs), you might get a result like this:

    Running 10s test @ http://localhost:8080/quote-cache
      8 threads and 100 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    13.27ms   23.64ms 313.09ms   94.80%
        Req/Sec     1.33k   207.26     2.24k    85.38%
      106333 requests in 10.02s, 28.70MB read
    Requests/sec:  10614.21
    Transfer/sec:      2.86MB
