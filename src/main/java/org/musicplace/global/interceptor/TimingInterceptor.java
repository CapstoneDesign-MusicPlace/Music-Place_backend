package org.musicplace.global.interceptor;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class TimingInterceptor implements HandlerInterceptor {
    private final MeterRegistry meterRegistry;

    public TimingInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.nanoTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.nanoTime() - startTime;

        meterRegistry.timer("http.server.requests",
                "method", request.getMethod(),
                "uri", request.getRequestURI(),
                "status", String.valueOf(response.getStatus())
        ).record(duration, TimeUnit.NANOSECONDS);
    }
}
