package io.vodzinskiy;

import java.util.ArrayList;
import java.util.List;

/**
 * Курсова робота з "Архітектури комп'ютерів. Частина 2"
 * Варіант: 2
 * Завдання: A = min(C) * Z + D * (MX*MR) * max(Z)
 * ПВВ1: C, Z, A, MX
 * ПВВР: MR, D
 * Водзінський Роман ІО-04
 * Дата: 19.04.2023
 **/

public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread started");
        Data data = new Data();
        Monitor monitor = new Monitor(data);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < Data.P; i++) {
            threads.add(new ThreadI(monitor, data, i+1));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main thread finished");
    }
}