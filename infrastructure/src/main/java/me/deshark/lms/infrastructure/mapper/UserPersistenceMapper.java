package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.model.auth.vo.Password;
import me.deshark.lms.infrastructure.entity.UserDO;
import org.springframework.stereotype.Component;

/**
 * @author DE_SHARK
 */
// 对象映射器
@Component
public class UserPersistenceMapper {

    public UserDO toDataObject(AuthUser authUser) {
        return UserDO.builder()
                .uuid(authUser.getUuid())
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .passwordHash(authUser.getPasswordHash().encryptedValue())
                .status(authUser.getStatus())
                .build();
    }

    public AuthUser toDomainEntity(UserDO userDO) {
        return AuthUser.builder()
                .uuid(userDO.getUuid())
                .username(userDO.getUsername())
                .email(userDO.getEmail())
                .passwordHash(new Password(userDO.getPasswordHash()))
                .status(userDO.getStatus())
                .build();
    }
}
