package com.hust.kstn.models;

public class Store {
    private static final int MAX_NUMBERS_STORED = 100;
    DigitalVideoDisc itemsInStore[] = new DigitalVideoDisc[MAX_NUMBERS_STORED]; 
    private int storeSize;
    public Store() {
        storeSize = 0;
    }

    public void addDVD(DigitalVideoDisc disc) {
        if (storeSize == MAX_NUMBERS_STORED) {
            System.out.println("The store is almost full");
            return;
        } 

        itemsInStore[storeSize++] = disc;
        System.out.println("The disc has been added successfully");
    }

    public void removeDVD(DigitalVideoDisc disc) {
        if (storeSize == 0) {
            System.out.println("The store is empty");
            return;
        }

        DigitalVideoDisc[] newArray = new DigitalVideoDisc[MAX_NUMBERS_STORED];
        int cnt = 0;
        for (int i = 0; i < storeSize; i++) {
            if (itemsInStore[i] == disc) {
                continue;
            }

            newArray[cnt++] = itemsInStore[i];
        }

        storeSize = cnt;
        itemsInStore = newArray;
        System.out.println("The disc has been removed successfully");
    }
}
