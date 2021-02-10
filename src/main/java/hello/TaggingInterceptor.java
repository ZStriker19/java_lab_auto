package hello;

import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class TaggingInterceptor implements TraceInterceptor {
    @Override
    public Collection<? extends MutableSpan> onTraceComplete(
            Collection<? extends MutableSpan> trace) {

        List<MutableSpan> taggedTrace = new ArrayList<>();
        for (final MutableSpan span : trace) {
            span.setTag("interceptor", "hit");

            String operationName = span.getOperationName().toString();

            if (operationName == "servlet.request") {
                span.setTag("new_trace_interceptor", "tag_added");
            }
        }
        return trace;
    }



    @Override
    public int priority() {
        // some high unique number so this interceptor is last
        return 100;
    }
}
