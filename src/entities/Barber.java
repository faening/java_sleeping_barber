package entities;

public class Barber implements Runnable {
    private BarberShop barberShop;

    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        while(true) {
            try {
                // Simula o corte de cabelo
                Customer customer = barberShop.cutHair();
                Thread.sleep(2000);
                System.out.println("O barbeiro terminou de atender o cliente + " + customer.getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
