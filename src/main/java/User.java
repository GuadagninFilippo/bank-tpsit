import java.util.Scanner;

public class User {
  private final String personalCodeUser;
  private double personalWallet;
  private final String username;

  public User(String pcu, String username) {
    this.personalCodeUser = pcu;
    this.personalWallet = 100;
    this.username = username;
  }

  public String getPersonalCodeUser() {
    return personalCodeUser;
  }

  public double getPersonalWallet() {
    return personalWallet;
  }

  public String getUsername() {
    return username;
  }

  public void deposit(Bank b) {
    if (b.checkPersonalCode(this)) {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Your wallet: " + personalWallet);
      System.out.println("How much money do you want to deposit?");

      double money = scanner.nextDouble();

      if (money > personalWallet) {
        System.out.println("You have less money in your wallet");
      } else {
        for (BankAccount account : b.getAccountList()) {
          if (account.getPersonalCodeBank().equals(personalCodeUser)) {
            personalWallet -= money;
            account.deposit(money);
          }
        }
      }
    }
  }

  public void withdraw(Bank b) {
    if (b.checkPersonalCode(this)) {
      Scanner scanner = new Scanner(System.in);
      BankAccount userAccount = null;

      for (BankAccount account : b.getAccountList()) {
        if (account.getPersonalCodeBank().equals(personalCodeUser)) {
          userAccount = account;
          break;
        }
      }

      if (userAccount != null) {
        System.out.println("In your bank account: " + userAccount.getPersonalBalance());
        System.out.println("How much money do you want to withdraw?");
        double money = scanner.nextDouble();

        if (money > userAccount.getPersonalBalance()) {
          System.out.println("You have less money in your personal balance");
        } else {
          personalWallet += money;
          userAccount.withdraw(money);
        }
      }
    }
  }

  public void lookWallet() {
    System.out.println("In your wallet there are: " + personalWallet + "$.");
  }

  public void lookPersonalBalance(Bank b) {
    if (b.checkPersonalCode(this)) {
      for (BankAccount account : b.getAccountList()) {
        if (account.getPersonalCodeBank().equals(personalCodeUser)) {
          System.out.println(
              "In your bank account there are: " + account.getPersonalBalance() + "$.");
          break;
        }
      }
    }
  }

  public void setPersonalWallet(double w) {
    personalWallet += w;
  }
}
