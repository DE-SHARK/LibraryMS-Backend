package me.deshark.lms.common.exception.domain;

import lombok.Getter;

/**
 * 服务操作异常
 * @author DE_SHARK
 */
@Getter
public class DomainServiceException extends DomainException {

    public DomainServiceException(String message) {
        super(message);
    }

    public DomainServiceException(String errorCode, String message) {
        super(errorCode, message);
    }
}
