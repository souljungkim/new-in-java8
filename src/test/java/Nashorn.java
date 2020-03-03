import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

import static org.junit.Assert.assertTrue;

/**
 * REF:
 *  - http://www.n-k.de/riding-the-nashorn/
 */
public class Nashorn {

    /**************************************************
     * Ready
     **************************************************/
    public String getTextFromFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String absolutePath = file.getAbsolutePath();

        assertTrue(absolutePath.endsWith(fileName));
        return readFromInputStream(new FileInputStream(file));
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }



    /**************************************************
     * Test
     **************************************************/
    @Test
    public void invokingJavaScriptFunctionsFromJava() throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(getTextFromFile("example.js"));
        // cast the script engine to an invocable instance
        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("testFunc", "Tony");
        assert result.equals("hello from javascript");
        assert result instanceof String;

        Object result2 = invocable.invokeFunction("testFunc2", "Tony");
        assert result2.equals("Tony");
    }


    @Test
    public void invokingJavaMethodsFromJavaScript(){

    }

}
