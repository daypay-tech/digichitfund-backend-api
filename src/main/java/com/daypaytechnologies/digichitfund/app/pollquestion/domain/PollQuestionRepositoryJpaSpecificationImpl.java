package com.daypaytechnologies.digichitfund.app.pollquestion.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PollQuestionRepositoryJpaSpecificationImpl implements PollQuestionRepositoryJpaSpecification {

    private final EntityManager entityManager;

    @Override
    public List<PollQuestion> findAllPollQuestion(Long categoryId, Long pollId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PollQuestion> pollQuestionCriteriaQuery = builder.createQuery(PollQuestion.class);
        Root<PollQuestion> root = pollQuestionCriteriaQuery.from(PollQuestion.class);
        List<Predicate> predicates = new ArrayList<>();
        if(categoryId != null) {
            Predicate categoryCondition = builder.equal(root.get("poll").get("category").get("id"), categoryId);
            predicates.add(categoryCondition);
        }
        if(pollId != null) {
            Predicate pollCondition = builder.equal(root.get("poll").get("id"), pollId);
            predicates.add(pollCondition);
        }
        if(!predicates.isEmpty()) {
            pollQuestionCriteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        return entityManager.createQuery(pollQuestionCriteriaQuery).getResultList();
    }
}
