import org.junit.Test;

import java.time.LocalDate;

public class TimePackage {


    @Test
    public void time(){
        LocalDate today = LocalDate.now();
        assert today.getYear() == 2020;

        LocalDate otherDay = today.withYear(1982);
        assert otherDay.getYear() == 1982;
    }

}
