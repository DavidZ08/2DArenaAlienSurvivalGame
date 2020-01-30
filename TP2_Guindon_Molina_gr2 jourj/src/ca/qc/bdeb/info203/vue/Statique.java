/**
 * Interface utilisée pour différencier les objets statiques(roches,buissons,bonis) des objets mobiles(bougeables)
 */
package ca.qc.bdeb.info203.vue;

import java.awt.Rectangle;

/**
 *
 * @author 1679219
 */
public interface Statique {
    public Rectangle getBounds();
}
