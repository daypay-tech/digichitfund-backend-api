package com.daypaytechnologies.digichitfund.master.gender.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.master.gender.data.GenderData;
import com.daypaytechnologies.digichitfund.master.gender.request.SaveGenderRequest;
import com.daypaytechnologies.digichitfund.master.gender.services.GenderReadPlatformService;
import com.daypaytechnologies.digichitfund.master.gender.services.GenderWritePlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/genders")
@RestController
public class GenderApiResource {

    private final GenderReadPlatformService genderReadPlatformService;

    private final GenderWritePlatformService genderWritePlatformService;

    @PostMapping
    public Response saveGender(@RequestBody SaveGenderRequest request){
        return this.genderWritePlatformService.saveGender(request);
    }
    @GetMapping
    public List<GenderData> fetchAll(){
        return this.genderReadPlatformService.fetchAll();
    }
}
