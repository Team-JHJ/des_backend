package me.kjeok.des_backend.dto;

import lombok.Getter;
import me.kjeok.des_backend.domain.Description;

@Getter
public class DescriptionResponse {
    private final String name;
    private final String description;

    public DescriptionResponse(Description description) {
        this.name = description.getName();
        this.description = description.getDescription();
    }
}
