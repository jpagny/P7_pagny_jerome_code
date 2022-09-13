package com.nnk.springboot;

import com.nnk.springboot.validator.PasswordConstraintValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordValidatorTest {

    private final PasswordConstraintValidator validator = new PasswordConstraintValidator();


    @Test
    @DisplayName("Should be returned true when password is encoded")
    public void should_beReturnedTrue_when_passwordIsEncoded() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("123456");
        assertFalse(pw.contains("123456"));
        assertTrue(pw.length() > 10);
    }

    @Test
    @DisplayName("Should be returned true when password has upper case and numeric and special character")
    public void should_beReturnedTrue_when_passwordHasUpperCaseAndNumericAndSpecialCharacter() {
        assertTrue(validator.isValid("Test69&!Test", null));
    }

    @Test
    @DisplayName("Should be returned false when password has not upper case")
    public void should_beReturnedFalse_when_passwordHasNotUpperCase() {
        assertFalse(validator.isValid("test69&!test", null));
    }

    @Test
    @DisplayName("Should be returned when password has not special character")
    public void should_beReturnedFalse_when_passwordHasNotSpecialCharacter() {
        assertFalse(validator.isValid("Test69test", null));
    }

    @Test
    @DisplayName("Should be returned false when password has not digit")
    public void should_beReturnedFalse_when_passwordHasNotDigit() {
        assertFalse(validator.isValid("Test&!test", null));
    }


}
