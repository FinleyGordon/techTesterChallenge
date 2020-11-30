package vender;

import vender.drinks.Drink;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.Main.init;

public class VendingMachine {

    private Drink selectedDrink;
    private final List<Integer> acceptedCoins = Arrays.asList(1,5,10,25);
    private List<Drink> drinksSold = new ArrayList<>();
    private int resetCode;
    private int coinsCollected = 0;
    private List<Drink> availableDrinks;
    BufferedReader reader;

    public VendingMachine(List<Drink> availableDrinks, int resetCode, BufferedReader reader) {
        System.out.println("*** Welcome to the Vending Machine 3000 *** ");
        this.availableDrinks = availableDrinks;
        this.resetCode = resetCode;
        this.reader = reader;
    }

    public void displayMenu() throws IOException {
        System.out.println("Here are the available drink: ");
        for(int i = 1; i < availableDrinks.size() + 1; i++) {
            Drink drink = availableDrinks.get(i -1);
            System.out.printf("%d. %s - %dp%n", i, drink.getName(), drink.getPrice());
        }

        System.out.print("Please enter the number of the drink you'd like to select:");
        readDrinkSelection();
    }

    private void readDrinkSelection() throws IOException {
        try {
            int selection = Integer.parseInt(reader.readLine());
            if (selection == getResetCode()) {
                resetVendingMachine();
            } else {
                setSelectedDrink(availableDrinks.get(selection - 1));
                System.out.println("You have selected: " + getSelectedDrink().getName());
                requestPayment();
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("\n**Please enter a valid number**\n");
            displayMenu();
        }
    }

    private void requestPayment() throws IOException {
        System.out.println("If you would like to change your selection or to get a refund please enter -1");
        int moneyReceived = 0;
        int cost = getSelectedDrink().getPrice();
        while(moneyReceived < cost) {
            System.out.printf("Please insert %dp:", cost - moneyReceived);
            int coin = Integer.parseInt(reader.readLine());
            if (acceptedCoins.contains(coin)) {
                System.out.printf("%dp received%n", coin);
                moneyReceived += coin;
            } else if (coin == -1) {
                System.out.printf("You have been refunded %dp %n", moneyReceived);
                displayMenu();
            } else {
                System.out.println("Please enter a valid coin: " + acceptedCoins.toString());
            }
        }
        System.out.printf("Dispensing %s...%n", getSelectedDrink().getName());
        sellDrink(getSelectedDrink());

        if(moneyReceived > cost) {
            System.out.printf("Dispensing extra change... %dp returned%n", moneyReceived - cost);
        }
        displayMenu();
    }

    public void resetVendingMachine() throws IOException {
        System.out.println("-------Reset initiated-----------");
        System.out.println("Drinks sold: " + getDrinksSold().stream().collect(Collectors.groupingBy(Drink::getName, Collectors.counting())));
        System.out.printf("Dispensing total money collected... %sp returned%n", getCoinsCollected());
        resetDrinksSold();
        resetCoinsCollected();
        init();
    }

    public void sellDrink(Drink drink) {
        coinsCollected += drink.getPrice();
        drinksSold.add(drink);
    }

    public Drink getSelectedDrink() {
        return selectedDrink;
    }

    public void setSelectedDrink(Drink selectedDrink) {
        this.selectedDrink = selectedDrink;
    }

    public List<Integer> getAcceptedCoins() {
        return acceptedCoins;
    }

    public List<Drink> getDrinksSold() {
        return drinksSold;
    }

    public void resetDrinksSold() {
        drinksSold = new ArrayList<>();
    }

    public int getResetCode() {
        return resetCode;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public void resetCoinsCollected() {
        coinsCollected = 0;
    }

    public List<Drink> getAvailableDrinks() {
        return availableDrinks;
    }

}
