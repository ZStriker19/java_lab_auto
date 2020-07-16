package hello;

import datadog.trace.api.interceptor.MutableSpan;
import io.opentracing.util.GlobalTracer;

public class Randomizer {

    public int randomize () throws InterruptedException {
        Thread.sleep(200L);
        return 6;
    }



}