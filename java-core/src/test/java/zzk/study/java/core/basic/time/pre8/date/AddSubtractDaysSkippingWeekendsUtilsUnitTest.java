package zzk.study.java.core.basic.time.pre8.date;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AddSubtractDaysSkippingWeekendsUtilsUnitTest {

    @Test
    public void givenLocalDateAndDaysToAdd_thenAddDaysSkippingWeekends() {
        LocalDate initialDate = LocalDate.of(2019, 11, 7);
        LocalDate expectedDate = LocalDate.of(2019, 11, 13);
        LocalDate result = AddSubtractDaysSkippingWeekendsUtils.addDaysSkippingWeekends(initialDate, 4);
        assertEquals(expectedDate, result);
    }

    @Test
    public void givenLocalDateAndDaysToSubtract_thenSubtractDaysSkippingWeekends() {
        LocalDate initialDate = LocalDate.of(2019, 11, 7);
        LocalDate expectedDate = LocalDate.of(2019, 11, 1);
        LocalDate result = AddSubtractDaysSkippingWeekendsUtils.subtractDaysSkippingWeekends(initialDate, 4);
        assertEquals(expectedDate, result);
    }

    public static class AddSubtractDaysSkippingWeekendsUtils {

        public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
            LocalDate result = date;
            int addedDays = 0;
            while (addedDays < days) {
                result = result.plusDays(1);
                if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++addedDays;
                }
            }
            return result;
        }

        public static LocalDate subtractDaysSkippingWeekends(LocalDate date, int days) {
            LocalDate result = date;
            int subtractedDays = 0;
            while (subtractedDays < days) {
                result = result.minusDays(1);
                if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    ++subtractedDays;
                }
            }
            return result;
        }
    }

}
