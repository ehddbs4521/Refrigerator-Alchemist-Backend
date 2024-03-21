package studybackend.refrigeratorcleaner.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.redis.entity.BlackList;
import studybackend.refrigeratorcleaner.redis.repository.BlackListRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static studybackend.refrigeratorcleaner.error.ErrorCode.*;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String LOGIN_CHECK_URL = "/login";

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BlackListRepository blackListRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String requestURI = request.getRequestURI();

        if (requestURI.equals(LOGIN_CHECK_URL) || pathMatcher.match("/auth/**", requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (accessToken != null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
            return;
        }

        throw new CustomException(NOT_VALID_ACCESSTOKEN);

    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .orElseThrow(() -> new CustomException(NOT_EXTRACT_ACCESSTOKEN));

        String socialID = jwtService.extractSocialId(accessToken)
                .orElseThrow(() -> new CustomException(NOT_EXTRACT_SOCIALID));

        User user = userRepository.findBySocialId(socialID)
                .orElseThrow(() -> new CustomException(NOT_EXIST_USER_SOCIALID));

        Optional<BlackList> blackList = blackListRepository.findBySocialId(socialID);

        if (!blackList.isEmpty() && blackList.get().getAccessToken().equals(accessToken)) {
            throw new CustomException(NOT_VALID_ACCESSTOKEN);
        }

        saveAuthentication(user);

        filterChain.doFilter(request, response);
    }


    public void saveAuthentication(User user) {
        String password = user.getPassword();
        if (password == null) {
            password = UUID.randomUUID().toString().replace("-","").substring(0,8);
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getSocialId())
                .password(password)
                .roles(user.getRole())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
