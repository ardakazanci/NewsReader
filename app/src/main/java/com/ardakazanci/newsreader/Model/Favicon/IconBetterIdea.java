package com.ardakazanci.newsreader.Model.Favicon;

import java.util.List;

public class IconBetterIdea {

    private String url;
    private List<Icon> icons;

    public IconBetterIdea(String url, List<Icon> icons) {
        this.url = url;
        this.icons = icons;
    }

    public IconBetterIdea() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
