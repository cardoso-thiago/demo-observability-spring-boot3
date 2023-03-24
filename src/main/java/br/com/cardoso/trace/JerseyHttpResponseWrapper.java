package br.com.cardoso.trace;

import io.micrometer.tracing.http.HttpClientResponse;
import jakarta.ws.rs.client.ClientResponseContext;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Collection;
import java.util.Collections;

public class JerseyHttpResponseWrapper implements HttpClientResponse {

    final JerseyHttpRequestWrapper request;

    final ClientResponseContext response;

    final Throwable error;

    @Override
    public Object unwrap() {
        return response;
    }

    JerseyHttpResponseWrapper(JerseyHttpRequestWrapper request, ClientResponseContext response, Throwable error) {
        this.request = request;
        this.response = response;
        this.error = error;
    }

    JerseyHttpResponseWrapper(JerseyHttpRequestWrapper request, ClientResponseContext response) {
        this.request = request;
        this.response = response;
        this.error = null;
    }

    @Override
    public Collection<String> headerNames() {
        return this.response != null ? this.response.getHeaders().keySet() : Collections.emptyList();
    }

    @Override
    public JerseyHttpRequestWrapper request() {
        return request;
    }

    @Override
    public Throwable error() {
        return error;
    }

    @Override
    public int statusCode() {
        try {
            int result = response != null ? response.getStatus() : 0;
            if (result <= 0 && error instanceof HttpStatusCodeException) {
                result = ((HttpStatusCodeException) error).getRawStatusCode();
            }
            return result;
        } catch (Exception e) {
            return 0;
        }
    }
}
