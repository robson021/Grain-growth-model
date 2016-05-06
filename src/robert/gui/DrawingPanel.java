package robert.gui;

import robert.model.Neighbourhood;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Random;

/**
 * Created by student on 2016-05-05.
 */
public class DrawingPanel extends JPanel {
    private static DrawingPanel self = null;
    public static final int SIZE = 65;

    public CellPane[][] getCells() {
        return cells;
    }

    private CellPane[][] cells = new CellPane[SIZE][SIZE];

    public DrawingPanel() {
        setLayout(new GridBagLayout());
        self = this;

        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                CellPane cellPane = new CellPane(row, col);
                Border border = null;
                if (row < SIZE - 1) {
                    if (col < SIZE - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < SIZE - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                cells[row][col] = cellPane;
                cellPane.setBorder(border);
                add(cellPane, gbc);
            }
        }
    }

    public static DrawingPanel getSelf() {
        return self;
    }

    public void setRandomSeeds(final int SEEDS_NUM) {
        Random random = new Random();
        System.out.println("Random cells:\n");
        for (int x, y, i = 0; i < SEEDS_NUM; i++) {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();
            cells[x][y].makeRandomSeed(new Color(r, g, b));
        }
    }

    public void clearCells() {
        for (int j, i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++)
                cells[i][j].clearMe();
        }
    }

    public void checkCells(Neighbourhood neighbourhood) {

        //System.out.println(neighbourhood.toString());

        /*for (int j, i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++) {
                cells[i][j].checkMyNeighbourhood(neighbourhood);
            }
        }*/

        for (CellPane c : CellPane.getSeeds()) {
            c.checkMyNeighbourhood(neighbourhood);
        }

    }
}
