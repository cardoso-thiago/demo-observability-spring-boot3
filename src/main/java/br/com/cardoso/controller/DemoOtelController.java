package br.com.cardoso.controller;

import br.com.cardoso.service.DemoOtelServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoOtelController {

    private final DemoOtelServiceImpl otelService;

    public DemoOtelController(DemoOtelServiceImpl otelService) {
        this.otelService = otelService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello " + otelService.hello();
    }

    @GetMapping("/helloJersey")
    public String helloJersey() {
        return "Hello " + otelService.helloJersey();
    }
    @GetMapping("/world")
    public String world() {
        return "World";
    }
}
