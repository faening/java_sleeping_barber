package entities;

import java.util.Random;

public class Customer implements Runnable {
    private int id;
    private BarberShop barberShop;
    public Customer(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    public Customer(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int i = 0;
        Random rdn = new Random();

        while(true) {
            try {
                // Simula o intervalo entre a chagada de novos clientes
                barberShop.addCustomerOnQueue(new Customer(i));
                Thread.sleep(rdn.nextInt(3000));
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId() {
        return id;
    }
}
