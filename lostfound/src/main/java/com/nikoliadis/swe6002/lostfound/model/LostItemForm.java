package com.nikoliadis.swe6002.lostfound.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LostItemForm {

    @NotBlank(message = "Title is required")
    @Size(max = 120, message = "Title must be up to 120 characters")
    private String title;

    @Size(max = 500, message = "Description must be up to 500 characters")
    private String description;

    @Size(max = 120, message = "Location must be up to 120 characters")
    private String location;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
