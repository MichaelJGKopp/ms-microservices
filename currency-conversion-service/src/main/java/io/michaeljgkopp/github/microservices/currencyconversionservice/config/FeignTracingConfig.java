//package io.michaeljgkopp.github.microservices.currencyconversionservice.config;
//
//import io.opentelemetry.api.GlobalOpenTelemetry;
//import io.opentelemetry.context.Context;
//import io.opentelemetry.context.propagation.TextMapSetter;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignTracingConfig {
//
//    private static final TextMapSetter<RequestTemplate> setter = new TextMapSetter<>() {
//        @Override
//        public void set(RequestTemplate carrier, String key, String value) {
//            if (carrier != null) {
//                carrier.header(key, value);
//            }
//        }
//    };
//
//    @Bean
//    public RequestInterceptor otelFeignInterceptor() {
//        return new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate template) {
//                // Retrieve the current OpenTelemetry context
//                Context context = Context.current();
//                // Use the global OpenTelemetry propagator to inject tracing headers into the template
//                GlobalOpenTelemetry.getPropagators().getTextMapPropagator()
//                        .inject(context, template, setter);
//            }
//        };
//    }
//}
