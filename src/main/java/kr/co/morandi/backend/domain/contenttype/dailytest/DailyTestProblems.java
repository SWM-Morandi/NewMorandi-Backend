package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestProblems extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyProblemsId;

    private Long submitCount;

    private Long solvedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyTest dailyTest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
    private DailyTestProblems(DailyTest dailyTest, Problem problem) {
        this.dailyTest = dailyTest;
        this.problem = problem;
        this.submitCount = 0L;
        this.solvedCount = 0L;
    }
    public static DailyTestProblems create(DailyTest dailyTest, Problem problem) {
        return new DailyTestProblems(dailyTest, problem);
    }
}
