package com.baidu.duer.chinatalk_refactor.bean.resource;

public class FileResource {
    /** id */
    private int id;
    /** 文件名 */
    private String name;
    /** 原始文件名 */
    private String originalname;
    /** 文件类型 */
    private String type;
    /** 文件大小 */
    private String size;
    /** 文件访问路径（不包含url前缀） */
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ContentResource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalname='" + originalname + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
