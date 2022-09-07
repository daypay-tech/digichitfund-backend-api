package com.daypaytechnologies.digichitfund.app.proxyresponded.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.proxyresponded.request.ProxyRespondedRequest;
import com.daypaytechnologies.digichitfund.app.proxyresponded.services.ProxyRespondedWritePlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/proxyResponded")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ProxyRespondedApiResource")
public class ProxyRespondedApiResource {

    private final ProxyRespondedWritePlatformService proxyRespondedWritePlatformService;

    @PostMapping
    public Response saveProxyResponded(@RequestBody ProxyRespondedRequest request){
        return this.proxyRespondedWritePlatformService.saveProxyResponded(request);
    }
}
