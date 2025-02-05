package Back_end.services;

import Back_end.dto.NewUserDto;
import Back_end.entities.Role;
import Back_end.entities.User;
import Back_end.enums.RoleList;
import Back_end.jwt.JwtUtil;
import Back_end.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public AuthService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(String username, String password){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public void registerUser(NewUserDto newUserDto){
        if (userService.existsByUserName(newUserDto.getEmail())){
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        Role roleUser = roleRepository.findByName(RoleList.ROLE_USER_0).orElseThrow(()->new RuntimeException("Rol no encontrado"));
        User user = User.builder()
                .email(newUserDto.getEmail())
                .name(newUserDto.getName())
                .last_name(newUserDto.getLast_name())
                .password(passwordEncoder.encode(newUserDto.getPassword()))
                .role(roleUser)
                .build();
        userService.save(user);
    }
}
