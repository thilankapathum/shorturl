package dev.thilanka.shorturl.security.user;

import dev.thilanka.shorturl.security.config.AuthenticationResponse;
import dev.thilanka.shorturl.security.email.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.signUp(request);
//        emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
        return ResponseEntity.status(HttpStatus.CREATED).body("User account created by: " + request.getUsername() + ". Check your email to verify the account");
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(userService.signIn(request));
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String verificationToken){
        return userService.verifyUser(verificationToken);
    }


}
