package com.pluralsight.filemanger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReceiptFileManager {

    public static String loadReceipt(String fileName) {
        StringBuilder receipt = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                receipt.append(input).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
        }
        return receipt.toString();
    }

    public static void saveReceipt(String fileName, String receipt) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(receipt);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error Writing to File");
        }
    }

    public static String getFileName() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        return "Receipts/" + today.format(dateFormatter) + "-" + now.format(timeFormatter) + ".txt";
    }
}
