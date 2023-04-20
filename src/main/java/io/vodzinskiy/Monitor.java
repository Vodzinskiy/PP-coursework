package io.vodzinskiy;

public class Monitor {
    private int a = Integer.MAX_VALUE;
    private int b = Integer.MIN_VALUE;

    private int F1 = 0;
    private int F2 = 0;
    private int F3 = 0;

    private final Data data;

    public Monitor(Data data) {
        this.data = data;
    }


    public synchronized void inputAwait() throws InterruptedException {
        F1++;
        if (F1 == Data.P) {
            notifyAll();
            return;
        }
        wait();
    }

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

    public synchronized void outputAwait() throws InterruptedException {
        F3++;
        if (F3 == Data.P){
            notifyAll();
            return;
        }
        wait();
    }

}
