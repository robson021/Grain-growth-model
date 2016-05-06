package robert.gui;

import robert.model.Neighbourhood;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static DrawingPanel drawingPanel;
    private Thread mainThread = null;
    private boolean isRunning = false;
    private JComboBox comboBox;
    private JTextField seedCountTextField;
    private JLabel infoLabel;

    public MainFrame() throws HeadlessException {
        super("Rekrystalizacja");
        this.setLayout(new BorderLayout());
        drawingPanel = new DrawingPanel();

        this.add(drawingPanel, BorderLayout.CENTER);
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setBackground(Color.GRAY);

        infoLabel = new JLabel("[info]");
        infoLabel.setForeground(Color.GREEN.darker());
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
        southPanel.setBackground(Color.GRAY);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            int seeds;
            try {
                seeds = Integer.parseInt(seedCountTextField.getText());
            } catch (Exception ex) {
                infoLabel.setText("Bad input number!");
                return;
            }
            if (mainThread == null && seeds > 0) {
                mainThread = new Thread(new StartAction(seeds));
            } else {
                infoLabel.setText("Already running!");
                return;
            }
            mainThread.start();
        });

        southPanel.add(startButton);
        JButton stopButton = new JButton("Stop");
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
            drawingPanel.setRandomSeeds(seeds);
        }

        @Override
        public void run() {
            isRunning = true;
            infoLabel.setText("Started");
            //System.out.println("Thead Started!");
            while (isRunning) {
                try {
                    drawingPanel.checkCells(Neighbourhood.fromString(comboBox.getItemAt(comboBox.getSelectedIndex()).toString()));
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            //System.out.println("thread ended");
            infoLabel.setText("Stopped");
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
