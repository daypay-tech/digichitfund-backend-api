package com.daypaytechnologies.digichitfund.infrastructure;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseList {

    public List<Response> responseList;

    public ResponseList() {

    }

    public void addResponse(final Long id) {
        if(responseList == null) {
            responseList = new ArrayList<>();
        }
        responseList.add(Response.of(id));
    }
}
