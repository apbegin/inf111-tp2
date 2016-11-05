import java.io.Serializable;

/**
 * Classe qui regroupe tout qui concerne un disjoncteur dans le projet.
 *
 * On y retrouve les constantes et les sous-programmes lié à un disjoncteur.
 * 
 * Implémente l'interface Serializable pour la sauvegarde dans un fichier
 * binaire.
 * 
 * 
 * @author Antoine Proulx-Bégin
 */
public class Disjoncteur implements Serializable {

	/**
	 * Enlève un "warning". On ne gère pas les versions.
	 */
	private static final long serialVersionUID = 1L;

	// état possible d'un disjoncteur.
	public static final int ALLUME = 1;
	public static final int ETEINT = 0;

	// Choix d'ampérages possibles.
	private static final int MIN_AMPERAGE = 15;
	private static final int MAX_AMPERAGE = 60;

	// Tous les ampérages permis dans un tableau.
	public static final int AMPERAGES_PERMIS[] = { MIN_AMPERAGE, 20, 40, 50,
			MAX_AMPERAGE };

	// Construction d'une chaîne avec les ampérages permis. Sert à valider.
	public static final String CHAINE_AMPERAGE_PERMIS = "15/20/40/50/60";

	// Les tensions possibles.
	public static final int TENSION_ENTREE = 240;
	public static final int TENSION_PHASE = 120;

	// Construction d'une chaîne avec les tensions permises. Sert à valider.
	public static final String CHAINE_TENSION_PERMISE = "120/240";

	// Pourcentage de la puissance MAX toléré
	public static final double POURCENTAGE_PUISSANCE_MAX = 0.80d;

	/******************************
	 * * Les attributs d'un disjoncteur
	 ********************************/

	private double ampere;
	private double tension;

	// Une liste qui contient les demandes (charge) sur le circuit.
	private ListeDemande demandeDuCircuit;

	// ALLUME ou ETEINT.
	private int etat;

	public Disjoncteur() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructeur copie de l'objet Disjoncteur
	 * 
	 * @param ampere
	 * @param tension
	 */
	public Disjoncteur(double ampere, double tension) {
		this.ampere = ampere;
		this.tension = tension;
		this.etat = ETEINT;
		this.demandeDuCircuit = new ListeDemande();
	}

	/**
	 * 
	 * @return nombre max d'ampère du disjoncteur
	 */
	public double getAmpere() {
		return this.ampere;
	}

	/**
	 * 
	 * @return la tension du disjoncteur
	 */
	public int getTension() {
		return (int) this.tension;
	}

	/**
	 * 
	 * @return puissance maximale supporté par le disjoncteur
	 */
	public double getPuissanceEnWatt() {

		return getAmpere() * getTension() * POURCENTAGE_PUISSANCE_MAX;
	}

	/**
	 * 
	 * @return 0 si le disjoncteur est fermé, 1 s'il est ouvert
	 */
	public int getEtat() {
		return this.etat;
	}

	/**
	 * 
	 * @return pourcentage d'utilisation du disjoncteur
	 */
	public double getRatio() {
		return (this.totalDemande()
				/ (this.getAmpere() * POURCENTAGE_PUISSANCE_MAX)) * 100;
	}

	/**
	 * Ajoute une demande au disjoncteur
	 * 
	 * @param demande
	 */
	public void AjoutDemande(double demande) {
		demandeDuCircuit.ajouterElement(demande);

		if (totalDemande() > getAmpere() * POURCENTAGE_PUISSANCE_MAX)
			this.etat = ETEINT;
		else
			this.etat = ALLUME;
	}

	/**
	 * Retire la demande passé en paramètre (valeur négative) met à jour l'état
	 * du disjoncteur si nécessaire
	 * 
	 * @param demande
	 */
	public void RetirerDemande(double demande) {
		if (this.trouverDemande(demande)) {
			this.demandeDuCircuit.supprimerElement();

			if (totalDemande() < this.getAmpere() * POURCENTAGE_PUISSANCE_MAX)
				this.etat = ALLUME;
		}
	}

	/**
	 * Calcul le nombre total d'ampère de la liste
	 * 
	 * @return le nombre total d'ampere dans la liste
	 */
	public double totalDemande() {
		double total = 0;
		if (!demandeDuCircuit.estVide()) {
			demandeDuCircuit.setPosDebut();

			for (int i = 0; i < demandeDuCircuit.getNbElements(); i++) {
				total += (double) demandeDuCircuit.getElement();
				demandeDuCircuit.setPosSuivant();
			}
		}
		return total;
	}

	/**
	 * Cherche si le nombre d'ampere recus en parametre se trouve dans la liste.
	 * 
	 * @param nbAmpere
	 * 
	 * @return true si la valeur de "nbAmpere" existe dans la liste
	 * 
	 */
	public boolean trouverDemande(double nbAmpere) {
		boolean trouve=false;
		int compteur = demandeDuCircuit.getNbElements();
		demandeDuCircuit.setPosDebut();

		while (!trouve && compteur > 0) {
			//nbAmpere est négatif si l'on retire une demande
			if ((double) demandeDuCircuit.getElement() == Math.abs(nbAmpere)) {
				return true;
			}
			compteur--;
			demandeDuCircuit.setPosSuivant();
		}
		return false;
	}
}
