import com.octo.workerdecorator.annotation.java.WorkerDecoration;
import com.octo.workerdecorator.integration.JavaSimpleInterface;
import com.octo.workerdecorator.integration.JavaSimpleInterface2;
import com.octo.workerdecorator.integration.JavaSimpleInterface2Decorated;
import com.octo.workerdecorator.integration.JavaSimpleInterfaceDecorated;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
