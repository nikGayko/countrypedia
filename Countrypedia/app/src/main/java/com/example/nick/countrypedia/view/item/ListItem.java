package com.example.nick.countrypedia.view.item;

public abstract class ListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;

    abstract public int getType();
}
