import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SystemOfAlertTest {

   SystemOfAlert system = new SystemOfAlert();

    @Test
    public void addUserToSystemOfAlertsOK(){
        User user = new User();
        user.setIdUser(446496);
        user.setName("Romina");
        user.setTopicList(null);
        system.addUserToSystemOfAlerts(user);
        assertEquals(system.getListOfUsers().size(), 1);
    }

    @Test
    public void addUserToSystemOfAlertsNotOK(){
        User user1 = new User();
        user1.setIdUser(446496);
        system.addUserToSystemOfAlerts(user1);
        User user2 = new User();
        user2.setIdUser(446496);
        system.addUserToSystemOfAlerts(user2);
        assertEquals(system.getListOfUsers().size(), 1);
    }

    @Test()
    public void addThemeToSystemOfAlertsOK(){
        Topic topic = new Topic(8585,"food");
        system.addTopicToSystemOfAlerts(topic);
        assertEquals(system.getListOfTopics().size(), 1);
    }

    @Test()
    public void addThemeToSystemOfAlertsNotOK(){
        Topic topic1 = new Topic(8585,"food");
        system.addTopicToSystemOfAlerts(topic1);
        Topic topic2 = new Topic(8585,"food");
        system.addTopicToSystemOfAlerts(topic2);
        assertEquals(system.getListOfTopics().size(), 1);
    }

    @Test
    public void addThemeToUserOK(){
        Topic topic = new Topic(8585,"food");
        User user = new User();
        user.setIdUser(564640);
        user.setName("Roberto");
        system.addUserToSystemOfAlerts(user);
        assertTrue(system.addTopicToUser(topic,user));
    }

    @Test
    public void createAlertsByTheme(){
        User user = new User();
        user.setIdUser(564640);
        user.setName("Roberto");
        ArrayList<Topic> themesList =  new ArrayList<Topic>();
        themesList.add(new Topic(8585,"food"));
        user.setTopicList(themesList);
        system.addUserToSystemOfAlerts(user);

        Alert alert = new Alert();
        alert.setIdAlert(6545);
        alert.setMessage("blablablabla");
        alert.setTopic(new Topic(8585,"food"));
        system.createAlertsByTopic(alert);

        assertEquals(system.getListOfAlertsByUser().get(0).getIdAlert(),6545);
        assertEquals(system.getListOfAlertsByUser().get(0).getUser().getIdUser(),564640);
    }

    @Test
    public void sendAlertToUser(){
        User user = new User();
        user.setIdUser(564640);
        user.setName("Roberto");
        ArrayList<Topic> themesList =  new ArrayList<Topic>();
        themesList.add(new Topic(8585,"food"));
        user.setTopicList(themesList);
        system.addUserToSystemOfAlerts(user);

        Alert alert = new Alert();
        alert.setIdAlert(6545);
        alert.setMessage("blablablabla");
        alert.setTopic(new Topic(8585,"food"));
        system.sendAlertToUser(alert,user);

        assertEquals(system.getListOfAlertsByUser().get(0).getIdAlert(),6545);
        assertEquals(system.getListOfAlertsByUser().get(0).getUser().getIdUser(),564640);
    }

    @Test
    public void readAlert(){
        User user = new User();
        user.setIdUser(564640);
        user.setName("Roberto");
        Topic topic = new Topic(8585,"food");
        LocalDateTime today = LocalDateTime.now();
        AlertByUser alertByUser = new AlertByUser(54156468, topic, "message", today, TypeOfAlert.URGENT, user);
        system.readAlert(alertByUser);
        assertTrue(alertByUser.isReadOK());
    }

    @Test
    public void getAllUnreadAlertsByUser(){
        User user = new User();
        user.setIdUser(564640);
        user.setName("Roberto");
        AlertByUser alertByUser;

        Topic topic1 = new Topic(8585,"food");
        LocalDateTime date1 = LocalDateTime.of(2024,12,10,3,0,0);
        alertByUser = new AlertByUser(111111, topic1, "message1", date1, TypeOfAlert.URGENT, user);
        system.getListOfAlertsByUser().add(alertByUser);

        Topic topic2 = new Topic(99585,"shoes");
        LocalDateTime date2 = LocalDateTime.of(2024,10,11,13,10,50);
        alertByUser = new AlertByUser(222222, topic2, "message2", date2, TypeOfAlert.INFORMATIVE, user);
        system.getListOfAlertsByUser().add(alertByUser);

        Topic topic3 = new Topic(8585,"gym");
        LocalDateTime date3 = LocalDateTime.of(2024,12,15,3,0,50);
        alertByUser = new AlertByUser(333333, topic3, "message3", date3, TypeOfAlert.URGENT, user);
        system.getListOfAlertsByUser().add(alertByUser);

        Topic topic4 = new Topic(99586,"pets");
        LocalDateTime date4 = LocalDateTime.of(2024,10,1,10,10,50);
        alertByUser = new AlertByUser(444444, topic4, "message4", date4, TypeOfAlert.INFORMATIVE, user);
        system.getListOfAlertsByUser().add(alertByUser);

        system.getAllUnreadAlertsByUser(user);
        assertEquals(system.getListOfAllUnreadAlertsByUser().get(0).getIdAlert(),333333);
        assertEquals(system.getListOfAllUnreadAlertsByUser().get(1).getIdAlert(),111111);
        assertEquals(system.getListOfAllUnreadAlertsByUser().get(2).getIdAlert(),444444);
        assertEquals(system.getListOfAllUnreadAlertsByUser().get(3).getIdAlert(),222222);
    }

    @Test
    public void getAllAlertsByTheme(){

        AlertByUser alertByUser;

        Topic topic = new Topic(8585,"food");

        User user1 = new User();
        user1.setIdUser(564640);
        user1.setName("Roberto");

        LocalDateTime date = LocalDateTime.of(2024,10,1,10,10,50);

        Alert alert = new Alert(111111, topic, "message", date , TypeOfAlert.URGENT);
        alertByUser = new AlertByUser(alert.getIdAlert(), alert.getTopic(), alert.getMessage(), alert.getExpirationDate(),
                alert.getType(), user1);
        system.getListOfAlertsByUser().add(alertByUser);

        User user2 = new User();
        user2.setIdUser(545156);
        user2.setName("Marta");
        alertByUser = new AlertByUser(alert.getIdAlert(), alert.getTopic(), alert.getMessage(), alert.getExpirationDate(),
                alert.getType(), user2);
        system.getListOfAlertsByUser().add(alertByUser);

        Alert alert2 = new Alert(222222, topic, "message", date, TypeOfAlert.INFORMATIVE);
        alertByUser = new AlertByUser(alert2.getIdAlert(), alert2.getTopic(), alert2.getMessage(), alert2.getExpirationDate(),
                alert2.getType(), user2);
        system.getListOfAlertsByUser().add(alertByUser);

        system.getAllAlertsByTopic(topic);
        assertEquals(system.getListOfAlertsWithQuantityOfUsers().size(),2);
        assertEquals(system.getListOfAlertsWithQuantityOfUsers().get(0).getIdAlert(),111111);
        assertEquals(system.getListOfAlertsWithQuantityOfUsers().get(0).getQuantityOfUsers(),2);
        assertEquals(system.getListOfAlertsWithQuantityOfUsers().get(1).getIdAlert(),222222);
        assertEquals(system.getListOfAlertsWithQuantityOfUsers().get(1).getQuantityOfUsers(),1);
    }

}
