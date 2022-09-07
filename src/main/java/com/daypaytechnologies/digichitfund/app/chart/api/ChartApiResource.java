package com.daypaytechnologies.digichitfund.app.chart.api;

import com.daypaytechnologies.digichitfund.app.chart.data.*;
import com.daypaytechnologies.digichitfund.app.chart.services.ChartReadPlatformService;
import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/charts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ChartApiResource")
public class ChartApiResource {

    private final ChartReadPlatformService chartReadPlatformService;

    @GetMapping("/memberSubscriptionChartData")
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_SUPER_ADMIN})
    public List<MemberSubscriptionData> getAllMemberSubscription(@RequestParam(name = "year", required = false) String year) {
        return this.chartReadPlatformService.fetchMemberSubscriptionChartDataList(year);
    }

    @GetMapping("/pollResults")
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_SUPER_ADMIN})
    public OverallPollResultData pollResults(@RequestParam(name = "categoryId") Long categoryId,
                                                             @RequestParam(name = "pollId") Long pollId) {
        final OverallPollResultData overallPollResultData = new OverallPollResultData();
        overallPollResultData.setOverAllGenderChartData(this.chartReadPlatformService.fetchGenderChartDateList(categoryId, pollId));
        overallPollResultData.setOverallAgeResultData(this.chartReadPlatformService.fetchAgeChartDateList(categoryId, pollId));
        overallPollResultData.setQuestionChartDataList(this.chartReadPlatformService.fetchAllQuestionChartData(categoryId, pollId));
        return overallPollResultData;
    }
}
