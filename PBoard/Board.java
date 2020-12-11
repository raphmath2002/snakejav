package com.company.PBoard;

import com.company.PSnake.Snake;
import com.company.PSnake.SnakePart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.*;

public class Board {
    private String[][] game_board;
    private final String foodSymbol = "M";
    private final String spSymbol = "@";
    private final String voidSymbol = "*";
    private static Board gameInstance = null;
    private int sizex;
    private int sizey;
    private boolean hasFood = false;
    private int foodPosY;
    private int foodPosX;
    private JFrame frame;
    private JPanel panel;

    private Board(int sizex, int sizey) {
        this.game_board = new String[sizey][sizex];
        this.sizex = sizex;
        this.sizey = sizey;
        this.frame = new JFrame();
        this.frame.setPreferredSize(new Dimension(800, 800));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setFocusable(true);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    public static Board getInstance(int sizex, int sizey) {
        if (gameInstance == null)
            gameInstance = new Board(sizex, sizey);
        return gameInstance;
    }

    public int startGame() {
        for (String[] board_case : this.game_board) {
            Objects.requireNonNull(this);
            Arrays.fill((Object[])board_case, this.voidSymbol);
        }
        final Snake snake = new Snake(3, 5);
        generateFood(snake);
        applySnake(snake);
        this.frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == 38) {
                    snake.turn("u");
                } else if (keyCode == 40) {
                    snake.turn("d");
                } else if (keyCode == 39) {
                    snake.turn("r");
                } else if (keyCode == 37) {
                    snake.turn("l");
                }
            }

            public void keyReleased(KeyEvent e) {}
        });
        while (snake.getState()) {
            try {
                Thread.sleep(150L);
                snake.move();
                applySnake(snake);
            } catch (Exception e) {
                snake.kill();
            }
        }
        frame.dispose();
        return 0;
    }

    private void generateFood(Snake snake) {
        boolean finded = false;
        while (!finded && !this.hasFood) {
            int foodPosX = (int)(Math.random() * 20.0D);
            int foodPosY = (int)(Math.random() * 20.0D);
            if (foodPosX > this.sizex || foodPosX < 0 || foodPosY > this.sizey || foodPosY < 0)
                continue;
            for (SnakePart part : snake.getSnakeP()) {
                if (part.getPosY() != foodPosY && part.getPosX() != foodPosX) {
                    this.foodPosX = foodPosX;
                    this.foodPosY = foodPosY;
                    this.hasFood = true;
                    finded = true;
                }
            }
        }
    }

    private void applySnake(Snake snake) {
        for (String[] board_case : this.game_board) {
            Objects.requireNonNull(this);
            Arrays.fill((Object[])board_case, this.voidSymbol);
        }
        if (this.hasFood) {
            Objects.requireNonNull(this);
            this.game_board[this.foodPosY][this.foodPosX] = this.foodSymbol;
        }
        ArrayList<SnakePart> snake_parts = snake.getSnakeP();
        boolean snakeAte = false;
        for (SnakePart part : snake_parts) {
            if (part.isHead && this.game_board[part.getPosY()][part.getPosX()].equals(this.foodSymbol))
                snakeAte = true;
            Objects.requireNonNull(this);
            this.game_board[part.getPosY()][part.getPosX()] = this.spSymbol;
        }
        if (snakeAte) {
            this.hasFood = false;
            generateFood(snake);
            snake.addPart();
        }
        this.panel = new JPanel();
        this.panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.7D;
        c.weighty = 0.7D;
        for (int y = 0; y < this.game_board.length; y++) {
            for (int x = 0; x < (this.game_board[y]).length; x++) {
                JLabel grid_char = new JLabel(this.game_board[y][x]);
                Objects.requireNonNull(this);
                if (this.game_board[y][x].equals(this.spSymbol))
                    grid_char.setForeground(Color.red);
                c.gridy = y;
                c.gridx = x;
                this.panel.add(grid_char, c);
            }
        }
        this.frame.add(this.panel);
        this.frame.pack();
    }
}
