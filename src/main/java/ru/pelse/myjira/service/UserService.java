package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import ru.pelse.myjira.entity.Role;
import ru.pelse.myjira.entity.User;
import ru.pelse.myjira.repository.RoleRepository;
import ru.pelse.myjira.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByUsername(email);
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false; //проверка в RegistrationController на false
        }

        System.out.println("Пользователь не найден, регистрируем");
        user.setActive(true);
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setBirth(user.getBirth());
        user.setPhone(user.getPhone());
        System.out.println("Перед соханением пользоателя в бд");
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getUsername())) {
            String message = String.format(
                    "Здравствуйте, %s .\n" +
                            "Пожалуйста, подтвердите регистрацию, перейдя по ссылке \n" +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getUsername(), "Activation code", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        System.out.println("код активации: " + code);

        if (user == null) {
            System.out.println("activateUser: user == null");
            return false;
        }
        //пользователь подтвердил почту => установим setActivationCode(null)
        user.setActivationCode("done");
        userRepository.save(user);

        return true;
    }
}
