import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank implements Serializable {
  private final ArrayList<BankAccount> accountList;
  private final ArrayList<Account> accountUserList;
  private int day;

  public Bank() {
    this.day = 0;
    this.accountList = new ArrayList<>();
    this.accountUserList = new ArrayList<>();
  }

  public void createAccountList(BankAccount o) {
    accountList.add(o);
  }

  public boolean checkPersonalCode(User u) {
    for (BankAccount account : accountList) {
      if (account.getPersonalCodeBank().equals(u.getPersonalCodeUser())) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<BankAccount> getAccountList() {
    return accountList;
  }

  public void timeTravel(User u) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Day: " + day);
    System.out.println(
        "Do you want to do a time travel (y or n)? Every time travel is 30 days long.");

    char answer = scanner.next().charAt(0);

    while (answer == 'y') {
      if (this.checkPersonalCode(u)) {
        u.setPersonalWallet(100.00);

        for (BankAccount account : accountList) {
          for (Investment investment : account.getInvestmentList()) {

            if (investment.getLimit() == 0 && investment.getIsFinish()) {
              account.setPersonalBalance(investment.payment());
              investment.setIsFinish();
            }
            investment.setLimit();
          }
        }
      }
      day += 30;
      System.out.println("Day: " + day);
      System.out.println("Do you want to do another time travel (y or n)?");
      answer = scanner.next().charAt(0);
    }
  }

  public void registerUser(String username, String password, User user) {
    accountUserList.add(new Account(username, password, user));
  }

  public Account authenticateUser(String username, String password) {
    for (Account account : accountUserList) {
      if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
        return account;
      }
    }
    return null;
  }

  public void saveData(String nomeFile) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
      oos.writeObject(this);
      System.out.println("Dati salvati con successo.");

    } catch (IOException e) {
      System.err.println("Errore durante il salvataggio dei dati: " + e.getMessage());
    }
  }

  public static Bank uploadData(String nomeFile) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {
      Bank bank = (Bank) ois.readObject();
      System.out.println("Dati caricati con successo.");
      return bank;

    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Errore durante il caricamento dei dati: " + e.getMessage());
      return null;
    }
  }
}
