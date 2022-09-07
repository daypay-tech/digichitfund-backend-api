package com.daypaytechnologies.digichitfund.master.gender.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.master.gender.request.SaveGenderRequest;

public interface GenderWritePlatformService {

    Response saveGender(SaveGenderRequest request);
}
