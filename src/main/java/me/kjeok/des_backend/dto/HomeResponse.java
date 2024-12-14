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

    public HomeResponse(Home home, boolean inverterFault, boolean derFault, boolean homeloadFault, boolean smartmeterFault) {
        this.id = home.getId();
        this.homename = home.getHomeName();
        this.inverterFault = inverterFault;
        this.derFault = derFault;
        this.homeloadFault = homeloadFault;
        this.smartmeterFault = smartmeterFault;
    }
}
