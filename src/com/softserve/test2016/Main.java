package com.softserve.test2016;

import java.util.ArrayList;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 16.03.2016.
 */
public class Main {
    public static void main(String[] Args) {
        essay1();
        essay2();
    }

    public static void essay1() {
        Academy academy = new Academy();

        academy.addPerson(new Worker());
        academy.addPerson(new Student());
        academy.addPerson(new Person());

        academy.showAll();
    }

    public static void essay2() {
        Order order1 = new Order();
        Order order2 = new Order(new Customer());
        Order order3 = new Order(new Customer(), 20);
        Order order4 = new Order(new Customer(), 400);

        System.out.println(order1);
        System.out.println(order2);
        System.out.println(order3);
        System.out.println(order4);
    }
}

/**
 * Parent class for Student and Worker
 */
class Person {
    private String name = "";
    public static String[] names = {
            "Max", "Alex", "Ivan", "Nick", "George"
    };

    public Person() {
        setName(names[(int) (Math.random() * 5)]);
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String showData() {
        return String.format("%s", getName());
    }
}


class Student extends Person {
    private String education = "";
    public String[] educations = {
            "KPI", "KNU", "KIMO", "KNEU", "LP"
    };

    public Student() {
        super();
        this.education = educations[(int) (Math.random() * 5)];
    }

    public String getEducation() {
        return education;
    }

    @Override
    public String showData() {
        return String.format("Student %s %s", getName(), getEducation());
    }
}

class Worker extends Person {
    private String workPlace = "";
    public String[] workplaces = {
            "driver", "painter", "carpenter", "designer", "doctor"
    };

    public Worker() {
        super();
        this.workPlace = workplaces[(int) (Math.random() * 5)];
    }

    public String getWorkPlace() {
        return workPlace;
    }

    @Override
    public String showData() {
        return String.format("Worker %s %s", getName(), getWorkPlace());
    }
}

class Academy {
    private ArrayList<Person> container = new ArrayList<>(10);

    public Academy() {
        this.container = new ArrayList<>(0);
    }

    public void showAll() {
        for (Person person : container) {
            System.out.printf("%s%n", person.showData());
        }
    }

    public void addPerson(Person person) {
        this.container.add(person);
    }
}

class Order {
    private Customer customer;
    private int days;
    public static final int ORDER_MAX_DAYS = 366;

    public Order() {
        this.customer = new Customer();
    }
    public Order(Customer customer) {
        setCustomer(customer);
        this.days = (int) (Math.random() * 11) - 5;
    }
    public Order(Customer customer, int days) {
        setCustomer(customer);
        try {
            setDays(days);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }
    }

    public int getDays() {
        return days;
    }
    public void setDays(int days) throws IllegalArgumentException {
        if (days >= 0 && days < ORDER_MAX_DAYS) {
            this.days = days;
        } else {
            throw new IllegalArgumentException("Wrong days count: " + days); // >1 year order
        }
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return String.format("Order for %s, %d days old", getCustomer(), getDays());
    }
}

class Customer {
    private String name = "";

    public Customer() {
        setName(Person.names[(int) (Math.random() * 5)]);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
