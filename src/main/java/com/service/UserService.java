package com.service;

import com.model.Customers;
import com.repository.CustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {

    @Lazy
    private final PasswordEncoder passwordEncoder;

   // private final UsersRepository usersRepository;
    private final CustomersRepository usersRepository;

    public Optional<Customers> loadUserByEmail(String email){
        Optional<Customers> user = usersRepository.findByEmail(email);
        return user;
    }

//    public Optional<MinimalUser> loadMinimalUserByEmail(String email){
//        return loadUserByEmail(email).map(MinimalUser::new);
//    }
//
//    public Optional<DetailedUser> loadDetailedUserByEmail(String email){
//        return loadUserByEmail(email).map(DetailedUser::new);
//    }
//
//    public Users save(UserSignupDto signupDto){
//        Users user = new Users(signupDto.getName(), signupDto.getSurname(), passwordEncoder.encode(signupDto.getPassword()),signupDto.getEmail(),signupDto.getPhone(), "ABSTRACT_SYSTEM_USER");
//        return usersRepository.save(user);
//    }
//
//    public Users update(Users user){
//        return usersRepository.save(user);
//    }
//
//    public void delete(Users user){
//        usersRepository.delete(user);
//    }
//
//    public Users createMachineUser(String email){
//        return usersRepository.save(Users.builder()
//                .name(RandomStringUtils.randomAlphabetic(10))
//                .surname(RandomStringUtils.randomAlphabetic(10))
//                .password(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(16)))
//                .email(email)
//                .role("MACHINE_USER")
//                .creationDate(new Timestamp(System.currentTimeMillis()))
//                .build());
//    }
//
//    public boolean exists(String email){
//        return usersRepository.existsByEmail(email);
//    }
}

