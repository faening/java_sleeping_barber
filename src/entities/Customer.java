package entities;

import java.util.Random;

public class Customer implements Runnable {
    private int id;
    private BarberShop barberShop;
    public Customer(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    public Customer() { }

    @Override
    public void run() {
        int i = 0;
        Random rdn = new Random();

        while(true) {
            try {
                barberShop.addCustomer(i);
                i++;
                Thread.sleep(rdn.nextInt(3000));  // Simular o intervalo entre a chegada de novos clientes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
