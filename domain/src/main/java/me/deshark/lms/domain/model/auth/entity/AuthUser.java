package me.deshark.lms.domain.model.auth.entity;

import lombok.Builder;
import lombok.Data;
import me.deshark.lms.common.exception.auth.AuthenticationException;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.auth.vo.Password;
import me.deshark.lms.domain.model.auth.vo.UserRole;
import me.deshark.lms.domain.model.auth.vo.UserStatus;
import me.deshark.lms.domain.service.auth.PasswordEncryptor;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Data
@Builder
public class AuthUser {
    private UUID uuid;
    private String username;
    private Password passwordHash;
    private String email;
    private UserStatus status;
    private UserRole userRole;

    // 创建新用户
    public static AuthUser createUser(String username, String rawPassword, String email, PasswordEncryptor encryptor) {
        return AuthUser.builder()
                .uuid(GUID.v7())
                .username(username)
                .passwordHash(Password.fromEncrypted(encryptor.encrypt(rawPassword)))
                .email(email)
                .status(UserStatus.UNVERIFIED)
                .build();
    }

    // 业务行为方法
    public void authenticate(String rawPassword, PasswordEncryptor encryptor) {
        if (!passwordHash.matches(rawPassword, encryptor)) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if (status != UserStatus.ACTIVE) {
            throw new AuthenticationException("用户状态异常: " + status);
        }
    }

    // 状态变更方法
    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
}
