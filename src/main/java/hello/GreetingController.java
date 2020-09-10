package hello;


import datadog.trace.api.CorrelationIdentifier;
import datadog.trace.api.DDTags;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

import datadog.trace.api.Trace;
import datadog.trace.api.interceptor.MutableSpan;

import io.opentracing.tag.Tags;


import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.aopalliance.intercept.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import java.nio.charset.StandardCharsets;

//import datadog.trace.api.DDTags;

////Users/zach.groves/projects/my_sandboxes/java_apm_lab/lab1/SpringTest0/src/main/java/hello/GreetingController.java
//import io.opentracing.Scope;
//import io.opentracing.Tracer;
//import io.opentracing.util.GlobalTracer;
//import io.opentracing.propagation.Format;
//import io.opentracing.propagation.TextMap;

//import io.opentracing.util.GlobalTracer;
//because you need datadog.trace.api.DDTags in order to create a custom span with a custom service name you need to
//specify dd-trace-ot as a dependency

import java.io.IOException;




@RestController
public class GreetingController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    HttpServletRequest request;

//    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Value("#{environment['sleeptime'] ?: '2000'}")
    private long sleepTime;


    @RequestMapping("/ServiceC")
    public String serviceC(HttpServletRequest request) throws InterruptedException, IOException {
        //my map I'm creating for testing my own code:
        Map<String, String> map_test_z = new HashMap<>();

        System.out.println(String.valueOf(CorrelationIdentifier.getTraceId()));
//        logger.info("In Service C ***************");
//
//        logger.debug("Hello world.");




        MutableSpan span = ((MutableSpan) GlobalTracer.get().activeSpan());
        MutableSpan localRootSpan = ((MutableSpan) span).getLocalRootSpan();
        localRootSpan.getServiceName();
        localRootSpan.setTag(DDTags.RESOURCE_NAME, "Maximo");
        System.out.println("here's the status");
        System.out.println(span.getTags().get("http.status"));
        span.setTag("ok", "pickachu");
//        String orderId = (String) span.getTags().get("http.status");
        localRootSpan.setTag("http.status", (String) span.getTags().get("http.status"));
        localRootSpan.setTag("da_heck", (String) span.getTags().get("ok"));
        localRootSpan.setTag("this.one", "pull");

        for (Map.Entry<String, Object> entry : span.getTags().entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());

        doSomeStuff("ok");

        Randomizer rando = new Randomizer();
        rando.randomize();

        Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print(key + "\n" + value + "\n");
            map_test_z.put(key, value);
        }
        Thread.sleep(50L);
        System.out.println("Right before multiply");
        // multiplyBy12((float) 23.3);


        doSomeOtherStuff(doSomeStuff("\n how about this but really"));


        //Post to downsteam service using OKhttp specifically
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("http://localhost:9393/ServiceD")
                .addHeader("this is a header", "here it is")
                .build();

        Response response = client.newCall(req).execute();

        String rs = response.body().string();

//Post to downstream service original
        //put headers into HttpEntity object for them to be sent over
        //HttpEntity<String> entity = new HttpEntity<>("body");
        // String rs = restTemplate.postForEntity("http://localhost:9393/ServiceD", entity, String.class).getBody();

        return rs;
    }


    @RequestMapping("/ServiceX")
    public String serviceX(HttpServletRequest request) throws InterruptedException, IOException {


        HttpEntity<String> entity = new HttpEntity<>("body");
        String rs = restTemplate.postForEntity("http://localhost:9393/ServiceY", entity, String.class).getBody();

        return rs;
    }



    @Trace(operationName = "job.exec", resourceName = "MyJob.process")
    public String doSomeStuff (String somestring) throws InterruptedException {
        String helloStr = String.format("Hello, %s!", somestring);
        Thread.sleep(200L);
        return helloStr;
    }


    public void doSomeOtherStuff (String somestring) throws InterruptedException {
        Tracer tracer = GlobalTracer.get();
//        try (Scope scope = tracer.buildSpan("doSomeOtherStuff").startActive(true)) {
//            scope.span().setTag("Service", "doSomeOtherStuffService");
//
//            Thread.sleep(1000);
//        }
        System.out.println(somestring);
        Thread.sleep(200L);
    }

    @Trace(operationName = "multiply.twelve", resourceName = "ToTry.process")
    private float multiplyBy12 (Float num) throws InterruptedException {
        num = num * 12;
        System.out.print("here's the number:");
        System.out.print(num);
        Thread.sleep(250L);
        return num;
    }


}
