package com.baidu.duer.chinatalk_refactor.bean.question;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.resource.AnswerResource;
import com.baidu.duer.chinatalk_refactor.bean.resource.ContentResource;
import com.baidu.duer.chinatalk_refactor.bean.word.Word;

import java.util.ArrayList;

public class Question {

    @NonNull
    private int id; // 问题id
    private SelectQuestion selectQuestion; // 判断题
    private JudgmentQuestion judgmentQuestion; // 选择题
    private NarrateQuestion narrateQuestion; // 问答题
    private ArrayList<Word> words; // 与问题相关的字/词
    private ArrayList<ContentResource> contentResources; // 问题内容所用到的静态资源(比如图片)
    private ArrayList<AnswerResource> answerResource; // 问题答案用到的静态资源(比如图片)

    public Question(int id, SelectQuestion selectQuestion) {
        this.id = id;
        this.selectQuestion = selectQuestion;
    }

    public Question(int id, JudgmentQuestion judgmentQuestion) {
        this.id = id;
        this.judgmentQuestion = judgmentQuestion;
    }

    public Question(int id, NarrateQuestion narrateQuestion) {
        this.id = id;
        this.narrateQuestion = narrateQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SelectQuestion getSelectQuestion() {
        return selectQuestion;
    }

    public void setSelectQuestion(SelectQuestion selectQuestion) {
        this.selectQuestion = selectQuestion;
    }

    public JudgmentQuestion getJudgmentQuestion() {
        return judgmentQuestion;
    }

    public void setJudgmentQuestion(JudgmentQuestion judgmentQuestion) {
        this.judgmentQuestion = judgmentQuestion;
    }

    public NarrateQuestion getNarrateQuestion() {
        return narrateQuestion;
    }

    public void setNarrateQuestion(NarrateQuestion narrateQuestion) {
        this.narrateQuestion = narrateQuestion;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public ArrayList<ContentResource> getContentResources() {
        return contentResources;
    }

    public void setContentResources(ArrayList<ContentResource> contentResources) {
        this.contentResources = contentResources;
    }

    public ArrayList<AnswerResource> getAnswerResource() {
        return answerResource;
    }

    public void setAnswerResource(ArrayList<AnswerResource> answerResource) {
        this.answerResource = answerResource;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", selectQuestion=" + selectQuestion +
                ", judgmentQuestion=" + judgmentQuestion +
                ", narrateQuestion=" + narrateQuestion +
                ", words=" + words +
                ", contentResources=" + contentResources +
                ", answerResource=" + answerResource +
                '}';
    }
}
