import entities.Barber;
import entities.BarberShop;
import entities.Customer;

public class Main {
    public static void main(String[] args) {
        BarberShop barberShop = new BarberShop(5);
        Thread barberThread = new Thread(new Barber(barberShop));
        Thread customerThread = new Thread(new Customer(barberShop));

        barberThread.start();
        customerThread.start();
    }
}