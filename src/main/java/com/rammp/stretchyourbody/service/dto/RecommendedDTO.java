package com.rammp.stretchyourbody.service.dto;

/**
 * Created by paulasegura on 11/7/17.
 */
public class RecommendedDTO {
    private Long id;
    private String name;
    private String tag;

    public RecommendedDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
