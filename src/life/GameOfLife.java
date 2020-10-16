package life;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOfLife extends JFrame {

    private GameOfLifeModel gol;
    private JLabel generationLabel;
    private JLabel aliveLabel;
    private Grid grid;
    final private int cellSize = 7;
    private final int size = 100;
    private final int width = size * cellSize + 175;
    private final int height = size * cellSize + 60;
    private int fps = 60;
    private final Timer timerPlay = new Timer(1000 / fps, actionEvent -> nextGeneration());


    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setResizable(false);
        setVisible(true);
        gol.nextGeneration();
        timerPlay.start();
    }
    private void initComponents() {
        gol = new GameOfLifeModel(size);
        setSize(width, height);
        Container c =  new Container();
        setContentPane(c);

        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        generationLabel.setText("Generation #" + gol.generation);
        generationLabel.setBounds(10, 50, 100, 10);
        c.add(generationLabel);

        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: " + gol.aliveCells);
        aliveLabel.setBounds(10, 70, 100, 10);
        c.add(aliveLabel);

        JToggleButton PlayToggleButton = new JToggleButton();
        try {
            Image PlayToggleIcon = ImageIO.read(new File("play-resume-button.png")).getScaledInstance(45, 45, Image.SCALE_DEFAULT);
            PlayToggleButton.setIcon(new ImageIcon(PlayToggleIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlayToggleButton.addActionListener(actionEvent -> {
            if (PlayToggleButton.isSelected()) {
                timerPlay.stop();
            } else timerPlay.start();
        });
        PlayToggleButton.setName("PlayToggleButton");
        PlayToggleButton.setBounds(10, 10, 30, 30);
        c.add(PlayToggleButton);

        JButton ResetButton = new JButton();
        try {
            Image ResetButtonIcon = ImageIO.read(new File("ResetButtonIcon.png")).getScaledInstance(45, 45, Image.SCALE_DEFAULT);
            ResetButton.setIcon(new ImageIcon(ResetButtonIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResetButton.addActionListener(ActionEvent -> {
            if (!ResetButton.isSelected()) {
                restartGame();
            }
        });
        ResetButton.setName("ResetButton");
        ResetButton.setBounds(50, 10, 30, 30);
        c.add(ResetButton);

        JLabel fpsLabel = new JLabel();
        fpsLabel.setName("FPSLabel");
        fpsLabel.setBounds(10, 90, 100, 20);
        fpsLabel.setText("Current fps = " + fps);
        c.add(fpsLabel);
        JSlider speedSlider = new JSlider();
        speedSlider.setOrientation(SwingConstants.VERTICAL);
        speedSlider.setValue(fps);
        speedSlider.setName("SpeedSlider");
        speedSlider.setBounds(5, 110, 80, 150);
        speedSlider.setMajorTickSpacing(12);
        speedSlider.setMaximum(144);
        speedSlider.setMinimum(0);
        speedSlider.setSnapToTicks(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(changeEvent -> {
            fps = speedSlider.getValue();
            if (fps == 0) {
                timerPlay.stop();
            } else {
                timerPlay.start();
                timerPlay.setDelay(1000 / fps);
            }
            fpsLabel.setText("Current fps = " + fps);
        });
        c.add(speedSlider);

        grid = new Grid();
        grid.setBounds(140, 10, (cellSize + 1) * size,(cellSize + 1) * size);
        c.add(grid);
    }

    private void restartGame() {
        gol = new GameOfLifeModel(size);
        setSize(width, height);
        generationLabel.setText("Generation #" + gol.generation);
        aliveLabel.setText("Alive: " + gol.aliveCells);
        grid.repaint();
        gol.nextGeneration();
    }

    private void nextGeneration () {
        generationLabel.setText("Generation #" + gol.generation);
        aliveLabel.setText("Alive: " + gol.aliveCells);
        grid.repaint();
        gol.nextGeneration();
    }


    private class Grid extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < gol.size; i++) {
                for (int j = 0; j < gol.size; j++) {
                    g.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    if (gol.grid[i][j] == 1) {
                        g.setColor(Color.BLACK);
                        g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    }
                }
            }

        }
    }
}
