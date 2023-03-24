package br.com.cardoso.trace;

import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.http.HttpClientHandler;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;

public class ClientTracingFeature implements Feature {

    final CurrentTraceContext currentTraceContext;
    final HttpClientHandler handler;

    public ClientTracingFeature(CurrentTraceContext currentTraceContext, HttpClientHandler handler) {
        this.currentTraceContext = currentTraceContext;
        this.handler = handler;
    }

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new ClientTracingFilter(currentTraceContext, handler));
        return true;
    }
}
