package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;
import java.time.LocalTime;

public class Resource {
    private static final Semaphore semaphore = new Semaphore(2);
    public static void accessResource(Thread t) {

        try {
            // First, get a permit.
            System.out.println(t.getName() + " is waiting for a permit. (" + LocalTime.now() + ")");

            // acquiring the lock
            semaphore.acquire();

            System.out.println(t.getName() + " gets a permit. (" + LocalTime.now() + ")");

            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Release the permit. 
        System.out.println(t.getName() + " releases the permit. (" + LocalTime.now() + ")");
        semaphore.release();
    }
}
