package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeResponse {
    private Long id;
    private String homename;
    private Boolean inverterFault;
    private Boolean derFault;
    private Boolean homeloadFault;
    private Boolean smartmeterFault;
}
