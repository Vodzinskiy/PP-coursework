package io.vodzinskiy;

public class ThreadI extends Thread {
    private final Monitor monitor;
    private final Data data;
    private final int id;

    public ThreadI(Monitor monitor, Data data, int id) {
        this.monitor = monitor;
        this.data = data;
        this.id = id;
    }

    public void run() {
        System.out.printf("Thread %d started\n", id);
        try {
            if (id == 1) {
                // введення даних для потоку 1
                data.inputVector(data.C);
                data.inputVector(data.Z);
                data.inputMatrix(data.MX);
                // синхронізація при введенні даних
                monitor.inputAwait();
            } else if (id == Data.P) {
                // введення даних для потоку Р
                data.inputVector(data.D);
                data.inputMatrix(data.MR);
                // синхронізація при введенні даних
                monitor.inputAwait();
            } else {
                // синхронізація при введенні даних
                monitor.inputAwait();
            }
            // обчислення ai та bi
            int ai = data.minSubVectorElement(data.C, id);
            int bi = data.maxSubVectorElement(data.Z, id);
            // синхронізація при обчисленні ai та bi
            monitor.writeABAndAwait(ai, bi);
            // обчислення A_H
            data.calculationA(id);
            // синхронізація при виводі результату
            monitor.outputAwait();
            if (id == 1) {
                // вивід результату першим потоком
                data.printResult();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Thread %d finished\n", id);
    }
}
