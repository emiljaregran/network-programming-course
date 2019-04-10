package w3_e9_tcp_othello.Client;

import javax.swing.*;

class Square extends JButton
{
    private final int row;
    private final int column;

    Square(Icon icon, int row, int column)
    {
        setBorder(null);
        setIcon(icon);
        this.row = row;
        this.column = column;
    }

    int getRow()
    {
        return row;
    }

    int getColumn()
    {
        return column;
    }
}
