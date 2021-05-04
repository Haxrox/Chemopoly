import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Chemopoly {
    // [ Components ] //
    private JTextField moneyField1;
    private javax.swing.JPanel JPanel;
    private JButton withdrawButton1;
    private JTextField moneyField2;
    private JButton depositButton2;
    private JButton withdrawButton2;
    private JTextField moneyField3;
    private JButton depositButton3;
    private JButton withdrawButton3;
    private JTextField moneyField4;
    private JButton depositButton4;
    private JButton withdrawButton4;
    private JTextField statusField;
    private JTextField valueField;
    private JButton depositButton1;
    private JButton newButton;
    private JTextField playersField;
    private JButton endButton;
    private JButton rollButton;
    private JPanel Player1;
    private JPanel Player2;
    private JPanel Player3;
    private JPanel Player4;
    private JLabel playersLabel;
    // [ Variables ] //
    // Classes
    GameManager gameManager;
    // ArrayLists
    ArrayList<JTextField> moneyFields = new ArrayList<JTextField>();
    ArrayList<JButton> depositButtons = new ArrayList<JButton>();
    ArrayList<JButton> withdrawButtons = new ArrayList<JButton>();
    // Values
    int startingMoney = 1000;
    // [ Constructor ] //
    public Chemopoly() {
        // New Game
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!playersField.getText().isEmpty()) {
                    try {
                        if (Integer.parseInt(playersField.getText()) > 1 && Integer.parseInt(playersField.getText()) <= 4) {
                            gameManager = new GameManager(Integer.parseInt(playersField.getText()));
                            for (int index = 0; index < gameManager.getPlayerCount(); index++) {
                                editStat(index, startingMoney);
                                depositButtons.get(index).setEnabled(true);
                                withdrawButtons.get(index).setEnabled(true);
                            }
                            statusField.setText("New game of Chemopoly started with: " + gameManager.getPlayerCount() + " players!");
                            newButton.setEnabled(false);
                            playersField.setEnabled(false);
                            rollButton.setEnabled(true);
                            endButton.setEnabled(true);
                            valueField.setEditable(true);
                        } else {
                            statusField.setText("Chemopoly only supports 2-4 people!");
                        }
                    } catch (NumberFormatException exception) {
                        statusField.setText("Give a number count!");
                    }
                } else {
                    statusField.setText("No player count given!");
                }
            }
        });
        // EndGame
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                endGame();
            }
        });
        // Roll Button
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int num1 = ((int)(Math.random() * 6) + 1 );
                int num2 = ((int)(Math.random() * 6) + 1 );
                if (num1 == num2) {
                    statusField.setText("You rolled a: " + num1 + " and a " + num2 + "! Move " + (num1 + num2) + " squares and roll again!");
                } else {
                    statusField.setText("You rolled a: " + num1 + " and a " + num2 + "! Move " + (num1 + num2) + " squares!");
                }
            }
        });
        // Adding Deposit Buttons
        depositButtons.add(0, depositButton1);
        depositButtons.add(1, depositButton2);
        depositButtons.add(2, depositButton3);
        depositButtons.add(3, depositButton4);
        // Adding Withdraw Buttons
        withdrawButtons.add(0, withdrawButton1);
        withdrawButtons.add(1, withdrawButton2);
        withdrawButtons.add(2, withdrawButton3);
        withdrawButtons.add(3, withdrawButton4);
        // Initializing Buttons
        for (int buttonIndex = 0; buttonIndex < depositButtons.size(); buttonIndex ++) {
            depositButton(depositButtons.get(buttonIndex), buttonIndex);
            withdrawButton(withdrawButtons.get(buttonIndex), buttonIndex);
        }
        // Initializing Money Fields
        moneyFields.add(0, moneyField1);
        moneyFields.add(1, moneyField2);
        moneyFields.add(2, moneyField3);
        moneyFields.add(3, moneyField4);
    }
    // [ Functions ] //
    // VerifyField
    private boolean verifyField(JTextField textField) {
        if (!textField.getText().isEmpty()) {
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException exception) {
                return false;
            }
        } else {
            return false;
        }
    }
    // Depositing Button
    private void depositButton(JButton button, int index) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (verifyField(valueField)) {
                    statusField.setText("Player" + (index + 1) + " received: " + valueField.getText() + "kJ of energy!" );
                    editStat(index, Integer.parseInt(valueField.getText()));
                } else {
                    statusField.setText("Add a number to the value field!");
                }
            }
        });
    }
    // Withdrawing Button
    private void withdrawButton(JButton button, int index) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (verifyField(valueField)) {
                    statusField.setText("Player" + (index + 1) + " lost: " + valueField.getText() + "kJ of energy!" );
                    editStat(index, -Integer.parseInt(valueField.getText()));
                } else {
                    statusField.setText("Add a number to the value field!");
                }
            }
        });
    }
    // End Game
    private void endGame() {
        int richestPlayer = 0;
        int richestValue = 0;
        for (int index = 0; index < gameManager.getPlayerCount(); index ++) {
            if (gameManager.getPlayerMoney(index) > richestValue) {
                richestPlayer = index;
                richestValue = gameManager.getPlayerMoney(index);
            }
        }
        statusField.setText("Player" + (richestPlayer + 1) + " won with " + richestValue + "kJ of energy!");
        for (int index = 0; index < gameManager.getPlayerCount(); index++) {
            depositButtons.get(index).setEnabled(false);
            withdrawButtons.get(index).setEnabled(false);
        }
        endButton.setEnabled(false);
        rollButton.setEnabled(false);
        valueField.setEditable(false);
        newButton.setEnabled(true);
        playersField.setEnabled(true);
    }
    // EditStat
    private void editStat(int player, int value) {
        int playerMoney = gameManager.editMoney(player, value);
        if (playerMoney <= 0) {
            moneyFields.get(player).setText("Bankrupt!");
            depositButtons.get(player).setEnabled(false);
            withdrawButtons.get(player).setEnabled(false);
            statusField.setText("Player" + (player + 1) + " is bankrupt!");
        } else {
            moneyFields.get(player).setText(playerMoney + "kJ");
        }
        if (gameManager.getRemainingPlayers() <= 1) {
            endGame();
        }
    }
    // [ Main ] //
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chemopoly");
        frame.setContentPane(new Chemopoly().JPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
