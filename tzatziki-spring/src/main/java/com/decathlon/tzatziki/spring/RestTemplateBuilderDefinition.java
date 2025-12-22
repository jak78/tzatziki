package com.decathlon.tzatziki.spring;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Proxy;


/**
 * RestTemplateBuilder support for Spring Boot 3.x
 * This class is only compiled and loaded when RestTemplateBuilder is available (Spring Boot 3.x)
 * In Spring Boot 4.x, use RestClientBuilderDefinition instead.
 */
@ConditionalOnClass(name = "org.springframework.boot.web.client.RestTemplateBuilder")
@Component
public class RestTemplateBuilderDefinition implements HttpInterceptorDefinition<Object> {
    @Override
    public Object rewrite(Object restTemplateBuilder) {
        // Use reflection to avoid compile-time dependency on RestTemplateBuilder
        try {
            Class<?> builderClass = Class.forName("org.springframework.boot.web.client.RestTemplateBuilder");
            java.lang.reflect.Method method = builderClass.getMethod("additionalInterceptors", 
                org.springframework.http.client.ClientHttpRequestInterceptor[].class);
            
            ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
                @Override
                public @NotNull ClientHttpResponse intercept(
                        @NotNull HttpRequest request,
                        byte @NotNull [] body,
                        @NotNull ClientHttpRequestExecution execution) throws IOException {
                    HttpRequest proxiedHttpRequest = (HttpRequest) Proxy.newProxyInstance(
                            request.getClass().getClassLoader(),
                            new Class[]{HttpRequest.class},
                            (proxy, proxyMethod, args) -> switch (proxyMethod.getName()) {
                                case "getURI" -> HttpInterceptor.remap(request.getURI());
                                default -> proxyMethod.invoke(request, args);
                            });
                    return execution.execute(proxiedHttpRequest, body);
                }
            };
            
            return method.invoke(restTemplateBuilder, new ClientHttpRequestInterceptor[]{interceptor});
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure RestTemplateBuilder", e);
        }
    }
}
