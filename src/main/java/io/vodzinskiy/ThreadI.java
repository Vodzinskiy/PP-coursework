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
                data.inputVector(data.C);
                data.inputVector(data.Z);
                data.inputMatrix(data.MX);

                monitor.inputAwait();
            } else if (id == Data.P) {
                data.inputVector(data.D);
                data.inputMatrix(data.MR);

                monitor.inputAwait();
            } else {
                monitor.inputAwait();
            }

            int ai = data.minSubVectorElement(data.C, id);
            int bi = data.maxSubVectorElement(data.Z, id);

            monitor.writeABAndAwait(ai, bi);

            data.calculationA(id);

            monitor.outputAwait();
            if (id == 1) {
                data.printResult();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Thread %d finished\n", id);
    }
}
