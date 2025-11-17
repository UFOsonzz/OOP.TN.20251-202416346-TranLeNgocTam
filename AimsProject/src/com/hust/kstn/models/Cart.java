package com.hust.kstn.models;

public class Cart {
    private static final int MAX_NUMBERS_ORDEREDED = 20;
    private DigitalVideoDisc[] itemsInCart = new DigitalVideoDisc[MAX_NUMBERS_ORDEREDED];
    private int cartSize;

    public Cart() {
        cartSize = 0;
    }

    public int qtyOrdered() {
        return cartSize;
    }

    public void addDVD(DigitalVideoDisc disc) {
        if (cartSize == MAX_NUMBERS_ORDEREDED) {
            System.out.println("The cart is almost full");
            return;
        } 

        itemsInCart[cartSize++] = disc;
        System.out.println("The disc has been added successfully");
    }

    public void addDVD(DigitalVideoDisc disc1, DigitalVideoDisc disc2) {
        for (int i = 1; i <= 2; i++) {
            if (cartSize == MAX_NUMBERS_ORDEREDED) {
                System.out.println("The cart is almost full");
                return;
            } else {
                itemsInCart[cartSize++] = (i == 1 ? disc1 : disc2);
                System.out.println("The disc has been added successfully");
            }
        }
    }

    public void addDVDList(DigitalVideoDisc discs[]) {
        for (int i = 1; i <= discs.length; i++) {
            if (cartSize == MAX_NUMBERS_ORDEREDED) {
                System.out.println("The cart is almost full");
                return;
            } else {
                itemsInCart[cartSize++] = discs[i - 1];
                System.out.println("The disc has been added successfully");
            }
        }
    }

    public void addDVD(DigitalVideoDisc... discs) {
        for (DigitalVideoDisc disc : discs) {
            if (cartSize == MAX_NUMBERS_ORDEREDED) {
                System.out.println("The cart is almost full");
                return;
            } else {
                itemsInCart[cartSize++] = disc;
                System.out.println("The disc has been added successfully");
            }
        }
    }

    public void removeDVD(DigitalVideoDisc disc) {
        if (cartSize == 0) {
            System.out.println("The cart is empty");
            return;
        }

        DigitalVideoDisc[] newArray = new DigitalVideoDisc[MAX_NUMBERS_ORDEREDED];
        int cnt = 0;
        for (int i = 0; i < cartSize; i++) {
            if (itemsInCart[i] == disc) {
                continue;
            }

            newArray[cnt++] = itemsInCart[i];
        }

        cartSize = cnt;
        itemsInCart = newArray;
        System.out.println("The disc has been removed successfully");
    }

    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (int i = 0; i < cartSize; i++)
            totalCost += itemsInCart[i].getCost();
        return totalCost;
    }

    public void print() {
        System.out.println("==========================THE CURRENT CART==========================");
        System.out.println("Total items: " + "[" + Integer.toString(cartSize) + "]");
        double subTotal = 0.0;
        for (int i = 0; i < cartSize; i++) {
            subTotal += itemsInCart[i].getCost();
            System.out.println(itemsInCart[i].toString());
        }
        System.out.println("Subtotal: " + "[" + Double.toString(subTotal) + "]$");
        System.out.println("====================================================================");
    }

}
