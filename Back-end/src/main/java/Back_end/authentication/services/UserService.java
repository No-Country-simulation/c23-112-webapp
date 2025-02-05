package Back_end.authentication.services;

import Back_end.authentication.entities.User;
import Back_end.authentication.repositories.UserRepository;
import Back_end.authentication.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authority
        );
    }

    public boolean existsByUserName(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User updateUser(String userId, User userDetails) {
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Actualizar los campos básicos
            existingUser.setName(userDetails.getName());
            existingUser.setLast_name(userDetails.getLast_name());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhoto(userDetails.getPhoto());
            existingUser.setPhone(userDetails.getPhone());
            existingUser.setProfession(userDetails.getProfession());
            existingUser.setSkills(userDetails.getSkills());
            existingUser.setAboutMe(userDetails.getAboutMe());
            existingUser.setCv(userDetails.getCv());

            // ✅ Establecer el usuario en cada objeto de la lista antes de asignarla
            existingUser.setExperience(userDetails.getExperience().stream()
                    .map(experience -> {
                        experience.setUser(existingUser);
                        return experience;
                    })
                    .collect(Collectors.toList()));

            existingUser.setEducation(userDetails.getEducation().stream()
                    .map(education -> {
                        education.setUser(existingUser);
                        return education;
                    })
                    .collect(Collectors.toList()));

            // ✅ Actualizar contraseña solo si el usuario envió una nueva
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            // ✅ Si se permite modificar el rol del usuario
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }

            // Guardar el usuario actualizado en la base de datos
            return userRepository.save(existingUser);
        }

        return null;  // Si el usuario no existe
    }


}
