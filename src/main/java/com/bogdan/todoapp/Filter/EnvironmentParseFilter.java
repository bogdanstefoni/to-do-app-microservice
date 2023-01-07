package com.bogdan.todoapp.Filter;

import com.bogdan.todoapp.Service.CustomRequestBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class EnvironmentParseFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(EnvironmentParseFilter.class);

    @Autowired
    Environment environment;

    @Autowired
    CustomRequestBean customRequestBean;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String port = environment.getProperty("local.server.port");
        customRequestBean.setEnvironment(port);
        logger.info("Application running on port {}", port);

        filterChain.doFilter(request, response);


    }
}
