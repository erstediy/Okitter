package com.krizhanovsky.okitter.service;

import com.krizhanovsky.okitter.bean.PasswordEncoder;
import com.krizhanovsky.okitter.entity.Role;
import com.krizhanovsky.okitter.entity.User;
import com.krizhanovsky.okitter.repository.UserRepository;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, LockedException {
        User user = repository.findByUsername(username);
        //if(user == null) throw new LockedException("Пользователь не найден");
        //if(user.getActivationCode()!=null) throw new LockedException("Аккаунт не активирован");
        return user;
    }

    public boolean addUser(User user){
        User userFrom = repository.findByUsername(user.getUsername());

        if(userFrom != null){
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(user.getPassword()));

        repository.save(user);

        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if(!StringUtils.isNullOrEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Okkiter. Please, visit next link to activate your account: \n"
                    + "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(),"Activation code",message);
        }
    }

    public boolean activateUser(String code) {
        User user = repository.findByActivationCode(code);
        if(user == null) return false;

        user.setActive(true);
        user.setActivationCode(null);

        repository.save(user);

        return true;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void userSave(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for(String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        repository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = ((!StringUtils.isEmptyOrWhitespaceOnly(email) && !email.equals(userEmail))||
                (!StringUtils.isEmptyOrWhitespaceOnly(userEmail) && !userEmail.equals(email)));
        if(isEmailChanged){
            user.setEmail(email);
            user.setActive(false);
            if(!StringUtils.isEmptyOrWhitespaceOnly(email)) user.setActivationCode(UUID.randomUUID().toString());
        }
        if(!StringUtils.isNullOrEmpty(password)) user.setPassword(passwordEncoder.getPasswordEncoder().encode(password));

        repository.save(user);

        if(isEmailChanged) sendMessage(user);
    }
}
