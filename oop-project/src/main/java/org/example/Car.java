package org.example;

import java.util.Objects;

public class Car {

    int price;

    public Car() {}

    public Car(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return price == car.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
