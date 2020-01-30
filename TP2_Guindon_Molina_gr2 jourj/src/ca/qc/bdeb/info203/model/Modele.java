/**
 * Le modèle sure lequel la "Vue" se base
 */
package ca.qc.bdeb.info203.model;

import java.util.Observable;

/**
 *
 * @author 1679219
 */
public class Modele extends Observable {

    public int pointage;
    public int pointDeVie=3;

    public Modele() {
    }
    
    /**
     * 
     * @return le score
     */
    public int getPointage() {
        return pointage;
    }

    public int getPointDeVie() {
        return pointDeVie;
    }

    public void diminuerVie() {
        pointDeVie--;
        miseAJour();
    }

    public void recupererVie() {
        pointDeVie = 3;
        miseAJour();
    }

    public void augmenterPointsLPF() {
        pointage += 2;
        miseAJour();
    }

    public void augmenterPointsLM() {
        pointage += 1;
        miseAJour();
    }

    public void augmenterPointsLTF() {
        pointage += 3;
        miseAJour();
    }

    public void augmenterPointsBoost() {
        pointage += 5;
        miseAJour();
    }
    
    public void reinitialiserPoints(){
        pointage = 0;
        miseAJour();
    }

    public void miseAJour() {
        setChanged();
        notifyObservers();
    }
    
}
