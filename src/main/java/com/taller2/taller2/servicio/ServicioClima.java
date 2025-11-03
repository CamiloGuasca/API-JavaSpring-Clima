package com.taller2.taller2.servicio;

import com.taller2.taller2.modelo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioClima {
    @Value("${openweathermap.api.key}")
    private String apiKey; 
    
    private final RestTemplate restTemplate = new RestTemplate();

    public RespuestaPronostico obtenerPronostico(String ciudad){
        final String API_URL_BASE = "https://api.openweathermap.org/data/2.5/forecast";
        String url = UriComponentsBuilder.fromUriString(API_URL_BASE)
            .queryParam("q",ciudad)
            .queryParam("appid", apiKey)
            .queryParam("units", "metric")
            .toUriString();
            System.out.println("URL FINAL CON CLAVE: " + url);
        return restTemplate.getForObject(url, RespuestaPronostico.class);
    }

    public String formatearInfo(String ciudad){

        RespuestaPronostico respro = obtenerPronostico(ciudad);
        if(respro == null ||  respro.getList() == null || respro.getList().isEmpty()){
            return "No se pudo obtener pronostico para la ciudad "+ciudad+" revise bien perrito";
        }

        List<Pronostico> todRes = respro.getList();

        List<Pronostico> prox24h = todRes.stream().limit(8).collect(Collectors.toList());

        double promtem24h = prox24h.stream()
            .mapToDouble(item -> item.getDatos().getTemperatura())
            .average()
            .orElse(Double.NaN);

        String descripEsp = prox24h.get(0).getClima().get(0).getDescripcion();

        long ulthoract = prox24h.get(0).getDt();

        Instant instant = Instant.ofEpochSecond(ulthoract);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Z"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horaFormateada = localDateTime.format(formatter);


        List<Pronostico> prox3dias = todRes.stream().limit(24).collect(Collectors.toList());

        double temMin3D = prox3dias.stream()
            .mapToDouble(item -> item.getDatos().getTemperatura())
            .min()
            .orElse(Double.NaN);
        
        double temMax3D = prox3dias.stream()
            .mapToDouble(item -> item.getDatos().getTemperatura())
            .max()
            .orElse(Double.NaN);
        
        
        double temMin5D = todRes.stream()
            .mapToDouble(item -> item.getDatos().getTemperatura())
            .min()
            .orElse(Double.NaN);
        
        double temMax5D = todRes.stream()
            .mapToDouble(item -> item.getDatos().getTemperatura())
            .max()
            .orElse(Double.NaN);
            

            return String.format(
            "=========================================\n" +
            "        Pronóstico para %s\n" +
            "=========================================\n\n" +
            "--- Próximas 24 horas ---\n" +
            "Temperatura promedio: %.2f °C\n" +
            "Descripción general: %s\n" +
            "Hora del pronóstico (Referencia): %s\n\n" +
            "--- Resumen Próximos 3 días ---\n" +
            "Mínima esperada: %.2f °C\n" +
            "Máxima esperada: %.2f °C\n\n" + 
            "--- Resumen Próximos 5 días (Global) ---\n" + 
            "Mínima esperada: %.2f °C\n" +                 
            "Máxima esperada: %.2f °C\n" +                 
            "=========================================",
            ciudad, 
            promtem24h, 
            descripEsp, 
            horaFormateada,
            temMin3D, 
            temMax3D,
            temMin5D, 
            temMax5D  
        );
    }
}