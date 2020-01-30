/**
 * Interface utilisée pour différencier les objets statiques(roches,buissons,bonis) des objets mobiles(bougeables)
 */
package ca.qc.bdeb.info203.vue;

import java.awt.Rectangle;

/**
 *
 * @author 1627939
 */
public interface Bougeable { //tous les composants bougeable sauf le hero
    public void bouger(int x,int y);
    public Rectangle getBounds();
}
