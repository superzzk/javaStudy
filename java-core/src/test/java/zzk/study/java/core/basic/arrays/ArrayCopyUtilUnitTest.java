package zzk.study.java.core.basic.arrays;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ArrayCopyUtilUnitTest {
    private static Employee[] employees;
    private static final int MAX = 2;

    @BeforeClass
    public static void setup(){
      createEmployeesArray();
    }

    private static void createEmployeesArray() {
        employees = new Employee[MAX];
          Employee employee;
          for(int i = 0; i < MAX; i++) {
              employee = new Employee();
              employee.setName("Emp"+i);
              employee.setId(i);
              employees[i] = employee;
          }
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaSystemsArrayCopy_thenSuccessful(){
        int[] array = {23, 43, 55};
        int[] copiedArray = new int[3];
        
        System.arraycopy(array, 0, copiedArray, 0, 3);
        
        Assert.assertArrayEquals(copiedArray, array);
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedSubSequenceViaSystemsArrayCopy_thenSuccessful(){
        int[] array = {23, 43, 55, 12, 65, 88, 92};
        int[] copiedArray = new int[3];
        
        System.arraycopy(array, 2, copiedArray, 0, 3);
        
        assertTrue(3 == copiedArray.length);
        assertTrue(copiedArray[0] == array[2]);
        assertTrue(copiedArray[1] == array[3]);
        assertTrue(copiedArray[2] == array[4]);
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedSubSequenceViaArraysCopyOfRange_thenSuccessful(){
        int[] array = {23, 43, 55, 12, 65, 88, 92};
        
        int[] copiedArray = Arrays.copyOfRange(array, 1, 4);
        
        assertTrue(3 == copiedArray.length);
        assertTrue(copiedArray[0] == array[1]);
        assertTrue(copiedArray[1] == array[2]);
        assertTrue(copiedArray[2] == array[3]);
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaArraysCopyOf_thenValueChangeIsSuccessful(){
        int[] array = {23, 43, 55, 12};
        int newLength = array.length;
        
        int[] copiedArray = Arrays.copyOf(array, newLength);
        
        Assert.assertArrayEquals(copiedArray, array);
        array[0] = 9;
        assertTrue(copiedArray[0] != array[0]);
        copiedArray[1] = 12;
        assertTrue(copiedArray[1] != array[1]);
    }

    @Test
    public void givenArrayOfNonPrimitiveType_whenCopiedViaArraysCopyOf_thenDoShallowCopy(){
        Employee[] copiedArray = Arrays.copyOf(employees, employees.length);
        
        Assert.assertArrayEquals(copiedArray, employees);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element caused change in the copied array
        assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaArrayClone_thenValueChangeIsSuccessful(){
        int[] array = {23, 43, 55, 12};
        
        int[] copiedArray = array.clone();
        
        Assert.assertArrayEquals(copiedArray, array);
        array[0] = 9;
        assertTrue(copiedArray[0] != array[0]);
        copiedArray[1] = 12;
        assertTrue(copiedArray[1] != array[1]);
    }

    @Test
    public void givenArraysOfNonPrimitiveType_whenCopiedViaArrayClone_thenDoShallowCopy(){
        Employee[] copiedArray = employees.clone();
        
        Assert.assertArrayEquals(copiedArray, employees);;
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element changed the copied array
        assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }

    @Test
    public void givenArraysOfCloneableNonPrimitiveType_whenCopiedViaArrayClone_thenDoShallowCopy(){
        Address[] addresses = createAddressArray();
        
        Address[] copiedArray = addresses.clone();
        
        addresses[0].setCity(addresses[0].getCity()+"_Changed");
        Assert.assertArrayEquals(copiedArray, addresses);
    }

    @Test
    public void givenArraysOfSerializableNonPrimitiveType_whenCopiedViaSerializationUtils_thenDoDeepCopy(){
        Employee[] copiedArray = SerializationUtils.clone(employees);
       
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element didn't change in the copied array
        Assert.assertFalse(
            copiedArray[0].getName().equals(employees[0].getName()));
    }
    
    @Test
    public void givenArraysOfNonPrimitiveType_whenCopiedViaStream_thenDoShallowCopy(){
        Employee[] copiedArray = Arrays.stream(employees).toArray(Employee[]::new);
        
        Assert.assertArrayEquals(copiedArray, employees);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element didn't change in the copied array
        assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }
    
    @Test
    public void givenArraysOfPrimitiveType_whenCopiedViaStream_thenSuccessful(){
        String[] strArray = {"orange", "red", "green'"};
        
        String[] copiedArray = Arrays.stream(strArray).toArray(String[]::new);
        
        Assert.assertArrayEquals(copiedArray, strArray);
    }
    
    private Address[] createAddressArray(){
        Address[] addresses = new Address[1];
        addresses[0] = createAddress();
        return addresses;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setCountry("USA");
        address.setState("CA");
        address.setCity("San Francisco");
        address.setStreet("Street 1");
        address.setZipcode("59999");
        return address;
    }

    public static class Address implements Cloneable {
        private String country;
        private String state;
        private String city;
        private String street;
        private String zipcode;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            super.clone();
            Address address = new Address();
            address.setCity(this.city);
            address.setCountry(this.country);
            address.setState(this.state);
            address.setStreet(this.street);
            address.setZipcode(this.zipcode);
            return address;
        }
    }

    public static class Employee implements Serializable {
        private static final long serialVersionUID = -2454619097207585825L;
        private int id;
        private String name;
        private int age;

        public Employee() {
        }

        public Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Employee(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public int getId() {
            return id;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
