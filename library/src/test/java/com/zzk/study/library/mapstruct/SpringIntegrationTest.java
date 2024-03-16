package com.zzk.study.library.mapstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Configuration
public class SpringIntegrationTest {

    @Autowired
    private SimpleDestinationMapperUsingInjectedService mapper;

    @Test
    public void givenSourceToDestination_whenMaps_thenNameEnriched() {
        // Given
        SimpleSource source = new SimpleSource();
        source.setName("Bob");
        source.setDescription("The Builder");

        // When
        SimpleDestination destination = mapper.sourceToDestination(source);

        // Then
        assertThat(destination).isNotNull();
        assertThat(destination.getName()).isEqualTo("-:: Bob ::-");
        assertThat(destination.getDescription()).isEqualTo("The Builder");
    }

    @Mapper(componentModel = "spring")
    public static abstract class SimpleDestinationMapperUsingInjectedService {

        @Autowired
        protected SimpleService simpleService;

        @Mapping(target = "name", expression = "java(simpleService.enrichName(source.getName()))")
        public abstract SimpleDestination sourceToDestination(SimpleSource source);

        public abstract SimpleSource destinationToSource(SimpleDestination destination);


    }
    @Service
    public class SimpleService {

        public String enrichName(String name) {
            return "-:: " + name + " ::-";
        }
    }

    public static class SimpleSource {

        private String name;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public static class SimpleDestination {

        private String name;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }



}