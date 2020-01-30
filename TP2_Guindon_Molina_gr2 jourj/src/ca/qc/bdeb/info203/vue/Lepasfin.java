/**
 * Classe utilisée pour spécifier 
 * le comportement,l'apparence et les points(points de vie, points(score), et dégâts infligés) d'un ennemi "LePasFin"
 * Est un enfant de Ennemis
 */
package ca.qc.bdeb.info203.vue;



/**
 *
 * @author 1627939
 */
public class Lepasfin extends Ennemis{
    
    public Lepasfin() {
        super(2,1,2,"bleufront.gif","bleuback.gif",TypeEnnemis.LEPASFIN);
    }
    
}
