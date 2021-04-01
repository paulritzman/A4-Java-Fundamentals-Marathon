import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Scanner;

public class CashRegister {
  public static final String RECEIPTS_PATH = "./outstandingReceipts.txt";
  public static final String PROMPT_INITIAL_REGISTER_AMOUNT = "Hello what is the starting balance in your drawer?";
  public static final String PROMPT_ORDER_COST_AMOUNT = "How much is the customers order?";
  public static final String PROMPT_PAYMENT_AMOUNT = "Amount of cash received:";
  public static final String PROMPT_INSUFFICIENT_PAYMENT = "Not enough money! Please, enter more.";
  public static final String PROMPT_PAYMENT_TOO_LARGE_TO_HANDLE = "Please provide money closer to the amount due.";

  public static void main(String[] args) throws FileNotFoundException {
    double startingAmount = 0.0, customerOrder = 0.0, cashReceived = 0.0, change = 0.0;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    startingAmount = getMoneyValue(PROMPT_INITIAL_REGISTER_AMOUNT);
    startingAmount += readReceipts();
    System.out.println("Starting amount in drawer: " + formatter.format(startingAmount));

    customerOrder = getMoneyValue(PROMPT_ORDER_COST_AMOUNT);
    cashReceived = getMoneyValue(PROMPT_PAYMENT_AMOUNT);
    change = cashReceived - customerOrder;

    while (cashReceived < customerOrder || change > startingAmount) {
      if (cashReceived < customerOrder) {
        cashReceived = getMoneyValue(PROMPT_INSUFFICIENT_PAYMENT);
      } else {
        cashReceived = getMoneyValue(PROMPT_PAYMENT_TOO_LARGE_TO_HANDLE);
      }

      change = cashReceived - customerOrder;
    }

    if (change > 0) {
      System.out.println("Here's your change: " + formatter.format(change));
    } else {
      System.out.println("You get a gold star for having exact change!");
    }

    startingAmount -= change;
    System.out.println("Amount left in register: " + formatter.format(startingAmount));
  }

  private static double readReceipts() throws FileNotFoundException {
    double totalAmount = 0.0, currentAmount = 0.0;
    String currentLine = "";

    File receiptFile = new File(RECEIPTS_PATH);
    Scanner scanFromFile = new Scanner(receiptFile);

    while (scanFromFile.hasNextLine()) {
      currentLine = scanFromFile.nextLine();
      currentAmount = Double.parseDouble(currentLine);
      totalAmount += currentAmount;
    }

    scanFromFile.close();
    return totalAmount;
  }

  private static double getMoneyValue(String prompt) {
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