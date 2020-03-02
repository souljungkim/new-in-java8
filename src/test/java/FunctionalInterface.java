import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;


/***************************************************************************
 * Java provide default interface in java.util.function
 * ~Predicate: Return true or false
 * ~Function: 1 args / Return some value
 * ~Supplier: No args / Return some value
 * ~Consumer: 1 args / No return
 * ~Operator: Like Function but args and return value are same.
 ***************************************************************************/
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
        //- With Annonymous Class
        SomeSimpleFunction funcWithAnnonymousClass = new SomeSimpleFunction() {
            String testString = "Annonymous Class";
            @Override
            public String process(){
                return this.testString;
            }
        };
        assert !funcWithAnnonymousClass.process().equals(this.testString);
        assert funcWithAnnonymousClass.process().equals("Annonymous Class");

        //- With Lambda
        SomeSimpleFunction funcWithLambda = () -> this.testString;
        assert funcWithLambda.process().equals(this.testString);
    }


    @Test
    public void implementsDefaultAPI(){
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

        //Or like this..
        assert numberList.stream().anyMatch( (n) -> n % 2 == 0 );
    }

}
