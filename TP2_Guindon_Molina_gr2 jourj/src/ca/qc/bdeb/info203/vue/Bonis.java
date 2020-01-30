/**
 * Classe mère qui représente les différents bonis 
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
public class Bonis extends JComponent implements Statique{
    /**
     * L'image utilisée pour représenter le boni
     */
    protected Image img;
     
    /**
     * Variable utilisée pour assigner le type de boni au boni
     */
    protected TypeBonis type;

    public Bonis(String imgGif, TypeBonis type) {
        setSize(32,32);
        img = Toolkit.getDefaultToolkit().getImage(imgGif);
        this.type=type;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(img, 0, 0, this);
    }
    
}
