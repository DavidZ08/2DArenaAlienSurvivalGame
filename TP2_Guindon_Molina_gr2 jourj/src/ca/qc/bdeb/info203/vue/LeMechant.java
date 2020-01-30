/**
 * Classe utilisée pour spécifier 
 * le comportement,l'apparence et les points(points de vie, points(score), et dégâts infligés) d'un ennemi "LeMechant"
 * Est un enfant de Ennemis
 */
package ca.qc.bdeb.info203.vue;




/**
 *
 * @author 1627939
 */
public class LeMechant extends Ennemis{
    
    public LeMechant() {
        super(1,1,1,"greenfront.gif","greenback.gif",TypeEnnemis.LEMECHANT);
    }
    
}
