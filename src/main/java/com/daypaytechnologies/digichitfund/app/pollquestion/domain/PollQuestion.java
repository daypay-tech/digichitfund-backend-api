package com.daypaytechnologies.digichitfund.app.pollquestion.domain;

import com.daypaytechnologies.digichitfund.app.core.baseentity.AdministrationUserBaseEntity;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreateQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdateQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOption;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "PollQuestion")
@Table(name = "poll_question")
@Getter
@Setter
public class PollQuestion extends AdministrationUserBaseEntity {

    @Id
    @GeneratedValue(generator = "np_poll_question_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "np_poll_question_gen", sequenceName = "np_poll_question_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="poll_id", nullable=false)
    @Basic(optional = false)
    private Poll poll;

    @Column(name = "question")
    private String question;

    @Column(name = "is_deleted", nullable = true)
    @Basic(optional = true)
    private Boolean isDeleted;

    @Column(name = "deleted_on", nullable = true)
    @Basic(optional = true)
    private LocalDate deletedOn;

    @Column(name = "deleted_by", nullable = true)
    @Basic(optional = true)
    private Integer deletedBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_question_id", referencedColumnName = "id")
    private List<PollQuestionOption> options = new ArrayList<>();

    public static PollQuestion from(final CreateQuestionRequest createPollQuestionRequest,
                                    final Poll poll, final AdministrationUser createdBy) {
        final PollQuestion pollQuestion = new PollQuestion();
        pollQuestion.setPoll(poll);
        pollQuestion.setCreatedAt(LocalDateTime.now());
        pollQuestion.setUpdatedAt(LocalDateTime.now());
        pollQuestion.setCreatedBy(createdBy);
        pollQuestion.setUpdatedBy(createdBy);
        pollQuestion.setQuestion(createPollQuestionRequest.getQuestion());
        pollQuestion.setOptions(PollQuestionOption.from(createPollQuestionRequest.getOptions()));
        return pollQuestion;
    }

    public static PollQuestion from(final UpdateQuestionRequest createPollQuestionRequest,
                                    final Poll poll, final AdministrationUser createdBy) {
        final PollQuestion pollQuestion = new PollQuestion();
        pollQuestion.setPoll(poll);
        pollQuestion.setCreatedAt(LocalDateTime.now());
        pollQuestion.setUpdatedAt(LocalDateTime.now());
        pollQuestion.setCreatedBy(createdBy);
        pollQuestion.setUpdatedBy(createdBy);
        pollQuestion.setQuestion(createPollQuestionRequest.getQuestion());
        pollQuestion.setOptions(PollQuestionOption.createFromUpdate(createPollQuestionRequest.getOptions()));
        return pollQuestion;
    }

    public static List<PollQuestion> from(final List<CreateQuestionRequest> createPollQuestionRequest,
                                          final Poll poll, final AdministrationUser createdBy) {
        List<PollQuestion> pollQuestions = new ArrayList<>();
        for(CreateQuestionRequest question: createPollQuestionRequest) {
            final PollQuestion pollQuestion = from(question, poll, createdBy);
            pollQuestions.add(pollQuestion);
        }
        return pollQuestions;
    }
}
