import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
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
    return "Date: "
        + date
        + ", Amount: "
        + amount
        + ", Type: "
        + type
        + ", Description: "
        + description;
  }
}
