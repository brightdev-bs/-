package org.example.password;

import org.example.password.PasswordGeneratedPolicy;

public class CorrectPassowrdGenerator implements PasswordGeneratedPolicy {

    @Override
    public String generatePassword() {
        return "aaabbbcc";
    }
}
