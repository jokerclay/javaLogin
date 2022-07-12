package com.example.javalogoin.AppUser;

import com.example.javalogoin.registration.token.ConfirmationToken;
import com.example.javalogoin.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private  final static String  USER_NOT_FOUND_MSG = "邮箱为 %s 的用户未找到";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
    public String singUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

       if (userExists) {
           throw new IllegalStateException("邮箱已存在");
       }
        String encodedPassword = bcryptPasswordEncoder.encode(appUser.getPassword());

       appUser.setPassword(encodedPassword);

       // save the user
       appUserRepository.save(appUser);

       // 随机生成的字符串 token
       String token = UUID.randomUUID().toString();
       // TODO: send confirmation and token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),        // token 过期时间
                appUser
        );

        // 保存用户
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //  TODO: 发送 email
        // 返回 token
       return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }


}
