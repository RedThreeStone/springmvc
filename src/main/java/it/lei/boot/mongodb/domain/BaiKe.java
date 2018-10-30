package it.lei.boot.mongodb.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaiKe {
    private String id;
    private String desc;
    private List<String> tag=new ArrayList<>();
    private Date createDate;
    private Date updateDate;

    @Override
    public String toString() {
        return "BaiKe{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", tag=" + tag +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
