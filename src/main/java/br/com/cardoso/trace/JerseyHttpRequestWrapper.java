package br.com.cardoso.trace;

import io.micrometer.tracing.http.HttpClientRequest;
import jakarta.ws.rs.client.ClientRequestContext;

import java.util.Collection;

public class JerseyHttpRequestWrapper implements HttpClientRequest {

    final ClientRequestContext delegate;

    JerseyHttpRequestWrapper(ClientRequestContext delegate) {
        this.delegate = delegate;
    }

    @Override
    public Collection<String> headerNames() {
        return this.delegate.getHeaders().keySet();
    }

    @Override
    public Object unwrap() {
        return delegate;
    }

    @Override
    public String method() {
        return delegate.getMethod();
    }

    @Override
    public String path() {
        return delegate.getUri().getPath();
    }

    @Override
    public String url() {
        return delegate.getUri().toString();
    }

    @Override
    public String header(String name) {
        Object result = delegate.getHeaders().getFirst(name);
        return result != null ? result.toString() : null;
    }

    @Override
    public void header(String name, String value) {
        delegate.getHeaders().add(name, value);
    }

}