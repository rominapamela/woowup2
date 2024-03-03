import java.time.LocalDateTime;

public class Alert
{
    protected long idAlert;
    protected Topic topic;
    protected String message;
    // 6 - An alert can have an expiration date and time
    protected LocalDateTime expirationDate;
    // 7 - There are two types of alerts: URGENT AND INFORMATIVE
    protected TypeOfAlert type;

    public Alert() {
    }

    public Alert(long idAlert, Topic topic, String message, LocalDateTime expirationDate, TypeOfAlert type) {
        this.idAlert = idAlert;
        this.topic = topic;
        this.message = message;
        this.expirationDate = expirationDate;
        this.type = type;
    }
    public long getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(long idAlert) {
        this.idAlert = idAlert;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public TypeOfAlert getType() {
        return type;
    }

    public void setType(TypeOfAlert type) {
        this.type = type;
    }
}
