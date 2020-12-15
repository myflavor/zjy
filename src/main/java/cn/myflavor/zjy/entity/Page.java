package cn.myflavor.zjy.entity;

import java.util.List;

public class Page<T> {
    private String count;
    private List<T> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
