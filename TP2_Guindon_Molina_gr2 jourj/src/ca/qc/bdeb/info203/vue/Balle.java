/**
 * Classe utilisée pour représenter un balle de «shotgun» et son comportement
 */
package ca.qc.bdeb.info203.vue;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author 1679219
 */
public class Balle extends JComponent implements Bougeable {

    /**
     * Vitesse en y
     */
    private int deltaY = 4;
    
    /**
     * Vitesse en x
     */
    private int deltaX = 4;
    
    /**
     * Sert à recevoir la direction du héro actuelle
     */
    private Direction direction;

    public Balle(Direction direction) {
        setSize(25, 25);
        this.direction = direction;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillOval(0, 0, getWidth(), getHeight());
    }

     /**
     * Sert à faire bouger la balle
     * @param x position initiale en x
     * @param y position initiale en y
     */
    @Override
    public void bouger(int x, int y) {
        switch (direction) {
            case HAUT: {
                setLocation(getX(), getY() - deltaY);
                break;
            }
            case BAS: {
                setLocation(getX(), getY() + deltaY);
                break;
            }
            case GAUCHE: {
                setLocation(getX() - deltaX, getY());
                break;
            }
            case DROITE: {
                setLocation(getX() + deltaX, getY());
                break;
            }
        }
    }

}
