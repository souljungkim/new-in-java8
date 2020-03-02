import org.junit.Test;

/***************************************************************************
 * <BEFORE>  Java Heap-[Eden|S0|S1|OLD]  Permanent Heap-[Permanent]  Native Memory-[C Heap|Thread Stack]
 *      (Removed) PermGen
 *       -XX:PermSize=350m
 *       -XX:MaxPermSize=400m
 * 
 *
 *
 *
 * <AFTER>  Java Heap-[Eden|S0|S1|OLD]  Native Memory-[Metaspace|C Heap|Thread Stack]
 *      (New) Metaspace
 *       -XX:MaxMetaspaceSize=
 *       -XX:MetaspaceSize=
 ***************************************************************************/
public class RemovalOfPermGen {

    @Test
    public void main(){
        //None
    }

}
