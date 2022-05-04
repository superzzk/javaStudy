package zzk.study.java.core.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.Arrays.sort;
import static org.junit.Assert.assertTrue;

public class Java8ComparatorUnitTest {

    private Employee[] employees;
    private Employee[] employeesArrayWithNulls;
    private Employee[] sortedEmployeesByName;
    private Employee[] sortedEmployeesByNameDesc;
    private Employee[] sortedEmployeesByAge;
    private Employee[] sortedEmployeesByMobile;
    private Employee[] sortedEmployeesBySalary;
    private Employee[] sortedEmployeesArray_WithNullsFirst;
    private Employee[] sortedEmployeesArray_WithNullsLast;
    private Employee[] sortedEmployeesByNameAge;
    private Employee[] someMoreEmployees;
    private Employee[] sortedEmployeesByAgeName;;

    @Before
    public void initData() {
        employees = new Employee[] { new Employee("John", 25, 3000, 9922001), new Employee("Ace", 22, 2000, 5924001), new Employee("Keith", 35, 4000, 3924401) };
        employeesArrayWithNulls = new Employee[] { new Employee("John", 25, 3000, 9922001), null, new Employee("Ace", 22, 2000, 5924001), null, new Employee("Keith", 35, 4000, 3924401) };

        sortedEmployeesByName = new Employee[] { new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
        sortedEmployeesByNameDesc = new Employee[] { new Employee("Keith", 35, 4000, 3924401), new Employee("John", 25, 3000, 9922001), new Employee("Ace", 22, 2000, 5924001) };

        sortedEmployeesByAge = new Employee[] { new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };

        sortedEmployeesByMobile = new Employee[] { new Employee("Keith", 35, 4000, 3924401), new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001), };

        sortedEmployeesBySalary = new Employee[] { new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401), };

        sortedEmployeesArray_WithNullsFirst = new Employee[] { null, null, new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
        sortedEmployeesArray_WithNullsLast = new Employee[] { new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401), null, null };

        someMoreEmployees = new Employee[] { new Employee("Jake", 25, 3000, 9922001), new Employee("Jake", 22, 2000, 5924001), new Employee("Ace", 22, 3000, 6423001), new Employee("Keith", 35, 4000, 3924401) };

        sortedEmployeesByAgeName = new Employee[] { new Employee("Ace", 22, 3000, 6423001), new Employee("Jake", 22, 2000, 5924001), new Employee("Jake", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
        sortedEmployeesByNameAge = new Employee[] { new Employee("Ace", 22, 3000, 6423001), new Employee("Jake", 22, 2000, 5924001), new Employee("Jake", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
    }

    @Test
    public void whenComparing_thenSortedByName() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        sort(employees, employeeNameComparator);
         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByName));
    }

    @Test
    public void whenComparingWithComparator_thenSortedByNameDesc() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName, (s1, s2) -> {
            return s2.compareTo(s1);
        });
        sort(employees, employeeNameComparator);
         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByNameDesc));
    }

    @Test
    public void whenReversed_thenSortedByNameDesc() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparatorReversed = employeeNameComparator.reversed();
        sort(employees, employeeNameComparatorReversed);
         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByNameDesc));
    }

    @Test
    public void whenComparingInt_thenSortedByAge() {
        Comparator<Employee> employeeAgeComparator = Comparator.comparingInt(Employee::getAge);
        sort(employees, employeeAgeComparator);
         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByAge));
    }

    @Test
    public void whenComparingLong_thenSortedByMobile() {
        Comparator<Employee> employeeMobileComparator = Comparator.comparingLong(Employee::getMobile);
        sort(employees, employeeMobileComparator);
        // System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByMobile));
    }

    @Test
    public void whenComparingDouble_thenSortedBySalary() {
        Comparator<Employee> employeeSalaryComparator = Comparator.comparingDouble(Employee::getSalary);
        sort(employees, employeeSalaryComparator);
        // System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesBySalary));
    }

    @Test
    public void whenNaturalOrder_thenSortedByName() {
        Comparator<Employee> employeeNameComparator = Comparator.<Employee> naturalOrder();
        sort(employees, employeeNameComparator);
        // System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByName));
    }

    @Test
    public void whenReverseOrder_thenSortedByNameDesc() {
        Comparator<Employee> employeeNameComparator = Comparator.<Employee> reverseOrder();
        sort(employees, employeeNameComparator);
        // System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByNameDesc));
    }

    @Test
    public void whenNullsFirst_thenSortedByNameWithNullsFirst() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparator_nullFirst = Comparator.nullsFirst(employeeNameComparator);
        sort(employeesArrayWithNulls, employeeNameComparator_nullFirst);
         System.out.println(Arrays.toString(employeesArrayWithNulls));
        assertTrue(Arrays.equals(employeesArrayWithNulls, sortedEmployeesArray_WithNullsFirst));
    }

    @Test
    public void whenNullsLast_thenSortedByNameWithNullsLast() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparator_nullLast = Comparator.nullsLast(employeeNameComparator);
        sort(employeesArrayWithNulls, employeeNameComparator_nullLast);
        // System.out.println(Arrays.toString(employeesArrayWithNulls));
        assertTrue(Arrays.equals(employeesArrayWithNulls, sortedEmployeesArray_WithNullsLast));
    }

    @Test
    public void whenThenComparing_thenSortedByAgeName() {
        Comparator<Employee> employee_Age_Name_Comparator = Comparator.comparing(Employee::getAge).thenComparing(Employee::getName);

        sort(someMoreEmployees, employee_Age_Name_Comparator);
        // System.out.println(Arrays.toString(someMoreEmployees));
        assertTrue(Arrays.equals(someMoreEmployees, sortedEmployeesByAgeName));
    }

    @Test
    public void whenThenComparing_thenSortedByNameAge() {
        Comparator<Employee> employee_Name_Age_Comparator = Comparator.comparing(Employee::getName).thenComparingInt(Employee::getAge);

        sort(someMoreEmployees, employee_Name_Age_Comparator);
        // System.out.println(Arrays.toString(someMoreEmployees));
        assertTrue(Arrays.equals(someMoreEmployees, sortedEmployeesByNameAge));
    }

    @Data
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Employee implements Comparable<Employee>{
        String name;
        int age;
        double salary;
        long mobile;

        public Employee(String name, int age, int salary, int mobile){
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.mobile = mobile;
        }

        public String getName(){
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getSalary() {
            return salary;
        }

        public long getMobile() {
            return mobile;
        }

        @Override
        public int compareTo(Employee argEmployee) {
            return name.compareTo(argEmployee.getName());
        }

    }
}
