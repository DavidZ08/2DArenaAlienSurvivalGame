/**
 * Le contrôleur qui contrôle la vue et le modèle
 */
package ca.qc.bdeb.info203.controleur;

import ca.qc.bdeb.info203.model.Modele;
import ca.qc.bdeb.info203.vue.Fenetre;
import ca.qc.bdeb.info203.vue.TypeEnnemis;

/**
 *
 * @author 1679219
 */
public class Controleur {

    private Modele modele = new Modele();
    private Fenetre fenetre = new Fenetre(modele, this);

    public Controleur() {
    }

    /**
     * Dit au modèle de récupérer ls points de vie
     */
    public void recupererVie() {
        modele.recupererVie();
    }

    /**
     * Dit au modèle de diminuer les points de 
     */
    public void diminuerVie() {
        modele.diminuerVie();
    }

    /**
     * Dit au modèle d'augmenter les points selon le type d'ennemi abattu
     * @param type le type d'ennemi
     */
    public void augmenterPoints(TypeEnnemis type) {
        switch (type) {
            case LEPASFIN: {
                modele.augmenterPointsLPF();
                break;
            }
            case LEMECHANT: {
                modele.augmenterPointsLM();
                break;
            }
            case LETROUBLEFETE: {
                modele.augmenterPointsLTF();
                break;
            }
            default: {
               
                break;
            }
        }
    }
    
    public void augmenterPointsBoost(){
        modele.augmenterPointsBoost();
    }

}
