package robert.gui;

import robert.model.Neighbourhood;
import robert.model.Placement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Collections;
import java.util.Random;

/**
 * Created by student on 2016-05-05.
 */
public class DrawingPanel extends JPanel {
    private static DrawingPanel self = null;
    public static final int SIZE = 70;

    public CellPane[][] getCells() {
        return cells;
    }

    private final CellPane[][] cells = new CellPane[SIZE][SIZE];

    public DrawingPanel() {
        setLayout(new GridBagLayout());
        self = this;

        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                CellPane cellPane = new CellPane(row, col);
                Border border;
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

    public static DrawingPanel getPanel() {
        return self;
    }

    public void setRandomSeeds(final int SEEDS_NUM, Placement p, int ray) {
        Random random = new Random();
        float r, g, b;
        switch (p) {
            case RANDOM:
                for (int x, y, i = 0; i < SEEDS_NUM; i++) {
                    x = random.nextInt(SIZE);
                    y = random.nextInt(SIZE);
                    r = random.nextFloat();
                    g = random.nextFloat();
                    b = random.nextFloat();
                    cells[x][y].makeRandomSeed(new Color(r, g, b));
                }
                break;
            case EVENLY:
                int step = SEEDS_NUM;
                if (step < 3 || (step > 10)) step = 7;
                System.out.println("step: " + step);
                for (int j, i = step; i < SIZE; i += step) {
                    for (j = step; j < SIZE; j += step) {
                        r = random.nextFloat();
                        g = random.nextFloat();
                        b = random.nextFloat();
                        cells[i][j].makeRandomSeed(new Color(r, g, b));
                    }
                }
                break;
            case IN_RAY:
                if (ray < 1 || ray > 8) ray = 6;
                System.out.println("ray: " + ray);
                for (int k, x, y, i = 0; i < SEEDS_NUM; i++) {
                    x = random.nextInt(SIZE);
                    y = random.nextInt(SIZE);
                    r = random.nextFloat();
                    g = random.nextFloat();
                    b = random.nextFloat();

                    k = 0;

                    while (!cells[x][y].checkArea(ray) && k < 5) {
                        x = random.nextInt(SIZE);
                        y = random.nextInt(SIZE);
                        ++k;
                    }
                    if (cells[x][y].checkArea(ray)) {
                        cells[x][y].makeRandomSeed(new Color(r, g, b));
                    }
                }
                break;
        }
    }

    public void clearCells() {
        for (int j, i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++)
                cells[i][j].clearMe();
        }
    }

    public void checkCells(Neighbourhood neighbourhood) {

        long t = System.nanoTime();
        Collections.shuffle(CellPane.getSeeds(), new Random(t)); // random update order

        for (CellPane c : CellPane.getSeeds()) {
            c.checkMyNeighbourhood(neighbourhood);
        }

    }

}
