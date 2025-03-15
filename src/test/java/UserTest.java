import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void testDepositValidAmount() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    account.setPersonalBalance(0.0);

    double depositAmount = 100.0;
    boolean depositResult = user.deposit(bank, depositAmount);

    assertTrue(depositResult, "Deposit should succeed with valid amount.");
    assertEquals(0.0, user.getPersonalWallet(), "Wallet should decrease by deposit amount.");
    assertEquals(100.0, account.getPersonalBalance(), "Balance should increase by deposit amount.");
  }

  @Test
  public void testDepositNegativeAmount() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    // Wallet parte da 100, quindi non è necessario impostarlo a 200 qui
    account.setPersonalBalance(0.0);

    double depositAmount = -10.0;
    boolean depositResult = user.deposit(bank, depositAmount);

    assertFalse(depositResult, "Deposit should fail with negative amount.");
    assertEquals(100.0, user.getPersonalWallet(), "Wallet should not change with negative amount.");
    assertEquals(
        0.0, account.getPersonalBalance(), "Balance should not change with negative amount.");
  }

  @Test
  public void testDepositZeroAmount() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    // Wallet parte da 100, quindi non è necessario impostarlo a 200 qui
    account.setPersonalBalance(0.0);

    double depositAmount = 0.0;
    boolean depositResult = user.deposit(bank, depositAmount);

    assertFalse(depositResult, "Deposit should fail with zero amount.");
    assertEquals(100.0, user.getPersonalWallet(), "Wallet should not change with zero amount.");
    assertEquals(0.0, account.getPersonalBalance(), "Balance should not change with zero amount.");
  }

  @Test
  public void testWithdrawPositiveAmount() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    account.setPersonalBalance(100.0);

    double withdrawValue = 50.0;
    user.withdraw(bank, withdrawValue);

    assertEquals(150.0, user.getPersonalWallet(), "Wallet should increase by withdraw amount.");
    assertEquals(50.0, account.getPersonalBalance(), "Balance should decrease by withdraw amount.");
  }

  @Test
  public void testWithdrawNegativeAmount() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    account.setPersonalBalance(100.0);

    double withdrawValue = -10.0;
    user.withdraw(bank, withdrawValue);

    assertEquals(
        100.0,
        user.getPersonalWallet(),
        "Wallet should not change due to negative withdraw amount.");
    assertEquals(
        100.0,
        account.getPersonalBalance(),
        "Balance should not change due to negative withdraw amount.");
  }

  @Test
  public void testWithdrawZeroAmount() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    account.setPersonalBalance(100.0);

    double withdrawValue = 0.0;
    user.withdraw(bank, withdrawValue);

    assertEquals(
        100.0, user.getPersonalWallet(), "Wallet should not change due to zero withdraw amount.");
    assertEquals(
        100.0,
        account.getPersonalBalance(),
        "Balance should not change due to zero withdraw amount.");
  }

  @Test
  public void testWithdrawInsufficientFunds() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    account.setPersonalBalance(50.0);

    double withdrawValue = 100.0;
    user.withdraw(bank, withdrawValue);

    assertEquals(
        100.0, user.getPersonalWallet(), "Wallet should not change due to insufficient funds.");
    assertEquals(
        50.0, account.getPersonalBalance(), "Balance should not change due to insufficient funds.");
  }
}
