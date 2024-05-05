package kr.co.morandi.backend;

import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import kr.co.morandi.backend.defense_information.infrastructure.controller.DailyDefenseController;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import kr.co.morandi.backend.defense_record.infrastructure.controller.DailyRecordController;
import kr.co.morandi.backend.member_management.infrastructure.config.cookie.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.filter.OncePerRequestFilter;

@WebMvcTest(controllers = {
        DailyDefenseController.class,
        DailyRecordController.class
},
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        OncePerRequestFilter.class
                })
}
)
@ActiveProfiles("test")
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected DailyDefenseUseCase dailyDefenseUseCase;

    @MockBean
    protected CookieUtils cookieUtils;

    @MockBean
    protected DailyRecordRankUseCase dailyRecordRankUseCase;

}