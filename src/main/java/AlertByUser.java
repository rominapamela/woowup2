import java.time.LocalDateTime;

public class AlertByUser extends Alert {

    private User user;
    private boolean readOK;

    public AlertByUser(long idAlert, Topic topic, String message, LocalDateTime expirationDate,
                       TypeOfAlert type, User user) {
        super(idAlert, topic, message, expirationDate, type);
        this.user = user;
        this.readOK = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isReadOK() {
        return readOK;
    }

    public void markAlertAsRead(){
        this.readOK = true;
    }
}
