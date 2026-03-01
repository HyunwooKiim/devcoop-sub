package core.domain.audit;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLog {
    /**
     * variable
     */
    private final String id;
    private final String targetId;
    private final String action;
    private final String message;
    private final LocalDateTime timestamp;


    /**
     * constructor
     */
    public AuditLog(String targetId, String action, String message) {
        this.id = UUID.randomUUID().toString();
        this.targetId = targetId;
        this.action = action;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


    /**
     * getter
     */
    public String getId() {
        return id;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    /**
     * toString
     */
    @Override
    public String toString() {
        return String.format("[%s] [%s] 대상:%s - %s", timestamp, action, targetId, message);
    }
}
