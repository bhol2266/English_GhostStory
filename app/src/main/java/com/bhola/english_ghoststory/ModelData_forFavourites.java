package com.bhola.english_ghoststory;

public class ModelData_forFavourites {

    int _id;
    String Title,Story;
    int Liked;

    public ModelData_forFavourites() {
    }

    public ModelData_forFavourites(int _id, String title, String story, int liked) {
        this._id = _id;
        Title = title;
        Story = story;
        Liked = liked;
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

    public int getLiked() {
        return Liked;
    }

    public void setLiked(int liked) {
        Liked = liked;
    }
}
