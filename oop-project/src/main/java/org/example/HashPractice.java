package org.example;

public class HashPractice {

    public static void main(String[] args) {
        Car c = new Car(10);
        Car c2 = new Car(10);

        System.out.println(c.hashCode());
        System.out.println(c2.hashCode());
    }

}
