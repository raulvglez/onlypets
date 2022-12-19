package com.example.fb.implementations;

import com.example.fb.entities.Authority;
import com.example.fb.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.fb.entities.User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe ese usuario"));

        List grantedList = new ArrayList();
        for (Authority authority: user.getAuthority()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
            grantedList.add(grantedAuthority);
        }

        UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), grantedList);
        return userDetails;
    }
}
