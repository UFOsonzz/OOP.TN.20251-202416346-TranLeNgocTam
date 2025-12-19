import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private Calculator calculator;
    private StringBuilder currentInput;
    
    public CalculatorGUI() {
        calculator = new Calculator();
        currentInput = new StringBuilder();
        
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        displayPanel.add(display, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttons = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "x",
            "C", "0", "=", "/"
        };
        
        for (String text : buttons) {
            JButton button = createButton(text);
            buttonPanel.add(button);
        }
        
        add(buttonPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addActionListener(this);
        button.setFocusPainted(false);
        
        if (text.equals("C")) {
            button.setBackground(new Color(231, 76, 60));
            button.setForeground(Color.WHITE);
        } else if (text.matches("[+\\-x/=]")) {
            button.setBackground(new Color(52, 73, 94));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(236, 240, 241));
            button.setForeground(Color.BLACK);
        }
        
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
        } else if (command.equals("=")) {
            try {
                if (currentInput.length() > 0) {
                    double result = calculator.calculate(currentInput.toString());
                    String resultText;
                    if (result == (long) result) {
                        resultText = String.format("%d", (long)result);
                    } else {
                        resultText = String.valueOf(result);
                    }
                    display.setText(resultText);
                    currentInput.setLength(0);
                }
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } else {
            if (currentInput.length() == 0 && !display.getText().isEmpty()) {
                display.setText("");
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        }
    }
}
