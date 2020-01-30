package ca.qc.bdeb.info203.vue;

import ca.qc.bdeb.info203.controleur.Controleur;
import ca.qc.bdeb.info203.model.Modele;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author 1679219
 */
public class Fenetre extends JFrame {

    /**
     * Permet de garder le codes des touches maintenues et pressées
     */
    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();

    /**
     * Objets essentiels pour la construction du JMenuBar en haut de la fenêtre
     */
    private JMenuBar mnuBar = new JMenuBar();
    private JMenu mnuFichier = new JMenu("Fichier");
    private JMenu mnuPointDInterrogation = new JMenu("?");
    private JMenuItem mnuFichierNouvellePartie = new JMenuItem("Nouvelle partie");
    private JMenuItem mnuFichierQuitter = new JMenuItem("Quitter");
    private JMenuItem mnuPointAide = new JMenuItem("Aide");
    private JMenuItem mnuPointAPropos = new JMenuItem("À propos");
    
    /**
     * Le monde dans lequel le jeu se passe
     */
    private Monde monde;

    public Fenetre(Modele modele, Controleur controleur) throws HeadlessException {
        setTitle("Contre-attaque de la tentacule mauve");

        Monde monde = new Monde(listeKeyCodes, modele, controleur);
        setResizable(false);
        add(monde);
        creerInterface();
        creerEvenementsMenus();
        creerEvenementsHeros();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
/**
     * Permet de créer le JMenuBar avec tous ses contenus
     */
    private void creerInterface() {
        mnuBar.add(mnuFichier);
        mnuBar.add(mnuPointDInterrogation);

        mnuFichier.add(mnuFichierNouvellePartie);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuFichierQuitter);

        mnuPointDInterrogation.add(mnuPointAide);
        mnuPointDInterrogation.addSeparator();
        mnuPointDInterrogation.add(mnuPointAPropos);

        setJMenuBar(mnuBar);
    }
/**
     * Permet de gérer les évènements dans le JMenuBar
     */
    private void creerEvenementsMenus() {

        mnuFichierNouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monde.nouvellePartie();
            }
        });

        mnuFichierQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mnuPointAide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(monde, "Sauvez l'humanité de la menace de Tentacule Mauve!\n Utilisez les flèches pour contrôler Bernard et la barre d'espace pour tirer un laser pour éliminer les ennemis.");
            }
        });

        mnuPointAPropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(monde, "David Molina & Laurianne Guindon "
                        + "\nDate de création du message: Jeudi 8 décembre 2017", "À propos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

     /**
     * Permet de gérer les évènements par rapport au héro (pour le contrôler)
     */
    private void creerEvenementsHeros() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!listeKeyCodes.contains(e.getKeyCode())) {
                    listeKeyCodes.add(e.getKeyCode());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                listeKeyCodes.remove(new Integer(e.getKeyCode()));
            }
        });
    }

    /**
     * 
     * @return ls liste de keycodes
     */
    public ArrayList<Integer> getListeKeyCodes() {
        return listeKeyCodes;
    }

}
