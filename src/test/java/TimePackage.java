import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRulesException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * REF:
 *  - https://www.daleseo.com/java8-instant/
 *  - https://www.journaldev.com/2800/java-8-date-localdate-localdatetime-instant
 *  - http://blog.eomdev.com/java/2016/04/01/%EC%9E%90%EB%B0%948%EC%9D%98-java.time-%ED%8C%A8%ED%82%A4%EC%A7%80.html
 */
public class TimePackage {



    @Test
    public void main(){
        LocalDate today = LocalDate.now();
        LocalDate otherDay = today.withYear(1982).withMonth(3).withDayOfMonth(23);
        assert otherDay.getDayOfMonth() == 23 && otherDay.getMonthValue() == 3 && otherDay.getYear() == 1982;
        assert otherDay.isBefore(today);
        assert today.isAfter(otherDay);
    }



    /***************************************************************************
     *
     * LocalDate
     *
     ***************************************************************************/
    @Test
    public void localDate(){
        //Current Date
        LocalDate today = LocalDate.now();
        assert Integer.parseInt(today.toString().split("-")[0]) == today.getYear();
        assert Integer.parseInt(today.toString().split("-")[1]) == today.getMonthValue();
        assert Integer.parseInt(today.toString().split("-")[2]) == today.getDayOfMonth();

        //Creating LocalDate by providing input arguments
        //2014-01-01
        LocalDate date = LocalDate.of(2014, Month.JANUARY, 1);
        assert date.equals( LocalDate.parse("2014-01-01") );
        assert date.getDayOfWeek() == DayOfWeek.WEDNESDAY;
        assert date.getDayOfMonth() == 1 && date.getMonth().getValue() == 1 && date.getMonthValue() == 1 && date.getYear() == 2014;
        assert !date.isLeapYear();

        //2020-02-29
        date = LocalDate.of(2020, Month.FEBRUARY, 29);
        assert date.equals( LocalDate.parse("2020-02-29") );
        assert date.getDayOfWeek() == DayOfWeek.SATURDAY;
        assert date.getDayOfMonth() == 29 && date.getMonth().getValue() == 2 && date.getMonthValue() == 2 && date.getYear() == 2020;
        assert date.isLeapYear();

        //TimeZone
        LocalDate todaySeoul = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate todayTokyo = LocalDate.now(ZoneId.of("Asia/Tokyo"));
//        assert todaySeoul.equals(todayTokyo);  //not good to compare

        //From 01/01/1970
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        assert dateFromBase.getDayOfMonth() == 1 && dateFromBase.getMonthValue() == 1 && dateFromBase.getYear() == 1971;

        //From 01/01/2014
        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        assert hundredDay2014.getDayOfMonth() == 10 && hundredDay2014.getMonthValue() == 4 && hundredDay2014.getYear() == 2014;

        //Format
        assert hundredDay2014.format(DateTimeFormatter.ofPattern("d::MM::uuuu")).equals("10::04::2014");
        assert hundredDay2014.format(DateTimeFormatter.BASIC_ISO_DATE).equals("20140410");

        //Plus & Minus & Compare
        assert hundredDay2014.isAfter( LocalDate.parse("2014-04-01") );
        assert hundredDay2014.isBefore( LocalDate.parse("2014-04-25") );
        assert hundredDay2014.plusDays(10).equals( LocalDate.parse("2014-04-20") );
        assert hundredDay2014.plusDays(20).equals( LocalDate.parse("2014-04-30") );
        assert hundredDay2014.plusDays(30).equals( LocalDate.parse("2014-05-10") );
        assert hundredDay2014.plusDays(60).equals( LocalDate.parse("2014-06-09") );
        assert hundredDay2014.minusDays(30).equals( LocalDate.parse("2014-03-11") );
        assert hundredDay2014.minusDays(60).equals( LocalDate.parse("2014-02-09") );
        assert hundredDay2014.plusMonths(5).equals( LocalDate.parse("2014-09-10") );
        assert hundredDay2014.plusMonths(10).equals( LocalDate.parse("2015-02-10") );
        assert hundredDay2014.plusMonths(12).equals( LocalDate.parse("2015-04-10") );
        assert hundredDay2014.minusMonths(10).equals( LocalDate.parse("2013-06-10") );
        assert hundredDay2014.minusMonths(12).equals( LocalDate.parse("2013-04-10") );
    }

    @Test(expected = DateTimeException.class)
    public void invalidDate2014(){
        LocalDate date = LocalDate.of(2014, Month.FEBRUARY, 29);
    }

    @Test(expected = DateTimeException.class)
    public void invalidDate2020(){
        LocalDate date = LocalDate.of(2020, Month.FEBRUARY, 30);
    }

    @Test(expected = ZoneRulesException.class)
    public void invalidZone(){
        LocalDate today = LocalDate.now(ZoneId.of("Asia/SuperPower"));
    }



    /***************************************************************************
     *
     * LocalTime
     *
     ***************************************************************************/
    @Test
    public void localTime(){
        //Current Time
        LocalTime time = LocalTime.now();
        System.out.println("Current Time="+time);

        //Creating LocalTime by providing input arguments
        LocalTime specificTime = LocalTime.of(8,45,25,40);
        assert specificTime.getHour() == 8 && specificTime.getMinute() == 45 && specificTime.getSecond() == 25 && specificTime.getNano() == 40;

        //NanoSecond when toString()
        assert LocalTime.of(8,45,25,100000000).toString().split(":")[2].split("[.]")[1].equals("100");
        assert LocalTime.of(8,45,25,100000).toString().split(":")[2].split("[.]")[1].equals("000100");
        assert LocalTime.of(8,45,25,100).toString().split(":")[2].split("[.]")[1].equals("000000100");
        assert LocalTime.of(8,45,25,1).toString().split(":")[2].split("[.]")[1].equals("000000001");

        String[] timeExpArray = specificTime.toString().split(":");
        String[] secondTimeExpArray = timeExpArray[2].split("[.]");
        assert timeExpArray[0].equals("08");
        assert timeExpArray[1].equals("45");
        assert secondTimeExpArray[0].equals("25");
        assert secondTimeExpArray[1].equals("000000040");

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalTime todayKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        LocalTime todaySeoul = LocalTime.now(ZoneId.of("Asia/Seoul"));
        LocalTime todayTokyo = LocalTime.now(ZoneId.of("Asia/Tokyo"));
//        assert todaySeoul.equals(todayTokyo);  //not good to compare
        assert !todayKolkata.equals(todaySeoul);
        assert !todayKolkata.equals(todayTokyo);

        //From 01/01/1970
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        assert specificSecondTime.toString().equals("02:46:40");
        assert LocalTime.ofSecondOfDay(10).toString().equals("00:00:10");
        assert LocalTime.ofSecondOfDay(65).toString().equals("00:01:05");
        assert LocalTime.ofSecondOfDay(60 * 60).toString().equals("01:00");
        assert LocalTime.ofSecondOfDay(60 * 60 +1).toString().equals("01:00:01");
        assert LocalTime.ofSecondOfDay(60 * 60 * 20).toString().equals("20:00");
        assert LocalTime.ofSecondOfDay(60 * 60 * 20 +1).toString().equals("20:00:01");

        //From 01/01/2014
        LocalTime timeToTest = LocalTime.ofSecondOfDay(1);
        assert timeToTest.getHour() == 0 && timeToTest.getMinute() == 0 && timeToTest.getSecond() == 1;

        //Plus & Minus & Compare
        assert timeToTest.isAfter( LocalTime.parse("00:00:00") );
        assert timeToTest.isBefore( LocalTime.parse("00:00:02") );
        assert timeToTest.plusSeconds(10).equals( LocalTime.parse("00:00:11") );
        assert timeToTest.plusSeconds(20).equals( LocalTime.parse("00:00:21") );
        assert timeToTest.plusSeconds(30).equals( LocalTime.parse("00:00:31") );
        assert timeToTest.plusSeconds(60).equals( LocalTime.parse("00:01:01") );
        assert timeToTest.minusSeconds(30).equals( LocalTime.parse("23:59:31") );
        assert timeToTest.minusSeconds(60).equals( LocalTime.parse("23:59:01") );
        assert timeToTest.plusMinutes(5).equals( LocalTime.parse("00:05:01") );
        assert timeToTest.plusMinutes(60).equals( LocalTime.parse("01:00:01") );
        assert timeToTest.plusMinutes(120).equals( LocalTime.parse("02:00:01") );
        assert timeToTest.minusMinutes(60).equals( LocalTime.parse("23:00:01") );
        assert timeToTest.minusMinutes(120).equals( LocalTime.parse("22:00:01") );
    }

    @Test(expected = DateTimeException.class)
    public void invalidHourRange(){
        LocalTime.of(24,20);
    }

    @Test(expected = DateTimeException.class)
    public void invalidHourRange2(){
        LocalTime.of(25,20);
    }

    @Test(expected = DateTimeException.class)
    public void invalidMinuteRange(){
        LocalTime.of(23,60);
    }

    @Test(expected = DateTimeException.class)
    public void invalidSecondRange(){
        LocalTime.of(23,59, 60);
    }

    @Test(expected = DateTimeException.class)
    public void invalidNanoSecondRange(){
        LocalTime.of(8,45,25,1000000000);
    }



    /***************************************************************************
     *
     * LocalDateTime
     *
     ***************************************************************************/
    @Test
    public void localDateTime(){
        //Current Date
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current DateTime= " + today);

        //Current Date using LocalDate and LocalTime
        today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("Current DateTime= " + today);

        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 11, 2, 55);
        assert specificDate.equals( LocalDateTime.parse("2014-01-01T11:02:55") );
        assert specificDate.getDayOfMonth() == 1 && specificDate.getMonthValue() == 1 && specificDate.getYear() == 2014;
        assert specificDate.getHour() == 11 && specificDate.getMinute() == 2 && specificDate.getSecond() == 55;

        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime todaySeoul = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime todayTokyo = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
//        assert todaySeoul.equals(todayTokyo); //not good to compare
        assert !todayKolkata.equals(todaySeoul);
        assert !todayKolkata.equals(todayTokyo);

        //From 01/01/1970
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
        dateFromBase.equals( LocalDateTime.parse("1970-01-01T02:46:40") );

        //Format
        assert specificDate.format(DateTimeFormatter.ofPattern("d::MM::uuuu HH::mm::ss")).equals("1::01::2014 11::02::55");
        assert specificDate.format(DateTimeFormatter.BASIC_ISO_DATE).equals("20140101");

    }



    /***************************************************************************
     *
     * Instant
     *  - For timestamp
     *
     ***************************************************************************/
    @Test
    public void instant(){
        //Base Date
        Instant epoch = Instant.EPOCH;
        assert epoch.toString().equals("1970-01-01T00:00:00Z");
        assert epoch.equals( Instant.parse("1970-01-01T00:00:00Z") );
        assert epoch.equals( Instant.ofEpochSecond(0) );
        assert epoch.getEpochSecond() == epoch.toEpochMilli();
        assert Instant.ofEpochSecond(1_000_000_000).equals( Instant.parse("2001-09-09T01:46:40Z") );
        assert Instant.ofEpochSecond(-1_000_000_000).equals( Instant.parse("1938-04-24T22:13:20Z") );

        //Current
        Instant currentTimestamp = Instant.now();
        assert 0 < currentTimestamp.toEpochMilli();
        assert epoch.toEpochMilli() < currentTimestamp.toEpochMilli();
        assert Instant.ofEpochMilli(currentTimestamp.toEpochMilli()).equals(currentTimestamp);

        long epochSecond = currentTimestamp.getEpochSecond();
        long epochMilli = currentTimestamp.toEpochMilli();
        assert epochMilli > epochSecond;

        //ZonedDateTime to Instant
        ZonedDateTime zdtSeoul = Year.of(2020).atMonth(6).atDay(18).atTime(13, 15).atZone(ZoneId.of("Asia/Seoul"));
        Instant instantSeoul = zdtSeoul.toInstant();
        ZonedDateTime zdtVancouver = instantSeoul.atZone(ZoneId.of("America/Vancouver"));
        Instant instantVancouver = zdtVancouver.toInstant();
        assert !zdtSeoul.equals(zdtVancouver);          //- Not Same
        assert instantSeoul.equals(instantVancouver);   //- Same
    }



    /***************************************************************************
     *
     * Calendar to Instant & Date to Instant
     *  - For timestamp
     *
     ***************************************************************************/
    @Test
    public void convertingToInstant(){
        //Date to Instant
        Instant timestamp = new Date().toInstant();
        //Now we can convert Instant to LocalDateTime or other similar classes
        LocalDateTime date = LocalDateTime.ofInstant(timestamp, ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        System.out.println("Date = "+date);

        //Calendar to Instant
        Instant time = Calendar.getInstance().toInstant();
        System.out.println(time);
        //TimeZone to ZoneId
        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        System.out.println(defaultZone);

        //ZonedDateTime from specific Calendar
        ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
        System.out.println(gregorianCalendarDateTime);

        //Date API to Legacy classes
        Date dt = Date.from(Instant.now());
        System.out.println(dt);

        TimeZone tz = TimeZone.getTimeZone(defaultZone);
        System.out.println(tz);

        GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
        System.out.println(gc);
    }



    /***************************************************************************
     *
     * Period
     *
     ***************************************************************************/
    @Test
    public void period(){
        Period period;
        LocalDate dateA = LocalDate.of(2020,8,15);
        LocalDate dateB = LocalDate.of(2020,1,25);

        //.until()
        period = dateB.until(dateA);
        assert period.getYears() == 0 && period.getMonths() == 6 && period.getDays() == 21;

        //Period.between()
        period = Period.between(dateB, dateA);
        assert period.getYears() == 0 && period.getMonths() == 6 && period.getDays() == 21;
    }



    /***************************************************************************
     *
     * Duration
     *
     ***************************************************************************/
    @Test
    public void duration(){
        Duration duration;
        LocalDate dateBefore = LocalDate.of(2020,1,25);
        LocalDate dateAfter = LocalDate.of(2020,8,15);
        LocalTime timeBefore = LocalTime.of(12,59,00);
        LocalTime timeAfter = LocalTime.of(23,17,03);

        //Duration.between(A, B)
        duration = Duration.between(timeBefore, timeAfter);
        assert duration.getSeconds() == 37083 && duration.getNano() == 0;

        //Duration.of*(T)
        duration = Duration.ofMillis(30);
        assert duration.getSeconds() == 0 && duration.getNano() == 30000000;

        duration = Duration.ofSeconds(30);
        assert duration.getSeconds() == 30 && duration.getNano() == 0;

        duration = Duration.ofMinutes(30);
        assert duration.getSeconds() == 1800 && duration.getNano() == 0;

        duration = Duration.ofHours(30);
        assert duration.getSeconds() == 108000 && duration.getNano() == 0;

        duration = Duration.ofDays(30);
        assert duration.getSeconds() == 2592000 && duration.getNano() == 0;

        //ChronoUnit.*.between(A, B)
        assert ChronoUnit.NANOS.between(timeBefore, timeAfter) == 37083000000000L;
        assert ChronoUnit.SECONDS.between(timeBefore, timeAfter) == 37083L;
        assert ChronoUnit.MINUTES.between(timeBefore, timeAfter) == 618L;
        assert ChronoUnit.HOURS.between(timeBefore, timeAfter) == 10L;
        assert ChronoUnit.DAYS.between(dateBefore, dateAfter) == 203L;
        assert ChronoUnit.MONTHS.between(dateBefore, dateAfter) == 6L;
        assert ChronoUnit.YEARS.between(dateBefore, dateAfter) == 0L;
    }


}
