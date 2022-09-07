package com.daypaytechnologies.digichitfund.security.services;

import com.google.gson.Gson;
import com.daypaytechnologies.digichitfund.infrastructure.controlleradvice.ApiErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Gson gson = new Gson();
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setStatusCode(401);
        apiErrorInfo.setErrorCode("error.msg.invalid.login.credentials");
        apiErrorInfo.setMessage("Either username or password wrong");
        String result = gson.toJson(apiErrorInfo);
        response.getOutputStream().println(result);
    }
}
