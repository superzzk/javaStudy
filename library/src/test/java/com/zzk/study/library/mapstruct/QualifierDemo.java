package com.zzk.study.library.mapstruct;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class QualifierDemo {

    @Test
    public void givenUserBodyImperialValuesDTOToUserBodyValuesObject_whenMaps_thenCorrect() {
        UserBodyImperialValuesDTO dto  = new UserBodyImperialValuesDTO();
        dto.setInch(10);
        dto.setPound(100);

        UserBodyValues obj = UserBodyValuesMapper.INSTANCE.userBodyValuesMapper(dto);

        assertNotNull(obj);
        assertEquals(25.4, obj.getCentimeter(), 0);
        assertEquals(45.35, obj.getKilogram(), 0);
    }

    @Test
    public void givenUserBodyImperialValuesDTOWithInchToUserBodyValuesObject_whenMaps_thenCorrect() {
        UserBodyImperialValuesDTO dto = new UserBodyImperialValuesDTO();
        dto.setInch(10);

        UserBodyValues obj = UserBodyValuesMapper.INSTANCE.userBodyValuesMapper(dto);

        assertNotNull(obj);
        assertEquals(25.4, obj.getCentimeter(), 0);
    }

    @Test
    public void givenUserBodyImperialValuesDTOWithPoundToUserBodyValuesObject_whenMaps_thenCorrect() {
        UserBodyImperialValuesDTO dto = new UserBodyImperialValuesDTO();
        dto.setPound(100);

        UserBodyValues obj = UserBodyValuesMapper.INSTANCE.userBodyValuesMapper(dto);

        assertNotNull(obj);
        assertEquals(45.35, obj.getKilogram(), 0);
    }

    @Getter
    @Setter
    public static class UserBodyValues {
        private double kilogram;
        private double centimeter;
    }

    @Getter
    @Setter
    public static class UserBodyImperialValuesDTO {
        private int inch;
        private int pound;
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface PoundToKilogramMapper {}

    @Mapper
    public interface UserBodyValuesMapper {

        UserBodyValuesMapper INSTANCE = Mappers.getMapper(UserBodyValuesMapper.class);

        @Mapping(source = "pound", target = "kilogram", qualifiedBy = PoundToKilogramMapper.class)
        @Mapping(source = "inch", target = "centimeter", qualifiedByName = "inchToCentimeter")
        public UserBodyValues userBodyValuesMapper(UserBodyImperialValuesDTO dto);

        @Named("inchToCentimeter")
        public static double inchToCentimeter(int inch) {
            return inch * 2.54;
        }

        @PoundToKilogramMapper
        public static double poundToKilogram(int pound) {
            return pound * 0.4535;
        }
    }
}