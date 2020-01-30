/**
 * Classe utilisée pour représenter un buisson
 */
package ca.qc.bdeb.info203.vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

/**
 *
 * @author 1679219
 */
public class Buisson extends JComponent implements Statique {

     /**
     * L'image utilisée pour représenter un buisson
     */
    private Image imgBuisson = Toolkit.getDefaultToolkit().getImage("buisson1.gif");

    public Buisson() {
        setSize(32,32);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.drawImage(imgBuisson, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
