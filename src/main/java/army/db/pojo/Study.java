package army.db.pojo;

public class Study {
    private Integer id;

    private String title;

    private String time;

    private Integer imgurl;

    private Integer state;

    private String extra;

    private Integer releaseby;

    private Integer point;

    private byte[] content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Integer getImgurl() {
        return imgurl;
    }

    public void setImgurl(Integer imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public Integer getReleaseby() {
        return releaseby;
    }

    public void setReleaseby(Integer releaseby) {
        this.releaseby = releaseby;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}