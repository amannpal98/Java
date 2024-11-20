import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class ListModifier extends Thread {
    private List<Integer> list;

    public ListModifier(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        // Adding elements to the list
        for (int i = 0; i < 5; i++) {
            list.add(i);
            System.out.println("Added: " + i);
            try {
                Thread.sleep(100); // Sleep to simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ListPrinter extends Thread {
    private List<Integer> list;

    public ListPrinter(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        // Iterating through the list
        for (Integer number : list) {
            System.out.println("Printed: " + number);
            try {
                Thread.sleep(150); // Sleep to simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ConcurrentModificationDemo {
    public static void main(String[] args) {
        List<Integer> list = new CopyOnWriteArrayList<>(); // Using CopyOnWriteArrayList to avoid exceptions

        ListModifier modifier = new ListModifier(list);
        ListPrinter printer = new ListPrinter(list);

        printer.start();
        modifier.start();

        try {
            printer.join();
            modifier.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final list: " + list);
    }
}