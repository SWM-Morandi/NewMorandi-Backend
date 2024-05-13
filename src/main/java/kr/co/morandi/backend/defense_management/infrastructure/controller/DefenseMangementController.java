package kr.co.morandi.backend.defense_management.infrastructure.controller;

import jakarta.validation.Valid;
import kr.co.morandi.backend.common.web.MemberId;
import kr.co.morandi.backend.defense_management.application.response.session.StartDailyDefenseResponse;
import kr.co.morandi.backend.defense_management.application.usecase.session.DailyDefenseManagementUsecase;
import kr.co.morandi.backend.defense_management.infrastructure.request.dailydefense.StartDailyDefenseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/daily-defense")
@RequiredArgsConstructor
public class DefenseMangementController {

    private final DailyDefenseManagementUsecase dailyDefenseManagementUsecase;

     @PostMapping
     public ResponseEntity<StartDailyDefenseResponse> startDailyDefense(@MemberId Long memberId,
                                                                        @Valid @RequestBody StartDailyDefenseRequest request) {

         return ResponseEntity.ok(dailyDefenseManagementUsecase
                 .startDailyDefense(request.toServiceRequest(), memberId, LocalDateTime.now())
         );
     }
}
