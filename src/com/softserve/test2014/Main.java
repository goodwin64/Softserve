package com.softserve.test2014;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Max Donchenko (https://github.com/goodwin64) on 12.03.2016.
 */
public class Main {
    public static void main(String[] args) {
        generateEmployees("data-in.txt", 35);

        ArrayList<Employee> employees = new ArrayList<>(10);

        try {
            read("data-in.txt", employees);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Collections.sort(employees, new SortDescBySalary());

        System.out.println("[problem A] First five employees full info:");
        for (int i = 0; i < 5; i++) {
            System.out.println(employees.get(i));
        }

        System.out.println("[problem B] Last three employees id:");
        for (int i = employees.size() - 1; i > employees.size() - 4; i--) {
            System.out.println(employees.get(i).getID());
        }

        try {
            FileWriter sw = new FileWriter("data-out.txt", true);
            for (Employee employee : employees) {
                sw.write(employee + "\n");
            }
            sw.close();
        } catch (FileNotFoundException e1) {
            System.err.print(e1.getMessage());
        } catch (IOException e2) {
            e2.printStackTrace();
        }

    }

    public static void oneLineToFile(String path, String employeeData) {
        try {
            FileWriter sw = new FileWriter(path, true);
            sw.write(employeeData + "\n");
            sw.close();
        } catch (FileNotFoundException e1) {
            System.err.print(e1.getMessage());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void generateEmployees(String filepath, int count) {
        for (int i = 0; i < count; i++) {
            if (Math.random() < 0.5) {
                // TODO: rearrange id assignment
                oneLineToFile(filepath, new Worker().toString());
            } else {
                oneLineToFile(filepath, new Freelancer().toString());
            }
        }
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }

    public static void read(String fileName,
                            ArrayList<Employee> employees) throws FileNotFoundException {
        File file = new File(fileName);
        exists(fileName);

        try {
            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.charAt(0) == 'w') {
                        employees.add(new Worker(line));
                    } else if (line.charAt(0) == 'f') {
                        employees.add(new Freelancer(line));
                    }
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Employee {
    private String name;
    private int age;
    private double salary;
    private int id;
    private static int maxID = 0;

    public static String[] maleNames = {
            "Max", "Alex", "Sergio", "George", "Ivan"
    };

    public Employee() {
        String randName = maleNames[(int) Math.floor(Math.random() * 5)];
        int randAge = (int) (Math.random() * (30 - 20 + 1)) + 20;
        this.name = randName;
        this.age = randAge;
        this.id = maxID + 1;
        this.maxID++;
    }

    /**
     * @param entireData    String contains employee's type, name, age and salary
     *                      separated by spaces:
     *                      w Max  20 2500.95
     *                      f Alex 24 12800
     */
    public Employee(String entireData) {
        List<String> attributes = Arrays
                .stream(entireData.split(" "))
                .filter(x -> !x.equals(""))
                .collect(Collectors.toList());
        // TODO: check if employee's data is correct
        this.name = attributes.get(2);
        this.age = Integer.parseInt(attributes.get(3));
        this.salary = Double.parseDouble(attributes.get(4));
        this.id = maxID + 1;
        this.maxID++;
    }
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    protected void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    protected void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }
    protected void setSalary(double salary) {
        this.salary = salary;
    }

    public int getID() {
        return id;
    }
    protected void setID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        /*
        * Locale is English because float numbers delimiter
        * should be point "." instead of comma ",".
        */
        return String.format(Locale.ENGLISH, "%4d %10s %d %10.2f",
                this.getID(), this.getName(), this.getAge(), this.getSalary());
    }
}

/**
 * Employees with hourly wage.
 */
class Worker extends Employee {

    public Worker() {
        super();
        double randSalary = Math.random() * 3000 + 2000;
        this.setSalary(randSalary);
    }
    public Worker(String entireData) {
        super(entireData);
    }
    public Worker(String name, int age, double monthlyPayment) {
        super(name, age);
        this.setSalary(monthlyPayment);
    }

    @Override
    public String toString() {
        return String.format("w %s", super.toString());
    }
}

/**
 * Employees with fixed monthly payment.
 */
class Freelancer extends Employee {

    public Freelancer() {
        super();
        double randSalary = Math.random() * 20 + 20;
        this.setSalary(20.8 * 8 * randSalary);
    }
    public Freelancer(String entireData) {
        super(entireData);
    }
    public Freelancer(String name, int age, double hourlyRate) {
        super(name, age);
        this.setSalary(20.8 * 8 * hourlyRate);
    }

    @Override
    public String toString() {
        return String.format("f %s", super.toString());
    }
}

class SortDescBySalary implements Comparator<Employee> {
    @Override
    public int compare(Employee em1, Employee em2) {
        double s1 = em1.getSalary();
        double s2 = em2.getSalary();

        if (s2 == s1) {
            return em1.getName().compareTo(em2.getName());
        } else {
            return (s2 - s1 < 0) ? -1 : 1;
        }
    }
}
