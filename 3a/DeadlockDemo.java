import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

class Resource {
    private final String name;

    public Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Thread1 extends Thread {
    private final Resource resource1;
    private final Resource resource2;

    public Thread1(Resource resource1, Resource resource2) {
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public void run() {
        synchronized (resource1) {
            System.out.println("Thread 1: Holding " + resource1.getName());
            try {
                // Simulate some work with resource1
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1: Waiting for " + resource2.getName());
            synchronized (resource2) {
                System.out.println("Thread 1: Acquired " + resource2.getName());
            }
        }
    }
}

class Thread2 extends Thread {
    private final Resource resource1;
    private final Resource resource2;

    public Thread2(Resource resource1, Resource resource2) {
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public void run() {
        synchronized (resource2) {
            System.out.println("Thread 2: Holding " + resource2.getName());
            try {
                // Simulate some work with resource2
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2: Waiting for " + resource1.getName());
            synchronized (resource1) {
                System.out.println("Thread 2: Acquired " + resource1.getName());
            }
        }
    }
}

public class DeadlockDemo {
    public static void main(String[] args) {
        Resource resource1 = new Resource("Resource1");
        Resource resource2 = new Resource("Resource2");

        Thread1 thread1 = new Thread1(resource1, resource2);
        Thread2 thread2 = new Thread2(resource1, resource2);

        thread1.start();
        thread2.start();

        // Monitor for deadlock
        monitorForDeadlock();
    }

    private static void monitorForDeadlock() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        while (true) {
            try {
                // Get thread information
                long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
                if (deadlockedThreads != null) {
                    ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreads);
                    System.out.println("Deadlock detected!");
                    for (ThreadInfo threadInfo : threadInfos) {
                        System.out.println("Thread " + threadInfo.getThreadName() + " is deadlocked.");
                    }
                    System.exit(0); // Terminate the program gracefully
                }

                // Sleep for a while before checking again
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}