package com.hust.kstn.test;

import com.hust.kstn.models.Store;
import com.hust.kstn.models.DigitalVideoDisc;

public class StoreTest {
    public static void main(String[] args) {
        Store store = new Store();
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("DoMixi funny moments", 
        "Comedy", 36.36, "DoMixi", 360);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Anhtoi xin loi khan gia", 
        "Tragedy", 0.63, "Anhtoi", 63);

        store.addDVD(dvd1);
        store.addDVD(dvd2);
        store.removeDVD(dvd2);
        store.removeDVD(dvd1);
        store.removeDVD(dvd1);
        for (int i = 0; i < 101; i++) {
            String randomTitle = Integer.toString(i);
            DigitalVideoDisc newDisc = new DigitalVideoDisc(randomTitle, 
            "Category" + randomTitle, i + 0.99, "Director" + randomTitle, i * 10);
            store.addDVD(newDisc);
        }

    }
}
