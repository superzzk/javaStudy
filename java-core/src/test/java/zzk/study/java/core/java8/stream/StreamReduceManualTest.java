package zzk.study.java.core.java8.stream;

import zzk.study.java.core.java8.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamReduceManualTest {

    @Test
    public void givenIntegerList_whenReduceWithSumAccumulatorLambda_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        int result = numbers.stream().reduce(0, (a, b) -> a + b);
        
        assertThat(result).isEqualTo(21);
    }

    @Test
    public void givenIntegerList_whenReduceWithSumAccumulatorMethodReference_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        int result = numbers.stream().reduce(0, Integer::sum);
        
        assertThat(result).isEqualTo(21);
    }

    @Test
    public void givenStringList_whenReduceWithConcatenatorAccumulatorLambda_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        
        String result = letters.stream().reduce("", (a, b) -> a + b);
        
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    public void givenStringList_whenReduceWithConcatenatorAccumulatorMethodReference_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        
        String result = letters.stream().reduce("", String::concat);
        
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    public void givenStringList_whenReduceWithUppercaseConcatenatorAccumulator_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        
        String result = letters.stream().reduce("", (a, b) -> a.toUpperCase() + b.toUpperCase());
        
        assertThat(result).isEqualTo("ABCDE");
    }

    @Test
    public void givenUserList_whenReduceWithAgeAccumulatorAndSumCombiner_thenCorrect() {
        List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
        
        int result = users.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        
        assertThat(result).isEqualTo(65);
    }

    @Test
    public void givenUserList_whenReduceWithGreaterAgeAccumulator_thenFindsOldest() {
        List<User> users = Arrays.asList(new User("John", 30), new User("Alex", 40), new User("Julie", 35));

        User oldest = users.stream().reduce(users.get(0), (user1, user2) -> user1.getAge() >= user2.getAge() ? user1 : user2);

        assertThat(oldest).isEqualTo(users.get(1));
    }

    @Test
    public void givenUserListWithRatings_whenReduceWithGreaterAgeAccumulator_thenFindsOldest() {
        User john = new User("John", 30);
        john.getRating().add(new Review(5, ""));
        john.getRating().add(new Review(3, "not bad"));
        User julie = new User("Julie", 35);
        john.getRating().add(new Review(4, "great!"));
        john.getRating().add(new Review(2, "terrible experience"));
        john.getRating().add(new Review(4, ""));
        List<User> users = Arrays.asList(john, julie);

        Rating averageRating = users.stream().reduce(new Rating(), (rating, user) -> Rating.average(rating, user.getRating()), Rating::average);

        assertThat(averageRating.getPoints()).isEqualTo(3.6);
    }

    @Test
    public void givenStringList_whenReduceWithParallelStream_thenCorrect() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        
        String result = letters.parallelStream().reduce("", String::concat);
        
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElements_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        Assertions.assertThat(NumberUtils.divideListElements(numbers, 1)).isEqualTo(21);
    }

    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElementsWithExtractedTryCatchBlock_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 1)).isEqualTo(21);
    }

    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElementsWithExtractedTryCatchBlockAndListContainsZero_thenCorrect() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 1)).isEqualTo(21);
    }
    
    @Test
    public void givenNumberUtilsClass_whenCalledDivideListElementsWithExtractedTryCatchBlockAndDividerIsZero_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 0)).isEqualTo(0);
    }
    
    @Test
    public void givenStream_whneCalleddivideListElementsWithApplyFunctionMethod_thenCorrect() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        assertThat(NumberUtils.divideListElementsWithApplyFunctionMethod(numbers, 1)).isEqualTo(21);
    }
    
    @Test
    public void givenTwoStreams_whenCalledReduceOnParallelizedStream_thenFasterExecutionTime() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            userList.add(new User("John" + i, i));
        }
        long currentTime1 = System.currentTimeMillis();
        userList.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        long sequentialExecutionTime = System.currentTimeMillis() -currentTime1;
        long currentTime2 = System.currentTimeMillis();
        userList.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        long parallelizedExecutionTime = System.currentTimeMillis() - currentTime2;
        
        assertThat(parallelizedExecutionTime).isLessThan(sequentialExecutionTime);
    }

    public class User {

        private final String name;
        private final int age;
        private final Rating rating = new Rating();

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Rating getRating() {
            return rating;
        }

        @Override
        public String toString() {
            return "User{" + "name=" + name + ", age=" + age + '}';
        }
    }

    public static class Rating {

        double points;
        List<Review> reviews = new ArrayList<>();

        public Rating() {}

        public void add(Review review) {
            reviews.add(review);
            computeRating();
        }

        private double computeRating() {
            double totalPoints = reviews.stream().map(Review::getPoints).reduce(0, Integer::sum);
            this.points = totalPoints / reviews.size();
            return this.points;
        }

        public static Rating average(Rating r1, Rating r2) {
            Rating combined = new Rating();
            combined.reviews = new ArrayList<>(r1.reviews);
            combined.reviews.addAll(r2.reviews);
            combined.computeRating();
            return combined;
        }

        public double getPoints() {
            return points;
        }

        public List<Review> getReviews() {
            return reviews;
        }
    }

    public class Review {

        int points;
        String review;

        public Review(int points, String review) {
            this.points = points;
            this.review = review;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }
    }

}
