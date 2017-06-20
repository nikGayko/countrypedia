package com.example.nick.countrypedia.view.item;

public class Group extends ListItem{
    private String mGroupName;

    public Group(String alphabetGroup) {
        mGroupName = alphabetGroup;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }


    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
