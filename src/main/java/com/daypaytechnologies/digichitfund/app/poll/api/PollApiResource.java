package com.daypaytechnologies.digichitfund.app.poll.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.poll.data.PollData;
import com.daypaytechnologies.digichitfund.app.poll.data.PollQuestionAggregationData;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.poll.request.CreatePollRequest;
import com.daypaytechnologies.digichitfund.app.poll.request.UpdatePollRequest;
import com.daypaytechnologies.digichitfund.app.poll.services.PollReadPlatformService;
import com.daypaytechnologies.digichitfund.app.poll.services.PollWritePlatformService;
import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/polls")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PollApiResource")
//@SecurityRequirement(name = "navpulseuk")
public class PollApiResource {

    private final PollReadPlatformService pollReadPlatformService;

    private final PollWritePlatformService pollWritePlatformService;

    @PostMapping
    @RolesAllowed({ RoleConstants.ROLE_ADMIN })
    public Response savePoll(@RequestBody CreatePollRequest request){
        return this.pollWritePlatformService.savePoll(request);
    }
    @GetMapping
    @RolesAllowed({ RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_ADMIN })
    public Page<PollData> fetchAll(@RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.pollReadPlatformService.fetchAll(categoryId, page, pageSize);
    }

    @GetMapping("/flatPolls")
    @RolesAllowed({ RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_ADMIN })
    public List<PollData> fetchAll(@RequestParam(name = "categoryId", required = false) Long categoryId){
        return this.pollReadPlatformService.fetchAll(categoryId);
    }

    @GetMapping("/questions/aggregation")
    @RolesAllowed({ RoleConstants.ROLE_ADMIN })
    public Page<PollQuestionAggregationData> fetchAllAggregatedQuestions(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "pollId", required = false) Long pollId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.pollReadPlatformService.fetchAllPollQuestionAggregation(categoryId, pollId, page, pageSize);
    }

    @GetMapping("/{id}")
    @RolesAllowed({ RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_ADMIN })
    public PollData  fetchByPollId(@PathVariable Long id) {
        return this.pollReadPlatformService.fetchByPollId(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed({ RoleConstants.ROLE_ADMIN })
    public Response updatePoll(@PathVariable Long id, @RequestBody UpdatePollRequest request){
        return this.pollWritePlatformService.updatePoll(id, request);
    }
    @DeleteMapping("/{id}")
    @RolesAllowed({ RoleConstants.ROLE_ADMIN })
    public Response deletePollById(@PathVariable Long id) {
        final Poll poll = this.pollWritePlatformService.delete(id);
        return Response.of(poll.getId());
    }
}
