package com.zzk.study.guava.preconditions;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.google.common.base.Preconditions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreconditionsTest {
    /**
     * 通过if语句校验参数
      **/
    @Test
    public void test1() throws Exception {

        getPerson(8, "peida");
        getPerson(-9, "peida");
        getPerson(8, "");
        getPerson(8, null);
    }

    private static void getPerson(int age, String neme) throws Exception {
        if (age > 0 && neme != null && !neme.isEmpty()) {
            System.out.println("a person age:" + age + ",neme:" + neme);
        } else {
            System.out.println("参数输入有误！");
        }
    }

    @Test
    public void whenCheckArgumentEvaluatesFalse_throwsException() {
        int age = -18;

        assertThatThrownBy(() -> Preconditions.checkArgument(age > 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(null).hasNoCause();
    }

    @Test
    public void givenErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero";

        assertThatThrownBy(() -> Preconditions.checkArgument(age > 0, message))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(message).hasNoCause();
    }

    @Test
    public void givenTemplatedErrorMessage_whenCheckArgumentEvaluatesFalse_throwsException() {
        final int age = -18;
        final String message = "Age can't be zero or less than zero, you supplied %s.";

        assertThatThrownBy(() -> Preconditions.checkArgument(age > 0, message, age))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(message, age).hasNoCause();
    }

    @Test
    public void givenArrayOfIntegers_whenCheckElementIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };

        assertThatThrownBy(() -> Preconditions.checkElementIndex(6, numbers.length - 1))
                .isInstanceOf(IndexOutOfBoundsException.class).hasNoCause();
    }

    @Test
    public void givenNullString_whenCheckNotNullCalledWithMessage_throwsException() {
        final String nullObject = null;
        final String message = "Please check the Object supplied, its null!";

        assertThatThrownBy(() -> Preconditions.checkNotNull(nullObject, message))
                .isInstanceOf(NullPointerException.class).hasMessage(message).hasNoCause();
    }

    @Test
    public void givenArrayOfIntegers_whenCheckPositionIndexEvaluatesFalse_throwsException() {
        final int[] numbers = { 1, 2, 3, 4, 5 };

        assertThatThrownBy(() -> Preconditions.checkPositionIndex(6, numbers.length - 1))
                .isInstanceOf(IndexOutOfBoundsException.class).hasNoCause();
    }

    @Test
    public void givenValidStatesAndMessage_whenCheckStateEvaluatesFalse_throwsException() {
        final int[] validStates = { -1, 0, 1 };
        final int givenState = 10;
        final String message = "You have entered an invalid state";

        assertThatThrownBy(() -> Preconditions.checkState(Arrays.binarySearch(validStates, givenState) > 0, message))
                .isInstanceOf(IllegalStateException.class).hasMessageStartingWith(message).hasNoCause();
    }

}
