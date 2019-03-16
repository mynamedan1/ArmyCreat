package army.db.pojo;

public class HonorRecord {
    private Integer id;

    private Integer userid;

    private Integer point;

    private String time;

    private Integer type;

    private String extra;

    private String typeexpense;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public String getTypeexpense() {
        return typeexpense;
    }

    public void setTypeexpense(String typeexpense) {
        this.typeexpense = typeexpense == null ? null : typeexpense.trim();
    }
}