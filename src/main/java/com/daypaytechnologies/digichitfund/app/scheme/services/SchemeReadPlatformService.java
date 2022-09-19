package com.daypaytechnologies.digichitfund.app.scheme.services;

import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeData;
import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeTemplateData;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;

import java.util.List;

public interface SchemeReadPlatformService {
    Page<SchemeData> fetchAll(int page, int pageSize);

    SchemeTemplateData fetchTemplateData();

    List<SchemeData> fetchAll();

    SchemeData fetchBySchemeId(Long schemeId);

}
