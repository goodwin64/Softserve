package com.softserve.test2016;

import java.util.ArrayList;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 16.03.2016.
 */
public class Main {
    public static void main(String[] Args) {
        essay1();
    }

    public static void essay1() {
        Academy academy = new Academy();

        academy.addPerson(new Worker());
        academy.addPerson(new Student());
        academy.addPerson(new Person());

        academy.showAll();
    }

    public static void essay2() {
        // TODO: implement the 2nd part of Java essay
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
        return getName();
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
        return String.format("Student %s %s \n", getName(), getEducation());
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
        return String.format("Worker %s %s \n", getName(), getWorkPlace());
    }
}

class Academy {
    private ArrayList<Person> container = new ArrayList<>(10);

    public Academy() {
        this.container = new ArrayList<>(0);
    }

    public void showAll() {
        for (Person person : container) {
            System.out.printf("%s", person.showData());
        }
    }

    public void addPerson(Person person) {
        this.container.add(person);
    }
}
