package kr.co.morandi.backend.judgement.domain.model.submit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmitCode {

    @Column(name = "source_code", columnDefinition = "TEXT", nullable = false)
    private String sourceCode;

    @Enumerated(EnumType.STRING)
    private Language language;

    public static SubmitCode of(String sourceCode, Language language) {
        return new SubmitCode(sourceCode, language);
    }

    private void validateLength(String sourceCode) {
        if(sourceCode == null || sourceCode.isEmpty())
            throw new MorandiException(SubmitErrorCode.SOURCE_CODE_NOT_FOUND);
    }

    @Builder
    private SubmitCode(String sourceCode, Language language) {
        validateLength(sourceCode);
        this.sourceCode = sourceCode;
        this.language = language;
    }
}