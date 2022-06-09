package com.bhola.english_ghoststory;

public class RowData {
    int _id;
    String Title;
    String Story;
    int Liked;


    public RowData() {
    }


    public RowData(int _id, String title, String story, int like) {
        this._id = _id;
        Title = title;
        Story = story;
        Liked = like;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStory() {
        return Story;
    }

    public void setStory(String story) {
        Story = story;
    }

    public int getLike() {
        return Liked;
    }

    public void setLike(int like) {
        Liked = like;
    }
}
