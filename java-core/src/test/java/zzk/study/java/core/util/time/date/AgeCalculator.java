package zzk.study.java.core.util.time.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class AgeCalculator {

    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        // validate inputs ...
        return Period.between(birthDate, currentDate)
            .getYears();
    }

    public int calculateAgeWithJava7(Date birthDate, Date currentDate) {
        // validate inputs ...
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1) / 10000;
        return age;
    }
}