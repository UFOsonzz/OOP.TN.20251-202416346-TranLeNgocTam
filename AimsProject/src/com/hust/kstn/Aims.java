package com.hust.kstn;
import com.hust.kstn.models.Cart;
import com.hust.kstn.models.DigitalVideoDisc;
public class Aims {
    public static void main(String args[]) {
        Cart cart = new Cart();
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("DoMixi funny moments", 
        "Comedy", 36.36, "DoMixi", 360);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Anhtoi xin loi khan gia", 
        "Tragedy", 0.63, "Anhtoi", 63);

        cart.addDVD(dvd1);
        cart.addDVD(dvd2);
        cart.print();
        System.out.println("Total cost: " + cart.calculateTotalCost());
        cart.removeDVD(dvd2);
        cart.print();
        System.out.println("Total cost: " + cart.calculateTotalCost());
        cart.removeDVD(dvd2);
        cart.removeDVD(dvd1);
        cart.removeDVD(dvd1);
        for (int i = 0; i < 21; i++) {
            String randomTitle = Integer.toString(i);
            DigitalVideoDisc newDisc = new DigitalVideoDisc(randomTitle, 
            "Category" + randomTitle, i + 0.99, "Director" + randomTitle, i * 10);
            cart.addDVD(newDisc);
        }
    }
}
