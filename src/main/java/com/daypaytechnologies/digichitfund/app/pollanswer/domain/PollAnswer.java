package com.daypaytechnologies.digichitfund.app.pollanswer.domain;

import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestion;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOption;
import com.daypaytechnologies.digichitfund.app.proxyresponded.domain.ProxyResponded;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Entity(name = "PollAnswer")
@Table(name = "poll_answer")
@Getter
@Setter
public class PollAnswer {

    @Id
    @GeneratedValue(generator = "np_poll_answer_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "np_poll_answer_gen", sequenceName = "np_poll_answer_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    @Basic(optional = false)
    private PollQuestion question;

    @ManyToOne
    @JoinColumn(name="option_id", nullable=false)
    @Basic(optional = false)
    private PollQuestionOption option;

    @ManyToOne
    @JoinColumn(name="record_created_by", nullable=false)
    @Basic(optional = false)
    private Member createdBy;

    @Column(name = "record_created_at", nullable = false)
    @Basic(optional = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="record_updated_by", nullable=false)
    @Basic(optional = false)
    private Member updatedBy;

    @Column(name = "record_updated_at", nullable = false)
    @Basic(optional = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="member_answered_by")
    private Member answeredBy;

    @ManyToOne
    @JoinColumn(name="proxy_responded_id")
    private ProxyResponded proxyResponded;

    @Column(name = "answered_on")
    private LocalDateTime answeredOn;

    @Version
    private Long version;

    public static PollAnswer from(PollQuestion question, PollQuestionOption option, Member answeredBy) {
        final PollAnswer pollAnswer = new PollAnswer();
        pollAnswer.setQuestion(question);
        pollAnswer.setOption(option);
        pollAnswer.setAnsweredOn(LocalDateTime.now());
        pollAnswer.setAnsweredBy(answeredBy);
        pollAnswer.setCreatedAt(LocalDateTime.now());
        return pollAnswer;
    }

    public static PollAnswer from(PollQuestion question, PollQuestionOption option) {
        return from(question, option, null);
    }
}
