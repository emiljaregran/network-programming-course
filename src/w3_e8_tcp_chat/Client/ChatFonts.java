package w3_e8_tcp_chat.Client;

import java.awt.*;

class ChatFonts
{
    private final String[] preferedFonts = {"Courier New", "Noto Mono"};

    final Font label;
    final Font textField;
    final Font list;
    final Font inputMessageTextField;
    final Font chatWindow;

    ChatFonts()
    {
        String font = getfont();

        this.label = new Font(font, Font.BOLD, 14);
        this.textField = new Font(font, Font.BOLD, 14);
        this.list = new Font(font, Font.BOLD, 14);
        this.inputMessageTextField = new Font(font, Font.PLAIN, 16);
        this.chatWindow = new Font(font, Font.PLAIN, 16);
    }

    private String getfont()
    {
        for (String font : preferedFonts)
        {
            if (fontExists(font))
            {
                return font;
            }
        }

        return "Default font";
    }

    private boolean fontExists(String fontName)
    {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = graphicsEnvironment.getAvailableFontFamilyNames();

        for (String font : fonts)
        {
            if (font.equals(fontName))
            {
                return true;
            }
        }

        return false;
    }
}
