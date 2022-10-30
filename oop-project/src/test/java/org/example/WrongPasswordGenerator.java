package org.example;

public class WrongPasswordGenerator implements PasswordGeneratedPolicy {

    @Override
    public String generatePassword() {
        return "aa";
    }
}
