import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Bank {
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

    String answer;
    while (true) {
      answer = scanner.nextLine().toLowerCase();

      if (answer.equals("y")) {
        if (this.checkPersonalCode(u)) {
          u.setPersonalWallet(100.00);

          for (BankAccount account : accountList) {
            for (Investment investment : account.getInvestmentList()) {
              if (investment.getLimit() == 0 && investment.getIsFinish()) {
                double investmentResult = investment.payment(); // risultato dell'investimento
                account.setPersonalBalance(account.getPersonalBalance() + investmentResult);
                investment.setIsFinish();
                Date currentDate = new Date();
                Transaction transaction =
                    new Transaction(
                        currentDate,
                        investmentResult,
                        "Investment Payment",
                        "Investment completed");
              }
              investment.setLimit();
            }
          }
        }
        day += 30;
        System.out.println("Day: " + day);
        System.out.println("Do you want to do another time travel (y or n)?");
      } else if (answer.equals("n")) {
        break; // Esci dal ciclo se l'utente inserisce 'n'
      } else {
        System.out.println("Invalid input. Please enter 'y' or 'n'.");
        System.out.println(
            "Do you want to do a time travel (y or n)? Every time travel is 30 days long.");
      }
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
    try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile))) {
      for (Account account : accountUserList) {
        User user = account.getUser();
        writer.println(
            user.getPersonalCodeUser()
                + ","
                + user.getUsername()
                + ","
                + account.getUsername()
                + ","
                + account.getPassword());
        for (BankAccount bankAccount : accountList) {
          if (bankAccount.getPersonalCodeBank().equals(user.getPersonalCodeUser())) {
            writer.println(
                "Account,"
                    + bankAccount.getPersonalCodeBank()
                    + ","
                    + bankAccount.getPersonalBalance());
            for (Transaction transaction : bankAccount.getTransactionHistory()) {
              writer.println("Transaction," + transaction.toString());
            }
            for (Investment investment : bankAccount.getInvestmentList()) {
              writer.println("Investment," + investment.toString());
            }
            break;
          }
        }
      }
      System.out.println("Data saved successfully.");
    } catch (IOException e) {
      System.err.println("Error saving data: " + e.getMessage());
    }
  }

  public static Bank uploadData(String nomeFile) {
    Bank bank = new Bank();
    try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
      String line;
      User currentUser = null;
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 4
            && !parts[0].equals("Account")
            && !parts[0].equals("Transaction")
            && !parts[0].equals("Investment")) {
          String personalCode = parts[0];
          String usernameUser = parts[1];
          String usernameAccount = parts[2];
          String password = parts[3];

          User user = new User(personalCode, usernameUser);
          bank.registerUser(usernameAccount, password, user);
          currentUser = user;

        } else if (parts.length >= 3 && parts[0].equals("Account")) {

          if (currentUser != null) {
            String personalCode = parts[1];
            double personalBalance = Double.parseDouble(parts[2]);

            BankAccount bankAccount = new BankAccount(personalCode, currentUser.getUsername());
            bankAccount.setPersonalBalance(personalBalance);
            bank.accountList.add(bankAccount);

          } else {
            System.err.println("Error: 'Account' line found before valid user.");
          }
        } else if (parts.length >= 4 && parts[0].equals("Transaction")) {
          try {
            Date date = formatter.parse(parts[1]); // analizza la stringa di data
            double amount = Double.parseDouble(parts[2]);
            String type = parts[3];
            String description = parts[4];

            Transaction transaction = new Transaction(date, amount, type, description);

            for (BankAccount account : bank.accountList) {
              if (account.getPersonalCodeBank().equals(currentUser.getPersonalCodeUser())) {
                account.getTransactionHistory().add(transaction);
                break;
              }
            }
          } catch (ParseException e) {
            System.err.println("Error parsing data:" + e.getMessage());
          }
        } else if (parts.length >= 3 && parts[0].equals("Investment")) {
          double qInvest = Double.parseDouble(parts[1]);
          int duration = Integer.parseInt(parts[2]);
          int risk = Integer.parseInt(parts[3]);

          Investment investment = new Investment(qInvest, duration, risk);

          for (BankAccount account : bank.accountList) {
            if (account.getPersonalCodeBank().equals(currentUser.getPersonalCodeUser())) {
              account.getInvestmentList().add(investment);
              break;
            }
          }
        }
      }
      System.out.println("Data saved successfully.");
      return bank;
    } catch (IOException e) {
      System.err.println("Error loading data:" + e.getMessage());
      return null;
    }
  }
}
