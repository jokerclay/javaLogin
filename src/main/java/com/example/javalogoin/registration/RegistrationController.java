package com.example.javalogoin.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request ) {
        return registrationService.register(request);
    }
    // check if the  email is valid

    @GetMapping("/confirm")
    public String Confirm(@RequestParam String token) {
        return registrationService.confirmToken(token);
    }



}
