package TickTock.Chrono.View;


import TickTock.Chrono.Controller.Chrono;
import TickTock.Chrono.util.State;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class SwingGui extends JFrame {

    private static JLabel chronoDisplay;
    private JPanel chronoOffsetButtonsPanel;
    private final JButton chronoToggleButton;
    private final JButton configButton;
    private int[] offsetResetValues;
    private int index = 0;

    public static void build() {
        Chrono.setState(State.TIME);
        EventQueue.invokeLater(() -> new SwingGui().setVisible(true));
    }

    private SwingGui() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());

        JPanel chronoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        chronoDisplay = new JLabel();
        chronoDisplay.setFont(new Font("Serif", Font.BOLD, 32));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        chronoToggleButton = new JButton();
        chronoToggleButton.setText(Chrono.getState() == State.CONFIG ? "CANCEL" : Chrono.getState().name());


        // Action listener
        chronoToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (chronoToggleButton.getText().equals("CANCEL")) {
                    Chrono.getChronoType().setOffset(-offsetResetValues[0], -offsetResetValues[1], -offsetResetValues[2]);
                    showConfigButtons(false);
                    offsetResetValues = new int[3];
                    configButton.setText("CONFIG");
                } else {
                    Chrono.setState(Chrono.getState() == State.DATE ? State.TIME : State.DATE);

                }
                chronoToggleButton.setText(Chrono.getState().name());

                try {
                    updateDisplay();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        configButton = new JButton("CONFIG");

        // Action listener
        configButton.addActionListener(e -> {


            if (configButton.getText().equals("CONFIG")) {
                index = 0;
                offsetResetValues = new int[3];
                showConfigButtons(true);
                configButton.setText("SAVE");
                chronoToggleButton.setText("CANCEL");

            } else {

                showConfigButtons(false);
                configButton.setText("CONFIG");
                chronoToggleButton.setText(Chrono.getState().name());
            }
        });

        chronoPanel.add(chronoDisplay);
        buttonPanel.add(chronoToggleButton);
        buttonPanel.add(configButton);
        getContentPane().add(chronoPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        try {
            updateDisplay();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //public void updateDisplay(String value) {timeLabel.setText(value);}

    private void showConfigButtons(Boolean enableChangeButtons) {

        if (enableChangeButtons) {

            chronoOffsetButtonsPanel = new JPanel();
            chronoOffsetButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            JLabel offsetIndex = new JLabel();
            offsetIndex.setText(String.format("%s: #%d", "Index: ", index));
            chronoOffsetButtonsPanel.add(offsetIndex);
            // Increase  button
            JButton plusBtn = new JButton("+");
            plusBtn.addActionListener(e -> {
                offsetResetValues[index]++;
                Chrono.getChronoType().setOffset(index == 0 ? 1 : 0, index == 1 ? 1 : 0, index == 2 ? 1 : 0);
                chronoDisplay.setText(Chrono.getChronoType().getInstant());
            });

            // Subtract button
            JButton minusBtn = new JButton("-");
            minusBtn.addActionListener(e -> {
                offsetResetValues[index]--;
                Chrono.getChronoType().setOffset(index == 0 ? -1 : 0, index == 1 ? -1 : 0, index == 2 ? -1 : 0);
                chronoDisplay.setText(Chrono.getChronoType().getInstant());
            });

            // Next button
            JButton nextBtn = new JButton("NEXT");
            nextBtn.addActionListener(e -> {
                if (index < 2) {
                    index++;
                } else {
                    index = 0;
                }
                offsetIndex.setText(String.format("%s: #%d", "Index: ", index));

            });


            chronoOffsetButtonsPanel.add(plusBtn);
            chronoOffsetButtonsPanel.add(minusBtn);
            chronoOffsetButtonsPanel.add(nextBtn);
            getContentPane().add(chronoOffsetButtonsPanel, BorderLayout.CENTER);

        } else {
            getContentPane().remove(chronoOffsetButtonsPanel);
            revalidate();
            repaint();
        }

    }


    private static void updateDisplay() throws InterruptedException {
        if (Chrono.getState() == State.TIME) {
            new Thread(() -> {
                for (; ;) {
                    try {
                        chronoDisplay.setText(Chrono.getChronoType().getInstant());
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            chronoDisplay.setText(Chrono.getChronoType().getInstant());
        }
    }
}
