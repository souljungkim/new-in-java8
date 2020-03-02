import org.junit.Test;

public class InterfaceImprovements {


    /**************************************************
     * Ready
     **************************************************/
    interface TestInterface {
        Integer testSomeMethod();
        static String testStaticMethod(String param1){ return param1; }
        default int testDefaultMethod(int a, int b){ return a + b; }
    }

    class TestClass implements TestInterface {
        @Override
        public Integer testSomeMethod() { return 12345; }
    }



    /**************************************************
     * TEST
     **************************************************/
    @Test
    public void testInterfaceImprovements(){
        //(New) Static method in interface
        assert TestInterface.testStaticMethod("Hello?").equals("Hello?");

        TestClass tc = new TestClass();

        //(New) Default method in interface
        assert tc.testDefaultMethod(1, 2) == 3;

        //Implemented method
        assert tc.testSomeMethod() == 12345;
    }



}


