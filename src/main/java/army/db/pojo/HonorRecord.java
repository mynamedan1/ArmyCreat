package army.db.pojo;

public class HonorRecord {
    private Integer id;

    private String certificatenumber;

    private Integer point;

    private String time;

    private Integer type;

    private String extra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertificatenumber() {
        return certificatenumber;
    }

    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber == null ? null : certificatenumber.trim();
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}