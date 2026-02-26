package domain.audit;

import java.util.List;

public interface AuditRepository {
    void save(AuditLog log);

    List<AuditLog> findAllByTargetId(String targetId);

    List<AuditLog> findAll();
}
