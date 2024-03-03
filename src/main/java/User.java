import java.util.ArrayList;

public class User {

    private long idUser;
    private String name;
    private ArrayList<Topic> topicList =  new ArrayList<Topic>();

    public User() {
    }

    public User(long idUser, String name, ArrayList<Topic> topicList) {
        this.idUser = idUser;
        this.name = name;
        this.topicList = topicList;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(ArrayList<Topic> themesList) {
        this.topicList = themesList;
    }
}
