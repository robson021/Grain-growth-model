package robert.gui;

import robert.model.Neighbourhood;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static DrawingPanel drawingPanel;
    private static final int MIN_INPUT = 50;
    private Thread mainThread = null;
    private boolean isRunning = false;
    private JComboBox comboBox;
    private JTextField seedCountTextField;
    private JLabel infoLabel;
    private JButton startButton, stopButton;

    public MainFrame() throws HeadlessException {
        super("Recrystallization model");
        this.setLayout(new BorderLayout());
        drawingPanel = new DrawingPanel();

        Color defaultPanelBg = Color.YELLOW.darker();

        this.add(drawingPanel, BorderLayout.CENTER);
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setBackground(defaultPanelBg);

        infoLabel = new JLabel("[info]");
        infoLabel.setForeground(Color.RED.darker());
        //infoLabel.setMinimumSize(new Dimension(20, infoLabel.getHeight()));
        northPanel.add(infoLabel);
        northPanel.add(new JSeparator());
        northPanel.add(new JSeparator());

        northPanel.add(new JLabel("Neighbourhood type:"));

        comboBox = new JComboBox(Neighbourhood.values());
        northPanel.add(comboBox);

        seedCountTextField = new JTextField(3);
        northPanel.add(new JLabel("Seeds:"));
        northPanel.add(seedCountTextField);

        this.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.setBackground(defaultPanelBg);
        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            int seeds;
            try {
                seeds = Integer.parseInt(seedCountTextField.getText());
            } catch (Exception ex) {
                infoLabel.setText("Bad input number!");
                return;
            }

            if (seeds < MIN_INPUT) {
                infoLabel.setText("Input must be at least: " + MIN_INPUT);
                return;
            } else if (mainThread == null) {
                mainThread = new Thread(new StartAction(seeds));
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

        public StartAction(int seeds) {
            drawingPanel.clearCells();
            CellPane.getSeeds().clear();
            CellPane.getToUpdateList().clear();
            drawingPanel.setRandomSeeds(seeds);
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
                drawingPanel.checkCells(Neighbourhood.fromString(comboBox.getItemAt(comboBox.getSelectedIndex()).toString()));
                s = CellPane.UpdateCells();
                infoLabel.setText("Finished cycle: " + (++counter) /*+ " new grains: " + s*/);

                if (s == 0) {
                    break;
                } // all cells filled, no new grains

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                //break;
            }
            //System.out.println("thread ended");
            infoLabel.setText("Done");
            startButton.setText("Start");
            startButton.setEnabled(true);
            mainThread = null;
        }
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
