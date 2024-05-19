package sbu.cs.Semaphore;

public class Operator extends Thread {

    public Operator(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
        {
            Resource.accessResource(this); // critical section - a Maximum of 2 operators can access the resource concurrently
            try {
                sleep(500);
                System.out.println(this.getName() + " Finished i: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
