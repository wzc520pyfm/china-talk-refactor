package com.baidu.duer.chinatalk_refactor.bean.question;

public class NarrateQuestion {

    /** id */
    private int id;
    /** 题目类别 */
    private String questionClassification;
    /** 题目难度 */
    private String questionDifficulty;
    /** 题目内容 */
    private String content;
    /** 题目答案 */
    private String answer;
    /** 题目提示 */
    private String tip;
    /** 题目解析 */
    private String analysis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionClassification() {
        return questionClassification;
    }

    public void setQuestionClassification(String questionClassification) {
        this.questionClassification = questionClassification;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return "NarrateQuestion{" +
                "id=" + id +
                ", questionClassification='" + questionClassification + '\'' +
                ", questionDifficulty='" + questionDifficulty + '\'' +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", tip='" + tip + '\'' +
                ", analysis='" + analysis + '\'' +
                '}';
    }
}
