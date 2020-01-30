/**
 * Classe qui contient le jeu
 */
package ca.qc.bdeb.info203.vue;

import ca.qc.bdeb.info203.controleur.Controleur;
import ca.qc.bdeb.info203.model.Modele;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author 1679219
 */
public class Monde extends JPanel implements Observer {

    /**
     * Images permettant de représenter l'eau, les coeurs et le gazon
     */
    private Image imgGazon = Toolkit.getDefaultToolkit().getImage("floor1.gif");
    private Image imgCoeur = Toolkit.getDefaultToolkit().getImage("coeur.gif");
    private Image imgWater = Toolkit.getDefaultToolkit().getImage("water.gif");
    /**
     * JLabel qui permet de mettre le pointage dessus
     */
    private JLabel lblPointage = new JLabel();
    /**
     * Roche utilisée dans la création d'autres roches
     */
    private Roche roche = new Roche();

    /**
     * Les 4 buissons placés au 4 coins
     */
    private Buisson buisson1 = new Buisson();
    private Buisson buisson2 = new Buisson();
    private Buisson buisson3 = new Buisson();
    private Buisson buisson4 = new Buisson();

    /**
     * Bernard, le personnage que nous contrôlons
     */
    private Heros hero = new Heros();

    /**
     * Toutes les ArrayList importantes pour le fonctionnement du programme
     * lstStatique et lstBougeables sont les liste qui contiennents des objets
     * qui implémentent les inter faces "Statique" et "Bougeable"
     * lstStatiqueAEnlever et lstBougeablesAEnlever sont utilisée pour enlever
     * les composantes de l'écran après une collision
     */
    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();
    private ArrayList<Bougeable> lstBougeables = new ArrayList<>();
    private ArrayList<Bougeable> lstBougeablesAEnlever = new ArrayList<>();
    private ArrayList<Statique> lstStatique = new ArrayList<>();
    private ArrayList<Statique> lstStatiqueAEnlever = new ArrayList<>();
    private ArrayList<Ennemis> lstNouveauxEnnemis = new ArrayList<>();

    /**
     * La largeur et la hauteur du monde
     */
    private static final int LARGEUR = 800;
    private static final int HAUTEUR = 608;

    /**
     *
     */
    private int espaceX = LARGEUR - 32 - 10 * 32;
    private int espaceY = HAUTEUR - 32 - 8 * 32;
    private int entreeDebutX = 5 * 32;
    private int entreeDebutY = 4 * 32;

    /**
     * les différents int pour le jeu nbVie = les points de vie pointage = le
     * score difficulte = difficulté qui augmente avec le temps qui passe
     * countDown = "Cooldown" utilisé pour les tirs de laser
     */
    private int nbVie = 3;
    private int pointage = 0;
    private int difficulte = 0;
    private int countDown = 0;
    /**
     * Le temps qui passe dans le "Thread"
     */
    private double temps = 0;
    
    /**
     * Boolean qui détermine si la partie est terminée
     */
    private boolean gameOver = false;

     /**
     * Le contrôleur et le modèle pour le MVC
     */
    private Controleur controleur;
    private Modele modele;
    private Espace espace = Espace.LASER;
    private int nbDeCoupsBalles = 0;
    private int nbDeBombe = 0;

    Monde(ArrayList<Integer> listeKeyCodes, Modele modele, Controleur controleur) {
        modele.addObserver(this);
        setLayout(null);
        setPreferredSize(new Dimension(LARGEUR, HAUTEUR + 96));
        this.listeKeyCodes = listeKeyCodes;
        this.controleur = controleur;
        this.modele = modele;

        creerPanneau();

        gameThread.start();
    }

     /**
     * Crée l'espace de jeu avec le gazon, les roches et les buissons
     */
    private void creerPanneau() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 22; j++) {
                if ((i == 0 || i == 24) && (j < 4 || (j > 18 - 4 && j < 19))) {
                    roche = new Roche();
                    add(roche);
                    roche.setLocation(32 * i, 32 * j);
                    lstStatique.add(roche);
                }
                if ((j == 0 || j == 18) && (i < 5 || (i > 24 - 5 && i < 25))) {
                    roche = new Roche();
                    add(roche);
                    roche.setLocation(32 * i, 32 * j);
                    lstStatique.add(roche);
                }
            }
        }
        add(buisson1);
        buisson1.setLocation(32 * 3, 32 * 3);
        lstStatique.add(buisson1);
        add(buisson2);
        buisson2.setLocation(32 * 21, 32 * 3);
        lstStatique.add(buisson2);
        add(buisson3);
        buisson3.setLocation(32 * 3, 32 * 15);
        lstStatique.add(buisson3);
        add(buisson4);
        buisson4.setLocation(32 * 21, 32 * 15);
        lstStatique.add(buisson4);
        add(lblPointage);
        add(hero);
        hero.setLocation(390, 440);

        nouveauxEnnemis(difficulte);
        choixEntree();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 22; j++) {
                if (i >= 0 && i < 25) {
                    if (j >= 0 && j < 19) {
                        g.drawImage(imgGazon, 32 * i, 32 * j, this);
                    } else if (j >= 19) {
                        g.drawImage(imgWater, 32 * i, 32 * j, this);
                    }
                }
                if (j == 20 && i > 24 - nbVie && i < 25) {
                    g.drawImage(imgCoeur, 32 * i, 32 * j, this);
                }
                lblPointage.setSize(lblPointage.getText().length() * 50, 32);
                lblPointage.setFont(new java.awt.Font("Serif", 1, 30));
                lblPointage.setForeground(Color.BLACK);
                lblPointage.setText("Points: " + pointage);
                lblPointage.setLocation(32, 32 * 20);
            }
        }
    }

    /**
     * Permet de créer des vagues d'ennemis plus en plus difficiles en relation avec le temps qui passe
     * @param difficulte degré de difficulté qui augmente avec le temps
     */
    private void nouveauxEnnemis(int difficulte) {
        int type;
        if (difficulte < 4) {
            for (int i = 0; i < 2 + difficulte; i++) {
                type = aleatoire(1, 2);
                if (type == 1) {
                    LeMechant leMechant = new LeMechant();
                    lstNouveauxEnnemis.add(leMechant);
                } else if (type == 2) {
                    LeTroubleFete leTroubleFete = new LeTroubleFete();
                    lstNouveauxEnnemis.add(leTroubleFete);
                }
            }
        } else if (difficulte >= 4 && difficulte < 10) {
            for (int i = 0; i < 3 + difficulte; i++) {
                type = aleatoire(1, 3);
                switch (type) {
                    case 1:
                        LeMechant leMechant = new LeMechant();
                        lstNouveauxEnnemis.add(leMechant);
                        break;
                    case 2:
                        LeTroubleFete leTroubleFete = new LeTroubleFete();
                        lstNouveauxEnnemis.add(leTroubleFete);
                        break;
                    case 3:
                        Lepasfin lepasfin = new Lepasfin();
                        lstNouveauxEnnemis.add(lepasfin);
                        break;
                    default:
                        break;
                }
            }
        } else if (difficulte >= 10 && difficulte < 20) {
            for (int i = 0; i < 4 + difficulte; i++) {
                type = aleatoire(2, 3);
                if (type == 1) {
                    LeMechant leMechant = new LeMechant();
                    lstNouveauxEnnemis.add(leMechant);
                } else if (type == 2) {
                    LeTroubleFete leTroubleFete = new LeTroubleFete();
                    lstNouveauxEnnemis.add(leTroubleFete);
                }
            }
        } else if (difficulte >= 20) {
            for (int i = 0; i < 5 + difficulte; i++) {
                LeTroubleFete leTroubleFete = new LeTroubleFete();
                lstNouveauxEnnemis.add(leTroubleFete);
            }
        }
    }

    /**
     * Le thread qui contrôle les mouvements et les collisions
     */
    Thread gameThread = new Thread() {
        @Override
        public void run() {
            while (true) {
                while (!gameOver) {
                    if (temps % 3000 == 0) {
                        nouveauxEnnemis(difficulte);
                        choixEntree();
                        difficulte++;
                    }
                    choisirActionHeros(listeKeyCodes);
                    for (Bougeable bougeable : lstBougeables) {
                        bougeable.bouger(hero.getX(), hero.getY());
                        if (bougeable instanceof LaserNormal || bougeable instanceof Balle) {
                            if (toucheUnBord((JComponent) bougeable, LARGEUR, HAUTEUR)) {
                                lstBougeablesAEnlever.add(bougeable);
                            }
                            for (Statique statique : lstStatique) {
                                if (bougeable.getBounds().intersects(statique.getBounds())) {
                                    lstBougeablesAEnlever.add(bougeable);
                                }
                            }
                            for (Bougeable bougeables : lstBougeables) {
                                if (bougeables instanceof Ennemis) {
                                    if (bougeable.getBounds().intersects(bougeables.getBounds())) {
                                        bonus((Ennemis) bougeables);
                                        lstBougeablesAEnlever.add(bougeable);
                                        lstBougeablesAEnlever.add(bougeables);
                                        if (bougeables instanceof LeMechant) {
                                            controleur.augmenterPoints(TypeEnnemis.LEMECHANT);
                                        } else if (bougeables instanceof Lepasfin) {
                                            controleur.augmenterPoints(TypeEnnemis.LEPASFIN);
                                        } else if (bougeables instanceof LeTroubleFete) {
                                            controleur.augmenterPoints(TypeEnnemis.LETROUBLEFETE);
                                        }
                                    }
                                }
                            }
                        } else if (bougeable.getBounds().intersects(hero.getBounds())) {
                            lstBougeablesAEnlever.add(bougeable);
                            controleur.diminuerVie();
                            invalidate();
                            repaint();
                            if (nbVie == 0) {
                                gameOver = true;
                                int rep = JOptionPane.showConfirmDialog(Monde.this, "Partie terminée. \n merci d'avoir joué!", "Game over", JOptionPane.CLOSED_OPTION);
                            }
                        } else if (bougeable instanceof Ennemis) {
                            for (Statique statique : lstStatique) {
                                if (bougeable.getBounds().intersects(statique.getBounds())) {
                                    ((Ennemis) bougeable).arreter(hero.getDirection());
                                }
                            }
                        }

                    }
                    for (Statique statique : lstStatique) {
                        if (statique instanceof Bonis) {
                            if (hero.getBounds().intersects(statique.getBounds())) {
                                if (statique instanceof BoniVie) {
                                    controleur.recupererVie();
                                } else if (statique instanceof BoniBombe) {
                                    espace = Espace.BOMBE;
                                } else if (statique instanceof BoniShotgun) {
                                    espace = Espace.BALLE;
                                }
                                lstStatiqueAEnlever.add(statique);
                               controleur.augmenterPointsBoost();
                            }
                        }
                    }
                    for (Statique statique : lstStatique) {
                        if (statique.getBounds().intersects(hero.getBounds())) {
                            hero.arreter();
                        }
                    }
                    for (Bougeable bougeable : lstBougeablesAEnlever) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                remove((JComponent) bougeable);
                                invalidate();
                                repaint();
                            }
                        });
                    }
                    lstBougeables.removeAll(lstBougeablesAEnlever);
                    lstBougeablesAEnlever.clear();

                    for (Statique statique : lstStatiqueAEnlever) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                remove((JComponent) statique);
                                invalidate();
                                repaint();
                            }
                        });
                    }
                    lstStatique.removeAll(lstStatiqueAEnlever);
                    lstStatiqueAEnlever.clear();
                    try {
                        Thread.sleep(10);
                        temps = temps + 10;
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
    };

    private boolean toucheUnBord(JComponent bougeable, int x, int y) {
        boolean toucheUnBord = false;

        if (!(bougeable.getX() > 0 && bougeable.getX() < x - bougeable.getWidth() && bougeable.getY() > 0 && bougeable.getY() < y - bougeable.getHeight())) {
            toucheUnBord = true;
        }

        return toucheUnBord;
    }
    

    /**
     * Permet de gérer les actions du héro avec les "Keycodes"
     * @param listeKeyCodes les touches entrain d'êtres pressées
     */
    private void choisirActionHeros(ArrayList<Integer> listeKeyCodes) {
        if (listeKeyCodes.contains(KeyEvent.VK_UP) && hero.getY() >= 0) {
            hero.choisirHaut();
        } else if (listeKeyCodes.contains(KeyEvent.VK_DOWN) && hero.getY() < HAUTEUR - hero.getHeight()) {
            hero.choisirBas();
        } else if (listeKeyCodes.contains(KeyEvent.VK_LEFT) && hero.getX() >= 0) {
            hero.choisirGauche();
        } else if (listeKeyCodes.contains(KeyEvent.VK_RIGHT) && hero.getX() < LARGEUR - hero.getWidth()) {
            hero.choisirDroite();
        }
        if (countDown == 0) {
            if (listeKeyCodes.contains(KeyEvent.VK_SPACE)) {
                if (espace == Espace.LASER) {
                    creerLaser();
                } else if (espace == Espace.BOMBE || nbDeBombe > 0) {
                    effetBombe();
                    modele.augmenterPointsBoost();
                    nbDeBombe--;
                } else if (espace == Espace.BALLE || nbDeCoupsBalles > 0) {
                    if (nbDeCoupsBalles > 0) {
                        creerBalles();
                        nbDeCoupsBalles--;
                    } else {
                        espace = Espace.LASER;
                    }
                }
                countDown = 20;
            }
        } else {
            countDown--;
        }
    }

      /**
     * Permet de choisir une entrée aléatoire pour un ennemi
     */
    private void choixEntree() {
        Random r1 = new Random();
        int posX = r1.nextInt(espaceX) + entreeDebutX;
        int posY = r1.nextInt(espaceY) + entreeDebutY;
        for (int i = 0; i < lstNouveauxEnnemis.size(); i++) {
            int entree = r1.nextInt(4) + 1;
            switch (entree) {
                case 1:
                    lstBougeables.add(lstNouveauxEnnemis.get(i));
                    this.add(lstNouveauxEnnemis.get(i));
                    lstNouveauxEnnemis.get(i).setLocation(posX, 0);
                    break;
                case 2:
                    lstBougeables.add(lstNouveauxEnnemis.get(i));
                    this.add(lstNouveauxEnnemis.get(i));
                    lstNouveauxEnnemis.get(i).setLocation(LARGEUR - 32, posY);
                    break;
                case 3:
                    lstBougeables.add(lstNouveauxEnnemis.get(i));
                    this.add(lstNouveauxEnnemis.get(i));
                    lstNouveauxEnnemis.get(i).setLocation(posX, HAUTEUR - 32);
                    break;
                case 4:
                    lstBougeables.add(lstNouveauxEnnemis.get(i));
                    this.add(lstNouveauxEnnemis.get(i));
                    lstNouveauxEnnemis.get(i).setLocation(0, posY);
                    break;
            }
        }
        lstNouveauxEnnemis.clear();
    }

     /**
     * Permet de tirer le laser en fonction de la direction du héro
     */
    private void creerLaser() {
        LaserNormal laser = new LaserNormal(hero.getDirection());
        switch (hero.getDirection()) {
            case HAUT: {
                laser.setLocation(hero.getX() + (hero.getWidth() / 2) - (laser.getWidth()), hero.getY() - laser.getHeight());
                break;
            }
            case BAS: {
                laser.setLocation(hero.getX() + (hero.getWidth() / 2) - laser.getWidth(), hero.getY() + hero.getHeight());
                break;
            }
            case GAUCHE: {
                laser.setLocation(hero.getX() - laser.getWidth(), hero.getY() + (hero.getHeight() / 2));
                break;
            }
            case DROITE: {
                laser.setLocation(hero.getX() + hero.getWidth(), hero.getY() + (hero.getHeight() / 2));
                break;
            }
        }
        lstBougeables.add(laser);
        add(laser);
    }

    /**
     * permet de mettre à jour les composantes graphique en se basant sur le modele
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        changerPointDeVie();
        changerPointage();
    }
/**
     * Permet de changer les points de vie en se fiant au modèle
     */
    private void changerPointDeVie() {
        nbVie = modele.getPointDeVie();
    }
 /**
     * Permet de changer le score en se fiant au modèle
     */
    private void changerPointage() {
        pointage = modele.getPointage();
    }
/**
     * Permet de créer et afficher un boni lorsqu'un ennemi disparait
     * @param ennemi 
     */
    private void bonus(Ennemis ennemi) {
        Bonis bonis;
        Random r = new Random();
        int bonus = r.nextInt(4);
        switch (bonus) {
            case 0:
                bonis = new BoniShotgun();
                nbDeCoupsBalles = nbDeCoupsBalles + 10;
                bonis.setLocation(ennemi.getLocation());
                add(bonis);
                lstStatique.add(bonis);
                break;
            case 1:
                bonis = new BoniBombe();
                nbDeBombe++;
                bonis.setLocation(ennemi.getLocation());
                add(bonis);
                lstStatique.add(bonis);
                break;
            case 2:
                bonis = new BoniVie();
                bonis.setLocation(ennemi.getLocation());
                add(bonis);
                lstStatique.add(bonis);
                break;
        }
    }
 /**
     * Permet de créer et d'afficher les balles du «shotgun»
     */
    private void creerBalles() {
        Balle balle1 = new Balle(hero.getDirection());
        Balle balle2 = new Balle(hero.getDirection());
        Balle balle3 = new Balle(hero.getDirection());

        switch (hero.getDirection()) {
            case HAUT: {
                balle1.setLocation(hero.getX() + (hero.getWidth() / 2) - (balle1.getWidth()), hero.getY() - balle1.getHeight());
                balle2.setLocation(hero.getX() + (hero.getWidth() / 2) - 2 * (balle1.getWidth()), hero.getY() - balle1.getHeight());
                balle3.setLocation(hero.getX() + (hero.getWidth() / 2), hero.getY() - balle1.getHeight());
                break;
            }

            case BAS: {
                balle1.setLocation(hero.getX() + (hero.getWidth() / 2) - balle1.getWidth(), hero.getY() + hero.getHeight());
                balle2.setLocation(hero.getX() + (hero.getWidth() / 2) - 2 * balle1.getWidth(), hero.getY() + hero.getHeight());
                balle3.setLocation(hero.getX() + (hero.getWidth() / 2), hero.getY() + hero.getHeight());
                break;
            }
            case GAUCHE: {
                balle1.setLocation(hero.getX() - balle1.getWidth(), hero.getY() + (hero.getHeight() / 2));
                balle2.setLocation(hero.getX() - balle1.getWidth(), hero.getY() + (hero.getHeight() / 2) - balle1.getHeight());
                balle3.setLocation(hero.getX() - balle1.getWidth(), hero.getY() + (hero.getHeight() / 2) + balle1.getHeight());
                break;
            }
            case DROITE: {
                balle1.setLocation(hero.getX() + hero.getWidth(), hero.getY() + (hero.getHeight() / 2));
                balle2.setLocation(hero.getX() + hero.getWidth(), hero.getY() + (hero.getHeight() / 2) - balle1.getHeight());
                balle3.setLocation(hero.getX() + hero.getWidth(), hero.getY() + (hero.getHeight() / 2) + balle1.getHeight());
                break;
            }
        }
        lstBougeables.add(balle1);
        lstBougeables.add(balle2);
        lstBougeables.add(balle3);
        this.add(balle1);
        this.add(balle2);
        this.add(balle3);
    }

    /**
     * Permet de chosir un nombre aléatoirement dans un intervalle fermé
     * @param min le minimum
     * @param max le maximum
     * @return le chiffre aléatoire dans l'intervalle
     */
    private int aleatoire(int min, int max) {
        Random r = new Random();
        int rand;
        rand = r.nextInt((max - min) + 1) + min;
        return rand;
    }

    /**
     * Enlève tous les ennemis du monde
     */
    private void effetBombe() {
        for (Bougeable bougeable : lstBougeables) {
            if (!lstBougeablesAEnlever.contains(bougeable)) {
                lstBougeablesAEnlever.add(bougeable);
            }
        }
        espace = Espace.LASER;
    }

    /**
     * permet de recommencer une nouvelle partie
     */
    public void nouvellePartie() {
        nbDeCoupsBalles = 0;
        nbDeBombe = 0;
        controleur.recupererVie();
        modele.reinitialiserPoints();
        effetBombe();
        gameOver = false;
        gameThread.start();
        temps = 0;
    }
}
