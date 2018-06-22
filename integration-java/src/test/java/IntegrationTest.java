import com.octo.workerdecorator.WorkerDecorator;
import com.octo.workerdecorator.annotation.java.WorkerDecoration;
import com.octo.workerdecorator.integration.*;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

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

        JavaSimpleInterface immutable = WorkerDecorator.decorate(implementation, executor);
        assertThat(immutable).isInstanceOf(JavaSimpleInterfaceDecorated.class);
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

        JavaSimpleInterface3 immutable = WorkerDecorator.decorate(implementation, executor);
        assertThat(immutable).isInstanceOf(JavaSimpleInterface3Decorated.class);
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

        WorkerDecoration<JavaSimpleInterface2> mutable = WorkerDecorator.decorate(JavaSimpleInterface2.class, executor);
        mutable.setDecorated(implementation);
        assertThat(mutable).isInstanceOf(JavaSimpleInterface2Decorated.class);
    }

    @Test
    public void testWeakMutable() {
        Executor executor = Executors.newSingleThreadExecutor();
        JavaSimpleInterface4 implementation = new JavaSimpleInterface4() {
            @Override
            public void a(int param) {
                // Stuff
            }

            @Override
            public void b(Date param) {
                // Stuff
            }
        };

        WorkerDecoration<JavaSimpleInterface4> mutable = WorkerDecorator.decorate(JavaSimpleInterface4.class, executor);
        mutable.setDecorated(implementation);
        assertThat(mutable).isInstanceOf(JavaSimpleInterface4Decorated.class);
    }
}
