package com.daypaytechnologies.digichitfund.app.scheme.api;

import com.daypaytechnologies.digichitfund.app.enumoptions.CalendarTypeEnum;
import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeData;
import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeTemplateData;
import com.daypaytechnologies.digichitfund.app.scheme.domain.Scheme;
import com.daypaytechnologies.digichitfund.app.scheme.request.CreateSchemeRequest;
import com.daypaytechnologies.digichitfund.app.scheme.request.UpdateSchemeRequest;
import com.daypaytechnologies.digichitfund.app.scheme.services.SchemeReadPlatformService;
import com.daypaytechnologies.digichitfund.app.scheme.services.SchemeWritePlatformService;
import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schemes")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Scheme")

public class SchemeApiResource {
    private final SchemeWritePlatformService schemeWritePlatformService;

    private final SchemeReadPlatformService schemeReadPlatformService;

    @PostMapping
    public Response saveScheme(@RequestBody CreateSchemeRequest request){
        return this.schemeWritePlatformService.save(request);
    }

    @GetMapping
    public Page<SchemeData> fetchAll(@RequestParam (name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.schemeReadPlatformService.fetchAll(page, pageSize);
    }

    @GetMapping("/templates")
    public SchemeTemplateData templates(){
        return this.schemeReadPlatformService.fetchTemplateData();
    }

    @GetMapping("/{id}")
    public SchemeData getSchemeById(@PathVariable Long id) {
        final SchemeData schemeData = this.schemeReadPlatformService.fetchBySchemeId(id);
        schemeData.setCalendarList(CalendarTypeEnum.getAllCalendarTypes());
        return schemeData;
    }
    @PutMapping("/{id}")
    public Response updateScheme(@PathVariable Long id, @RequestBody UpdateSchemeRequest request){
        return this.schemeWritePlatformService.update(id, request);
    }
    @DeleteMapping("/{id}")
    public Response deleteSchemeById(@PathVariable Long id) {
        final Scheme scheme = this.schemeWritePlatformService.delete(id);
        return Response.of(scheme.getId());
    }

}
