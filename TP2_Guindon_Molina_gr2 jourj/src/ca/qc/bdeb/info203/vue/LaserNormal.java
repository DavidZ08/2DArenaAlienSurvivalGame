/**
 * Classe utilisée pour représenter l'arme normale du héro
 */
package ca.qc.bdeb.info203.vue;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author 1679219
 */
public class LaserNormal extends JComponent implements Bougeable{
/**
     * Sert à reçevoir la direction vers laquelle le héro face
     */
    private final Direction dir;

    public LaserNormal(Direction direction) {
        this.dir=direction;
        switch(dir){
            case HAUT:
            case BAS:
        setSize(5, 20);
                break;
            case GAUCHE:
            case DROITE:
        setSize(20, 5);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
/**
     * Sert à faire bouger le laser dans les 4 directions
     * @param x location en x initiale
     * @param y location en y initiale
     */
@Override
    public void bouger(int x, int y) { 
        switch (dir) {
            case HAUT: {
                setLocation(getX(), getY() - 4);
                break;
            }
            case BAS: {
                setLocation(getX(), getY() + 4);
                break;
            }
            case GAUCHE: {
                setLocation(getX() - 4, getY());
                break;
            }
            case DROITE: {
                setLocation(getX() + 4, getY());
                break;
            }
        }
    }

    
}
