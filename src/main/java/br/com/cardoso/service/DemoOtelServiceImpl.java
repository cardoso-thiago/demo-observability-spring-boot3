package br.com.cardoso.service;

import br.com.cardoso.trace.ClientTracingFeature;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.http.HttpClientHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoOtelServiceImpl {

    private final RestTemplate restTemplate;
    private final CurrentTraceContext currentTraceContext;
    private final HttpClientHandler httpClientHandler;

    public DemoOtelServiceImpl(RestTemplate restTemplate
            , CurrentTraceContext currentTraceContext, HttpClientHandler httpClientHandler
    ) {
        this.restTemplate = restTemplate;
        this.currentTraceContext = currentTraceContext;
        this.httpClientHandler = httpClientHandler;
    }

    public String hello() {
        String path = "http://localhost:8080/world";
        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);
        return response.getBody();
    }

    public String helloJersey() {
        String path = "http://localhost:8080/world";
        Client client = ClientBuilder.newBuilder().build();
        WebTarget webTarget = client
                .register(new ClientTracingFeature(currentTraceContext, httpClientHandler))
                .target(path);
        Response response = webTarget.request().get();
        return response.readEntity(String.class);
    }
}
