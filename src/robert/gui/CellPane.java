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
    private static CellPane[][] cells = DrawingPanel.getPanel().getCells();
    private static java.util.List<CellToUpdate> toUpdateList = new ArrayList<>();
    private static java.util.List<CellPane> seeds = new ArrayList<>();
    private static final Color deadBackground = Color.WHITE;
    private static int idCounter = 0;
    private Color defaultBackground;
    private final int cordX, cordY;
    //private boolean alive = false;
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
        if (this.seed) {
            //System.out.println("The cell is already taken:\n\t" + toString());
            return;
        }
        this.seed = true;
        //this.alive = true;
        id = idCounter++;

        defaultBackground = color;
        setBackground(defaultBackground);
        seeds.add(this);
        //System.out.println(toString());
    }

    private void addMeToOtherSeed(int id, Color c) {
        this.id = id;
        this.defaultBackground = c;
        setBackground(defaultBackground);
        this.seed = true;
        //this.alive = true;
        seeds.add(this);
        //System.out.println("I'm seed now: " + this.toString());
    }

    public void clearMe() {
        this.seed = false;
        //this.alive = false;
        id = -1;
        setBackground(deadBackground);
    }

    public void checkMyNeighbourhood(Neighbourhood neighbourhood) {

        if (!this.seed) {
            System.out.println("I'm not seed");
            return;
        }

        CellPane otherCell = null;
        int x, y;
        switch (neighbourhood) {
            case VON_NEUMAN:
                x = this.cordX - 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x = this.cordX + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x = this.cordX;
                    y = this.cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY - 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
            case MOORE:
                for (int j, i = cordX - 1; i < (cordX + 2); i++) {
                    for (j = cordY - 1; j < (cordY + 2); j++) {
                        try {
                            if (i == cordX && j == cordY) continue;
                            otherCell = cells[i][j];
                            checkCandidate(otherCell);
                        } catch (Exception e) {
                            //System.out.println("Exception. X, Y: " + i + ", " + j);
                        }
                    }
                }
                break;
            case PENTAGONAL_LEFT:
                x = this.cordX - 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x = this.cordX + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY - 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
            case PENTAGONAL_RIGHT:
                x = this.cordX - 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x = this.cordX + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
            case PENTAGONAL_UP:
                x = this.cordX - 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY - 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
            case PENTAGONAL_DOWN:
                x = this.cordX + 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = this.cordY - 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
            case HEXAGONAL_A:
                x = this.cordX - 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = cordY + 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y--;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
            case HEXAGONAL_B:
                x = this.cordX - 1;
                y = this.cordY;
                try {
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y = cordY - 1;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    x++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                try {
                    y++;
                    otherCell = cells[x][y];
                    checkCandidate(otherCell);
                } catch (Exception e) {
                }
                break;
        }
    }

    private void checkCandidate(CellPane otherCell) {
        if (!otherCell.isSeed()) {
            toUpdateList.add(new CellToUpdate(otherCell.cordX, otherCell.cordY,
                    this.defaultBackground, this.id));
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

    private boolean isSeed() {
        return seed;
    }

    @Override
    public String toString() {
        return "CellPane{" +
                "cordX=" + cordX +
                ", cordY=" + cordY +
                //", alive=" + alive +
                ", seed=" + seed +
                ", id=" + id +
                '}';
    }

    private class CellToUpdate {
        final int x, y;
        final Color bg;
        final int id;

        public CellToUpdate(int x, int y, Color bg, int id) {
            this.x = x;
            this.y = y;
            this.bg = bg;
            this.id = id;

            //System.out.println("Added to update: " + toString());
        }

        @Override
        public String toString() {
            return "CellToUpdate{" +
                    "x=" + x +
                    ", y=" + y +
                    ", id=" + id +
                    '}';
        }
    }

    public static int UpdateCells() {
        int s = toUpdateList.size();
        CellPane cell;
        for (CellToUpdate c : toUpdateList) {
            cell = cells[c.x][c.y];
            cell.addMeToOtherSeed(c.id, c.bg);
        }
        toUpdateList.clear();
        return s;
    }

    public static java.util.List<CellToUpdate> getToUpdateList() {
        return toUpdateList;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }
}
