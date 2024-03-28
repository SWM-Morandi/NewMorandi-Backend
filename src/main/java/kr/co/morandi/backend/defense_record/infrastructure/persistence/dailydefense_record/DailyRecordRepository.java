package kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

    @Query("""
            select dr
            from DailyRecord dr
            left join fetch dr.details d
            left join fetch d.problem
            where dr.member = :member
            and CAST(dr.testDate as localdate) = :date
     """)
    Optional<DailyRecord> findDailyRecordByMemberAndDate(Member member, LocalDate date);

    @Query("""
            select dr
            from DailyRecord dr
            left join fetch dr.details d
            left join fetch d.problem
            where dr.member = :member
            and dr.recordId = :recordId
            and CAST(dr.testDate as localdate) = :date
     """)
    Optional<DailyRecord> findDailyRecordByRecordId(Member member, Long recordId, LocalDate date);

}