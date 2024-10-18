package dev.thilanka.shorturl.security.user;

import dev.thilanka.shorturl.security.config.AuthenticationResponse;
import dev.thilanka.shorturl.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//-- USER AUTHENTICATION WHEN SIGN-UP AND SIGN-IN --
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void signUp(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .disabled(true)
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public AuthenticationResponse signIn(SignInRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        //-- username & password is correct at here... (authenticationManager will throw exception if username or password are invalid)
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by: " + request.getUsername()));

        if (!user.isDisabled()) {
            var jwt = jwtService.generateJwt(user);
            return AuthenticationResponse.builder().jwt(jwt).build();
        } else throw new RuntimeException("User account is disabled");

    }
}
