package io.vodzinskiy;

public class Monitor {
    private int a = Integer.MAX_VALUE; // min(C)
    private int b = Integer.MIN_VALUE; // max(Z)

    private int F1 = 0; // прапор синхронізації при введенні даних
    private int F2 = 0; // прапор синхронізації при обчисленні a та b
    private int F3 = 0; // прапор синхронізації при виведенні результату

    private final Data data;

    public Monitor(Data data) {
        this.data = data;
    }

    // синхронізація при введенні даних
    public synchronized void inputAwait() throws InterruptedException {
        F1++;
        if (F1 == Data.P) {
            notifyAll();
            return;
        }
        wait();
    }

    // синхронізація обчислень a та b
    public synchronized void writeABAndAwait(int ai, int bi) throws InterruptedException {

        if (ai < a) {
            a = ai;
        }

        if (bi > b) {
            b = bi;
        }

        F2++;
        if (F2 == Data.P) {
            data.a = a;
            data.b = b;
            notifyAll();
            return;
        }
        wait();
    }

    // синхронізація при виведенні результату
    public synchronized void outputAwait() throws InterruptedException {
        F3++;
        if (F3 == Data.P) {
            notifyAll();
            return;
        }
        wait();
    }
}
