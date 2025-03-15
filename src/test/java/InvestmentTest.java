import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InvestmentTest {

  @Test
  public void testLimitDecrement() {
    Investment investment = new Investment(1000, 0, 0);
    int initialLimit = investment.getLimit();
    investment.setLimit();
    assertEquals(initialLimit - 1, investment.getLimit(), "Il limite dovrebbe diminuire di 1");
  }

  @Test
  public void testGetDuration() {
    Investment investment = new Investment(1000, 1, 0);
    assertEquals(1, investment.getDuration(), "La durata dovrebbe essere 1");
  }

  @Test
  public void testGetRisk() {
    Investment investment = new Investment(1000, 0, 2);
    assertEquals(2, investment.getRisk(), "Il rischio dovrebbe essere 2");
  }

  @Test
  public void testGetQInvest() {
    Investment investment = new Investment(1500, 0, 0);
    assertEquals(1500, investment.getQInvest(), "L'importo investito dovrebbe essere 1500");
  }

  @Test
  void investWithValidImport() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    double investedValue = 10.0;
    account.setPersonalBalance(10.0);

    boolean investmentStatus = account.invest(investedValue, "Short", "Low");

    assertTrue(investmentStatus, "Investment should result successfully done");
  }

  @Test
  void investWithInvalidImport() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    double investedValue = -10.0;
    account.setPersonalBalance(10.0);

    boolean investmentStatus = account.invest(investedValue, "Short", "Low");

    assertFalse(investmentStatus, "Investment should result not successfully done");
  }

  @Test
  void investWithNullImport() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    double investedValue = 0.0;
    account.setPersonalBalance(10.0);

    boolean investmentStatus = account.invest(investedValue, "Short", "Low");

    assertFalse(investmentStatus, "Investment should result not successfully done");
  }

  @Test
  void lowRiskInvest() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    double investedValue = 10.0;
    account.setPersonalBalance(10.0);
    double previousBalance = account.getPersonalBalance();

    boolean investmentStatus = account.invest(investedValue, "Short", "Low");
    double investmentResult = account.getPersonalBalance() - (previousBalance - investedValue);

    assertTrue(investmentStatus, "Investment should result successfully done");
    assertTrue(
        investmentResult >= investedValue * 0.7,
        "Due to low risk, investment result should not be less than the invested amount * 0.7");
  }

  @Test
  void mediumRiskInvest() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    double investedValue = 10.0;
    account.setPersonalBalance(10.0);
    double previousBalance = account.getPersonalBalance();

    boolean investmentStatus = account.invest(investedValue, "Short", "Medium");
    double investmentResult = account.getPersonalBalance() - (previousBalance - investedValue);

    assertTrue(investmentStatus, "Investment should result successfully done");
    assertTrue(
        investmentResult >= investedValue * 0.4,
        "Due to medium risk, investment result should not be less than the invested amount * 0.4");
  }

  @Test
  void highRiskInvest() {
    Bank bank = new Bank();
    User user = new User("001", "testUser");
    bank.registerUser("testUser", "password", user);
    BankAccount account = new BankAccount("001", "testUser");
    bank.createAccountList(account);

    double investedValue = 10.0;
    account.setPersonalBalance(10.0);
    double previousBalance = account.getPersonalBalance();

    boolean investmentStatus = account.invest(investedValue, "Short", "High");
    double investmentResult = account.getPersonalBalance() - (previousBalance - investedValue);

    assertTrue(investmentStatus, "Investment should result successfully done");
    assertTrue(
        investmentResult >= investedValue * 0.1,
        "Due to high risk, investment result should not be less than the invested amount * 0.1");
  }
}
