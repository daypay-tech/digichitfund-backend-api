package com.daypaytechnologies.digichitfund.app.pollquestion.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionData;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionDetailsData;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.SubmitPollingQuestionData;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionBulkRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdateBulkQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.data.PollQuestionOptionData;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdatePollQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.services.PollQuestionOptionReadPlatformService;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.services.PollQuestionOptionWritePlatformService;
import com.daypaytechnologies.digichitfund.app.pollquestion.services.PollQuestionReadPlatformService;
import com.daypaytechnologies.digichitfund.app.pollquestion.services.PollQuestionWritePlatformService;
import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PollQuestionApiResource")

public class PollQuestionApiResource {

    private final PollQuestionReadPlatformService pollQuestionReadPlatformService;

    private final PollQuestionWritePlatformService pollQuestionWritePlatformService;

    private final PollQuestionOptionReadPlatformService pollQuestionOptionReadPlatformService;

    private final PollQuestionOptionWritePlatformService pollQuestionOptionWritePlatformService;

    @PostMapping
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public Response save(@RequestBody CreatePollQuestionRequest request){
        return this.pollQuestionWritePlatformService.save(request);
    }

    @PostMapping("/saveBulk")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public Response saveBulk(@RequestBody CreatePollQuestionBulkRequest request){
        return this.pollQuestionWritePlatformService.saveBulk(request);
    }

    @GetMapping
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MEMBER})
    public Page<PollQuestionData> fetchAll(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "pollId", required = false) Long pollId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.pollQuestionReadPlatformService.fetchAll(categoryId, pollId, page, pageSize);
    }

    @GetMapping("/flat")
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MEMBER})
    public List<PollQuestionData> fetchAllQuestionsFlat(@RequestParam(name = "pollId", required = false) Long pollId){
        return this.pollQuestionReadPlatformService.fetchAll(pollId);
    }

    @GetMapping("/details")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public PollQuestionDetailsData fetchAllPollQuestionDetails(@RequestParam(name = "pollId", required = false) Long pollId) {
        return this.pollQuestionReadPlatformService.fetchQuestionDetails(pollId);
    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MEMBER})
    public PollQuestionData  fetchByPollQuestionId(@PathVariable Long id) {
        return this.pollQuestionReadPlatformService.fetchByPollQuestionId(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public Response update(@PathVariable Long id, @RequestBody UpdatePollQuestionRequest request){
        return this.pollQuestionWritePlatformService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public void deletePollQuestionById(@PathVariable Long id) {
        this.pollQuestionWritePlatformService.deleteQuestion(id);
    }

    @GetMapping("/{id}/options")
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MEMBER})
    public List<PollQuestionOptionData> fetchByPollQuestionOptions(@PathVariable Long id) {
        return this.pollQuestionOptionReadPlatformService.fetchAll(id);
    }

    @GetMapping("/nextQuestion")
    @RolesAllowed({RoleConstants.ROLE_MEMBER})
    public SubmitPollingQuestionData nextQuestion(@RequestParam(name = "pollId") Long pollId,
                                              @RequestParam(name = "pollQuestionId", required = false) Long pollQuestionId,
                                         @RequestParam(name = "answerForOtherProxyUser", required = false) Boolean answerForOtherProxyUser){
        PollQuestionData pollQuestionData = this.pollQuestionReadPlatformService.fetchNextQuestion(pollId, pollQuestionId, answerForOtherProxyUser);
        return constructSubmitPollingData(pollId, pollQuestionData);
    }

    @GetMapping("/previousQuestion")
    @RolesAllowed({RoleConstants.ROLE_MEMBER})
    public SubmitPollingQuestionData previousQuestion(@RequestParam(name = "pollId") Long pollId,
                                            @RequestParam(name = "pollQuestionId", required = false) Long pollQuestionId,
                                             @RequestParam(name = "answerForOtherProxyUser", required = false) Boolean answerForOtherProxyUser){
        PollQuestionData pollQuestionData = this.pollQuestionReadPlatformService.fetchPreviousQuestion(pollId, pollQuestionId, answerForOtherProxyUser);
        return constructSubmitPollingData(pollId, pollQuestionData);
    }

    private SubmitPollingQuestionData constructSubmitPollingData(Long pollId, PollQuestionData pollQuestionData) {
        final Long totalQuestion = this.pollQuestionReadPlatformService.totalQuestion(pollId);
        final SubmitPollingQuestionData submitPollingQuestionData = new SubmitPollingQuestionData();
        submitPollingQuestionData.setPollQuestionData(pollQuestionData);
        submitPollingQuestionData.setTotalQuestion(totalQuestion);
        return submitPollingQuestionData;
    }

    @GetMapping("/notAnsweredQuestions")
    @RolesAllowed({RoleConstants.ROLE_MEMBER})
    public List<PollQuestionData> fetchAll(@RequestParam(name = "pollId") Long pollId) {
        return this.pollQuestionReadPlatformService.fetchAllNonAnsweredQuestions(pollId);
    }

    @PutMapping("/updateBulk")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public void updateBulk(@RequestBody UpdateBulkQuestionRequest request){
        this.pollQuestionWritePlatformService.updateBulk(request.getPollId(), request.getQuestions());
    }

    @DeleteMapping("/{id}/options/{optionId}")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public void deletePollQuestionOption(@PathVariable Long id, @PathVariable Long optionId) {
        this.pollQuestionWritePlatformService.deleteOption(id, optionId);
    }
}
