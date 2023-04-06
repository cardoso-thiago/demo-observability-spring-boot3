package br.com.cardoso.configuration;

import io.micrometer.tracing.otel.bridge.EventListener;
import io.micrometer.tracing.otel.bridge.OtelSpanCustomizer;
import io.opentelemetry.api.trace.Span;
import org.slf4j.MDC;

public class CorrelationIdEventListener implements EventListener {

    private final OtelSpanCustomizer otelSpanCustomizer;

    public CorrelationIdEventListener(OtelSpanCustomizer otelSpanCustomizer) {
        this.otelSpanCustomizer = otelSpanCustomizer;
    }
    @Override
    public void onEvent(Object event) {
        System.out.println(Span.current().getSpanContext().getTraceId());
        otelSpanCustomizer.tag("correlationId", MDC.get("correlationId"));
    }
}
