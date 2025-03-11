public class Account {
  private String username;
  private String password;
  private User user;

  public Account(String username, String password, User user) {
    this.username = username;
    this.password = password;
    this.user = user;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public User getUser() {
    return user;
  }
}
