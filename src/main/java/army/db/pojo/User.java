package army.db.pojo;

public class User {
    private Integer id;

    private String name;

    private String certificatenumber;

    private String idcard;

    private String phonenumber;

    private String password;

    private Integer pointcount;

    private String importtype;

    private Integer state;

    private String imgurl;

    private String extra;

    private String updateby;

    private String updatetime;

    private Integer levelvalue;

    private String lavelname;

    private Integer changecount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCertificatenumber() {
        return certificatenumber;
    }

    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber == null ? null : certificatenumber.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getPointcount() {
        return pointcount;
    }

    public void setPointcount(Integer pointcount) {
        this.pointcount = pointcount;
    }

    public String getImporttype() {
        return importtype;
    }

    public void setImporttype(String importtype) {
        this.importtype = importtype == null ? null : importtype.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public Integer getLevelvalue() {
        return levelvalue;
    }

    public void setLevelvalue(Integer levelvalue) {
        this.levelvalue = levelvalue;
    }

    public String getLavelname() {
        return lavelname;
    }

    public void setLavelname(String lavelname) {
        this.lavelname = lavelname == null ? null : lavelname.trim();
    }

    public Integer getChangecount() {
        return changecount;
    }

    public void setChangecount(Integer changecount) {
        this.changecount = changecount;
    }
}