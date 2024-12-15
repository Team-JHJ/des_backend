package me.kjeok.des_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kjeok.des_backend.domain.*;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeResponse {
    private final Long id;
    private final String homename;
    private final Boolean inverterFault;
    private final Boolean derFault;
    private final Boolean homeloadFault;
    private final Boolean smartmeterFault;

    public HomeResponse(Home home) {
        this.id = home.getId();
        this.homename = home.getHomeName();
        this.inverterFault = home.getInverterFault();
        this.derFault = home.getDerFault();
        this.homeloadFault = home.getHomeloadFault();
        this.smartmeterFault = home.getSmartmeterFault();
    }
}
