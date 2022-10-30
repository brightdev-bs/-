package org.example;

public class CorrectPassowrdGenerator implements PasswordGeneratedPolicy {

    @Override
    public String generatePassword() {
        return "aaabbbcc";
    }
}
