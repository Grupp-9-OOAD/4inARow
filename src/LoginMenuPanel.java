import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginMenuPanel extends JPanel implements ActionListener {
    private final JTextField userNameField = new JTextField("Användarnamn");
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton newUserButton = new JButton("Create new user");
    private final JButton confirmLoginButton = new JButton("Log in");
    private final JButton highScoreButton = new JButton("Highscore");

    private final JLabel outputLabel = new JLabel("Välkommen till världens bästa 4-i-rad spel!");

    private final Game game;
    public LoginMenuPanel() {
        this.game = new Game(this);
        setLayout(new GridLayout(6, 1));
        add(outputLabel);
        add(userNameField);
        add(passwordField);
        add(newUserButton);
        newUserButton.addActionListener(this);
        add(confirmLoginButton);
        confirmLoginButton.addActionListener(this);
        add(highScoreButton);
        highScoreButton.addActionListener(this);


    }

    void createUser() {
        User user = new User()
                .setUserName(userNameField.getText().trim())
                .setPassword(passwordField.getText().trim());

        try {
            UserDatabase.addUser(user);
            outputLabel.setText(user + " added");
        } catch (IllegalArgumentException e) {
            outputLabel.setText(e.getMessage());
        }
    }

    void attemptLogin() {
        Optional<User> userOptional;
        userOptional = UserDatabase.getUser(userNameField.getText(), passwordField.getText());
        userOptional.ifPresentOrElse(this::loginSuccessful, this::loginFail);

    }

    void loginSuccessful(User user) {
        outputLabel.setText(user + " logged in");
        game.addUser(user);
    }

    void loginFail() {
        outputLabel.setText("Felaktigt användarnamn eller lösenord, försök igen");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newUserButton) {
            createUser();
        } else if (e.getSource() == confirmLoginButton) {
            attemptLogin();
        } else if (e.getSource() == highScoreButton){
            JOptionPane.showMessageDialog(this, game.getHighScoreString());
        }
    }
}
