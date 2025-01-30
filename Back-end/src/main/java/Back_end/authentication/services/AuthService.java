package Back_end.authentication.services;

import Back_end.authentication.dto.NewUserDto;
import Back_end.authentication.entities.Role;
import Back_end.authentication.entities.User;
import Back_end.authentication.enums.RoleList;
import Back_end.authentication.jwt.JwtUtil;
import Back_end.authentication.repositories.RoleRepository;
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
        if (userService.existsByUserName(newUserDto.getUserName())){
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        RoleList role = RoleList.getById(newUserDto.getRol());
        Role roleUser = roleRepository.findByName(role).orElseThrow(()->new RuntimeException("Rol no encontrado"));
        User user = User.builder()
                .userName(newUserDto.getUserName())
                .name(newUserDto.getName())
                .last_name(newUserDto.getLast_name())
                .password(passwordEncoder.encode(newUserDto.getPassword()))
                .role(roleUser)
                .build();
        userService.save(user);
    }
}
