public class Topic {

    private long idTopic;
    private String name;

    public Topic(long idTopic, String name) {
        this.idTopic = idTopic;
        this.name = name;
    }
    public long getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(long idTopic) {
        this.idTopic = idTopic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
