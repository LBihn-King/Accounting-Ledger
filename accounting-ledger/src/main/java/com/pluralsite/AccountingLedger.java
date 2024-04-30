package com.pluralsite;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedger {
    static Scanner scanner = new Scanner(System.in);
    static String file = "transactions.csv";
    static char selectedChar;
    static ArrayList<Transaction> transactions= new ArrayList<>();


    public static void main(String[] args) {
        home();
    }

    public static void home() {
        System.out.println("Home: \n\t" +
                "D) Add Deposit\n\t" +
                "P) Make Payment\n\t" +
                "L) Ledger\n\t" +
                "X) Exit");
        selectedChar = scanner.next().charAt(0);
        switch (selectedChar) {
            case 'D':
                addDeposit();
                break;
            case 'P':
                getPaymentInformation();
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

    private static void getPaymentInformation() {
        try {
            BufferedWriter bufWrite = new BufferedWriter(new FileWriter("paymentinformation.csv"));
            Debit debit = new Debit();
            System.out.println("Enter payment information:");
            System.out.println("Card Number: ");
            debit.setCardNumber(scanner.nextInt());
            System.out.println("Expiration: ");
            scanner.nextLine();
            debit.setExpirationDate(scanner.nextLine());
            System.out.println("Security code: ");
            debit.setSecurityCode(scanner.nextShort());
            bufWrite.write(String.valueOf(debit));
            bufWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makePayment() {

    }

    public static void ledgerScreen() {
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

    public static void displayAll() {
        readFromFile();
    }

    public static void displayDeposits() {

    }

    public static void displayPayments() {

    }

    public static void reportScreen() {
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

    public static void monthToDate() {

    }

    public static void previousMonth() {

    }

    public static void yearToDate() {

    }

    public static void previousYear() {

    }

    public static void searchByVendor() {

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

        public static void writeToFile(String action) {
            // Get the local date and time
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            String entry = (date + "|" + time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "|" + action + "\n");

            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ledger.csv", true));
                // Write the date, time and action to the ledger.csv file
                bufferedWriter.write(entry);
                System.out.println("Entry recorded");

                bufferedWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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