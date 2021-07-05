package com.spring.customSecurity.repository;

import com.spring.customSecurity.constant.Database;
import com.spring.customSecurity.model.User;
import com.spring.customSecurity.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    PasswordEncoder passwordEncoder;

    public User findUser(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        } else {
            UserAccount userAccount = Database.userAccountList.stream().filter(
                    u -> ObjectUtils.nullSafeEquals(u.getUsername(), username)).findAny().orElse(null);
            if(userAccount == null) {
                return null;
            }
            List<GrantedAuthority> gaList = new ArrayList<>();
            userAccount.getAuthorities().stream().forEach(auth -> gaList.add(() -> auth));
            User user =  new User(username, passwordEncoder.encode("secret"), true,
                    true, true, true, gaList);
            return user;
        }
    }
}
