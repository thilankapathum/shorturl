package dev.thilanka.shorturl.security.user;

import dev.thilanka.shorturl.security.config.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@Valid @RequestBody SignUpRequest request){
        return ResponseEntity.ok(userService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@Valid @RequestBody SignInRequest request){
        return ResponseEntity.ok(userService.signIn(request));
    }

}
