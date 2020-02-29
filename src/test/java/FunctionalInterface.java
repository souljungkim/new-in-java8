import org.junit.Test;

public class FunctionalInterface {


    @Test
    public void some(){
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
    public void someWithLambda(){
        SomeFunction<Integer> func = (a, b) -> a + b;
        assert func.process(2, 3) == 5;
        assert func.process(5, 5) == 10;
    }

}
