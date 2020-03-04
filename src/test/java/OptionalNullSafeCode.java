import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * REF:
 *  - https://www.baeldung.com/java-8-new-features#optional
 *  - https://www.daleseo.com/java8-optional-before/
 *  - https://www.daleseo.com/java8-optional-after/
 *  - https://www.daleseo.com/java8-optional-effective/
 *
 */
public class OptionalNullSafeCode {


    /**************************************************
     * Ready
     **************************************************/
    class User{
        User(String name, int age, Address address){ this.name = name; this.age = age; this.address = address; }
        private String name;
        private int age;
        private Address address;
        public int getAge(){ return age; }
        public Address getAddress(){ return address; }
    }

    class Address {
        Address(String street){ this.street = street; }
        private String street;
        public String getStreet(){ return street; };
    }

    List<User> userList = Arrays.asList(
            new User("Jack", 12, new Address("USA San. blah blah")),
            new User("Lena", 12, new Address("USA San. blah blah")),
            new User("John", 15, null),
            new User("Park", 15, new Address(null)),
            new User("Song", 15, new Address("KOREA Seo. blah blah")),
            new User("Lisa", 17, new Address("CANADA Ven. blah blah")),
            new User("Sam", 18, new Address(null)),
            new User("Ellen", 19, new Address("USA San. blah blah")),
            new User("Bart", 23, null),
            new User("Tony", 26, null),
            new User("Jin", 26, new Address(null)),
            new User("Losa", 31, new Address("CANADA Que. blah blah"))
    );



    /**************************************************
     * Test
     *  - Null => Substitute Value OR Supplier
     **************************************************/
    @Test
    public void orElse(){
        String nullValue = null;
        //Not Null String
        assert Optional.ofNullable( "TEST" ).map(String::length).orElse(0) == 4;
        //Null String
        assert Optional.ofNullable(nullValue).map(String::length).orElse(0) == 0;
        //Nullable Objects
        String substitute = "No Address";
        for (int i=0; i<userList.size(); i++){
            String result = Optional.ofNullable( userList.get(i) ).map(User::getAddress).map(Address::getStreet).orElse(substitute);
            assert (userList.get(i).getAddress() != null && userList.get(i).getAddress().getStreet() != null) ? !result.isEmpty() : result.equals(substitute);
        }
    }

    @Test
    public void orElseGet(){
        String substitute = "No Address";
        for (int i=0; i<userList.size(); i++){
            String result = Optional.ofNullable( userList.get(i) ).map(User::getAddress).map(Address::getStreet).orElseGet(() -> substitute);
            assert (userList.get(i).getAddress() != null && userList.get(i).getAddress().getStreet() != null) ? !result.isEmpty() : result.equals(substitute);
        }
    }


    /**************************************************
     * Test
     *  - Null => Exception
     **************************************************/
    @Test(expected = NullPointerException.class)
    public void throwSomething() throws NullPointerException {
        //Before
        String value = null;
        String result = "";
        try {
            result = value.toUpperCase();
        } catch (NullPointerException exception) {
            throw new NullPointerException();
        }
    }

    @Test(expected = NullPointerException.class)
    public void orElseThrow() throws NullPointerException {
        String value = null;
        String result = Optional.ofNullable(value).orElseThrow(NullPointerException::new).toUpperCase();
    }



    /**************************************************
     * Test
     *  - Check that value is null or not?
     **************************************************/
    @Test
    public void ifPresent(){
        Optional.ofNullable(null).ifPresent((v) -> { assert !((String)v).isEmpty(); });
    }

    @Test
    public void nonNullValue(){
        String str = "value";
        Optional<String> optional = Optional.of(str);
        assert optional.get().equals(str);
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyValue(){
        Optional<String> optional = Optional.empty();
        assert optional.get() == null; //Error
    }

    @Test(expected = NoSuchElementException.class)
    public void nullableValue(){
        Optional<String> optional = Optional.ofNullable(null);
        assert optional.get() == null; //Error
    }

}
