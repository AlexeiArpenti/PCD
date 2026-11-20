import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;

public class ThreadManager {

    private static ThreadGroup mainGroup;

    public static List<Thread> createThreads(boolean advanced, JTextArea output) {
        List<Thread> threads = new ArrayList<>();

        mainGroup = new ThreadGroup("Main");

        // Подгруппа GO -> GZ
        ThreadGroup go = new ThreadGroup(mainGroup, "GO");
        ThreadGroup gz = new ThreadGroup(go, "GZ");
        add(threads, gz, "Tha", 1, advanced, output);
        add(threads, gz, "Thb", 3, advanced, output);
        add(threads, gz, "Thc", 3, advanced, output);
        add(threads, gz, "Thd", 7, advanced, output);

        // Подгруппа GV
        ThreadGroup gv = new ThreadGroup(mainGroup, "GV");
        add(threads, gv, "ThA", 3, advanced, output);

        // Подгруппа GF
        ThreadGroup gf = new ThreadGroup(mainGroup, "GF");
        add(threads, gf, "Th1", 5, advanced, output);
        add(threads, gf, "Th2", 3, advanced, output);
        add(threads, gf, "Th3", 9, advanced, output);

        // Потоки, непосредственно принадлежащие Main
        add(threads, mainGroup, "Th1", 3, advanced, output);
        add(threads, mainGroup, "Th2", 0, advanced, output);

        return threads;
    }

    public static ThreadGroup getMainGroup() {
        return mainGroup;
    }

    private static void add(List<Thread> threads, ThreadGroup group,
                            String name, int priority, boolean advanced,
                            JTextArea output) {
        Thread thread = advanced
                ? new MyThread(group, name, priority, output)
                : new Thread(group, name);
        thread.setPriority(priority);
        threads.add(thread);
    }
}