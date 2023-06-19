package itmo.blps.mommy.controller;

import itmo.blps.mommy.dto.UserDTO;
import itmo.blps.mommy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",
            produces = "application/json")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        String token = userService.register(userDTO);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody UserDTO userDTO) {
        String token = userService.auth(userDTO);
        return ResponseEntity.ok().body(token);
    }
}
