package cn.myflavor.zjy.entity;

public class Cdk {
    private Integer id;
    private String cdkey;
    private Integer month;

    public Integer getId() {
        return id;
    }

    public Cdk() {
    }

    public Cdk(String cdkey, Integer month) {
        this.cdkey = cdkey;
        this.month = month;
    }

    public Cdk(Integer id, String cdkey, Integer month) {
        this.id = id;
        this.cdkey = cdkey;
        this.month = month;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCdkey() {
        return cdkey;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
