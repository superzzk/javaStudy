package zzk.study.java.core.java8;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import static zzk.study.java.core.java8.Java8GroupingByCollectorUnitTest.BlogPostType.*;

public class Java8GroupingByCollectorUnitTest {

	private static final List<BlogPost> posts =
			Arrays.asList(
					new BlogPost("News item 1", "Author 1", NEWS, 15),
					new BlogPost("Tech review 1", "Author 2", REVIEW, 5),
					new BlogPost("Programming guide", "Author 1", GUIDE, 20),
					new BlogPost("News item 2", "Author 2", NEWS, 35),
					new BlogPost("Tech review 2", "Author 1", REVIEW, 15));

	@Test
	public void givenAListOfPosts_whenGroupedByType_thenGetAMapBetweenTypeAndPosts() {
		Map<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType));

		assertEquals(2, postsPerType.get(NEWS).size());
		assertEquals(1, postsPerType.get(GUIDE).size());
		assertEquals(2, postsPerType.get(REVIEW).size());
	}

	@Test
	public void grouping() {
		final Map<Boolean, List<Integer>> collect = posts.stream().collect(partitioningBy(post -> post.getLikes() > 15,
				mapping(BlogPost::getLikes, toList())));
		System.out.println(collect);
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeAndTheirTitlesAreJoinedInAString_thenGetAMapBetweenTypeAndCsvTitles() {
		Map<BlogPostType, String> postsPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, mapping(BlogPost::getTitle, joining(", ", "Post titles: [", "]"))));

		assertEquals("Post titles: [News item 1, News item 2]", postsPerType.get(NEWS));
		assertEquals("Post titles: [Programming guide]", postsPerType.get(GUIDE));
		assertEquals("Post titles: [Tech review 1, Tech review 2]", postsPerType.get(REVIEW));
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeAndSumTheLikes_thenGetAMapBetweenTypeAndPostLikes() {
		Map<BlogPostType, Integer> likesPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, summingInt(BlogPost::getLikes)));

		assertEquals(50, likesPerType.get(NEWS)
				.intValue());
		assertEquals(20, likesPerType.get(REVIEW)
				.intValue());
		assertEquals(20, likesPerType.get(GUIDE)
				.intValue());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeInAnEnumMap_thenGetAnEnumMapBetweenTypeAndPosts() {
		EnumMap<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, () -> new EnumMap<>(BlogPostType.class), toList()));

		assertEquals(2, postsPerType.get(NEWS).size());
		assertEquals(1, postsPerType.get(GUIDE).size());
		assertEquals(2, postsPerType.get(REVIEW).size());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeInSets_thenGetAMapBetweenTypesAndSetsOfPosts() {
		Map<BlogPostType, Set<BlogPost>> postsPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, toSet()));

		assertEquals(2, postsPerType.get(NEWS).size());
		assertEquals(1, postsPerType.get(GUIDE).size());
		assertEquals(2, postsPerType.get(REVIEW).size());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeConcurrently_thenGetAMapBetweenTypeAndPosts() {
		ConcurrentMap<BlogPostType, List<BlogPost>> postsPerType = posts.parallelStream()
				.collect(groupingByConcurrent(BlogPost::getType));

		assertEquals(2, postsPerType.get(NEWS).size());
		assertEquals(1, postsPerType.get(GUIDE).size());
		assertEquals(2, postsPerType.get(REVIEW).size());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeAndAveragingLikes_thenGetAMapBetweenTypeAndAverageNumberOfLikes() {
		Map<BlogPostType, Double> averageLikesPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, averagingInt(BlogPost::getLikes)));

		assertEquals(25, averageLikesPerType.get(NEWS)
				.intValue());
		assertEquals(20, averageLikesPerType.get(GUIDE)
				.intValue());
		assertEquals(10, averageLikesPerType.get(REVIEW)
				.intValue());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeAndCounted_thenGetAMapBetweenTypeAndNumberOfPosts() {
		Map<BlogPostType, Long> numberOfPostsPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, counting()));

		assertEquals(2, numberOfPostsPerType.get(NEWS)
				.intValue());
		assertEquals(1, numberOfPostsPerType.get(GUIDE)
				.intValue());
		assertEquals(2, numberOfPostsPerType.get(REVIEW)
				.intValue());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeAndMaxingLikes_thenGetAMapBetweenTypeAndMaximumNumberOfLikes() {
		Map<BlogPostType, Optional<BlogPost>> maxLikesPerPostType = posts.stream()
				.collect(groupingBy(BlogPost::getType, maxBy(comparingInt(BlogPost::getLikes))));

		assertTrue(maxLikesPerPostType.get(NEWS).isPresent());
		assertEquals(35, maxLikesPerPostType.get(NEWS).get().getLikes());

		assertTrue(maxLikesPerPostType.get(GUIDE).isPresent());
		assertEquals(20, maxLikesPerPostType.get(GUIDE).get().getLikes());

		assertTrue(maxLikesPerPostType.get(REVIEW).isPresent());
		assertEquals(15, maxLikesPerPostType.get(REVIEW)
				.get()
				.getLikes());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByAuthorAndThenByType_thenGetAMapBetweenAuthorAndMapsBetweenTypeAndBlogPosts() {
		Map<String, Map<BlogPostType, List<BlogPost>>> map = posts.stream()
				.collect(groupingBy(BlogPost::getAuthor, groupingBy(BlogPost::getType)));

		assertEquals(1, map.get("Author 1")
				.get(NEWS)
				.size());
		assertEquals(1, map.get("Author 1")
				.get(GUIDE)
				.size());
		assertEquals(1, map.get("Author 1")
				.get(REVIEW)
				.size());

		assertEquals(1, map.get("Author 2")
				.get(NEWS)
				.size());
		assertEquals(1, map.get("Author 2")
				.get(REVIEW)
				.size());
		assertNull(map.get("Author 2")
				.get(GUIDE));
	}

	@Test
	public void givenAListOfPosts_whenGroupedByTypeAndSummarizingLikes_thenGetAMapBetweenTypeAndSummary() {
		Map<BlogPostType, IntSummaryStatistics> likeStatisticsPerType = posts.stream()
				.collect(groupingBy(BlogPost::getType, summarizingInt(BlogPost::getLikes)));

		IntSummaryStatistics newsLikeStatistics = likeStatisticsPerType.get(NEWS);

		assertEquals(2, newsLikeStatistics.getCount());
		assertEquals(50, newsLikeStatistics.getSum());
		assertEquals(25.0, newsLikeStatistics.getAverage(), 0.001);
		assertEquals(35, newsLikeStatistics.getMax());
		assertEquals(15, newsLikeStatistics.getMin());
	}

	@Test
	public void givenAListOfPosts_whenGroupedByComplexMapKeyType_thenGetAMapBetweenTupleAndList() {
		Map<Tuple, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
				.collect(groupingBy(post -> new Tuple(post.getType(), post.getAuthor())));

		List<BlogPost> result = postsPerTypeAndAuthor.get(new Tuple(GUIDE, "Author 1"));

		assertThat(result.size()).isEqualTo(1);

		BlogPost blogPost = result.get(0);

		assertThat(blogPost.getTitle()).isEqualTo("Programming guide");
		assertThat(blogPost.getType()).isEqualTo(GUIDE);
		assertThat(blogPost.getAuthor()).isEqualTo("Author 1");
	}

	public static class BlogPost {
		private String title;
		private String author;
		private BlogPostType type;
		private int likes;

		public BlogPost(String title, String author, BlogPostType type, int likes) {
			this.title = title;
			this.author = author;
			this.type = type;
			this.likes = likes;
		}

		public String getTitle() {
			return title;
		}

		public String getAuthor() {
			return author;
		}

		public BlogPostType getType() {
			return type;
		}

		public int getLikes() {
			return likes;
		}

		@Override
		public String toString() {
			return "BlogPost{" + "title='" + title + '\'' + ", type=" + type + ", likes=" + likes + '}';
		}
	}

	public enum BlogPostType {
		NEWS, REVIEW, GUIDE
	}

	public class Tuple {
		private final BlogPostType type;
		private final String author;

		public Tuple(BlogPostType type, String author) {
			this.type = type;
			this.author = author;
		}

		public BlogPostType getType() {
			return type;
		}

		public String getAuthor() {
			return author;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Tuple tuple = (Tuple) o;
			return type == tuple.type && author.equals(tuple.author);
		}

		@Override
		public int hashCode() {
			return Objects.hash(type, author);
		}

		@Override
		public String toString() {
			return "Tuple{" + "type=" + type + ", author='" + author + '\'' + '}';
		}
	}
}
