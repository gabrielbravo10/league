package com.bravo.league.controllers;

import com.bravo.league.controllers.dto.LoginDTO;
import com.bravo.league.controllers.dto.TokenDTO;
import com.bravo.league.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> store(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        authenticationManager.authenticate(login);

        TokenDTO tokenDTO = new TokenDTO("Bearer", authenticationService.getToken(login));

        return ResponseEntity.ok().body(tokenDTO);
    }
}
