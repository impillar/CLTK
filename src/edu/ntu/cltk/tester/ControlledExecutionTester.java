package edu.ntu.cltk.tester;

import edu.ntu.cltk.runtime.ControlledExecution;

import java.util.concurrent.TimeUnit;

public class ControlledExecutionTester {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ControlledExecution ce = new ControlledExecution(new Runnable() {

            @Override
            public void run() {
                int cnt = 1;
                while (cnt < 5) {
                    try {
                        System.out.println("Hello");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    cnt++;
                }
            }

        }, 10, TimeUnit.SECONDS);
        ce.setShowProgress(true);
        if (ce.start() == 1) {
            System.out.println("The thread is killed");
        } else {
            System.out.println("The thread finishes its task");
        }

        for (int i = 0; i < 100; ++i) {
            System.out.println("oh my god");
        }
    }

}
