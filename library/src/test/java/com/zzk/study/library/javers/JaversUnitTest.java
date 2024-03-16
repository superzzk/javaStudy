package com.zzk.study.library.javers;

import org.javers.common.collections.Lists;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JaversUnitTest {

    @Test
    public void givenPersonObject_whenApplyModificationOnIt_thenShouldDetectChange() {
        // given
        Javers javers = JaversBuilder.javers().build();

        Person person = new Person(1, "Michael Program");
        Person personAfterModification = new Person(1, "Michael Java");

        // when
        Diff diff = javers.compare(person, personAfterModification);

        // then
        ValueChange change = diff.getChangesByType(ValueChange.class).get(0);

        assertThat(diff.getChanges()).hasSize(1);
        assertThat(change.getPropertyName()).isEqualTo("name");
        assertThat(change.getLeft()).isEqualTo("Michael Program");
        assertThat(change.getRight()).isEqualTo("Michael Java");
    }

    @Test
    public void givenListOfPersons_whenCompare_ThenShouldDetectChanges() {
        // given
        Javers javers = JaversBuilder.javers().build();
        Person personThatWillBeRemoved = new Person(2, "Thomas Link");
        List<Person> oldList = Lists.asList(new Person(1, "Michael Program"), personThatWillBeRemoved);
        List<Person> newList = Lists.asList(new Person(1, "Michael Not Program"));

        // when
        Diff diff = javers.compareCollections(oldList, newList, Person.class);

        // then
        assertThat(diff.getChanges()).hasSize(3);

        ValueChange valueChange = diff.getChangesByType(ValueChange.class).get(0);
        assertThat(valueChange.getPropertyName()).isEqualTo("name");
        assertThat(valueChange.getLeft()).isEqualTo("Michael Program");
        assertThat(valueChange.getRight()).isEqualTo("Michael Not Program");

        ObjectRemoved objectRemoved = diff.getChangesByType(ObjectRemoved.class).get(0);
        assertThat(objectRemoved.getAffectedObject().get().equals(personThatWillBeRemoved)).isTrue();

        ListChange listChange = diff.getChangesByType(ListChange.class).get(0);
        assertThat(listChange.getValueRemovedChanges().size()).isEqualTo(1);

    }

    @Test
    public void givenListOfPerson_whenPersonHasNewAddress_thenDetectThatChange() {
        // given
        Javers javers = JaversBuilder.javers().build();

        PersonWithAddress person = new PersonWithAddress(1, "Tom", Arrays.asList(new Address("England")));

        PersonWithAddress personWithNewAddress = new PersonWithAddress(1, "Tom", Arrays.asList(new Address("England"), new Address("USA")));

        // when
        Diff diff = javers.compare(person, personWithNewAddress);
        List objectsByChangeType = diff.getObjectsByChangeType(NewObject.class);

        // then
        assertThat(objectsByChangeType).hasSize(1);
        assertThat(objectsByChangeType.get(0).equals(new Address("USA")));
    }

    @Test
    public void givenListOfPerson_whenPersonRemovedAddress_thenDetectThatChange() {
        // given
        Javers javers = JaversBuilder.javers().build();

        PersonWithAddress person = new PersonWithAddress(1, "Tom", Arrays.asList(new Address("England")));

        PersonWithAddress personWithNewAddress = new PersonWithAddress(1, "Tom", Collections.emptyList());

        // when
        Diff diff = javers.compare(person, personWithNewAddress);
        List objectsByChangeType = diff.getObjectsByChangeType(ObjectRemoved.class);

        // then
        assertThat(objectsByChangeType).hasSize(1);
        assertThat(objectsByChangeType.get(0).equals(new Address("England")));
    }

    public class Person {
        private Integer id;
        private String name;

        public Person(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Address {
        private String country;

        public Address(String country) {
            this.country = country;
        }

    }

    public class PersonWithAddress {
        private Integer id;
        private String name;
        private List<Address> address;

        public PersonWithAddress(Integer id, String name, List<Address> address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Address> getAddress() {
            return address;
        }

        public void setAddress(List<Address> address) {
            this.address = address;
        }
    }
}
