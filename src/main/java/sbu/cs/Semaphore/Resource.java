package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Resource {
    private static final Semaphore semaphore = new Semaphore(2);
    public static void accessResource(Thread t) {

        try {
            // First, get a permit.
            System.out.println(t.getName() + " is waiting for a permit.");

            // acquiring the lock
            semaphore.acquire();

            System.out.println(t.getName() + " gets a permit.");

            Thread.sleep(100);
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }

        // Release the permit. 
        System.out.println(t.getName() + " releases the permit.");
        semaphore.release();
    }
}
