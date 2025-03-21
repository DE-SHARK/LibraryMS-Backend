package me.deshark.lms.domain.repository.auth;

import me.deshark.lms.domain.model.auth.entity.AuthUser;

import java.util.Optional;


/**
 * @author DE_SHARK
 */
public interface UserRepository {

    // 根据用户名判断用户是否存在
    boolean existsByUsername(String username);

    // 保存用户聚合根
    void save(AuthUser authUser);

    // 根据用户名查找用户（用于登录验证）
    Optional<AuthUser> findByUsername(String username);

    // 可选：根据邮箱查找用户（用于扩展邮箱登录）

}
