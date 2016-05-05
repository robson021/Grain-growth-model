package robert.gui;

import robert.model.Neighbourhood;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static DrawingPanel drawingPanel;
    private Thread mainThread = null;
    private boolean isRunning = false;
    private JComboBox comboBox;

    public MainFrame() throws HeadlessException {
        super("Rekrystalizacja");
        this.setLayout(new BorderLayout());
        drawingPanel = new DrawingPanel();

        this.add(drawingPanel, BorderLayout.CENTER);
        JPanel northPanel = new JPanel(new FlowLayout());

        northPanel.add(new JLabel("Options:"));

        comboBox = new JComboBox(Neighbourhood.values());
        northPanel.add(comboBox);

        this.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            if (mainThread == null && !isRunning) {
                mainThread = new Thread(new StartAction());
            } else return;
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

        @Override
        public void run() {
            isRunning = true;
            System.out.println("Thead Started!");

            while (isRunning) {
                try {
                    drawingPanel.checkCells(Neighbourhood.fromString(comboBox.getItemAt(comboBox.getSelectedIndex()).toString()));
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            mainThread = null;
            System.out.println("thread ended");
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
