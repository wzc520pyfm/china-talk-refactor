package com.baidu.duer.chinatalk_refactor.bean.real;

import java.util.ArrayList;

public class RealQuestion {
    private int id;
    private String content;
    private ArrayList<String> items;
    private String answer;

    public RealQuestion(int id, String content, ArrayList<String> items, String answer) {
        this.id = id;
        this.content = content;
        this.items = items;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "RealQuestion{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
