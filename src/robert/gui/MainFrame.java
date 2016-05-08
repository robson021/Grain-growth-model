package robert.gui;

import robert.model.Neighbourhood;
import robert.model.Placement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static DrawingPanel drawingPanel;
    private static final int MIN_INPUT = DrawingPanel.SIZE * 4 / 3;
    private Thread mainThread = null;
    private boolean isRunning = false;
    private final JComboBox neighbourhoodBox, placementBox;
    private final JTextField seedCountTextField, rayTextField;
    private static final JLabel infoLabel = new JLabel("[info]");
    private final JButton startButton, stopButton;
    private static MainFrame self;

    private MainFrame() throws HeadlessException {
        super("Grain growth model by Robert Nowak");
        self = this;
        this.setLayout(new BorderLayout());
        drawingPanel = new DrawingPanel();

        Color defaultPanelBg = Color.YELLOW.darker();

        this.add(drawingPanel, BorderLayout.CENTER);
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setBackground(defaultPanelBg);

        infoLabel.setForeground(Color.RED.darker());
        //infoLabel.setMinimumSize(new Dimension(20, infoLabel.getHeight()));
        northPanel.add(infoLabel);
        northPanel.add(new JSeparator());
        northPanel.add(new JSeparator());

        northPanel.add(new JLabel("Neighbourhood:"));

        neighbourhoodBox = new JComboBox(Neighbourhood.values());
        northPanel.add(neighbourhoodBox);

        placementBox = new JComboBox(Placement.values());
        placementBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Placement.fromString(placementBox.getItemAt(placementBox.getSelectedIndex()).toString()).equals(Placement.IN_RAY)) {
                    //rayTextField.setVisible(true);
                    rayTextField.setEnabled(true);
                    System.out.println("ray box visible");
                } else {
                    //rayTextField.setVisible(false);
                    rayTextField.setEnabled(false);
                }
            }
        });
        northPanel.add(new JLabel("Placement:"));
        northPanel.add(placementBox);

        seedCountTextField = new JTextField(3);
        northPanel.add(new JLabel("Seeds:"));
        northPanel.add(seedCountTextField);

        rayTextField = new JTextField(3);
        rayTextField.setEnabled(false);
        northPanel.add(new JSeparator());
        northPanel.add(rayTextField);
        //rayTextField.setVisible(false);

        this.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.setBackground(defaultPanelBg);
        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            int seeds, ray = 0;
            try {
                seeds = Integer.parseInt(seedCountTextField.getText());
            } catch (Exception ex) {
                infoLabel.setText("Bad input number!");
                return;
            }

            if (seeds < MIN_INPUT && !Placement.fromString(placementBox.getItemAt(placementBox.getSelectedIndex()).toString()).equals(Placement.EVENLY)) {
                infoLabel.setText("Input must be at least: " + MIN_INPUT);
                return;
            } else if (mainThread == null) {
                if (rayTextField.isEnabled()) {
                    try {
                        ray = Integer.parseInt(rayTextField.getText());
                    } catch (Exception ex) {
                        infoLabel.setText("Bad input!");
                        return;
                    }
                }
                mainThread = new Thread(new StartAction(seeds, ray));
            } else {
                infoLabel.setText("Already running!");
                return;
            }
            mainThread.start();
        });

        southPanel.add(startButton);
        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            System.out.println("stopping thread");
            this.isRunning = false;
        });
        southPanel.add(stopButton);

        this.add(southPanel, BorderLayout.SOUTH);
    }


    private class StartAction implements Runnable {

        public StartAction(int seeds, int ray) {
            drawingPanel.clearCells();
            CellPane.getSeeds().clear();
            CellPane.getToUpdateList().clear();
            Placement p = Placement.fromString(placementBox.getItemAt(placementBox.getSelectedIndex()).toString());
            drawingPanel.setRandomSeeds(seeds, p, ray);
        }

        @Override
        public void run() {
            isRunning = true;
            startButton.setText("Running...");
            startButton.setEnabled(false);
            int s;
            int counter = 0;
            infoLabel.setText("Started");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            while (isRunning) {
                drawingPanel.checkCells(Neighbourhood.fromString(neighbourhoodBox.getItemAt(neighbourhoodBox.getSelectedIndex()).toString()));
                s = CellPane.UpdateCells();
                infoLabel.setText("Finished cycle: " + (++counter) /*+ " new grains: " + s*/);

                if (s == 0) { // all cells filled, no new grains
                    break;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                //break;
            }
            //System.out.println("thread ended");
            infoLabel.setText("Done in " + counter + " cycles");
            startButton.setText("Start");
            startButton.setEnabled(true);
            mainThread = null;
        }
    }

    public static MainFrame getSelf() {
        return self;
    }

    public static void setMessage(String msg) {
        infoLabel.setText(msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = new MainFrame();
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //mainFrame.setLocationRelativeTo(null);
                mainFrame.setLocationByPlatform(true);
                mainFrame.setResizable(false);
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
        });
    }
}
