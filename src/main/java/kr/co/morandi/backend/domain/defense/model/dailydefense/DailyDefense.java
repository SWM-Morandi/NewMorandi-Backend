package kr.co.morandi.backend.domain.defense.model.dailydefense;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.model.DefenseType.DAILY;

@Entity
@DiscriminatorValue("DailyDefense")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DailyDefense extends Defense {

    private LocalDate date;

    private Integer problemCount;

    @OneToMany(mappedBy = "DailyDefense", cascade = CascadeType.ALL)
    List<DailyDefenseProblem> DailyDefenseProblems = new ArrayList<>();

    private DailyDefense(LocalDate date, String contentName, List<Problem> problems) {
        super(contentName, DAILY);
        this.date = date;
        this.DailyDefenseProblems = problems.stream()
                .map(problem -> DailyDefenseProblem.create(this, problem))
                .toList();
        this.problemCount = problems.size();
    }
    public static DailyDefense create(LocalDate date, String contentName, List<Problem> problems) {
        return new DailyDefense(date, contentName, problems);
    }

}