package com.daypaytechnologies.digichitfund.app.pollquestionoption.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.request.UpdatePollQuestionOptionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.services.PollQuestionOptionReadPlatformService;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.services.PollQuestionOptionWritePlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questionOptions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PollQuestionOptionApiResource")
public class PollQuestionOptionApiResource {

    private final PollQuestionOptionReadPlatformService pollQuestionOptionReadPlatformService;

    private final PollQuestionOptionWritePlatformService pollQuestionOptionWritePlatformService;

    @PutMapping("/{id}")
    public Response updateOption(@PathVariable Long id, @RequestBody UpdatePollQuestionOptionRequest request){
        return this.pollQuestionOptionWritePlatformService.updateOption(id, request);
    }
}
