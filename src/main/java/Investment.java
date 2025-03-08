import java.util.Random;

public class Investment {
  private double qInvest;
  private int duration; // 0 1 2
  private int limit;
  private int riskEarning; // 0 1 2
  private boolean isFinish;
  private double result;

  public Investment(double q, int d, int r) {
    this.qInvest = q;
    this.duration = d;

    if (duration == 0) {
      this.limit = 12;
    } else if (duration == 1) {
      this.limit = 60;
    } else {
      this.limit = 120;
    }
    this.riskEarning = r;
    this.isFinish = true;
    this.result = 0;
  }

  public double payment() {
    Random rand = new Random();
    double perCentDuration = 0;

    if (duration == 0) {
      perCentDuration = (qInvest * 1) / 100;
    } else if (duration == 1) {
      perCentDuration = (qInvest * 5) / 100;
    } else if (duration == 2) {
      perCentDuration = (qInvest * 10) / 100;
    }

    double perCentRiskEarning = 0;
    int die;
    if (riskEarning == 0) {
      die = rand.nextInt(10) + 1;
      perCentRiskEarning = (die == 1) ? -((qInvest * 10) / 100) : ((qInvest * 10) / 100);
    } else if (riskEarning == 1) {
      die = rand.nextInt(10) + 1;
      perCentRiskEarning = (die <= 3) ? -((qInvest * 50) / 100) : ((qInvest * 50) / 100);
    } else if (riskEarning == 2) {
      die = rand.nextInt(10) + 1;
      perCentRiskEarning = (die <= 5) ? -((qInvest * 70) / 100) : ((qInvest * 70) / 100);
    }

    System.out.println("Investment is finished");
    this.setResult(qInvest + perCentDuration + perCentRiskEarning);
    return qInvest + perCentDuration + perCentRiskEarning;
  }

  public int getDuration() {
    return duration;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit() {
    limit -= 1;
  }

  public boolean getIsFinish() {
    return isFinish;
  }

  public void setIsFinish() {
    isFinish = false;
  }

  public double getQInvest() {
    return qInvest;
  }

  public int getRisk() {
    return riskEarning;
  }

  public void setResult(double b) {
    result = b;
  }

  public double getResult() {
    return result;
  }
}
