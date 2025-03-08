import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Bank sanPaolo = Bank.uploadData("sanpaolo.ser");
    if (sanPaolo == null) {
      sanPaolo = new Bank();
      // Crea alcuni account di prova se non ci sono dati salvati
      sanPaolo.registerUser("user1", "password123", new User("001", "user1"));
      sanPaolo.createAccountList(new BankAccount("001", "user1"));
      sanPaolo.registerUser("user2", "password456", new User("002", "user2"));
      sanPaolo.createAccountList(new BankAccount("002", "user2"));
    }

    Bank generali = Bank.uploadData("generali.ser");
    if (generali == null) {
      generali = new Bank();
      generali.registerUser("user3", "password789", new User("003", "user3"));
      generali.createAccountList(new BankAccount("003", "user3"));
    }

    System.out.println(" ************BANK ACCOUNT************ ");

    while (true) {
      System.out.println("\nChose an option: ");
      System.out.println("1. Login");
      System.out.println("2. Registration");
      System.out.println("3. Exit");

      try {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
          case 1:
            System.out.println("\nLogin:");
            System.out.println("Insert username:");
            String username = scanner.nextLine();
            System.out.println("Insert password:");
            String password = scanner.nextLine();

            int checkBank = -1;
            while (checkBank != 0 && checkBank != 1) {
              try {
                System.out.println("What bank you use? (San Paolo = 0 | Generali = 1)");
                checkBank = scanner.nextInt();
                scanner.nextLine();
                if (checkBank != 0 && checkBank != 1) {
                  System.out.println("Invalid input. Please enter 0 or 1.");
                }
              } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 0 or 1.");
                scanner.nextLine();
              }
            }

            Bank selectedBank = (checkBank == 0) ? sanPaolo : generali;

            Account account = selectedBank.authenticateUser(username, password);

            if (account != null) {
              System.out.println("Login successful!");
              gestisciOperazioni(scanner, selectedBank, account.getUser());
            } else {
              System.out.println(" Incorrect username or password ");
            }
            break;

          case 2:
            System.out.println("\nRegistration:");
            System.out.println("Insert username:");
            String newUsername = scanner.nextLine();
            System.out.println("Insert password:");
            String newPassword = scanner.nextLine();
            System.out.println("Insert personal code:");
            String newPersonalCode = scanner.nextLine();

            int newCheckBank = -1;
            while (newCheckBank != 0 && newCheckBank != 1) {
              try {
                System.out.println(
                    "Which bank do you want to use? (San Paolo = 0 | Generali = 1) ");
                newCheckBank = scanner.nextInt();
                scanner.nextLine();
                if (newCheckBank != 0 && newCheckBank != 1) {
                  System.out.println("Invalid input. Please enter 0 or 1.");
                }
              } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 0 or 1.");
                scanner.nextLine();
              }
            }

            Bank newSelectedBank = (newCheckBank == 0) ? sanPaolo : generali;

            User newUser = new User(newPersonalCode, newUsername);
            newSelectedBank.registerUser(newUsername, newPassword, newUser);
            newSelectedBank.createAccountList(new BankAccount(newPersonalCode, newUsername));
            System.out.println("Registration successful!");
            break;

          case 3:
            System.out.println("program closure!");
            sanPaolo.saveData("sanpaolo.ser");
            generali.saveData("generali.ser");
            scanner.close();
            return;

          default:
            System.out.println("Invalid option. ");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number. ");
        scanner.nextLine();
      }
    }
  }

  private static void gestisciOperazioni(Scanner scanner, Bank selectedBank, User user) {
    String answ;
    do {
      System.out.println("\nSelect an operation:");
      System.out.println("- Deposit (code 0)");
      System.out.println("- Whihdrawal (code 1)");
      System.out.println("- View wallet (code 2)");
      System.out.println("- View personal balance (code 3)");
      System.out.println("- time progress (code 4)");
      System.out.println("- Create an investment (code 5)");
      System.out.println("- View your investment list (code 6)");
      System.out.println("- View transaction history (code 7)");
      int operation = scanner.nextInt();
      scanner.nextLine();

      switch (operation) {
        case 0:
          System.out.println("Deposit operation.");
          user.deposit(selectedBank);
          break;
        case 1:
          System.out.println("Withdrawal operation.");
          user.withdraw(selectedBank);
          break;
        case 2:
          System.out.println("Looking at the wallet.");
          user.lookWallet();
          break;
        case 3:
          System.out.println("Check your personal balance. ");
          user.lookPersonalBalance(selectedBank);
          break;
        case 4:
          System.out.println("Time travel operation. ");
          selectedBank.timeTravel(user);
          break;
        case 5:
          System.out.println("Creating an investment.");
          createInvestiment(scanner, selectedBank, user);
          break;
        case 6:
          System.out.println("View investment status.");
          visualizeInvestment(selectedBank, user);
          break;
        case 7:
          System.out.println("View transaction history.");
          for (BankAccount account : selectedBank.getAccountList()) {
            if (account.getPersonalCodeBank().equals(user.getPersonalCodeUser())) {
              for (Transaction t : account.getTransactionHistory()) {
                System.out.println(t);
              }
            }
          }
          break;
        default:
          System.out.println("Invalid operation.");
      }
      do {
        System.out.println("Do you want to perform another operation? (y/n) ");
        answ = scanner.nextLine();
        if (!answ.equalsIgnoreCase("y") && !answ.equalsIgnoreCase("n")) {
          System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
      } while (!answ.equalsIgnoreCase("y") && !answ.equalsIgnoreCase("n"));
    } while (answ.equalsIgnoreCase("y"));
  }

  private static void createInvestiment(Scanner scanner, Bank selectedBank, User user) {
    System.out.println("Enter the investment amount: ");
    double importo = scanner.nextDouble();
    System.out.println(
        "Enter the investment term (0 = 12 months, 1 = 60 months, 2 = 120 months): ");
    int durata = scanner.nextInt();
    System.out.println("Enter your investment risk (0 = low, 1 = medium, 2 = high): ");
    int rischio = scanner.nextInt();

    Investment investimento = new Investment(importo, durata, rischio);

    for (BankAccount account : selectedBank.getAccountList()) {
      if (account.getPersonalCodeBank().equals(user.getPersonalCodeUser())) {
        account.createInvestmentList(investimento);
        break;
      }
    }
  }

  private static void visualizeInvestment(Bank selectedBank, User user) {
    for (BankAccount account : selectedBank.getAccountList()) {
      if (account.getPersonalCodeBank().equals(user.getPersonalCodeUser())) {
        account.printInvestmentStatus();
        break;
      }
    }
  }
}
