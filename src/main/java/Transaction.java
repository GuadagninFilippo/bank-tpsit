import java.util.Date;
import java.text.SimpleDateFormat;

public class Transaction {
  private Date date;
  private double amount;
  private String type;
  private String description;

  public Transaction(Date date, double amount, String type, String description) {
    this.date = date;
    this.amount = amount;
    this.type = type;
    this.description = description;
  }

  public Date getDate() {
    return date;
  }

  public double getAmount() {
    return amount;
  }

  public String getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    return formatter.format(date) + "," + amount + "," + type + "," + description;
  }
}
