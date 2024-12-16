package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponse {
    private final String name;
    private final String description;
}
