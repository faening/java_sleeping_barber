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
                barberShop.cutHair();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
