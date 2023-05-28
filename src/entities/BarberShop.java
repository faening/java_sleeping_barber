package entities;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class BarberShop {
    private BlockingQueue<Customer> queue;
    private int capacity;
    private Semaphore barberSemaphore;
    private Semaphore customerSemaphore;
    private Semaphore mutex;
    Random rdn = new Random();

    public BarberShop(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
        this.barberSemaphore = new Semaphore(0);
        this.customerSemaphore = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void cutHair() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("O barbeiro está dormindo");
            barberSemaphore.acquire();
        }

        Customer customer = queue.poll();

        if (customer != null) {
            // Simula o corte de cabelo
            System.out.printf("O barbeiro está cortando o cabelo do cliente %d \n", customer.getId());
            Thread.sleep(rdn.nextInt(2000));
            System.out.printf("O barbeiro terminou de cortar o cabelo do cliente %d \n", customer.getId());
            customerSemaphore.release();
        }
    }

    public void addCustomer(int n) throws InterruptedException {
        mutex.acquire();

        if (queue.size() < capacity) {
            Customer customer = new Customer();
            customer.setId(n);
            queue.add(customer);
            System.out.printf("Cliente %d chegou e sentou em uma cadeira \n", customer.getId());

            mutex.release();
            barberSemaphore.release();
        } else {
            System.out.println("Não há cadeiras disponíveis. O cliente foi embora.");
            mutex.release();
        }
    }
}
