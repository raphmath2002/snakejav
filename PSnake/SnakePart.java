package com.company.PSnake;

/**
 * Segment d'un serpent
 */
public class SnakePart {
    private int posX, posY;
    private String direction;
    public boolean isHead = false;


    /**
     * Constructeur d'un segment de serpent, il se définit par :
     * @param posX -> une position X
     * @param posY -> une position Y
     * @param dir -> une direction (u,d,r,l)
     * @param isHead -> Identifie si le segment est une tete ou non
     */
    public SnakePart(int posX, int posY, String dir, boolean isHead)
    {
        this.posX = posX;
        this.posY = posY;
        this.direction = dir;
        this.isHead = isHead;


    }

    /**
     * Retourne la position X du segment
     * @return int
     */
    public int getPosX() { return this.posX; }

    /**
     * retourne la position Y du segment
     * @return int
     */
    public int getPosY() { return this.posY; }

    /**
     * retourne la direction du segment
     * @return String
     */
    public String getDirection() { return this.direction; }


    /**
     * Définir la position X
     * @param posX -> int
     */
    public void setPosX(int posX) { this.posX = posX; }

    /**
     * Définir la position Y
     * @param posY -> int
     */
    public void setPosY(int posY) { this.posY = posY; }

    /**
     * Définir la direction
     * @param direction -> String (u, d, l, ou r)
     */
    public void setDirection(String direction) { this.direction = direction; }

}
