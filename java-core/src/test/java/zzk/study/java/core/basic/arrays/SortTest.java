package zzk.study.java.core.basic.arrays;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @program: javaStudy
 * @author: zhangzhongkun
 * @create: 2019-04-26 16:10
 **/
class Employee implements Comparable<Employee> {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public int compareTo(@NotNull Employee o) {
        return Double.compare(this.salary, o.salary);
    }

    public String getName() {
        return name;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public double getSalary() {
        return salary;
    }
}

public class SortTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Harry Hacker", 35000);
        staff[1] = new Employee("Carl Cracker", 75000);
        staff[2] = new Employee("Tony Tester", 38000);

        Arrays.sort(staff);

// print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary());
    }
}
