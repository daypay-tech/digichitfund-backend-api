package com.daypaytechnologies.digichitfund.app.pollanswer.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.ResponseList;
import com.daypaytechnologies.digichitfund.app.pollanswer.data.PollAnswerData;
import com.daypaytechnologies.digichitfund.app.pollanswer.request.CreatePollAnswerRequest;
import com.daypaytechnologies.digichitfund.app.pollanswer.services.PollAnswerReadPlatformService;
import com.daypaytechnologies.digichitfund.app.pollanswer.services.PollAnswerWritePlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PollAnswerApiResource")
public class PollAnswerApiResource {

    private final PollAnswerWritePlatformService pollAnswerWritePlatformService;

    private final PollAnswerReadPlatformService pollAnswerReadPlatformService;

    @PostMapping
    public Response save(@RequestBody CreatePollAnswerRequest request,
                         @RequestParam(name = "proxyRespondedId", required = false) Long proxyRespondedId) {
        return this.pollAnswerWritePlatformService.saveAnswer(request, proxyRespondedId);
    }
    @GetMapping
    public List<PollAnswerData> fetchAll(){
        return this.pollAnswerReadPlatformService.fetchAll();
    }

//    @GetMapping
//    public Page<PollAnswerData> fetchAll(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
//                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
//        return this.pollAnswerReadPlatformService.fetchAll(page, pageSize);
//    }


    @PostMapping("/saveBulk")
    public ResponseList saveBulk(@RequestBody List<CreatePollAnswerRequest> request,
                                 @RequestParam(name = "pollId") Long pollId,
                                 @RequestParam(name = "proxyRespondedId", required = false) Long proxyRespondedId) {
        return this.pollAnswerWritePlatformService.saveAnswer(pollId, request, proxyRespondedId);
    }
}
