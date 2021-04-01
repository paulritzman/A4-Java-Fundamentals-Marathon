import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CashRegister {
  public static final String RECEIPTS_PATH = "./outstandingReceipts.txt";

  public static void main(String[] args) throws FileNotFoundException {
    double startingAmount = getMoneyValue("Hello what is the starting balance in your drawer?");
    System.out.println(startingAmount);

    File receiptFile = new File(RECEIPTS_PATH);
    Scanner scanFromFile = new Scanner(receiptFile);
    while (scanFromFile.hasNextLine()) {
      String currentLine = scanFromFile.nextLine();
      double currentAmount = Double.parseDouble(currentLine);
      startingAmount += currentAmount;
    }
    System.out.println(startingAmount);

    double customerOrder = getMoneyValue("How much is the customers order?");
    double cashReceived = getMoneyValue("Amount of cash received:");
    System.out.println(cashReceived);

    while (customerOrder > cashReceived) {
      cashReceived = getMoneyValue("Not enough money! Please, enter more");
    }

    double change = cashReceived - customerOrder;

    while (change > startingAmount || change < 0) {
      if (change > 0) {
        cashReceived = getMoneyValue("Please provide money closer to the amount due.");
      } else {
        cashReceived = getMoneyValue("Not enough mula!");
      }
      change = cashReceived - customerOrder;
    }

    System.out.println(change);
    if (change > 0) {
      System.out.println("here's your change " + change);
    } else {
      System.out.println("you get a gold star for having exact change!");
    }
  }

  public static double getMoneyValue(String prompt) {
    System.out.println(prompt);
    Scanner moneyScanner = new Scanner(System.in);
    String userInput = moneyScanner.nextLine();
    while (userInput.isEmpty()) {
      System.out.println("Invalid input: " + prompt);
      userInput = moneyScanner.nextLine();
    }
    double moneyValue = Double.parseDouble(userInput);
    return moneyValue;
  }
}