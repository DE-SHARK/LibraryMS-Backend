package me.deshark.lms.common.exception.application;

/**
 * @author DE_SHARK
 */
public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(String message) {
        super(message);
    }
}
