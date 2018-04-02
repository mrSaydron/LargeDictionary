package ru.mrak.LargeDictionary.to;

public abstract class BaseTo {
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    //@Override
    public Integer getId() {
        return id;
    }

    //@Override
    public void setId(Integer id) {
        this.id = id;
    }
}
