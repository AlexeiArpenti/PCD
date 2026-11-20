import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private final JTextArea structureArea = new JTextArea();
    private final JTextArea resultArea = new JTextArea();

    public MainWindow() {
        setTitle("Лабораторная №2 — Вариант 4");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JButton mediumButton = new JButton("Средний уровень");
        JButton advancedButton = new JButton("Продвинутый уровень");
        JButton clearButton = new JButton("Очистить");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(mediumButton);
        buttons.add(advancedButton);
        buttons.add(clearButton);
        add(buttons, BorderLayout.NORTH);

        structureArea.setEditable(false);
        resultArea.setEditable(false);
        structureArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JScrollPane left = new JScrollPane(structureArea);
        JScrollPane right = new JScrollPane(resultArea);
        left.setBorder(BorderFactory.createTitledBorder("Структура потоков — вариант 4"));
        right.setBorder(BorderFactory.createTitledBorder("Результаты выполнения"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.43);
        add(split, BorderLayout.CENTER);

        mediumButton.addActionListener(e -> runMedium());
        advancedButton.addActionListener(e -> runAdvanced());
        clearButton.addActionListener(e -> {
            structureArea.setText("");
            resultArea.setText("");
        });

        showStructure();
    }

    private void runMedium() {
        resultArea.setText("СРЕДНИЙ УРОВЕНЬ\n"
                + "Потоки созданы, метод run() не переопределяется и потоки не запускаются.\n\n");

        List<Thread> threads = ThreadManager.createThreads(false, resultArea);
        showStructure();

        for (Thread thread : threads) {
            resultArea.append("Имя: " + thread.getName()
                    + " | Группа: " + thread.getThreadGroup().getName()
                    + " | Приоритет: " + thread.getPriority() + "\n");
        }
        resultArea.append("\nВсего потоков: " + threads.size() + "\n");
    }

    private void runAdvanced() {
        resultArea.setText("ПРОДВИНУТЫЙ УРОВЕНЬ\n"
                + "Переопределённый run() выводит имя, группу и приоритет потока.\n\n");
        List<Thread> threads = ThreadManager.createThreads(true, resultArea);
        showStructure();

        for (Thread thread : threads) {
            thread.start();
        }

        System.out.println("\nДерево групп и активных потоков (ThreadGroup.list()):");
        ThreadManager.getMainGroup().list();
    }

    private void showStructure() {
        ThreadGroup main = Thread.currentThread().getThreadGroup();

        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        java.io.PrintStream printStream = new java.io.PrintStream(output);

        System.setOut(printStream);
        main.list();
        System.setOut(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out)));

        structureArea.setText(output.toString());
    }
}