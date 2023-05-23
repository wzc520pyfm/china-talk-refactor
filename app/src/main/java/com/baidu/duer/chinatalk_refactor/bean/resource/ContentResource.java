package com.baidu.duer.chinatalk_refactor.bean.resource;

public class ContentResource {
    private FileResource fileResource;

    public FileResource getFileResource() {
        return fileResource;
    }

    public void setFileResource(FileResource fileResource) {
        this.fileResource = fileResource;
    }

    @Override
    public String toString() {
        return "ContentResource{" +
                "fileResource=" + fileResource +
                '}';
    }
}
