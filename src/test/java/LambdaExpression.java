import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class LambdaExpression {

    @Test
    public void lambda(){
        //Make and use
        Runnable java8Runner = () -> {
            System.out.println("I am running");
        };
        java8Runner.run();

        //With asList
        Arrays.asList( "a", "b", "d" ).forEach( s -> {
            System.out.println( s );
        });

        assert Stream.of(1,4,5,3,2).filter( n -> n < 5).count() == 4;

        //With stream
        int[] numbers = {1, 2, 3, 4, 5, 6, 7};
        assert Arrays.stream(numbers, 0, 7).count() == 7;
        assert Arrays.stream(numbers, 1, 6).sum() == 2+3+4+5+6;
    }

}
