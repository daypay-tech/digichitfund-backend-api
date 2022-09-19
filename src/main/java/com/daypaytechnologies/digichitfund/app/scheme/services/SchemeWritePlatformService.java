package com.daypaytechnologies.digichitfund.app.scheme.services;

import com.daypaytechnologies.digichitfund.app.scheme.domain.Scheme;
import com.daypaytechnologies.digichitfund.app.scheme.request.CreateSchemeRequest;
import com.daypaytechnologies.digichitfund.app.scheme.request.UpdateSchemeRequest;
import com.daypaytechnologies.digichitfund.infrastructure.Response;

public interface SchemeWritePlatformService {

    Response save(CreateSchemeRequest request);

    Response update(Long id, UpdateSchemeRequest request);

    Scheme delete(Long id);
}
