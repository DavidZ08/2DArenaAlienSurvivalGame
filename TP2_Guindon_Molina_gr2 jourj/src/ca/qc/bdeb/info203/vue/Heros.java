/**
 * Classe utilisée pour représenter les attributs du héro (apparence, fonctions, direction)
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
public class Heros extends JComponent {

    /**
     * Vitesse en x
     */
    private int deltaX = 2;
    /**
     * Vitesse en y
     */
    private int deltaY = 2;
    /**
     * Direction vers laquelle le héro face, initialement vers le bas
     */
    private Direction direction = Direction.BAS;
    /**
     * Toutes les images utilisées pour représenter le héro dans toutes les
     * directions Vu que le héro est vers le bas, on voit le front du héro
     * initialement
     */
    private Image imgHeroFront = Toolkit.getDefaultToolkit().getImage("herofront.gif");
    private Image imgHeroBas = Toolkit.getDefaultToolkit().getImage("heroback.gif");
    private Image imgHeroDroite = Toolkit.getDefaultToolkit().getImage("herodroite.gif");
    private Image imgHeroGauche = Toolkit.getDefaultToolkit().getImage("herogauche.gif");
    private Image imgImage = imgHeroFront;

    public Heros() {
        setSize(22, 51);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgImage, 0, 0, this);
    }

    /**
     * Fonction utilisée pour déplacer le héro pour le faire déplacer à gauche
     * Cette fonction gère son apparence, direction, vitesse lorsqu'il se
     * déplace vers la gauche
     */
    public void choisirGauche() {
        direction = Direction.GAUCHE;
        imgImage = imgHeroGauche;
        setLocation(getX() - deltaX, getY());
        invalidate();
        repaint();
    }

    /**
     * Fonction utilisée pour déplacer le héro pour le faire déplacer à droite
     * Cette fonction gère son apparence, direction, vitesse lorsqu'il se
     * déplace vers la droite
     */
    public void choisirDroite() {
        direction = Direction.DROITE;
        imgImage = imgHeroDroite;
        setLocation(getX() + deltaX, getY());
        invalidate();
        repaint();
    }

    /**
     * Fonction utilisée pour déplacer le héro pour le faire déplacer vers le
     * bas Cette fonction gère son apparence, direction, vitesse lorsqu'il se
     * déplace vers le bas
     */
    public void choisirBas() {
        direction = Direction.BAS;
        imgImage = imgHeroFront;
        setLocation(getX(), getY() + deltaY);
        invalidate();
        repaint();
    }

    /**
     * Fonction utilisée pour déplacer le héro pour le faire déplacer vers le
     * haut Cette fonction gère son apparence, direction, vitesse lorsqu'il se
     * déplace vers le haut
     */
    public void choisirHaut() {
        direction = Direction.HAUT;
        imgImage = imgHeroBas;
        setLocation(getX(), getY() - deltaY);
        invalidate();
        repaint();
    }

    /**
     * 
     * @return la direction actuelle
     */
    public Direction getDirection() {
        return direction;
    }

     /**
     * Permet d'arrêter le héro s'il s'approche d'un objet infranchissable
     */
    public void arreter() {
        if (null != this.getDirection()) {
            switch (this.getDirection()) {
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
