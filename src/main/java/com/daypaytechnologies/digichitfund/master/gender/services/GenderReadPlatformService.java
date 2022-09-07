package com.daypaytechnologies.digichitfund.master.gender.services;

import com.daypaytechnologies.digichitfund.master.gender.data.GenderData;

import java.util.List;

public interface GenderReadPlatformService {

    List<GenderData> fetchAll();

}
