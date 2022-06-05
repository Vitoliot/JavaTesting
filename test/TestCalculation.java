import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vitoliot.testing.Calculation;

import java.time.LocalTime;

public class TestCalculation {
    private final Calculation calculation = new Calculation();

    @Test
    @DisplayName("Тест№1")
    public void test1(){
        LocalTime[] startTimes = new LocalTime[] {
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 50)
        };

        int[] durations = { 60, 30, 10, 10, 40 };
        Assertions.assertArrayEquals(new String[]{"08:00-08:30", "08:30-09:00", "09:00-09:30", "09:30-10:00", "11:30-12:00", "12:00-12:30", "12:30-13:00", "13:00-13:30", "13:30-14:00", "14:00-14:30", "14:30-15:00", "15:40-16:10", "16:10-16:40", "17:30-18:00"}, calculation.getPeriods(startTimes, durations, 30, LocalTime.of(8, 0), LocalTime.of(18, 0)));
    }
}
