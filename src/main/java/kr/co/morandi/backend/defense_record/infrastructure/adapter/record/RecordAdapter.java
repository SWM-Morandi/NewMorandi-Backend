package kr.co.morandi.backend.defense_record.infrastructure.adapter.record;

import kr.co.morandi.backend.defense_record.application.port.out.record.RecordPort;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecordAdapter implements RecordPort {

    private final RecordRepository recordRepository;
    @Override
    public Optional<Record<?>> findRecordById(Long recordId) {
            return recordRepository.findById(recordId);
    }

    @Override
    public void saveRecord(Record<?> record) {
        recordRepository.save(record);
    }
}