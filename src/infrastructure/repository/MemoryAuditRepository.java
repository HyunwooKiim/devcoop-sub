package infrastructure.repository;

import domain.audit.AuditLog;
import domain.audit.AuditRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryAuditRepository implements AuditRepository {

    private final List<AuditLog> logs = new ArrayList<>();

    @Override
    public synchronized void save(AuditLog log) {
        logs.add(log);
    }

    @Override
    public List<AuditLog> findAllByTargetId(String targetId) {
        return logs.stream()
                .filter(log -> log.getTargetId().equals(targetId)).toList();
    }

    @Override
    public List<AuditLog> findAll() {
        return new ArrayList<>(logs);
    }
}
