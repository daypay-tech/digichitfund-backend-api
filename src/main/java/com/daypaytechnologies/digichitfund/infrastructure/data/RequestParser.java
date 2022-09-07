package com.daypaytechnologies.digichitfund.infrastructure.data;
import com.daypaytechnologies.digichitfund.infrastructure.request.IdRequest;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestParser {

    /**
     * Concat request body id list to str
     * @param idList
     * @return
     */
    public String joinIds(final List<IdRequest> idList) {
        return idList
                .stream()
                .map(a -> String.valueOf(a.getId()))
                .collect(Collectors.joining(","));
    }
}
