import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FunctionalInterface {

    /**************************************************
     * Ready
     **************************************************/
    @java.lang.FunctionalInterface
    interface SomeFunction<X> {
        X process(X arg1, X arg2);
    }

    @java.lang.FunctionalInterface
    interface SomeSimpleFunction {
        String process();
    }

    String testString = "This Class";



    /**************************************************
     * Test
     **************************************************/
    @Test
    public void implementsFunctionalInterface(){
        SomeFunction<Integer> func = new SomeFunction<Integer>() {
            @Override
            public Integer process(Integer arg1, Integer arg2){
                return arg1 * arg2;
            }
        };
        assert func.process(2, 3) == 6;
        assert func.process(5, 5) == 25;
    }

    @Test
    public void implementsFunctionalInterfaceWithLambda(){
        SomeFunction<Integer> func = (a, b) -> a * b;
        assert func.process(2, 3) == 6;
        assert func.process(5, 5) == 25;
    }



    @Test
    public void checkThis(){
        //- With Anonymous Class
        SomeSimpleFunction funcWithAnonymousClass = new SomeSimpleFunction() {
            String testString = "Anonymous Class";
            @Override
            public String process(){
                return this.testString;
            }
        };
        assert !funcWithAnonymousClass.process().equals(this.testString);
        assert funcWithAnonymousClass.process().equals("Anonymous Class");

        //- With Lambda
        SomeSimpleFunction funcWithLambda = () -> this.testString;
        assert funcWithLambda.process().equals(this.testString);
    }



    /***************************************************************************
     * Java provide default interface in java.util.function
     *      ~Predicate: Return true or false
     *      ~Function: 1 args / Return some value
     *      ~Supplier: No args / Return some value
     *      ~Consumer: 1 args / No return
     *      ~Operator: Like Function but args and return value are same.
     ***************************************************************************/
    @Test
    public void predicate(){
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        IntPredicate evenChecker = (n) -> n % 2 == 0;

        //Use custom predicate function - java.util.function.*
        boolean result = false;
        for (int n : numberList){
            if (evenChecker.test(n)){
                result = true;
                break;
            }
        }
        assert result;

        //Anonymous
        assert numberList.stream().anyMatch(new Predicate<Integer>(){
            @Override
            public boolean test(Integer n) {
                return n % 2 == 0;
            }
        });

        //Lambda
        assert numberList.stream().anyMatch( (n) -> n % 2 == 0 );
    }

    @Test
    public void consumer(){
        //creating sample Collection
        List<Integer> numberList = Stream.iterate(1, n -> n + 1 ).limit(10).collect(Collectors.toList());

        //traversing using Iterator
        Iterator<Integer> it = numberList.iterator();
        while(it.hasNext()){
            Integer i = it.next();
            System.out.println("Iterator::"+i);
        }

        //traversing through forEach method of Iterable with anonymous class
        numberList.forEach(new Consumer<Integer>() {
            public void accept(Integer n) {
                System.out.println("Anonymous::"+n);
            }
        });

        //traversing using Lambda
        numberList.forEach( n -> { System.out.println("Lambda::"+n); });
    }

}
