/**
 * /* Classe utilisée pour les roches dans les 4 coins du monde
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
public class Roche extends JComponent implements Statique {
    
//Image de la roche utilisée
    private Image imgRoche = Toolkit.getDefaultToolkit().getImage("roche1.gif");

    public Roche() {
        setSize(32, 32);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgRoche, 0, 0, this);
    }

}
