package studybackend.refrigeratorcleaner.login.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import studybackend.refrigeratorcleaner.Entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.Repository.UserRepository;

import static studybackend.refrigeratorcleaner.error.ErrorCode.NOT_EXIST_USER_SOCIALID;


@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_USER_SOCIALID));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getSocialId())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
