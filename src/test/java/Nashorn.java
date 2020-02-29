import org.junit.Test;
import sun.font.Script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;

/**
 * REF: http://www.n-k.de/riding-the-nashorn/
 */
public class Nashorn {

    @Test
    public void invokingJavaScriptFunctionsFromJava() throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader("example.js"));

        // cast the script engine to an invocable instance
        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("sayHello", "John Doe");
        System.out.println(result);
        System.out.println(result.getClass());
    }


    @Test
    public void invokingJavaMethodsFromJavaScript(){

    }

}
