package com.daypaytechnologies.digichitfund.master.age.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.master.age.data.AgeData;
import com.daypaytechnologies.digichitfund.master.age.request.CreateAgeRequest;
import com.daypaytechnologies.digichitfund.master.age.services.AgeReadPlatformService;
import com.daypaytechnologies.digichitfund.master.age.services.AgeWritePlatformService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ages")
@RequiredArgsConstructor
@Slf4j
public class AgeApiResource {

    private final AgeReadPlatformService ageReadPlatformService;

    private final AgeWritePlatformService ageWritePlatformService;

    @PostMapping
    public Response saveAge(@RequestBody CreateAgeRequest request){
        return this.ageWritePlatformService.saveAge(request);
    }
    @GetMapping
    public List<AgeData> fetchAll(){
        return this.ageReadPlatformService.fetchAll();
    }
    @GetMapping("/{id}")
    public AgeData getAge(@PathVariable(name = "id") Long id){
        return this.ageReadPlatformService.fetchById(id);
    }

    @PutMapping("/{id}")
    public Response updateAge(@PathVariable (name = "id") Long id ,
                              @RequestBody CreateAgeRequest request){
        return this.ageWritePlatformService.updateAge(id, request);
    }
}
