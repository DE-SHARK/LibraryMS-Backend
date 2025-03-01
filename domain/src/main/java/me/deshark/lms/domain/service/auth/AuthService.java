package me.deshark.lms.domain.service.auth;

import me.deshark.lms.common.exception.UsernameAlreadyExistedException;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.model.auth.vo.AuthTokenPair;
import me.deshark.lms.domain.model.auth.vo.TokenRequest;
import me.deshark.lms.domain.repository.auth.UserRepository;

/**
 * @author DE_SHARK
 */
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncryptor encryptor;
    private final TokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncryptor encryptor, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
        this.tokenProvider = tokenProvider;
    }

    public AuthUser registerUser(String username, String password, String email) {

        // 1. 校验用户名唯一性
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistedException(username);
        }

        // 2. 注册新用户
        AuthUser newUser = AuthUser.createUser(username, password, email, encryptor);
        userRepository.save(newUser);
        return newUser;
    }

    public AuthTokenPair authenticate(String username, String rawPassword) {
        AuthUser user = userRepository.findByUsername(username);
        user.authenticate(rawPassword, encryptor);
        return generateToken(new TokenRequest(username, user.getRole().name()));
    }

    public AuthTokenPair generateToken(TokenRequest tokenRequest) {
        return tokenProvider.generateToken(tokenRequest.getUsername(), tokenRequest.getRole());
    }
}
