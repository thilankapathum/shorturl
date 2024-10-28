package dev.thilanka.shorturl.security.user;

import dev.thilanka.shorturl.entity.domains.DomainsService;
import dev.thilanka.shorturl.security.config.AuthenticationResponse;
import dev.thilanka.shorturl.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//-- USER AUTHENTICATION WHEN SIGN-UP AND SIGN-IN --
@Service
@RequiredArgsConstructor
public class UserService {

    //-- Defined whether to allow all URLs to be shortened at application-dev.yml
    @Value("${shorturl.service.allowallurls}")
    private boolean ALLOW_ALL_URLS;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final DomainsService domainsService;

    public void signUp(SignUpRequest request) {

        //-- Check whether email-domain is allowed (if all URL shortening is not-allowed)
        if (ALLOW_ALL_URLS || domainsService.isEmailDomainAllowed(domainsService.emailDomainExtractor(request.getEmail()))) {

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
        } else throw new BadCredentialsException("Email address is not allowed");


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
