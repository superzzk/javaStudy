package com.zzk.study.library.mapstruct;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

class BeforeMappingDemo {

    private CarsMapper sut = Mappers.getMapper(CarsMapper.class);

    @Test
    void testGivenSubTypeElectric_mapsModifiedFieldsToSuperTypeDto_whenBeforeAndAfterMappingMethodscarCalled() {
        Car car = new ElectricCar();
        car.setId(12);
        car.setName("Tesla_Model_C");

        CarDTO carDto = sut.toCarDto(car);

        Assertions.assertEquals("TESLA_MODEL_C", carDto.getName());
        Assertions.assertEquals(FuelType.ELECTRIC, carDto.getFuelType());
    }

    @Test
    void testGivenSubTypeBioDiesel_mapsModifiedFieldsToSuperTypeDto_whenBeforeAndAfterMappingMethodscarCalled() {
        Car car = new BioDieselCar();
        car.setId(11);
        car.setName("Tesla_Model_X");

        CarDTO carDto = sut.toCarDto(car);

        Assertions.assertEquals("TESLA_MODEL_X", carDto.getName());
        Assertions.assertEquals(FuelType.BIO_DIESEL, carDto.getFuelType());
    }

    @Mapper
    public static abstract class CarsMapper {

        @BeforeMapping
        protected void enrichDTOWithFuelType(Car car, @MappingTarget CarDTO carDto) {
            if (car instanceof ElectricCar)
                carDto.setFuelType(FuelType.ELECTRIC);
            if (car instanceof BioDieselCar)
                carDto.setFuelType(FuelType.BIO_DIESEL);
        }

        @AfterMapping
        protected void convertNameToUpperCase(@MappingTarget CarDTO carDto) {
            carDto.setName(carDto.getName().toUpperCase());
        }

        public abstract CarDTO toCarDto(Car car);

    }

    @Getter
    @Setter
    public static class Car {
        private int id;
        private String name;
    }

    public static class ElectricCar extends Car { }
    public static class BioDieselCar extends Car { }

    @Getter
    @Setter
    public static class CarDTO {
        private int id;
        private String name;
        private FuelType fuelType;
    }

    public enum FuelType {
        ELECTRIC, BIO_DIESEL
    }

}
