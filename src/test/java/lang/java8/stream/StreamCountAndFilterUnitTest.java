package lang.java8.stream;

import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StreamCountAndFilterUnitTest {

    private List<Customer> customers;
    Customer john, sarah, charles, mary;

    @Before
    public void setUp() {
        john = new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e");
        sarah = new Customer("Sarah M.", 200);
        charles = new Customer("Charles B.", 150);
        mary = new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e");
        customers = Arrays.asList(john, sarah, charles, mary);
    }

    @Test
    public void givenListOfCustomers_whenCount_thenGetListSize() {
        long count = customers
          .stream()
          .count();

        assertThat(count).isEqualTo(4L);
    }

    @Test
    public void givenListOfCustomers_whenFilterByPointsOver100AndCount_thenGetTwo() {
        long countBigCustomers = customers
          .stream()
          .filter(c -> c.getPoints() > 100)
          .count();

        assertThat(countBigCustomers).isEqualTo(2L);
    }

    @Test
    public void givenListOfCustomers_whenUsingMethodOverHundredPointsAndCount_thenGetTwo() {
        long count = customers
          .stream()
          .filter(Customer::hasOverHundredPoints)
          .count();

        assertThat(count).isEqualTo(2L);
    }

    @Test
    public void givenListOfCustomers_whenFilterByPoints_thenGetTwo() {
        List<Customer> customersWithMoreThan100Points = customers
                .stream()
                .filter(c -> c.getPoints() > 100)
                .collect(Collectors.toList());

        assertThat(customersWithMoreThan100Points).hasSize(2);
        assertThat(customersWithMoreThan100Points).contains(sarah, charles);
    }

    @Test
    public void givenListOfCustomers_whenFilterByMethodReference_thenGetTwo() {
        List<Customer> customersWithMoreThan100Points = customers
                .stream()
                .filter(Customer::hasOverHundredPoints)
                .collect(Collectors.toList());

        assertThat(customersWithMoreThan100Points).hasSize(2);
        assertThat(customersWithMoreThan100Points).contains(sarah, charles);
    }

    @Test
    public void givenListOfCustomersWithOptional_whenFilterBy100Points_thenGetTwo() {
        Optional<Customer> john = Optional.of(new Customer("John P.", 15));
        Optional<Customer> sarah = Optional.of(new Customer("Sarah M.", 200));
        Optional<Customer> mary = Optional.of(new Customer("Mary T.", 300));
        List<Optional<Customer>> customers = Arrays.asList(john, sarah, Optional.empty(), mary, Optional.empty());

        List<Customer> customersWithMoreThan100Points = customers
                .stream()
                .flatMap(c -> c
                        .map(Stream::of)
                        .orElseGet(Stream::empty))
                .filter(Customer::hasOverHundredPoints)
                .collect(Collectors.toList());

        assertThat(customersWithMoreThan100Points).hasSize(2);
        assertThat(customersWithMoreThan100Points).contains(sarah.get(), mary.get());
    }

    @Test
    public void givenListOfCustomers_whenFilterWithCustomHandling_thenThrowException() {
        assertThatThrownBy(() -> customers
                .stream()
                .filter(Customer::hasValidProfilePhotoWithoutCheckedException)
                .count()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithTryCatch_thenGetTwo() {
        List<Customer> customersWithValidProfilePhoto = customers
                .stream()
                .filter(c -> {
                    try {
                        return c.hasValidProfilePhoto();
                    } catch (IOException e) {
                        //handle exception
                    }
                    return false;
                })
                .collect(Collectors.toList());

        assertThat(customersWithValidProfilePhoto).hasSize(2);
        assertThat(customersWithValidProfilePhoto).contains(john, mary);
    }

    @Test
    public void givenListOfCustomers_whenFilterWithTryCatchAndRuntime_thenThrowException() {
        List<Customer> customers = Arrays.asList(
                new Customer("John P.", 15, "https://images.unsplash.com/photo-1543320485-d0d5a49c2b2e"),
                new Customer("Sarah M.", 200), new Customer("Charles B.", 150),
                new Customer("Mary T.", 1, "https://images.unsplash.com/photo-1543297057-25167dfc180e"));

        assertThatThrownBy(() -> customers
                .stream()
                .filter(c -> {
                    try {
                        return c.hasValidProfilePhoto();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList())).isInstanceOf(RuntimeException.class);
    }

    public class Customer {
        private String name;
        private int points;
        private String profilePhotoUrl;

        public Customer(String name, int points) {
            this(name, points, "");
        }

        public Customer(String name, int points, String profilePhotoUrl) {
            this.name = name;
            this.points = points;
            this.profilePhotoUrl = profilePhotoUrl;
        }

        public String getName() {
            return name;
        }

        public int getPoints() {
            return points;
        }

        public boolean hasOver(int points) {
            return this.points > points;
        }

        public boolean hasOverHundredPoints() {
            return this.points > 100;
        }

        public boolean hasValidProfilePhoto() throws IOException {
            URL url = new URL(this.profilePhotoUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        }

        public boolean hasValidProfilePhotoWithoutCheckedException() {
            try {
                URL url = new URL(this.profilePhotoUrl);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
