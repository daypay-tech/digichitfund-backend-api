package com.daypaytechnologies.digichitfund.app.poll.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.poll.request.CreatePollRequest;
import com.daypaytechnologies.digichitfund.app.poll.request.UpdatePollRequest;

public interface PollWritePlatformService {

    Response savePoll(CreatePollRequest request);
    Response updatePoll(Long id, UpdatePollRequest request);
    Poll delete(Long id);
}
