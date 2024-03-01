package kr.co.morandi.backend.domain.contentrecord.customdefense;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.customdefense.CustomDefenseProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.customdefense.CustomDefenseProblemRecordRepository;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblems;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblemsRepository;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseRepository;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.contenttype.customdefense.DefenseTier.GOLD;
import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.OPEN;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomDefenseRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private CustomDefenseRepository customDefenseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomDefenseProblemsRepository customDefenseProblemsRepository;

    @Autowired
    private CustomDefenseRecordRepository customDefenseRecordRepository;
    @AfterEach
    void tearDown() {
        customDefenseRecordRepository.deleteAllInBatch();
        customDefenseProblemsRepository.deleteAllInBatch();
        customDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("커스텀 디펜스 모드 기록이 만들어 졌을 때 시험 날짜는 시작한 시점과 같아야 한다.")
    @Test
    public void testDateIsEqualNow() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getTestDate()).isEqualTo(startTime);
    }
    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 맞춘 문제 수는 0으로 설정되어야 한다.")
    @Test
    public void solvedCountIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getSolvedCount()).isZero();
    }

    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 총 문제 수 기록은 커스텀 디펜스 문제 수와 같아야 한다.")
    @Test
    public void problemCountIsEqual() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getProblemCount()).isEqualTo(customDefense.getProblemCount());
    }
    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 초기 전체 소요 시간은 0분 이어야 한다.")
    @Test
    public void totalSolvedTimeIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 세부 문제 기록의 정답 여부는 모두 오답이어야 한다.")
    @Test
    void isSolvedFalse() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);
        List<ContentProblemRecord> customDefenseProblemRecords = customDefenseRecord.getContentProblemRecords();

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("isSolved")
                .containsExactlyInAnyOrder(false, false);
    }

    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 세부 문제 기록의 문제 기록의 소요 시간은 0분이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);
        List<ContentProblemRecord> customDefenseProblemRecords = customDefenseRecord.getContentProblemRecords();

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("solvedTime")
                .containsExactlyInAnyOrder(0L, 0L);
    }

    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 세부 문제 기록의 제출 횟수는 0회 이어야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);
        List<ContentProblemRecord> customDefenseProblemRecords = customDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(customDefenseProblemRecords)
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L, 0L);
    }

    @DisplayName("커스텀 디펜스 모드 기록이 만들어졌을 때 세부 문제 기록의 문제 정답 코드는 null 값 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = createCustomProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);
        List<ContentProblemRecord> customDefenseProblemRecords = customDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(customDefenseProblemRecords)
                .extracting("solvedCode")
                .containsExactlyInAnyOrder(null, null);
    }

    private CustomDefense createCustomDefense() {
        Member member = createMember("author");
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        List<Problem> problems = List.of(problem1, problem2);
        problemRepository.saveAll(problems);

        LocalDateTime now = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        CustomDefense customDefense = CustomDefense.create(problems, member, "custom_defense",
                "custom_defense", OPEN, GOLD, 60L, now);

        customDefenseRepository.save(customDefense);

        return customDefense;
    }
    private List<Problem> createCustomProblems(CustomDefense customDefense) {
        List<CustomDefenseProblems> customDefenseProblems = customDefense.getCustomDefenseProblems();
        List<Problem> problems = customDefenseProblems.stream().map(CustomDefenseProblems::getProblem).collect(Collectors.toList());
        return problems;
    }
    private Member createMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}