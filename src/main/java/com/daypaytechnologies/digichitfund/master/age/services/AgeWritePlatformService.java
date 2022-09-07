package com.daypaytechnologies.digichitfund.master.age.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.master.age.request.CreateAgeRequest;

public interface AgeWritePlatformService {

    Response saveAge(CreateAgeRequest request);

    Response updateAge(Long id, CreateAgeRequest request);

}
