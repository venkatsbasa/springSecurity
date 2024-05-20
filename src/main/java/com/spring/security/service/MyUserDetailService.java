package com.spring.security.service;

import com.spring.security.model.MyUser;
import com.spring.security.model.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private MyUserRepo myUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<MyUser> user= myUserRepo.findByUsername(username);
        if(user.isPresent()){
         var userObj=user.get();

          return User.builder().
                  username(userObj.getUsername())
                  .password(userObj.getPassword())
                  .roles(getUserRoles(userObj)).build();
        }else {
            System.out.println("user not "+username);
            throw new UsernameNotFoundException("passed username not found"+username);
        }
    }

    private String[] getUserRoles(MyUser user) {
        if (user.getRole() == null) {
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
