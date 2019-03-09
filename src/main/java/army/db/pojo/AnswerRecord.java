package army.db.pojo;

public class AnswerRecord {
    private Integer id;

    private Integer examid;

    private String certificatenumber;

    private byte[] answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamid() {
        return examid;
    }

    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    public String getCertificatenumber() {
        return certificatenumber;
    }

    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber == null ? null : certificatenumber.trim();
    }

    public byte[] getAnswer() {
        return answer;
    }

    public void setAnswer(byte[] answer) {
        this.answer = answer;
    }
}