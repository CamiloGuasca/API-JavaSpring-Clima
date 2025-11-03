package com.taller2.taller2.modelo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pronostico {
    private long dt;

    @JsonProperty("main")
    private Datos datos;
    
    @JsonProperty("weather")
    private List<DescClima> clima;
}
