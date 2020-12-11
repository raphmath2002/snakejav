package com.company;

import com.company.PBoard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        Board game =  Board.getInstance(20, 20);
        game.startGame();

    }
}
