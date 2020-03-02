import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPI {

    /**************************************************
     * Ready
     **************************************************/
    List<String> nameList = Arrays.asList("Kim", "Lee", "Park", "Moon", "Song", "Hwang", "Son", "Jang", "Ahn", "Yang");
    List<Integer> numberList = Arrays.asList(1,2,3,4,5,6,7,8,9);

    List<String> nameDupList = Arrays.asList("Kim", "Kim", "Lee", "Park", "Song", "Song", "Hwang", "Son", "Kim", "Jang", "Ahn", "Park");
    List<Integer> numberDupList = Arrays.asList(1,1,2,3,4,5,6,7,8,9,5,5,3,2,1,8);

    class User{
        User(String name, int age){ this.name = name; this.age = age; }
        String name;
        int age;
        public int getAge(){ return age; }
    }

    List<User> userList = Arrays.asList(
            new User("Jack", 12),
            new User("Lena", 12),
            new User("John", 15),
            new User("Park", 15),
            new User("Song", 15),
            new User("Lisa", 17),
            new User("Sam", 18),
            new User("Ellen", 19),
            new User("Bart", 23),
            new User("Tony", 26),
            new User("Jin", 26),
            new User("Losa", 31)
    );



    /**************************************************
     * Test
     **************************************************/
    @Test
    public void makingStream(){
        //Arrays.stream()
        String[] wordArray = {"Using", "Stream", "API", "From", "Java8"};
        assert Arrays.stream(wordArray, 0, wordArray.length).count() == 5;

        //Stream.of()
        Stream<String> stream = Stream.of("hello nice to meet you!@#!".split("[\\P{L}]+"));
        assert stream.map( s -> s.length() ).distinct().count() == 4;
        Stream<String> stream2 = Stream.of("Using", "Stream", "API", "From", "Java8");
        assert stream2.filter( s -> s.length() < 5 ).count() == 2;

        //Stream.empty()
        assert Stream.empty().count() == 0;

        //Stream.generate()
        assert Stream.generate(() -> "Stream").limit(5).count() == 5;
        assert Stream.generate(() -> "Stream").limit(5).collect(Collectors.joining()).equals("StreamStreamStreamStreamStream");

        //Stream.generate()
        assert Stream.generate(Math::random).limit(5).count() == 5;
        assert Stream.generate(Math::random).limit(5).map(String::valueOf).collect(Collectors.joining()).length() > 10;

        //Stream.iterate()
        assert Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(5).map(String::valueOf).collect(Collectors.joining()).equals("01234");
        assert Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ZERO)).limit(5).map(String::valueOf).collect(Collectors.joining()).equals("00000");
        assert Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.TEN)).limit(5).map(String::valueOf).collect(Collectors.joining()).equals("111213141");
    }

    @Test
    public void filter(){
        assert nameList.stream().filter( s -> s.contains("o") ).count() == 3;
        assert numberList.stream().filter( n -> 5 < n ).count() == 4;
    }

    @Test
    public void limitAndSkip(){
        //limit
        assert nameList.stream().filter( s -> s.contains("g") ).limit(2).count() == 2;
        assert numberList.stream().filter( n -> 5 < n ).limit(2).count() == 2;
        assert numberList.stream().filter( n -> n < 7 ).limit(2).count() == 2;
        //skip
        assert nameList.stream().filter( s -> s.contains("g") ).skip(1).count() == 3;
        assert numberList.stream().filter( n -> 5 < n ).skip(2).count() == 2;
        assert numberList.stream().filter( n -> n < 7).skip(2).count() == 4;
    }

    @Test
    public void distinct(){
        /** Test String List **/
        List<String> distinctNameList = nameDupList.stream().distinct().collect(Collectors.toList());
        assert nameDupList.size() == 12;
        assert nameDupList.get(0).equals("Kim");
        assert nameDupList.get(1).equals("Kim");
        assert nameDupList.get(2).equals("Lee");
        assert distinctNameList.size() == 8;
        assert distinctNameList.get(0).equals("Kim");
        assert distinctNameList.get(1).equals("Lee");
        assert distinctNameList.get(2).equals("Park");
        /** Test Integer List **/
        List<Integer> distinctNumberList = numberDupList.stream().distinct().collect(Collectors.toList());
        assert numberDupList.size() == 16;
        assert numberDupList.get(0) == 1;
        assert numberDupList.get(1) == 1;
        assert numberDupList.get(2) == 2;
        assert distinctNumberList.size() == 9;
        assert distinctNumberList.get(0) == 1;
        assert distinctNumberList.get(1) == 2;
        assert distinctNumberList.get(2) == 3;
    }

    @Test
    public void forEach(){
        nameList.forEach( s -> System.out.println(s) );
    }

    @Test
    public void collect(){
        Set<Integer> set = numberList.stream().collect(Collectors.toSet());
        set.forEach( n -> System.out.println(n) );
    }

    @Test
    public void iterator(){
        int i = -1;
        Iterator<String> iter = nameList.stream().iterator();
        while(iter.hasNext()) {
            iter.next().equals(nameList.get(++i));
        }
    }

    @Test
    public void reduce(){
        //sum
        assert numberList.stream().reduce(Integer::sum).get() == 1+2+3+4+5+6+7+8+9;
        assert numberList.stream().reduce((a, b) -> a + b).get() == 1+2+3+4+5+6+7+8+9;
        assert numberList.stream().mapToInt(Integer::intValue).sum() == 1+2+3+4+5+6+7+8+9;
        assert nameList.stream().mapToInt(String::length).sum() == 37;
        //count
        assert numberList.stream().count() == 9;
        //min
        assert numberList.stream().min(Comparator.comparing(Integer::intValue)).get() == 1;
        assert numberList.stream().mapToInt(Integer::intValue).min().getAsInt() == 1;
        assert numberList.stream().mapToLong(Long::valueOf).min().getAsLong() == 1;
        //max
        assert numberList.stream().max(Comparator.comparing(Integer::intValue)).get() == 9;
        assert numberList.stream().mapToInt(Integer::intValue).max().getAsInt() == 9;
        assert numberList.stream().mapToLong(Long::valueOf).max().getAsLong() == 9L;
        //Average
        assert numberList.stream().mapToInt(Integer::intValue).average().getAsDouble() == (double)(1+2+3+4+5+6+7+8+9)/9;
        assert nameList.stream().mapToInt(String::length).average().getAsDouble() == (double)37 /10;
    }

    /**************************************************
     *
     * find
     *
     **************************************************/
    @Test
    public void find(){
        /** findFirst **/
        assert nameList.stream().findFirst().get().equals("Kim");
        assert numberList.stream().findFirst().get() == 1;
        //findFirst with sorted()
        assert nameList.stream().sorted(Comparator.comparing(String::length).reversed()).findFirst().get().equals("Hwang");
        assert nameList.stream().sorted(Comparator.comparing(String::length)).findFirst().get().equals("Kim");
        assert numberList.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).findFirst().get() == 9;
        assert numberList.stream().sorted(Comparator.comparing(Integer::intValue)).findFirst().get() == 1;
        //findFirst on parallelStream
        assert numberList.parallelStream().map( n -> n*5 ).findFirst().get() == 5;
        assert numberList.parallelStream().map( n -> n*5 ).sorted().findFirst().get() == 5; //Not sorted
        assert numberList.parallelStream().map( n -> n*5 ).sorted().collect(Collectors.toList()).get(0) == 5; //Sorted

        /** findAny **/ //- Check test when use parallelStream
        assert nameList.stream().findAny().get().equals("Kim");
        assert numberList.stream().findAny().get() == 1;
        //findAny with sorted()
        assert nameList.stream().sorted(Comparator.comparing(String::length).reversed()).findAny().get().equals("Hwang");
        assert nameList.stream().sorted(Comparator.comparing(String::length)).findAny().get().equals("Kim");
        assert numberList.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).findAny().get() == 9;
        assert numberList.stream().sorted(Comparator.comparing(Integer::intValue)).findAny().get() == 1;
        //findAny on parallelStream
        assert numberList.parallelStream().map( n -> n*5 ).findAny().get() != 5;
        assert numberList.parallelStream().map( n -> n*5 ).sorted().findAny().get() != 5; //Not sorted
        assert numberList.parallelStream().map( n -> n*5 ).sorted().collect(Collectors.toList()).get(0) == 5; //Sorted
    }



    /**************************************************
     *
     * match
     *
     **************************************************/
    @Test
    public void match(){
        //allMatch
        assert numberList.stream().allMatch( x -> 0 < x );
        assert !numberList.stream().allMatch( x -> 1 < x );
        //anyMatch
        assert numberList.stream().anyMatch( x-> x % 9 == 0 );
        assert !numberList.stream().anyMatch( x-> x % 10 == 0 );
        //noneMatch
        assert numberList.stream().noneMatch( x -> x < 0 );
        assert numberList.stream().noneMatch( x -> 10 < x );
        assert !numberList.stream().noneMatch( x -> 5 < x );
    }


    /**************************************************
     *
     * map
     *
     **************************************************/
    @Test
    public void map(){
        //Test1
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6);
        List<Integer> firstMapedList = numbers.map(num -> num * 3).collect(Collectors.toList());
        assert firstMapedList.size() == 6;
        assert firstMapedList.get(0) == 3;
        assert firstMapedList.get(1) == 6;
        assert firstMapedList.get(2) == 9;

        //Test2
        List<String> convertedNameList = nameList.parallelStream()
                .map( s -> { return s.concat("s"); })
                .collect(Collectors.toList());
        String s1, s2;
        for (int i=0; i<convertedNameList.size(); i++){
            s1 = convertedNameList.get(i);
            s2 = nameList.get(i);
            assert s2.concat("s").equals(s1);
        }
    }

    @Test
    public void mapStableOriginObject(){
        Stream<Integer> numbers = numberList.stream();
        List<Integer> secondMapedList = numbers.map(num -> num * 3).collect(Collectors.toList());
        //Converted Object
        assert secondMapedList.get(0) == 3;
        assert secondMapedList.get(1) == 6;
        assert secondMapedList.get(2) == 9;
        //Origin Object
        assert numberList.get(0) == 1;
        assert numberList.get(1) == 2;
        assert numberList.get(2) == 3;
    }

    @Test(expected = IllegalStateException.class)
    public void mapWithStreamError(){
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6);
        numbers.map(num -> num * 3);
        //- Error occur
        List<Integer> firstMapedList = numbers.collect(Collectors.toList());
    }

    @Test(expected = IllegalStateException.class)
    public void mapWithStreamError2(){
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6);
        List<Integer> firstMapedList = numbers.map(num -> num * 3).collect(Collectors.toList());
        //- Error occur
        List secondMapedList = numbers.collect(Collectors.toList());
    }

    @Test
    public void mapWithParallelStream(){
        List<String> convertedNameList = nameList.parallelStream()
                .map( s -> { return s.concat("s"); })
                .collect(Collectors.toList());
        String s1, s2;
        for (int i=0; i<convertedNameList.size(); i++){
            s1 = convertedNameList.get(i);
            s2 = nameList.get(i);
            assert s2.concat("s").equals(s1);
        }
    }


    /**************************************************
     *
     * peek
     *
     **************************************************/
    @Test
    public void peek(){
        assert nameDupList.stream()
                .distinct()
                .filter( s -> s.equals("Kim") || s.equals("Park") )
                .peek( s -> System.out.println("FILTER:" + s) )
                .noneMatch( s -> s.equals("Song") );

        assert !nameDupList.stream()
                .distinct()
                .filter( s -> s.equals("Lee") || s.equals("Song") )
                .peek( s -> System.out.println("FILTER:" + s) )
                .anyMatch( s -> s.equals("Park") );
    }


    /**************************************************
     *
     * groupingBy and partionBy
     *
     **************************************************/
    @Test
    public void groupingBy(){
        Map<Integer, List<User>> mapByAge = userList.stream().collect(Collectors.groupingBy(User::getAge));
        assert mapByAge.get(12).size() == 2;
        assert mapByAge.get(15).size() == 3;
        assert mapByAge.get(17).size() == 1;
        assert mapByAge.get(18).size() == 1;
    }

    @Test
    public void partionBy(){
        Map<Boolean, List<User>> mapByAdult = userList.stream().collect(Collectors.partitioningBy((User u) -> u.getAge() > 19));
        assert mapByAdult.get(true).size() == 4;
        assert mapByAdult.get(false).size() == 8;
    }


    /**************************************************
     *
     * IntStream
     *
     **************************************************/
    @Test
    public void intStream(){
        //- IntStream.of(...)
        IntStream streamFromIntStream = IntStream.of(1,2,3,4,5,6,7,8,9);
        assert streamFromIntStream.toArray().length == 9;

        //- Arrays.stream(ARRAY, startInclusive, endExclusive)
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        IntStream streamFromArrays = Arrays.stream(array, 0, 9);
        assert streamFromArrays.toArray().length == 9;

        //- IntStream.range(START-inclusive, END-exclusive)
        assert IntStream.range(0, 5).sum() == 0+1+2+3+4;
        assert IntStream.range(4, 7).sum() == 4+5+6;
        assert IntStream.range(11, 15).sum() == 11+12+13+14;

        //- IntStream.range(START-inclusive, END-inclusive)
        assert IntStream.rangeClosed(0, 5).sum() == 0+1+2+3+4+5;
        assert IntStream.rangeClosed(4, 7).sum() == 4+5+6+7;
        assert IntStream.rangeClosed(11, 15).sum() == 11+12+13+14+15;
    }

}
