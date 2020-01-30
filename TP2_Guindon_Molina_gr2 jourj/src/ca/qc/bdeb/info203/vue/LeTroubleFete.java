/**
 * Classe utilisée pour spécifier 
 * le comportement,l'apparence et les points(points de vie, points(score), et dégâts infligés) d'un ennemi "LeTroubleFete"
 * Est un enfant de Ennemis
 */
package ca.qc.bdeb.info203.vue;



/**
 *
 * @author 1627939
 */
public class LeTroubleFete extends Ennemis{
    
    public LeTroubleFete() {
        super(1,2,3,"purplefront.gif","purpleback.gif",TypeEnnemis.LETROUBLEFETE);
    }
    
}
