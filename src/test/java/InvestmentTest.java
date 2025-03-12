import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvestmentTest {

  @Test
  public void testPaymentPositive() {
    Investment investment =
        new Investment(1000, 0, 0); // Investimento a basso rischio e breve termine
    double result = investment.payment();
    assertTrue(result > 1000, "Il rendimento dovrebbe essere positivo");
  }

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
  public void testGetResult() {
    Investment investment = new Investment(1000, 0, 0);
    investment.payment();
    assertTrue(
        investment.getResult() != 0, "Il risultato dovrebbe essere diverso da 0 dopo il pagamento");
  }
}
