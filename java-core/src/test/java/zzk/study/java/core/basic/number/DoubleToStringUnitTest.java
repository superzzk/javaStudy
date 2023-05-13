package zzk.study.java.core.basic.number;

import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleToStringUnitTest {
    
    private static final double DOUBLE_VALUE = 3.56;
    private static final String TRUNCATED_DOUBLE = "3";
    private static final String ROUNDED_UP_DOUBLE = "4";
    
    
    @Test
    public void truncateByCastTest() {
        assertThat(String.valueOf((int) DOUBLE_VALUE)).isEqualTo(TRUNCATED_DOUBLE);
    }
    
    @Test
    public void roundingWithStringFormatTest() {
        assertThat(String.format("%.0f", DOUBLE_VALUE)).isEqualTo(ROUNDED_UP_DOUBLE);
    }

    @Test
    public void truncateWithNumberFormatTest() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.FLOOR);
        assertThat(nf.format(DOUBLE_VALUE)).isEqualTo(TRUNCATED_DOUBLE);
    }
    
    @Test
    public void roundWithNumberFormatTest() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        assertThat(nf.format(DOUBLE_VALUE)).isEqualTo(ROUNDED_UP_DOUBLE);
    }

    @Test
    public void truncateWithDecimalFormatTest() {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.FLOOR);
        assertThat(df.format(DOUBLE_VALUE)).isEqualTo(TRUNCATED_DOUBLE);
    }
    
    @Test
    public void roundWithDecimalFormatTest() {
        DecimalFormat df = new DecimalFormat("#,###");
        assertThat(df.format(DOUBLE_VALUE)).isEqualTo(ROUNDED_UP_DOUBLE);
    }

}
