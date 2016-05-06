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
    private static java.util.List<CellToUpdate> toUpdateList = new ArrayList<>();
    private static java.util.List<CellPane> seeds = new ArrayList<>();
    private static final Color deadBackground = Color.WHITE;
    private static int idCounter = 0;
    private Color defaultBackground;
    private final int cordX, cordY;
    private boolean alive = false;
    private boolean seed = false;
    private int id = -1;

    public CellPane(int x, int y) {
        cordX = x;
        cordY = y;
        setBackground(deadBackground);
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

    public void makeRandomSeed(Color color) {
        this.seed = true;
        this.alive = true;
        id = idCounter++;

        defaultBackground = color;
        setBackground(defaultBackground);
        this.seeds.add(this);
    }

    private void addMeToOtherSeed(int id, Color c) {
        this.id = id;
        this.defaultBackground = c;
        this.setBackground(deadBackground);
        this.seed = true;
        this.alive = true;
        this.seeds.add(this);
        System.out.println("I'm seed now: " + this.toString());
    }

    public void clearMe() {
        this.seed = false;
        this.alive = false;
        id = -1;
        setBackground(deadBackground);
    }

    public void checkMyNeighbourhood(Neighbourhood neighbourhood) {

        if (!this.isSeed()) {
            System.out.println("I'm not seed");
            return;
        }

        CellPane otherCell = null;

        switch (neighbourhood) {
            case VON_NEUMAN:
                break;
            case MOORE:
                for (int j, i = cordX - 1; i < (cordX + 2); i++) {
                    for (j = cordY - 1; j < (cordY + 2); j++) {
                        try {
                            if (i == cordX && j == cordY) continue;
                            otherCell = cells[i][j];
                            if (!otherCell.isSeed()) {
                                toUpdateList.add(new CellToUpdate(otherCell, this.defaultBackground, this.id));
                            }
                        } catch (Exception e) {
                            //System.out.println("Exception. X, Y: " + i + ", " + j);
                        }
                    }
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

    public int getId() {
        return id;
    }

    public Color getDefaultBackground() {
        return defaultBackground;
    }

    public static java.util.List<CellPane> getSeeds() {
        return seeds;
    }

    public boolean isSeed() {
        return seed;
    }

    @Override
    public String toString() {
        return "CellPane{" +
                "cordX=" + cordX +
                ", cordY=" + cordY +
                ", alive=" + alive +
                ", seed=" + seed +
                ", id=" + id +
                '}';
    }

    private class CellToUpdate {
        CellPane cell;
        Color bg;
        int id;

        public CellToUpdate(CellPane cell, Color bg, int id) {
            this.cell = cell;
            this.bg = bg;
            this.id = id;
        }
    }

    static int UpdateCells() {
        int s = toUpdateList.size();
        for (CellToUpdate c : toUpdateList) {
            c.cell.addMeToOtherSeed(c.id, c.bg);
        }
        toUpdateList.clear();
        return s;
    }
}
