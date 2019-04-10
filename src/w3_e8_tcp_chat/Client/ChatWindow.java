package w3_e8_tcp_chat.Client;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class ChatWindow
{
    private final JTextPane textPane;

    ChatWindow(ChatFonts fonts, ChatColors colors, JTextPane textPane)
    {
        this.textPane = textPane;

        DefaultCaret caret = (DefaultCaret)textPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        textPane.setFont(fonts.chatWindow);
        textPane.setBackground(colors.background);
        textPane.setForeground(Color.WHITE);
        textPane.setEditable(false);
    }

    void append(String text, Color color, boolean bold)
    {
        StyledDocument document = textPane.getStyledDocument();

        Style style = textPane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        try
        {
            document.insertString(document.getLength(), text, style);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
    }

    String getTimestamp()
    {
        return new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
    }
}
