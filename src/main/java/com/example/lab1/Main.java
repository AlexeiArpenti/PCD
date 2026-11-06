package com.example.lab1;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static TextArea resultArea;
    private Button startButton;

    private Thread coordinator;
    private Timeline infoTimer;
    private int symbolIndex;

    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) {

        Label label = new Label("Суммы позиций четных чисел по два");

        startButton = new Button("Запустить");

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startWork();
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(label, startButton, resultArea);

        VBox.setVgrow(resultArea, Priority.ALWAYS);

        Scene scene = new Scene(root, 750, 550);

        stage.setTitle("Лабораторная работа 1");
        stage.setScene(scene);
        stage.show();
    }

    private void startWork() {

        startButton.setDisable(true);
        resultArea.setText("Массив:\n");

        int[] mas = new int[100];
        Random random = new Random();

        for (int i = 0; i < mas.length; i++) {
            mas[i] = random.nextInt(100) + 1;
            resultArea.appendText(mas[i] + " ");
        }

        resultArea.appendText("\n\n");

        Thread th1 = new Thread(new Uslovie1(mas), "Alexei, Th1");
        Thread th2 = new Thread(new Uslovie1(mas), "Alexei, Th2");

        Thread th3 = new Thread(new Uslovie2(mas), "Adriana, Th1");
        Thread th4 = new Thread(new Uslovie2(mas), "Adriana, Th2");

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        coordinator = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    th1.join();
                    th2.join();
                    th3.join();
                    th4.join();

                } catch (InterruptedException e) {
                    printText("\nОжидание было прервано.\n");
                    System.err.println("Координатор был прерван во время ожидания рабочих потоков.");
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showStudentsInfo();
                    }
                });
            }
        }, "Coordinator");
        coordinator.start();
    }

    private void showStudentsInfo() {

        String info = "\nArpenti Alexei, CR-243\n" + "Bozul Adriana, CR-243";

        symbolIndex = 0;

        infoTimer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resultArea.appendText(String.valueOf(info.charAt(symbolIndex)));
                symbolIndex++;
                }
            }
        ));

        infoTimer.setCycleCount(info.length());

        infoTimer.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resultArea.appendText("\n");
                startButton.setDisable(false);
            }
        });
        infoTimer.play();
    }

    public static void printText(String text) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                resultArea.appendText(text);
            }
        });
    }

    @Override
    public void stop() {
        if (infoTimer != null) {
            infoTimer.stop();
        }
        if (coordinator != null) {
            coordinator.interrupt();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}