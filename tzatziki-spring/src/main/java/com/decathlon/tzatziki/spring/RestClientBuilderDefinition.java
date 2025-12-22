package com.decathlon.tzatziki.spring;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.lang.reflect.Proxy;


@ConditionalOnClass(RestClient.Builder.class)
@Component
public class RestClientBuilderDefinition implements HttpInterceptorDefinition<RestClient.Builder> {
    @Override
    public RestClient.Builder rewrite(RestClient.Builder restClientBuilder) {
        return restClientBuilder.requestInterceptor(new ClientHttpRequestInterceptor() {
            @Override
            public @NotNull ClientHttpResponse intercept(
                    @NotNull HttpRequest request,
                    byte @NotNull [] body,
                    @NotNull ClientHttpRequestExecution execution) throws IOException {
                HttpRequest proxiedHttpRequest = (HttpRequest) Proxy.newProxyInstance(
                        request.getClass().getClassLoader(),
                        new Class[]{HttpRequest.class},
                        (proxy, method, args) -> switch (method.getName()) {
                            case "getURI" -> HttpInterceptor.remap(request.getURI());
                            default -> method.invoke(request, args);
                        });
                return execution.execute(proxiedHttpRequest, body);
            }
        });
    }
}
