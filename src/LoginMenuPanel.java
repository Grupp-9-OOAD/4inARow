import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginMenuPanel extends JPanel implements ActionListener {
    public static final Color FOREGROUND_COLOR = new Color(0x79AA9E);
    public static final Color BACKGROUND_COLOR = new Color(0x0123AA);
    private final JTextField userNameField = new JTextField("");
    private final JTextField passwordField = new JTextField("");
    private final JButton newUserButton = new JButton("Skapa ny användare");
    private final JButton confirmLoginButton = new JButton("Logga in");


    private final JLabel outputLabel = new JLabel("Välkommen till Best Company Ever AB's 4-i-rad spel!");

    private final Game game;
    public LoginMenuPanel() {

        this.game = new Game(this);
        setLayout(new BorderLayout());

        outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outputLabel.setPreferredSize(new Dimension(950, 350));
        outputLabel.setFont(new Font("Bell MT", Font.BOLD, 40));
        outputLabel.setForeground(FOREGROUND_COLOR);
        outputLabel.setBackground(BACKGROUND_COLOR);

        JLabel footerLabel = new JLabel("Logga in med befintlig användare för att spela, alternativt skapa ny användare och logga sedan in.");
        footerLabel.setPreferredSize(new Dimension(950, 150));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Bell MT", Font.BOLD, 18));
        footerLabel.setBackground(BACKGROUND_COLOR);
        footerLabel.setForeground(FOREGROUND_COLOR);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(outputLabel, BorderLayout.CENTER);
        topPanel.add(footerLabel, BorderLayout.SOUTH);

        JLabel userNameLabel = new JLabel("Ange användarnamn: ");
        userNameLabel.setForeground(FOREGROUND_COLOR);
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel passwordLabel = new JLabel("Ange lösenord: ");
        passwordLabel.setForeground(FOREGROUND_COLOR);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel gridPanel = new JPanel(new GridLayout(2, 2));
        gridPanel.add(userNameLabel);
        gridPanel.add(userNameField);
        gridPanel.add(passwordLabel);
        gridPanel.add(passwordField);
        gridPanel.setBackground(BACKGROUND_COLOR);

        add(topPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(950, 200));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        newUserButton.setPreferredSize(new Dimension(250, 50));
        confirmLoginButton.setPreferredSize(new Dimension(250, 50));

        bottomPanel.add(newUserButton);
        bottomPanel.add(confirmLoginButton);

        add(bottomPanel, BorderLayout.SOUTH);

        newUserButton.addActionListener(this);
        confirmLoginButton.addActionListener(this);
        setBackground(BACKGROUND_COLOR);
    }

    void createUser() {
        User user = new User()
                .setUserName(userNameField.getText().trim())
                .setPassword(passwordField.getText().trim());

        try {
            UserDatabase.addUser(user);
            outputLabel.setText(user + " skapad");
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
        outputLabel.setText( "<html>" + user + " loggade in. <br/>Logga in spelare 2. </html>");
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
        }
    }
}
