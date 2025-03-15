import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class BankAccount implements Serializable {
  private String personalCodeBank;
  private double personalBalance;
  private ArrayList<Investment> investmentList;
  private boolean checkForInvestmentList;
  private String username;
  private ArrayList<Transaction> transactionHistory;

  public BankAccount(String pC, String username) {
    this.personalCodeBank = pC;
    this.personalBalance = 0;
    this.checkForInvestmentList = false;
    this.investmentList = new ArrayList<>();
    this.username = username;
    this.transactionHistory = new ArrayList<>();
  }

  public String getPersonalCodeBank() {
    return personalCodeBank;
  }

  public void setPersonalBalance(double z) {
    personalBalance += z;
  }

  public double getPersonalBalance() {
    return personalBalance;
  }

  public boolean invest(double amount, String duration, String risk) {
    if (amount <= 0 || amount > personalBalance) {
      return false; //  fallito
    }

    int durationValue = 0;
    if (duration.equalsIgnoreCase("Short")) {
      durationValue = 0;
    } else if (duration.equalsIgnoreCase("Medium")) {
      durationValue = 1;
    } else if (duration.equalsIgnoreCase("Long")) {
      durationValue = 2;
    } else {
      return false; //  durata non valida
    }

    int riskValue = 0;
    if (risk.equalsIgnoreCase("Low")) {
      riskValue = 0;
    } else if (risk.equalsIgnoreCase("Medium")) {
      riskValue = 1;
    } else if (risk.equalsIgnoreCase("High")) {
      riskValue = 2;
    } else {
      return false; // rischio non valido
    }

    Investment investment = new Investment(amount, durationValue, riskValue);
    double result = investment.payment();
    personalBalance = personalBalance - amount + result;

    return true; // ok
  }

  public void createInvestmentList(Investment i) {
    this.setCheckForInvestmentList(i);
    if (this.checkForInvestmentList) {
      personalBalance -= i.getQInvest();
      investmentList.add(i);
    } else {
      System.out.println(
          "You can't invest, your personal balance is lower than the money in your investment.");
      System.out.println("Deposit funds to invest.");
    }
  }

  public ArrayList<Investment> getInvestmentList() {
    return investmentList;
  }

  private void setCheckForInvestmentList(Investment i) {
    this.checkForInvestmentList = personalBalance >= i.getQInvest();
  }

  public void setPersonalBalance(double z, String type, String description) {
    double oldBalance = this.personalBalance;
    this.personalBalance += z;
    transactionHistory.add(
        new Transaction(
            new Date(),
            z,
            type,
            description + " Old balance: " + oldBalance + " New balance: " + this.personalBalance));
  }

  public void deposit(double amount) {
    setPersonalBalance(amount, "Deposit", "Deposit of " + amount);
  }

  public void withdraw(double amount) {
    setPersonalBalance(-amount, "Withdrawal", "Withdrawal of " + amount);
  }

  public ArrayList<Transaction> getTransactionHistory() {
    return transactionHistory;
  }

  public void printInvestmentStatus() {
    int counter = 1;
    for (Investment inv : investmentList) {
      System.out.println("***********************");
      System.out.println(counter);
      counter++;
      System.out.println("Total investment: " + inv.getQInvest());
      System.out.println("Duration: " + inv.getDuration());
      System.out.println("Risk: " + inv.getRisk());
      System.out.println("Result: " + inv.getResult());
      if (inv.getLimit() < 0) {
        System.out.println("This investment is finished.");
      } else {
        System.out.println("Finishes in: " + inv.getLimit() + " month(s)");
      }
    }
  }
}
