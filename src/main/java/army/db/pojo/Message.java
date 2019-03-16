package army.db.pojo;

public class Message {
    private Integer id;

    private String content;

    private Integer relaseuser;

    private Integer claimuser;

    private String sendtime;

    private Integer state;

    private String updatetime;

    private String extra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getRelaseuser() {
        return relaseuser;
    }

    public void setRelaseuser(Integer relaseuser) {
        this.relaseuser = relaseuser;
    }

    public Integer getClaimuser() {
        return claimuser;
    }

    public void setClaimuser(Integer claimuser) {
        this.claimuser = claimuser;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime == null ? null : sendtime.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}