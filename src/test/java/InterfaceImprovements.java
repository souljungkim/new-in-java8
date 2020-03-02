import org.junit.Test;

public class InterfaceImprovements {


    /**************************************************
     * Ready
     **************************************************/
    interface TestInterface1 {
        Integer testSomeMethod();
        Long testSameNameMethod();
        static String testStaticMethod(String param1){ return param1 +"1"; }
        default int testDefaultMethod1(int a, int b){ return a + b; }
//        default int testSameNameDefaultMethod(int a, int b){ return a + b; }
    }

    interface TestInterface2 {
        Integer testSomeMethod();
        Long testSameNameMethod();
        static String testStaticMethod(String param1){ return param1 +"2"; }
        default int testDefaultMethod2(int a, int b){ return a * b; }
//        default int testSameNameDefaultMethod(int a, int b){ return a + b; }
    }

    class TestClass implements TestInterface1, TestInterface2  {
        @Override
        public Integer testSomeMethod() { return 12345; }
        @Override
        public Long testSameNameMethod(){ return 5292L; }
        //Cannot implements if both default method name are same
//        default int testSameNameDefaultMethod(int a, int b){ return a + b; }
    }



    /**************************************************
     * TEST
     **************************************************/
    @Test
    public void testInterfaceImprovements(){
        //(New) Static method in interface
        assert TestInterface1.testStaticMethod("Hello?").equals("Hello?1");
        assert TestInterface2.testStaticMethod("Hello?").equals("Hello?2");

        TestClass tc = new TestClass();

        //(New) Default method in interface
        assert tc.testDefaultMethod1(1, 2) == 3;
        assert tc.testDefaultMethod2(1, 2) == 2;
        // Cannot use same name default method between multiple interfaces
//        assert tc.testSameNameDefaultMethod(1, 2) == 3;

        //Implemented method
        assert tc.testSomeMethod() == 12345;
        assert tc.testSameNameMethod() == 5292L;
    }



}


