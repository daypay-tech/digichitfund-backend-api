package com.daypaytechnologies.digichitfund.app.pollquestionoption.domain;

import com.daypaytechnologies.digichitfund.app.core.baseentity.AbstractBaseEntity;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdateQuestionOptionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.request.CreateQuestionOptionRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "PollQuestionOption")
@Table(name = "poll_question_option")
@Getter
@Setter
public class PollQuestionOption extends AbstractBaseEntity {

    @Id
    @GeneratedValue(generator = "np_poll_question_option_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "np_poll_question_option_gen", sequenceName = "np_poll_question_option_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "option_name")
    private String optionName;

    public static List<PollQuestionOption> from(List<CreateQuestionOptionRequest> optionRequest){
        final List<PollQuestionOption> pollQuestionOptions = new ArrayList<>();
        for(final CreateQuestionOptionRequest option: optionRequest) {
            final PollQuestionOption pollQuestionOption = new PollQuestionOption();
            pollQuestionOption.setOptionName(option.getOption());
            pollQuestionOptions.add(pollQuestionOption);
        }
        return pollQuestionOptions;
    }

    public static List<PollQuestionOption> createFromUpdate(List<UpdateQuestionOptionRequest> optionRequest){
        final List<PollQuestionOption> pollQuestionOptions = new ArrayList<>();
        for(final UpdateQuestionOptionRequest option: optionRequest) {
            final PollQuestionOption pollQuestionOption = new PollQuestionOption();
            pollQuestionOption.setOptionName(option.getOption());
            pollQuestionOptions.add(pollQuestionOption);
        }
        return pollQuestionOptions;
    }

    public static PollQuestionOption from(UpdateQuestionOptionRequest optionRequest){
        final PollQuestionOption pollQuestionOption = new PollQuestionOption();
        pollQuestionOption.setOptionName(optionRequest.getOption());
        return pollQuestionOption;
    }
}
