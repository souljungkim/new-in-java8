import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamAPI {

    List<String> nameList = Arrays.asList("Kim", "Lee", "Park", "Moon", "Song", "Hwang", "Son", "Jang", "Ahn", "Yang");
    List<Integer> numberList = Arrays.asList(1,2,3,4,5,6,7,8,9);


    @Test
    public void filter(){
        assert nameList.stream().filter(x -> x.contains("o") ).count() == 3;
        assert numberList.stream().filter(x -> 5 < x ).count() == 4;
    }

    @Test
    public void limit(){
        assert nameList.stream().filter(x -> x.contains("g") ).limit(2).count() == 2;
        assert numberList.stream().filter(x -> 5 < x ).limit(2).count() == 2;
        assert numberList.stream().filter(x -> 8 < x ).limit(2).count() == 1;
    }

    @Test
    public void distinct(){

    }

    @Test
    public void skip(){

    }

    @Test
    public void mapToSomething(){
        //mapToInt, mapToLong, mapToDouble
    }

    @Test
    public void calculate(){
        // count(), min(), max(), sum(), average()
    }

    @Test
    public void reduce(){

    }

    @Test
    public void forEach(){

    }

    @Test
    public void collect(){
        List<Integer> ages = new ArrayList<Integer>();
        ages.add(1);ages.add(2);ages.add(3);//1,2,3
        Set<Integer> set = ages.stream().collect(Collectors.toSet());
        set.forEach(x-> System.out.println(x));//1,2,3
    }

    @Test
    public void iterator(){
        List<String> names = Arrays.asList("jeong", "pro", "jdk", "java");
        Iterator<String> iter = names.stream().iterator();
        while(iter.hasNext()) {
            System.out.println(iter.next());//jeong, pro, jdk, java
        }
    }

    @Test
    public void match(){
        //noneMatch, anyMatch, allMatch
        List<Integer> ages = new ArrayList<Integer>();
        ages.add(1);ages.add(2);ages.add(3);//1,2,3
        System.out.println(ages.stream().filter(x -> x>1).noneMatch(x->x>2));//false
    }


    @Test
    public void map(){
        nameList.parallelStream()
                .map( s -> { return s.concat("s"); })
                .forEach( s -> System.out.println(s) );
    }

    @Test
    public void peek(){
        //Peek
    }

}
