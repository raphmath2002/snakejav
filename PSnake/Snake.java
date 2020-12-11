package com.company.PSnake;

import java.util.ArrayList;

/**
 * Représente le serpent, qui est une composition de Snakepart <br/>
 * ArrayList<SnakePart> snakep_list -> liste dynamique contenant les segments du serpents.<br/>
 * boolean living -> état de vie du serpent<br/>
 */
public class Snake {

    private final ArrayList<SnakePart> snakep_list = new ArrayList<>();
    private boolean living = true;

    /**
     * Constructeur du serpent, il va créer les segments SnakePart de base du serpent afin de commencer une partie
     * @param f_posX  position X sur la grille
     * @param f_posY  position Y ...
     */
    public Snake(int f_posX, int f_posY)
    {
        SnakePart firstPart = new SnakePart(f_posX, f_posY, "r", true);
        SnakePart secondPart = new SnakePart(f_posX-1, f_posY, "r", false);
        this.snakep_list.add(firstPart);
        this.snakep_list.add(secondPart);
    }

    /**
     * Retourne l'état du serpent
     * @return living
     */
    public boolean getState()
    {
        return this.living;
    }

    /**
     * Retourne le tableau de segments
     * @return snakep_list
     */
    public ArrayList<SnakePart> getSnakeP()
    {
        return snakep_list;
    }

    /**
     * Ajoute 1 segment au serpent
     */
    public void addPart()
    {
        //on prend le dernier segment
        SnakePart last = this.snakep_list.get(snakep_list.size()-1);
        int lastPartPosX = last.getPosX();
        int lastPartPosY = last.getPosY();

        String lastDirection = last.getDirection();

        //Et on positionne le nouveau segment selon la position et la direction du dernier
        if(lastDirection.equals("r"))
            this.snakep_list.add(new SnakePart(lastPartPosX-1, lastPartPosY, "r", false));
        if(lastDirection.equals("l"))
            this.snakep_list.add(new SnakePart(lastPartPosX+1, lastPartPosY, "l", false));
        if(lastDirection.equals("u"))
            this.snakep_list.add(new SnakePart(lastPartPosX, lastPartPosY+1, "u", false));
        if(lastDirection.equals("d"))
            this.snakep_list.add(new SnakePart(lastPartPosX, lastPartPosY-1, "d", false));
    }

    /**
     * Avance le serpent de 1
     */
    public void move()
    {
        for (SnakePart cur_part : snakep_list)
        {
            int pos = snakep_list.indexOf(cur_part);
            if (pos == 0) {
                if(!moveHead(cur_part)) this.kill();
            }
            else {
                String cur_dir = cur_part.getDirection();
                if ("r".equals(cur_dir)) {
                    cur_part.setPosX(cur_part.getPosX() + 1);
                } else if ("l".equals(cur_dir)) {
                    cur_part.setPosX(cur_part.getPosX() - 1);
                } else if ("u".equals(cur_dir)) {
                    cur_part.setPosY(cur_part.getPosY() - 1);
                } else if ("d".equals(cur_dir)) {
                    cur_part.setPosY(cur_part.getPosY() + 1);
                } else {
                    System.err.println("Error move body index: " + pos);
                }
            }
        }
        for (int i = this.snakep_list.size()-1; i >= 0; i--) {
            if(i != 0) this.snakep_list.get(i).setDirection(this.snakep_list.get(i-1).getDirection());
        }
    }

    /**
     * Tourne le serpent en changeant la direction de la tete
     * @param direction
     */
    public void turn(String direction)
    {
        boolean no_return;
        SnakePart head = this.snakep_list.get(0);
        SnakePart afterHead = this.snakep_list.get(1);

        if ("r".equals(direction)) {
            no_return = head.getPosX() + 1 != afterHead.getPosX();
        } else if ("l".equals(direction)) {
            no_return = head.getPosX() - 1 != afterHead.getPosX();
        } else if ("u".equals(direction)) {
            no_return = head.getPosY() - 1 != afterHead.getPosY();
        } else if ("d".equals(direction)) {
            no_return = head.getPosY() + 1 != afterHead.getPosY();
        } else {
            no_return = true;
        }

        if(!head.getDirection().equals(direction) && no_return) {
            head.setDirection(direction);
        }
    }

    /**
     * Permet de savoir si un segemnt de serpent se trouve à la postion donnée
     * @param posX
     * @param posY
     * @return boolean
     */
    private boolean isAPartHere(int posX, int posY)
    {
        for(SnakePart part : this.snakep_list)
        {
            if(part.getPosX() == posX && part.getPosY() == posY) return true;
        }
        return false;
    }


    /**
     * Faire avancer la tete, detecter si le serpent se mord la queue (si c'est le cas, return false)
     * @param head -> tete du serpent
     * @return boolean
     */
    private boolean moveHead(SnakePart head)
    {
        String direction = head.getDirection();

        if ("r".equals(direction)) {
            if (isAPartHere(head.getPosX() + 1, head.getPosY())) {
                return false;
            } else head.setPosX(head.getPosX() + 1);
        } else if ("l".equals(direction)) {
            if (isAPartHere(head.getPosX() - 1, head.getPosY())) {
                return false;
            } else head.setPosX(head.getPosX() - 1);
        } else if ("u".equals(direction)) {
            if (isAPartHere(head.getPosX(), head.getPosY() - 1)) {
                return false;
            } else head.setPosY(head.getPosY() - 1);
        } else if ("d".equals(direction)) {
            if (isAPartHere(head.getPosX(), head.getPosY() + 1)) {
                return false;
            } else head.setPosY(head.getPosY() + 1);
        } else {
            System.err.println("Error head direction");
        }
        return true;
    }
    /**
     * Tuer le serpent
     */
    public void kill()
    {
        this.living = false;
    }

}
