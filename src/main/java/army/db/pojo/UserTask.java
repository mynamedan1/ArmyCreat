package army.db.pojo;

public class UserTask {
    private Integer id;

    private Integer taskid;

    private Integer userid;

    private Integer state;

    private String payimageurl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPayimageurl() {
        return payimageurl;
    }

    public void setPayimageurl(String payimageurl) {
        this.payimageurl = payimageurl == null ? null : payimageurl.trim();
    }
}