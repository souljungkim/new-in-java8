import org.junit.Test;

import java.util.Arrays;

public class LambdaExpression {

    @Test
    public void lambda(){
        Runnable java8Runner = () -> {
            System.out.println("I am running");
        };

        Arrays.asList( "a", "b", "d" ).forEach(e -> {
            System.out.println( e );
        });
    }
}
