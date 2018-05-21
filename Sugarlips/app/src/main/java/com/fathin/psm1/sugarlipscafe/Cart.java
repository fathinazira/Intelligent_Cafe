package com.fathin.psm1.sugarlipscafe;

/**
 * Created by user-pc on 5/19/2018.
 */

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static List<Item> items = new ArrayList<Item>();

    public static void insert(Item item) {
        items.add(item);

    }

    public static void update(Product product) {
        int index = getIndex(product);
        int quantity = items.get(index).getQuantity() + 1;
        items.get(index).setQuantity(quantity);
    }

    public static void remove(Product product) {
        int index = getIndex(product);
        items.remove(index);
    }

    public static List<Item> contents() {
        return items;
    }

    public static double total() {
        double s = 0;
        for(Item item : items) {
            s += item.getProduct().getProduct_price() * item.getQuantity();
        }
        return s;
    }

    public static double calories() {
        double s = 0;
        for(Item item : items) {
            s += item.getProduct().getProduct_calories() * item.getQuantity();
        }
        return s;
    }

    public static boolean isExists(Product product) {
        return getIndex(product) != -1;
    }

    private static int getIndex(Product product) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getProduct_id() == product.getProduct_id()) {
                return i;
            }
        }
        return -1;
    }
}