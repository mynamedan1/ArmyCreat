package army.db.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    private String armycart;

    private String identitycart;

    private Integer phonenumber;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getArmycart() {
        return armycart;
    }

    public void setArmycart(String armycart) {
        this.armycart = armycart == null ? null : armycart.trim();
    }

    public String getIdentitycart() {
        return identitycart;
    }

    public void setIdentitycart(String identitycart) {
        this.identitycart = identitycart == null ? null : identitycart.trim();
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }
}