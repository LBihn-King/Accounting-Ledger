package com.pluralsite;

import java.io.*;
import java.util.Scanner;

public class AccountingLedger {
    static Scanner scanner = new Scanner(System.in);
    static String file = "transactions.csv";
    static char selectedChar;



    public static void main(String[] args) {
        home();
    }

    public static void home() {
        System.out.println("Home: \n\t" +
                "D) Add Deposit\n\t" +
                "P) Make Payment\n\t" +
                "L) Ledger" +
                "X) Exit");
        selectedChar = scanner.next().charAt(0);
        switch (selectedChar) {
            case 'D':
                addDeposit();
                break;
            case 'P':
                makePayment();
                break;
            case 'L':
                ledgerScreen();
                break;
            case 'X':
                System.out.println("Goodbye");
                return;
            default:
                System.out.println("Invalid input");
                home();
                break;

        }
    }

    private static void addDeposit() {

    }

    private static void makePayment() {

    }

    private static void ledgerScreen() {
        System.out.println("Ledger: \n\t" +
                "A) All\n\t" +
                "D) Deposits\n\t" +
                "P) Payments\n\t" +
                "R) Reports\n\t" +
                "H) Home");
        selectedChar = scanner.next().charAt(0);
        switch (selectedChar) {
            case 'A':
                displayAll();
                break;
            case 'D':
                displayDeposits();
                break;
            case 'P':
                displayPayments();
                break;
            case 'R':
                reportScreen();
                break;
            case 'H':
                home();
                break;
            default:
                System.out.println("Invalid input");
                ledgerScreen();
                break;
        }
    }

    private static void displayAll() {

    }

    private static void displayDeposits() {

    }

    private static void displayPayments() {

    }

    private static void reportScreen() {
        System.out.println("Reports: \n\t" +
                "1) Month To Date\n\t" +
                "2) Previous Month\n\t" +
                "3) Year To Date\n\t" +
                "4) Previous Year\n\t" +
                "5) Search By Vendor\n\t" +
                "0) Back");
        int selection = scanner.nextInt();
        switch (selection) {
            case 1:
                monthToDate();
                break;
            case 2:
                previousMonth();
                break;
            case 3:
                yearToDate();
                break;
            case 4:
                previousYear();
                break;
            case 5:
                searchByVendor();
                break;
            case 0:
                reportScreen();
                break;
            default:
                System.out.println("Invalid input");
                ledgerScreen();
                break;
        }
    }

    private static void monthToDate() {

    }

    private static void previousMonth() {

    }

    private static void yearToDate() {

    }

    private static void previousYear() {

    }

    private static void searchByVendor() {

    }
    public static void readFromFile() {
        // Try to open the file
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            String input;
            boolean skippedHeader = false;

            while ((input = bfr.readLine()) != null) {
                if (!skippedHeader) {
                    skippedHeader = true;
                    continue;
                }
                // Populate the transaction
                parseTransaction(input);
            }
            bfr.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseTransaction(String input) {
        // split the input
        String[] tokens = input.split("\\|");

        // Parse the tokens
        String date = tokens[0];
        String time = tokens[1];
        String description = tokens[2];
        String vendor = tokens[3];
        double amount = Double.parseDouble(tokens[4]);


        // Creates the transaction
        Transaction transaction = new Transaction(date, time, description, vendor, amount);


    }

    public static void logTransaction(Transaction transaction){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(transaction + "\n");
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}