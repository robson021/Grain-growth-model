package robert.gui;

import robert.model.Neighbourhood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by student on 2016-05-05.
 */
public class CellPane extends JPanel {
    private CellPane[][] cells = DrawingPanel.getSelf().getCells();
    private static java.util.List<CellPane> toUpdateList = new ArrayList<>();
    private static int idCounter = 0;
    private Color defaultBackground;
    private final int cordX, cordY;
    private boolean alive = false;
    private boolean partOfStructure = false;
    private boolean toUpdate = false;
    private boolean isSeed = false;
    private int id = -1;

    public CellPane(int x, int y) {
        cordX = x;
        cordY = y;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
    }

    public void makeSeed(Color color) {
        this.isSeed = true;
        this.alive = true;
        id = idCounter++;

        defaultBackground = color;
        setBackground(defaultBackground);
    }

    public void checkMyNeighbourhood(Neighbourhood neighbourhood) {

        switch (neighbourhood) {
            case VON_NEUMAN:
                break;
            case MOORE:
                int x = cordX - 1;
                for (int j, i = 0; i < 3; i++) {
                    int y = cordY - 1;
                    for (j = 0; j < 3; i++) {
                        if (x == cordX && y == cordY) continue;
                        try {
                            if (cells[x][y].isSeed) {
// TODO: 2016-05-05  
                            }
                        } catch (Exception e) {
                            System.out.println("exception: " + x + ", " + y);
                        }
                        y++;
                    }
                    x++;
                }
                break;
            case PENTAGONAL:
                break;
            case HEXAGONAL:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case UP:
                break;
            case DOWN:
                break;
        }
    }
}
