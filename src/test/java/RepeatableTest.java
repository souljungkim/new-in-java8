import annotation.Role;
import annotation.Roles;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class RepeatableTest {


    @Test
    public void checkRepeatableAnnotation(){
        Roles rolesAnn;
        Role[] roleAnn;
        Annotation[] anns;

        anns = Kim.class.getDeclaredAnnotations();
        assert anns.length == 1;
        assert ((Roles)anns[0]).value().length == 4;

        rolesAnn = Kim.class.getDeclaredAnnotation(Roles.class);
        assert rolesAnn.value().length == 4;

        anns = Kim.class.getDeclaredAnnotationsByType(Roles.class);
        assert anns.length == 1;

        roleAnn = Kim.class.getDeclaredAnnotationsByType(Role.class);
        assert roleAnn.length == 4;

        assert Arrays.stream(Kim.class.getDeclaredAnnotationsByType(Role.class))
                .allMatch( ann -> ann instanceof Role);
    }

}
