package br.com.cardoso.controller;

import br.com.cardoso.service.DemoOtelServiceImpl;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DemoOtelController {

    private final DemoOtelServiceImpl otelService;

    public DemoOtelController(DemoOtelServiceImpl otelService) {
        this.otelService = otelService;
    }

    @GetMapping("/hello")
    public String hello() {
        String correlationId = UUID.randomUUID().toString();
        System.out.println("Gerou o correlationId" + correlationId);
        MDC.put("correlationId", correlationId);
        return "Hello " + otelService.hello();
    }

    @GetMapping("/helloJersey")
    public String helloJersey() {
        String correlationId = UUID.randomUUID().toString();
        System.out.println("Gerou o correlationId" + correlationId);
        MDC.put("correlationId", correlationId);
        return "Hello " + otelService.helloJersey();
    }
    @GetMapping("/world")
    public String world() {
        return "World";
    }
}
