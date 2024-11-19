package com.yummy.puding.genshin.database.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

@Component
public class InterceptorConfig implements HandlerInterceptor {
    private static final Logger log = LogManager.getLogger(InterceptorConfig.class);

    @Value("${spring.application.name}")
    private String app;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("corrId", correlationId);

        log.info("======== Started {} {}://{}:{}{} ==========", request.getMethod(), request.getScheme(), request.getServerName(), request.getServerPort(), request.getRequestURI());
//        log.info(" {} {}://{}:{}{}", request.getMethod(), request.getScheme(), request.getServerName(), request.getServerPort(), request.getRequestURI());

        Long start = System.nanoTime();
        MDC.put("startTime", String.valueOf(start));

        response.setHeader("CorrId", correlationId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Long start = Long.parseLong(MDC.get("startTime"));
        Long end = System.nanoTime();
        long elapsedTimeInMillis = (end - start) / 1_000_000;
        String elapsedTime;
        if (elapsedTimeInMillis > 1000) {
            elapsedTime = (elapsedTimeInMillis / 1000) + " seconds";
        } else {
            elapsedTime = elapsedTimeInMillis + " ms";
        }
        log.info("======== Finished in {} ==========", elapsedTime);
        MDC.remove("start");
        MDC.remove("corrId");
    }
}