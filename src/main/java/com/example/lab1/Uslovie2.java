package com.example.lab1;

public class Uslovie2 implements Runnable {
    private int[] mas;
    public Uslovie2(int[] mas) {
        this.mas = mas;
    }
    @Override
    public void run() {
        int[] positions = new int[4];
        int count = 0;
        for (int i = mas.length - 1; i >= 0; i--) {
            if (mas[i] % 2 == 0) {
                positions[count] = i;
                count++;
                if (count == 4) {

                    int sum1 = positions[0] + positions[1];
                    int sum2 = positions[2] + positions[3];
                    int sum = sum1 + sum2;

                    Main.printText(Thread.currentThread().getName()
                            + ": ((" + positions[0] + " + " + positions[1]
                            + ") + (" + positions[2] + " + " + positions[3]
                            + ")) = " + sum + "\n"
                    );
                    count = 0;
                }
            }
        }
    }
}