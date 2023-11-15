//package com.example.datnsd56.security1;
//
//import com.example.datnsd56.entity.Account;
//import com.example.datnsd56.repository.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    private AccountRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        // Kiểm tra xem user có tồn tại trong database không?
//        Account user = userRepository.findByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new CustomUserDetails(user);
//    }
//
//
//}