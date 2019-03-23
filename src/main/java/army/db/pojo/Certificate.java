package army.db.pojo;

public class Certificate {
    private Integer id;

    private Integer userid;

    private Integer certificateid;

    private String certificatename;

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

    public Integer getCertificateid() {
        return certificateid;
    }

    public void setCertificateid(Integer certificateid) {
        this.certificateid = certificateid;
    }

    public String getCertificatename() {
        return certificatename;
    }

    public void setCertificatename(String certificatename) {
        this.certificatename = certificatename == null ? null : certificatename.trim();
    }
}