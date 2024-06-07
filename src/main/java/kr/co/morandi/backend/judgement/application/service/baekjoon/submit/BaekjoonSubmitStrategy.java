package kr.co.morandi.backend.judgement.application.service.baekjoon.submit;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.application.service.SubmitStrategy;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit.BaekjoonSubmitApiAdapter;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaekjoonSubmitStrategy implements SubmitStrategy {

    private final BaekjoonSubmitApiAdapter baekjoonSubmitApiAdapter;
    private final BaekjoonMemberCookieService baekjoonMemberCookieService;

    @Override
    public String submit(Language language, Problem problem, String sourceCode, SubmitVisibility submitVisibility) {
        final String baejoonProblemId = String.valueOf(problem.getBaekjoonProblemId());
        final String cookie = baekjoonMemberCookieService.getCurrentMemberCookie();

        /*
        * 제출을 하고 솔루션 아이디를 가져오는 메소드
        * */
        return baekjoonSubmitApiAdapter.submitAndGetSolutionId(baejoonProblemId, cookie, language, sourceCode, submitVisibility.getValue().toLowerCase());
    }


}