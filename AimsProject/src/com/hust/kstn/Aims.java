package com.hust.kstn;

public class Aims {
    public static void main(String args[]) {
        Cart cart = new Cart();
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("DoMixi funny moments", 
        "Comedy", "DoMixi", 360, 36.36);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Anhtoi xin loi khan gia", 
        "Tragedy", "Anhtoi", 63, 0.63);

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
            DigitalVideoDisc newDisc = new DigitalVideoDisc(randomTitle);
            cart.addDVD(newDisc);
        }
    }
}
