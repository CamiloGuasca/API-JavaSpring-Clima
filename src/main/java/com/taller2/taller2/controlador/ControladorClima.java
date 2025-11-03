package com.taller2.taller2.controlador;
import com.taller2.taller2.servicio.ServicioClima;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clima")
public class ControladorClima {
    private final ServicioClima servicioClima;
    
    public ControladorClima(ServicioClima servicioClima){
        this.servicioClima = servicioClima;
    }

    @GetMapping("/resumen")
    public String getMethodName(@RequestParam String ciudad) {
        return servicioClima.formatearInfo(ciudad);
    }
     @GetMapping("/aa")
    public String getMethodName() {
        return "Buena socio";
    }
    
}
