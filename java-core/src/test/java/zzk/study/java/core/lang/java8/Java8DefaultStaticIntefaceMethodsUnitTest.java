package zzk.study.java.core.lang.java8;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Java8DefaultStaticIntefaceMethodsUnitTest {

    @Test
    public void callStaticInterfaceMethdosMethods_whenExpectedResults_thenCorrect() {
        Vehicle vehicle = new VehicleImpl();
        String overview = vehicle.getOverview();
        long[] startPosition = vehicle.startPosition();

        assertEquals(overview, "ATV made by N&F Vehicles");
        assertEquals(startPosition[0], 23);
        assertEquals(startPosition[1], 15);
    }

    @Test
    public void callDefaultInterfaceMethods_whenExpectedResults_thenCorrect() {
        String producer = Vehicle.producer();
        assertEquals(producer, "N&F Vehicles");
    }

    public interface Vehicle {

        void moveTo(long altitude, long longitude);

        static String producer() {
            return "N&F Vehicles";
        }

        default long[] startPosition() {
            return new long[] { 23, 15 };
        }

        default String getOverview() {
            return "ATV made by " + producer();
        }
    }

    public class VehicleImpl implements Vehicle {

        @Override
        public void moveTo(long altitude, long longitude) {
            // do nothing
        }
    }

}
