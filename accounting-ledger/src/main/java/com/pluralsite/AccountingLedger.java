package com.pluralsite;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AccountingLedger {
    static Scanner scanner = new Scanner(System.in);
    static String file = "transactions.csv";
    static char selectedChar;
    static ArrayList<Transaction> transactionsList = new ArrayList<>();
    static LocalDate date = LocalDate.now();
    static DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static String dmy = (date.format(currentDate));
    static String[] timePeriod = dmy.split("-");

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
            case ('D' | 'd'):
                makeDeposit();
                break;
            case ('P' | 'p'):
                newDebit();
                makePayment();
                break;
            case ('L' | 'l'):
                ledgerScreen();
                break;
            case ('X' | 'x'):
                System.out.println("Goodbye");
                return;
            default:
                System.out.println("Invalid input");
                home();
                break;
        }
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
            case ('A' | 'a'):
                displayAll();
                break;
            case ('D' | 'd'):
                displayDeposits();
                break;
            case ('P' | 'p'):
                displayPayments();
                break;
            case ('R' | 'p'):
                reportScreen();
                break;
            case ('H' | 'h'):
                home();
                break;
            default:
                System.out.println("Invalid input");
                ledgerScreen();
                break;
        }
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
                ledgerScreen();
                break;
            default:
                System.out.println("Invalid input");
                reportScreen();
                break;
        }
    }

    private static void makeDeposit() {
        System.out.println("Enter deposit details:");
        Transaction deposit = new Transaction();
        newTransaction(deposit);
        try {
            BufferedWriter bufWrite = new BufferedWriter(new FileWriter(file, true));
            bufWrite.write((deposit)+"\n");
            bufWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        home();
    }

    public static void makePayment() {
        System.out.println("Enter payment details:");
        Transaction payment = new Transaction();
        newTransaction(payment);
        payment.setAmount(-payment.getAmount());
        try {
            BufferedWriter bufWrite = new BufferedWriter(new FileWriter(file, true));
            bufWrite.write((payment)+"\n");
            bufWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        home();
    }

    public static void displayAll() {
        readFromFile();
        Iterator<Transaction> transactionIterator = transactionsList.iterator();
        String output;
        while (transactionIterator.hasNext()) {
            output = String.valueOf(transactionIterator.next());
            System.out.println(output);
        }
        ledgerScreen();
    }

    public static void displayDeposits() {
        readFromFile();
        for (Transaction transaction : transactionsList) {
            if (transaction.getAmount() >= 0) {
                System.out.println(transaction);
            }
        }
        ledgerScreen();
    }

    public static void displayPayments() {
        readFromFile();
        for (Transaction transaction : transactionsList) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
        ledgerScreen();
    }

    public static void monthToDate() {
        readFromFile();
        Iterator<Transaction> transactionIterator = transactionsList.iterator();
        String output;
        while (transactionIterator.hasNext()) {
            output = String.valueOf(transactionIterator.next());
            String[] tokens = output.split("\\|");
            String[] dateMarkers = tokens[0].split("-");
            if (dateMarkers[2].equals(timePeriod[2])) {
                System.out.println(output);
            }
        }
        reportScreen();
    }

    public static void previousMonth() {
        readFromFile();
        Iterator<Transaction> transactionIterator = transactionsList.iterator();
        String output;
        while (transactionIterator.hasNext()) {
            output = String.valueOf(transactionIterator.next());
            String[] tokens = output.split("\\|");
            String[] dateMarkers = tokens[0].split("-");
            int currentMonth = Integer.parseInt((dateMarkers[2]));
            int prevMonth = Integer.parseInt(timePeriod[2]) - 1;
            if (currentMonth == prevMonth) {
                System.out.println(output);
            }
        }
        reportScreen();
    }

    public static void yearToDate() {
        readFromFile();
        Iterator<Transaction> transactionIterator = transactionsList.iterator();
        String output;
        while (transactionIterator.hasNext()) {
            output = String.valueOf(transactionIterator.next());
            String[] tokens = output.split("\\|");
            String[] dateMarkers = tokens[0].split("-");
            if (dateMarkers[1].equals(timePeriod[1])) {
                System.out.println(output);
            }
        }
        reportScreen();
    }

    public static void previousYear() {
        readFromFile();
        Iterator<Transaction> transactionIterator = transactionsList.iterator();
        String output;
        while (transactionIterator.hasNext()) {
            output = String.valueOf(transactionIterator.next());
            String[] tokens = output.split("\\|");
            String[] dateMarkers = tokens[0].split("-");
            int currentMonth = Integer.parseInt((dateMarkers[1]));
            int prevMonth = Integer.parseInt(timePeriod[1]) - 1;
            if (currentMonth == prevMonth) {
                System.out.println(output);
            }
        }
        reportScreen();
    }

    public static void searchByVendor() {
        System.out.println("Enter vendor name: ");
        String search = scanner.nextLine();
        scanner.nextLine();
        readFromFile();
        for (Transaction transaction : transactionsList) {
            if (transaction.getVendor().equals(search)) {
                System.out.println(transaction);
            }
        }
        reportScreen();
    }

    public static void readFromFile() {
        // Try to open the file
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(file)); // create buffered reader with input file
            String input; // create string to hold input
            boolean header = false; //create boolean that decides if header should be skipped
            while ((input = bfr.readLine()) != null) {
                if (!header) { //
                    header = true;
                    continue; //skips header
                }
                parseTransaction(input); // Populate the transaction
            }
            bfr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseTransaction(String input) {
        String[] tokens = input.split("\\|"); // split the input

        String date = tokens[0]; // parse the tokens
        String time = tokens[1];
        String description = tokens[2];
        String vendor = tokens[3];
        double amount = Double.parseDouble(tokens[4]);

        Transaction transaction = new Transaction(date, time, description, vendor, amount); // creates the transaction
        transactionsList.add(transaction); // adds transaction to array list
    }

    private static void newTransaction(Transaction transaction) { // sets values of new transactions
        transaction.currentDate();
        transaction.currentTime();
        scanner.nextLine();

        System.out.println("Description: ");
        transaction.setDescription(scanner.nextLine());

        System.out.println("Vendor: ");
        transaction.setVendor(scanner.nextLine());

        System.out.println("Amount: ");
        transaction.setAmount(scanner.nextDouble());
    }

    private static void newDebit() { //sets values of new Debit when called and writes it to a file
        try {
            BufferedWriter bufWrite = new BufferedWriter(new FileWriter("paymentinformation.csv")); //creates buffered writer and designates file destination
            Debit debit = new Debit();
            System.out.println("Enter debit information:");
            
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
}