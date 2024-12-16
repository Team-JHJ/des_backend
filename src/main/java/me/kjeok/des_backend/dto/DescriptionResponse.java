package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kjeok.des_backend.domain.Description;

@Getter
@AllArgsConstructor
public class DescriptionResponse {
    private final String name;
    private final String description;
    private final Object value;

    public DescriptionResponse(Description description, Object value) {
        this.name = description.getName();
        this.description = description.getDescription();
        this.value = value;
    }
}