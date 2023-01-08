package br.com.cardoso.configuration;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OtelConfiguration {

    @Bean
    public SdkTracerProvider otelSdkTracerProvider(Environment environment, ObjectProvider<SpanProcessor> spanProcessors,
                                            Sampler sampler) {
        String applicationName = environment.getProperty("application.name", "application");
        SdkTracerProviderBuilder builder = SdkTracerProvider.builder().setSampler(sampler)
                .setResource(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, applicationName)));
        spanProcessors.orderedStream().forEach(builder::addSpanProcessor);
        return builder.build();
    }

    //Configura o tracing para o RestTemplate através da classe TracingClientHttpRequestInterceptor
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
