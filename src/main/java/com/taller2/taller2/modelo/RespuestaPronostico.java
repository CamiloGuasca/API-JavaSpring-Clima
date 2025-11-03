package com.taller2.taller2.modelo;
import lombok.Data; 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; 
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaPronostico {
    private List<Pronostico> list;
}
