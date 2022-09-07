package com.daypaytechnologies.digichitfund.master.age.services;

import com.daypaytechnologies.digichitfund.master.age.data.AgeData;

import java.util.List;

public interface AgeReadPlatformService {

    List<AgeData> fetchAll();

    AgeData fetchById(Long id);
}
