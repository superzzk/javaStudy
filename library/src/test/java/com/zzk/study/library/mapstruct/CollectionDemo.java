package com.zzk.study.library.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionDemo {

    private CompanyMapperAdderPreferred companyMapper = Mappers.getMapper(CompanyMapperAdderPreferred.class);

    @Test
    void whenMappingToCompanyDTO_thenExpectCorrectMappingResult() {
        Employee employee = new Employee("John", "Doe");
        Company company = new Company();
        company.setEmployees(Collections.singletonList(employee));

        CompanyDTO result = companyMapper.map(company);

        List<EmployeeDTO> employees = result.getEmployees();
        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getFirstName()).isEqualTo("John");
        assertThat(employees.get(0).getLastName()).isEqualTo("Doe");
    }

    @Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, uses = EmployeeMapper.class)
    public interface CompanyMapperAdderPreferred {
        CompanyDTO map(Company company);
    }

    @Mapper
    public interface EmployeeMapper {
        EmployeeDTO map(Employee employee);

        List<EmployeeDTO> map(List<Employee> employees);

        Set<EmployeeDTO> map(Set<Employee> employees);

        Map<String, EmployeeDTO> map(Map<String, Employee> idEmployeeMap);
    }


    @Data
    public static class Company {
        private List<Employee> employees;
    }
    @Data
    public static class CompanyDTO {
        private List<EmployeeDTO> employees;

        public void addEmployee(EmployeeDTO employeeDTO) {
            if (employees == null) {
                employees = new ArrayList<>();
            }

            employees.add(employeeDTO);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Employee {
        private String firstName;
        private String lastName;
    }


    @Data
    public static class EmployeeDTO {
        private String firstName;
        private String lastName;
    }


    @Data
    public static class EmployeeFullNameDTO {
        private String fullName;
    }


}