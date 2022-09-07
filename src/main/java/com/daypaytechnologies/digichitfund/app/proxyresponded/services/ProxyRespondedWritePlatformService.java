package com.daypaytechnologies.digichitfund.app.proxyresponded.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.proxyresponded.request.ProxyRespondedRequest;

public interface ProxyRespondedWritePlatformService {

    Response saveProxyResponded(ProxyRespondedRequest request);
}
