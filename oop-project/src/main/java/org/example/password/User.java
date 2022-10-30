package org.example.password;

public class User {

    private String password;

    public void initPassworrd(PasswordGeneratedPolicy passwordGeneratedPolicy) {
        this.password = passwordGeneratedPolicy.generatePassword();

        int length = password.length();
        if (length < 8 || length > 12) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상 12자 이하여야 한다.");
        }
    }
}
