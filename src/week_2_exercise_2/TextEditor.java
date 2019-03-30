package week_2_exercise_2;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class TextEditor implements ActionListener
{
    private final JTextField textField = new JTextField();
    private final JButton openButton = new JButton("Open");
    private final JButton saveButton = new JButton("Save");
    private final JButton printButton = new JButton("Print");
    private final JButton exitButton = new JButton("Exit");
    private final JTextArea textArea = new JTextArea();

    private TextEditor()
    {
        final JFrame frame = new JFrame("Text Editor");
        final JPanel topPanel = new JPanel(new FlowLayout());
        final JPanel centerPanel = new JPanel(new BorderLayout());
        final JLabel label = new JLabel("Filename:");
        final JScrollPane scrollPane = new JScrollPane(textArea);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        topPanel.add(label);
        textField.setColumns(15);
        topPanel.add(textField);
        topPanel.add(openButton);
        topPanel.add(saveButton);
        topPanel.add(printButton);
        topPanel.add(exitButton);

        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        printButton.addActionListener(this);
        exitButton.addActionListener(this);

        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setLocation(300, 300);
        frame.setSize(520, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == openButton && textField.getText().length() > 0)
        {
            readFile(textField.getText());
        }
        else if (event.getSource() == saveButton && textField.getText().length() > 0)
        {
            saveFile(textField.getText());
        }
        else if (event.getSource() == printButton)
        {
            printFile();
        }
        else if (event.getSource() == exitButton)
        {
            System.exit(0);
        }
    }

    private void readFile(String filename)
    {
        try(FileReader fileReader = new FileReader("src\\week_2_exercise_2\\" + filename))
        {
            textArea.read(fileReader, null);
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "File not found!");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void saveFile(String filename)
    {
        try(FileWriter fileWriter = new FileWriter("src\\week_2_exercise_2\\" + filename))
        {
            textArea.write(fileWriter);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void printFile()
    {
        try
        {
            textArea.print();
        }
        catch(PrinterException e)
        {
            JOptionPane.showMessageDialog(null, "Error: Could not print.");
        }
    }

    public static void main(String[] args)
    {
        new TextEditor();
    }
}
