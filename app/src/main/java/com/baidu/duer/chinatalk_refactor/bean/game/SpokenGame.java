package com.baidu.duer.chinatalk_refactor.bean.game;

public class SpokenGame {
    private int imgResource;

    public SpokenGame(int imgResource) {
        this.imgResource = imgResource;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    @Override
    public String toString() {
        return "Game{" +
                "imgResource=" + imgResource +
                '}';
    }
}
