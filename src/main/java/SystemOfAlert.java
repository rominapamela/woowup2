import java.time.LocalDateTime;
import java.util.ArrayList;

public class SystemOfAlert {

    private ArrayList<User> listOfUsers = new ArrayList<User>();
    private ArrayList<Topic> listOfTopics = new ArrayList<Topic>();
    private ArrayList<Alert> listOfAlerts = new ArrayList<Alert>();
    private ArrayList<AlertByUser> listOfAlertsByUser = new ArrayList<AlertByUser>();
    private ArrayList<AlertByUser> listOfAllUnreadAlertsByUser = new ArrayList<AlertByUser>();
    private ArrayList<AlertWithQuantityOfUsers> listOfAlertsWithQuantityOfUsers = new ArrayList<AlertWithQuantityOfUsers>();

    public SystemOfAlert() {
    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public ArrayList<Topic> getListOfTopics() {
        return listOfTopics;
    }

    public void setListOfTopics(ArrayList<Topic> listOfTopics) {
        this.listOfTopics = listOfTopics;
    }

    public ArrayList<Alert> getListOfAlerts() {
        return listOfAlerts;
    }

    public void setListOfAlerts(ArrayList<Alert> listOfAlerts) {
        this.listOfAlerts = listOfAlerts;
    }

    public ArrayList<AlertByUser> getListOfAlertsByUser() {
        return listOfAlertsByUser;
    }

    public void setListOfAlertsByUser(ArrayList<AlertByUser> listOfAlertsByUser) {
        this.listOfAlertsByUser = listOfAlertsByUser;
    }

    public ArrayList<AlertByUser> getListOfAllUnreadAlertsByUser() {
        return listOfAllUnreadAlertsByUser;
    }

    public void setListOfAllUnreadAlertsByUser(ArrayList<AlertByUser> listOfAllUnreadAlertsByUser) {
        this.listOfAllUnreadAlertsByUser = listOfAllUnreadAlertsByUser;
    }

    public ArrayList<AlertWithQuantityOfUsers> getListOfAlertsWithQuantityOfUsers() {
        return listOfAlertsWithQuantityOfUsers;
    }

    public void setListOfAlertsWithQuantityOfUsers(ArrayList<AlertWithQuantityOfUsers> listOfAlertsWithQuantityOfUsers) {
        this.listOfAlertsWithQuantityOfUsers = listOfAlertsWithQuantityOfUsers;
    }

    // 1 - You can register users to receive alerts.
    public void addUserToSystemOfAlerts(User user){
        if (listOfUsers.size() == 0)
            listOfUsers.add(user);
        else
            if(!repeatedUser(user))
                listOfUsers.add(user);
    }

    public boolean repeatedUser(User u){
        boolean repeatedUser = false;
        for(User user : listOfUsers) {
            if (user.getIdUser() == u.getIdUser()) {
                repeatedUser = true;
                break;
            }
        }
        return repeatedUser;
    }

    // 2 - Topics can be registered to receive alerts
    public void addTopicToSystemOfAlerts(Topic topic){
        if (listOfTopics.size() == 0)
            listOfTopics.add(topic);
        else
        if(!repeatedTopic(topic))
            listOfTopics.add(topic);
    }

    public boolean repeatedTopic(Topic t){
        boolean repeatedTopic = false;
        for(Topic topic : listOfTopics) {
            if (topic.getIdTopic() == t.getIdTopic()) {
                repeatedTopic = true;
                break;
            }
        }
        return repeatedTopic;
    }

    // 3 - Users can choose which topics they want to receive alerts about
    public boolean addTopicToUser(Topic topic, User user){
        boolean topicAddedToUser = false;
        for (User u : listOfUsers){
            if (u.getIdUser() == user.getIdUser()) {
                u.getTopicList().add(topic);
                topicAddedToUser = true;
                break;
            }
        }
        return topicAddedToUser;
    }

    // 4 - An alert about a topic can be sent to all users who chose that topic
    public void createAlertsByTopic(Alert alert){
        for (User u: listOfUsers) {
            if (userContainsTopic(alert, u.getTopicList())){
                AlertByUser newAlert = new AlertByUser(alert.getIdAlert(), alert.getTopic(), alert.getMessage(), alert.expirationDate,alert.getType(), u);
                listOfAlertsByUser.add(newAlert);
            }
        }
    }
    public boolean userContainsTopic(Alert a, ArrayList<Topic> list){
        boolean containsTopicOK = false;
        for (Topic t: list) {
            if (t.getIdTopic() == a.getTopic().getIdTopic()){
                containsTopicOK = true;
                break;
            }
        }
        return containsTopicOK;
    }

    // 5 - An alert about a topic can be sent to a specific user
    public void sendAlertToUser(Alert alert, User user){
        if(userContainsTopic(alert, user.getTopicList())) {
            AlertByUser newAlert = new AlertByUser(alert.getIdAlert(), alert.getTopic(), alert.getMessage(),
                    alert.expirationDate,alert.getType(), user);
            listOfAlertsByUser.add(newAlert);
        }
    }

    // 8 - A user can mark an alert as read
    public void readAlert(AlertByUser alertByUser) {
        alertByUser.markAlertAsRead();
    }

    // 9 - You can get all unexpired alerts from a user that you haven't read yet
    public void getAllUnreadAlertsByUser(User u){
        LocalDateTime today = LocalDateTime.now();
        ArrayList<AlertByUser> listOfUrgentUnreadAlertsByUser= new ArrayList<AlertByUser>();
        ArrayList<AlertByUser> listOfInformativeAlertsByUser= new ArrayList<AlertByUser>();
        for(AlertByUser alertByUser : listOfAlertsByUser){
            if(existsAlertByUser(alertByUser, u) &&
                    !alertByUser.isReadOK() &&
                    alertByUser.getExpirationDate().isAfter(today))
                if (alertByUser.getType()==TypeOfAlert.URGENT)
                    listOfUrgentUnreadAlertsByUser.add(alertByUser);
                else
                    listOfInformativeAlertsByUser.add(alertByUser);
        }
        listOfAllUnreadAlertsByUser.addAll(this.orderUrgentAlertsByUsersFIFO(listOfUrgentUnreadAlertsByUser));
        listOfAllUnreadAlertsByUser.addAll(this.orderInformativeAlertsByUsersLIFO(listOfInformativeAlertsByUser));
    }

    public boolean existsAlertByUser(AlertByUser alert, User user){
        return alert.getUser().getIdUser() == user.getIdUser();
    }

    public ArrayList<AlertByUser> orderUrgentAlertsByUsersFIFO(ArrayList<AlertByUser> list){
        list.sort((AlertByUser a1, AlertByUser a2) -> a2.getExpirationDate().compareTo(a1.getExpirationDate()));
        return list;
    }

    public ArrayList<AlertByUser> orderInformativeAlertsByUsersLIFO(ArrayList<AlertByUser> list){
        list.sort((AlertByUser a1, AlertByUser a2) -> a1.getExpirationDate().compareTo(a2.getExpirationDate()));
        return list;
    }

    // 10 - You can get all non-expired alerts for a topic.
    // For each alert, it is reported whether it is for all users or for a specific one
    public void getAllAlertsByTopic(Topic topic) {
        LocalDateTime today = LocalDateTime.now();
        ArrayList<Alert> listOfAllAlerts = new ArrayList<Alert>();
        ArrayList<Alert> listOfUrgentAlerts = new ArrayList<Alert>();
        ArrayList<Alert> listOfInformativeAlerts = new ArrayList<Alert>();
        for (AlertByUser alertByUser : listOfAlertsByUser) {
            if (existsAlertByTheme(alertByUser, topic) &&
                    alertByUser.getExpirationDate().isAfter(today)) {
                Alert alert = new Alert(alertByUser.getIdAlert(), alertByUser.getTopic(), alertByUser.getMessage(),
                        alertByUser.getExpirationDate(), alertByUser.getType());
                if (alert.getType() == TypeOfAlert.URGENT && !alertIsInList(alert, listOfUrgentAlerts))
                    listOfUrgentAlerts.add(alert);
                else
                    if(alert.getType() == TypeOfAlert.INFORMATIVE && !alertIsInList(alert, listOfInformativeAlerts))
                        listOfInformativeAlerts .add(alert);
            }
        }
        listOfAllAlerts.addAll(orderUrgentAlertsByFIFO(listOfUrgentAlerts));
        listOfAllAlerts.addAll(orderInformativeAlertsByLIFO(listOfInformativeAlerts));
        listOfAlertsWithQuantityOfUsers = countUsersByAlert(listOfAllAlerts);
    }

    public boolean existsAlertByTheme(AlertByUser alert, Topic topic){
        return alert.getTopic().getIdTopic() == topic.getIdTopic();
    }

    public boolean alertIsInList(Alert a, ArrayList<Alert> listOfAlerts) {
        boolean alertIsInList = false;
        for (Alert alert : listOfAlerts)
            if (alert.getIdAlert() == a.getIdAlert()) {
                alertIsInList = true;
                break;
            }
        return alertIsInList;
    }

    public ArrayList<Alert> orderUrgentAlertsByFIFO(ArrayList<Alert> list){
        list.sort((Alert a1, Alert a2) -> a2.getExpirationDate().compareTo(a1.getExpirationDate()));
        return list;
    }

    public ArrayList<Alert> orderInformativeAlertsByLIFO(ArrayList<Alert> list){
        list.sort((Alert a1, Alert a2) -> a1.getExpirationDate().compareTo(a2.getExpirationDate()));
        return list;
    }

    private ArrayList<AlertWithQuantityOfUsers> countUsersByAlert(ArrayList<Alert> listOfAlerts) {
        ArrayList<AlertWithQuantityOfUsers> list = new ArrayList<AlertWithQuantityOfUsers>();
        for(Alert alert: listOfAlerts){
            int quantityOfUsers = 0;
            for (AlertByUser alertByUser : listOfAlertsByUser) {
                if(alertByUser.getIdAlert() == alert.getIdAlert())
                    quantityOfUsers++;
            }
            AlertWithQuantityOfUsers alertWithQuantityOfUsers = new AlertWithQuantityOfUsers(alert.getIdAlert(), alert.getTopic(), alert.getMessage(),
                    alert.getExpirationDate(), alert.getType(), quantityOfUsers);
            list.add(alertWithQuantityOfUsers);
        }
        return list;
    }
}
