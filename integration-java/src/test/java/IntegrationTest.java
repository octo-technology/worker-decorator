import com.octo.workerdecorator.annotation.java.WorkerDecoration;
import com.octo.workerdecorator.integration.*;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Very weak tests just to check that the decorations are generated and that it all compiles
 */
public class IntegrationTest {

    @Test
    public void testImmutable() {
        Executor executor = Executors.newSingleThreadExecutor();
        JavaSimpleInterface implementation = new JavaSimpleInterface() {
            @Override
            public void a(int param) {
                // Stuff
            }

            @Override
            public void b(Date param) {
                // Stuff
            }
        };

        JavaSimpleInterfaceDecorated immutable = new JavaSimpleInterfaceDecorated(implementation, executor);
    }

    @Test
    public void testWeakImmutable() {
        Executor executor = Executors.newSingleThreadExecutor();
        JavaSimpleInterface3 implementation = new JavaSimpleInterface3() {
            @Override
            public void a(int param) {
                // Stuff
            }

            @Override
            public void b(Date param) {
                // Stuff
            }
        };

        JavaSimpleInterface3Decorated immutable = new JavaSimpleInterface3Decorated(implementation, executor);
    }

    @Test
    public void testMutable() {
        Executor executor = Executors.newSingleThreadExecutor();
        JavaSimpleInterface2 implementation = new JavaSimpleInterface2() {
            @Override
            public void a(int param) {
                // Stuff
            }

            @Override
            public void b(Double param) {
                // Stuff
            }
        };

        WorkerDecoration<JavaSimpleInterface2> mutable = new JavaSimpleInterface2Decorated(executor);
        mutable.setDecorated(implementation);
    }
}
