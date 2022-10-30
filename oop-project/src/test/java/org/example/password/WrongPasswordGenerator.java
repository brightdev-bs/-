package org.example.password;

import org.example.password.PasswordGeneratedPolicy;

public class WrongPasswordGenerator implements PasswordGeneratedPolicy {

    @Override
    public String generatePassword() {
        return "aa";
    }
}
