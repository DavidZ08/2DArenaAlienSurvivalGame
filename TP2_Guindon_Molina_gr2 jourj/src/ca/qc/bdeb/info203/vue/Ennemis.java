/**
 * Classe mère utilisée pour représenter les ennemis
 */
package ca.qc.bdeb.info203.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

/**
 *
 * @author 1627939
 */
public abstract class Ennemis extends JComponent implements Bougeable {

    /**
     * Variables qui détermine le comportement de l'ennmi
     */
    protected int vitesse;
    protected int pointsDeVie;
    protected int points;

    /**
     * Strings qui contrôle l'apparence de l'ennemi
     */
    protected String imgFront;
    protected String imgBack;
    protected Image imgImage;

    /**
     * Variable utilisée pour déterminer le type de l'ennemi
     */
    protected TypeEnnemis type;

    public Ennemis(int vitesse, int pointsDeVie, int points, String imgFront, String imgBack, TypeEnnemis type) {
        this.vitesse = vitesse;
        this.pointsDeVie = pointsDeVie;
        this.points = points;
        this.imgFront = imgFront;
        this.imgBack = imgBack;
        this.type = type;
        setSize(35, 43);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgImage, 0, 0, this);
    }

    /**
     * Permet de déplacer l'ennemi par rapport à la position du héro
     *
     * @param xHero position en x du héro
     * @param yHero position en y du héro
     */
    @Override
    public void bouger(int xHero, int yHero) {
        if (yHero > getY()) {
            imgImage = Toolkit.getDefaultToolkit().getImage(imgFront);
            setLocation(getX(), getY() + vitesse);
        } else if (yHero < getY()) {
            imgImage = Toolkit.getDefaultToolkit().getImage(imgBack);
            setLocation(getX(), getY() - vitesse);
        } else {
            imgImage = Toolkit.getDefaultToolkit().getImage(imgFront);
            setLocation(getX(), getY());
        }
        if (xHero > getX()) {
            setLocation(getX() + vitesse, getY());
        } else {
            setLocation(getX() - vitesse, getY());
        }

        invalidate();
        repaint();
    }

    /**
     * Permet d'arrêter l'ennemi s'il s'approche d'un objet infranchissable
     *
     * @param direction direction actuelle du héro
     */
    public void arreter(Direction direction) {
        if (null != direction) {
            switch (direction) {
                case HAUT: {
                    this.setLocation(this.getX(), this.getY() + 3);
                    break;
                }
                case BAS: {
                    this.setLocation(this.getX(), this.getY() - 3);
                    break;
                }
                case GAUCHE: {
                    this.setLocation(this.getX() + 3, this.getY());
                    break;
                }
                case DROITE: {
                    this.setLocation(this.getX() - 3, this.getY());
                    break;
                }
                default: {
                    System.out.println("Erreur");
                    break;
                }
            }
        }
    }

}
