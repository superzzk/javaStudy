package com.zzk.study.library.mapstruct;

import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiParam {

    private DeliveryAddressMapper deliveryAddressMapper = Mappers.getMapper(DeliveryAddressMapper.class);

    @Test
    public void testGivenCustomerAndAddress_mapsToDeliveryAddress() {

        // given a customer
        Customer customer = new Customer().setFirstName("Max").setLastName("Powers");

        // and some address
        Address homeAddress = new Address().setStreet("123 Some Street").setCounty("Nevada").setPostalcode("89123");

        // when calling DeliveryAddressMapper::from
        DeliveryAddress deliveryAddress = deliveryAddressMapper.from(customer, homeAddress);

        // then a new DeliveryAddress is created, based on the given customer and his home address
        assertEquals(deliveryAddress.getForename(), customer.getFirstName());
        assertEquals(deliveryAddress.getSurname(), customer.getLastName());
        assertEquals(deliveryAddress.getStreet(), homeAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), homeAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), homeAddress.getPostalcode());

    }

    @Test
    public void testGivenDeliveryAddressAndSomeOtherAddress_updatesDeliveryAddress() {

        // given a delivery address
        DeliveryAddress deliveryAddress = new DeliveryAddress().setForename("Max")
            .setSurname("Powers")
            .setStreet("123 Some Street")
            .setCounty("Nevada")
            .setPostalcode("89123");

        // and some new address
        Address newAddress = new Address().setStreet("456 Some other street")
            .setCounty("Arizona")
            .setPostalcode("12345");

        // when calling DeliveryAddressMapper::updateAddress
        DeliveryAddress updatedDeliveryAddress = deliveryAddressMapper.updateAddress(deliveryAddress, newAddress);

        // then the *existing* delivery address is updated
        assertSame(deliveryAddress, updatedDeliveryAddress);

        assertEquals(deliveryAddress.getStreet(), newAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), newAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), newAddress.getPostalcode());
    }

    @Mapper
    public interface DeliveryAddressMapper {

        @Mapping(source = "customer.firstName", target = "forename")
        @Mapping(source = "customer.lastName", target = "surname")
        @Mapping(source = "address.street", target = "street")
        @Mapping(source = "address.postalcode", target = "postalcode")
        @Mapping(source = "address.county", target = "county")
        DeliveryAddress from(Customer customer, Address address);

        @Mapping(source = "address.postalcode", target = "postalcode")
        @Mapping(source = "address.county", target = "county")
        DeliveryAddress updateAddress(@MappingTarget DeliveryAddress deliveryAddress, Address address);
    }


    @Data
    @Accessors(chain = true)
    public static class DeliveryAddress {
        private String forename;
        private String surname;
        private String street;
        private String postalcode;
        private String county;
    }

    @Data
    @Accessors(chain = true)
    public static class Customer {
        private String firstName;
        private String lastName;
    }

    @Data
    @Accessors(chain = true)
    public static class Address {
        private String street;
        private String postalcode;
        private String county;
    }

}
