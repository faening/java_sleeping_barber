package entities;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShop {
    private BlockingQueue<Customer> queue;
    private final int CAPACITY;
    private ReentrantLock lock;
    private Condition sleepingBarber;
    Random rdn = new Random();

    public BarberShop(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        this.queue = new LinkedBlockingQueue<>(CAPACITY);
        lock = new ReentrantLock();
        sleepingBarber = lock.newCondition();
    }

    public Customer cutHair() throws InterruptedException {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                System.out.println("O barbeiro está dormindo...");
                sleepingBarber.await();
            }

            Customer customer = queue.poll();
            System.out.println("O barbeiro está atendendo o cliente " + customer.getId());
            return customer;
        } finally {
            lock.unlock();
        }
    }

    public void addCustomerOnQueue(Customer customer) throws InterruptedException {
        lock.lock();

        try {
            if (queue.offer(customer)) {
                System.out.println("O cliente " + customer.getId() + " entrou na fila de espera");
                if (lock.hasWaiters(sleepingBarber)) {
                    sleepingBarber.signal();
                }
            } else {
                System.out.println("A fila está cheia. O cliente " + customer.getId() + " foi embora");
            }
        } finally {
            lock.unlock();
        }
    }
}
