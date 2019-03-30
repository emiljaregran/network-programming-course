package week_2_exercise_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener
{
    private final JLabel labelNorth = new JLabel("North");
    private final JLabel labelWest = new JLabel("West");
    private final JLabel labelEast = new JLabel("East");
    private final JButton buttonCenter = new JButton("Flip");
    private final JButton buttonSouth = new JButton("Restore");
    private final JLabel bottomLabel = new JLabel("Hello world");
    private final JButton bottomButton = new JButton("Button");
    private final JTextField bottomTextField = new JTextField();

    private Main()
    {
        JPanel panelTop = new JPanel(new BorderLayout());
        JPanel panelBottom = new JPanel(new FlowLayout());

        setLayout(new GridLayout(2, 1));
        add(panelTop);
        add(panelBottom);

        panelTop.add(labelNorth, BorderLayout.NORTH);
        panelTop.add(labelWest, BorderLayout.WEST);
        panelTop.add(labelEast, BorderLayout.EAST);
        panelTop.add(buttonCenter, BorderLayout.CENTER);
        panelTop.add(buttonSouth, BorderLayout.SOUTH);

        panelBottom.add(bottomLabel);
        bottomTextField.setColumns(25);
        panelBottom.add(bottomTextField);
        panelBottom.add(bottomButton);

        buttonCenter.addActionListener(this);
        buttonSouth.addActionListener(this);
        bottomButton.addActionListener(this);

        setVisible(true);
        setTitle("Swing test");
        setLocation(400, 200);
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == buttonCenter)
        {
            labelNorth.setText("South");
            labelWest.setText("East");
            labelEast.setText("West");
        }
        else if (event.getSource() == buttonSouth)
        {
            labelNorth.setText("North");
            labelWest.setText("West");
            labelEast.setText("East");
        }
        else if (event.getSource() == bottomButton)
        {
            bottomLabel.setText(bottomTextField.getText());
            bottomTextField.setText("");
        }
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
