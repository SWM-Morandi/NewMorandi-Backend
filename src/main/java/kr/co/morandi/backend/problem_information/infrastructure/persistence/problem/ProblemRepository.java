package kr.co.morandi.backend.problem_information.infrastructure.persistence.problem;

import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.domain.model.problem.ProblemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("""
        SELECT p
        FROM Problem p
        WHERE p.problemStatus = 'ACTIVE'
        AND p.problemTier IN :problemTiers
        AND p.solvedCount >= :startSolvedCount
        AND p.solvedCount <= :endSolvedCount
        AND p NOT IN (SELECT ddp.problem
                      FROM DailyDefenseProblem ddp)
        ORDER BY FUNCTION('RAND')
    """)
    List<Problem> getDailyDefenseProblems(List<ProblemTier> problemTiers, Long startSolvedCount, Long endSolvedCount, Pageable pageable);

    List<Problem> findAllByProblemStatus(ProblemStatus problemStatus);
}
