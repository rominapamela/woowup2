import java.time.LocalDateTime;

public class AlertWithQuantityOfUsers extends Alert{

    private int quantityOfUsers;

    public AlertWithQuantityOfUsers(long idAlert, Topic topic, String message, LocalDateTime expirationDate, TypeOfAlert type, int quantityOfUsers) {
        super(idAlert, topic, message, expirationDate, type);
        this.quantityOfUsers = quantityOfUsers;
    }

    public int getQuantityOfUsers() {
        return quantityOfUsers;
    }

    public void setQuantityOfUsers(int quantityOfUsers) {
        this.quantityOfUsers = quantityOfUsers;
    }
}
