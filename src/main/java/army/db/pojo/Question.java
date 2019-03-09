package army.db.pojo;

public class Question {
    private Integer id;

    private Integer examid;

    private Integer questionindex;

    private Integer questiontype;

    private String questioncontent;

    private String questionselecttion;

    private String answer;

    private String extra;

    private Integer point;

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

    public Integer getQuestionindex() {
        return questionindex;
    }

    public void setQuestionindex(Integer questionindex) {
        this.questionindex = questionindex;
    }

    public Integer getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(Integer questiontype) {
        this.questiontype = questiontype;
    }

    public String getQuestioncontent() {
        return questioncontent;
    }

    public void setQuestioncontent(String questioncontent) {
        this.questioncontent = questioncontent == null ? null : questioncontent.trim();
    }

    public String getQuestionselecttion() {
        return questionselecttion;
    }

    public void setQuestionselecttion(String questionselecttion) {
        this.questionselecttion = questionselecttion == null ? null : questionselecttion.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}