package com;

import vender.VendingMachine;
import vender.drinks.Coke;
import vender.drinks.Pepsi;
import vender.drinks.Soda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        init();
    }
    private static void inputError() throws IOException {
        System.out.println("Please enter a valid number (1 or 2)");
        init();
    }

    public static void init() throws IOException {
        System.out.println("Please enter the corresponding number of the program you wish to use:" +
                "\n 1. Vending Machine 3000 " +
                "\n 2. API automation");
        int selection = 0;
        try {
            selection = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            inputError();
        }

        if( selection == 1) {
            VendingMachine vendingMachine = new VendingMachine(Arrays.asList(new Coke(), new Pepsi(), new Soda()), 12345, new BufferedReader(new InputStreamReader(System.in)));
            vendingMachine.displayMenu();
        } else if (selection == 2) {
            System.out.println("GOT HERE");
        } else {
            inputError();
        }
    }

}
