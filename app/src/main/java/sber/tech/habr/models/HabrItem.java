package sber.tech.habr.models;

public class HabrItem {
    private String title;
    private String link;
    private String description;

    public HabrItem() {
    }

    public HabrItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }
    public String getTitle() {
        return this.title;
    }
}
