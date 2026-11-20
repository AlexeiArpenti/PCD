import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MyThread extends Thread {

    private final JTextArea output;

    public MyThread(ThreadGroup group, String name, int priority, JTextArea output) {
        super(group, name);
        setDaemon(true);
        this.output = output;
        setPriority(priority);
    }

    @Override
    public void run() {
        String message = "Поток: " + getName()
                + " | Группа: " + getThreadGroup().getName()
                + " | Приоритет: " + getPriority() + "\n";

        System.out.print(message);
        if (output != null) {
            SwingUtilities.invokeLater(() -> output.append(message));
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}