import java.io.Serializable;

/**
 * Module qui permet la gestion d'une bo�te �lectrique avec disjoncteurs.
 *
 * La boite doit d'abord �tre initialis�e au nombre d'amp�res voulus ainsi que
 * son nombre de disjoncteurs maximum possibles.
 *
 * Impl�mente l'interface Serializable pour la sauvegarde dans un fichier
 * binaire.
 * 
 * @author Antoine Proulx-B�gin
 * 
 */
public class Boite implements Serializable {

	/**
	 * Enl�ve un "warning". On ne g�re pas les versions.
	 */
	private static final long serialVersionUID = 1L;

	/*********************************
	 * LES CONSTANTES DE LA BOITE
	 *********************************/
	// La modification a un effet direct sur l'affichage.
	public static final int MAX_DISJONCTEURS = 60;
	public static final int NB_COLONNES = 2;

	public static final int NB_LIGNES_MAX = MAX_DISJONCTEURS / NB_COLONNES;

	// Pour le remplissage de d�part.
	public static final double POURC_REMPLI = 0.6;
	public static final double POURC_TENSION_ENTREE = .3;

	public static final int AMPERAGE_MIN = 100;
	public static final int AMPERAGE_MAX = 400;

	/*********************************
	 * LES ATTRIBUTS DE LA BOITE
	 *********************************/
	private int maxAmperes;

	// Le tableau est 2D mais il est � l'envers de la r�alit� (ligne-colonne).
	// Toutes les m�thodes qui n�cessitent la position, re�oivent
	// (colonne-ligne).
	private Disjoncteur[][] tabDisjoncteurs;
	private int nbDisjoncteurs;

	// On d�duit les disjoncteurs TENSION_ENTREE par
	// nbDisjoncteurs - nbDisjoncteursPhase
	private int nbDisjoncteursPhase;

	/**
	 * Constructeur de l'objet Boite
	 * 
	 * @param max_amperes
	 */
	public Boite(int max_amperes) {
		// TODO Auto-generated constructor stub
		this.nbDisjoncteurs = 0;
		this.nbDisjoncteursPhase = 0;
		this.maxAmperes = max_amperes;
		this.tabDisjoncteurs = new Disjoncteur[NB_COLONNES][NB_LIGNES_MAX];
	}

	/**
	 * @return La consommation totale en Watts de la bo�te.
	 */
	public double getConsommationTotalEnWatt() {

		double total = 0;

		for (int i = 0; i < NB_COLONNES; i++) {
			for (int j = 0; j < NB_LIGNES_MAX; j++) {
				if (!getEmplacementEstVide(i, j))
					total += this.tabDisjoncteurs[i][j].getPuissanceEnWatt();
			}
		}
		return total;
	}

	/**
	 * @return la puissance totale consomm�e sur les disjoncteurs.
	 */
	public double puissance_total_boite() {

		double total = 0;

		for (int i = 0; i < NB_COLONNES; i++) {
			for (int j = 0; j < NB_LIGNES_MAX; j++) {
				if (!getEmplacementEstVide(i, j))
					total += this.tabDisjoncteurs[i][j].totalDemande();
			}
		}
		return total;
	}

	/**
	 * 
	 * @return Le temps de support de la charge.
	 */
	public double temps_ups() {
		return (this.getMaxAmperes() * Disjoncteur.TENSION_ENTREE)
				/ this.getConsommationTotalEnWatt();
	}

	/**
	 * 
	 * @return true si l'on peut ajouter un disjoncteur � la bo�te.
	 */
	public boolean getEmplacementEncoreDisponible() {
		return (this.getEmplacementDisponible().colonne == -1) ? false : true;
	}

	/**
	 * 
	 * @param j: position colonne
	 * @param i: position ligne
	 * @return le disjoncteur se trouvant � la position [j][i].
	 */
	public Disjoncteur getDisjoncteur(int j, int i) {

		return this.tabDisjoncteurs[j][i];
	}

	/**
	 * 
	 * @return nombre d'amp�re maximum de la bo�te.
	 */
	public int getMaxAmperes() {
		return this.maxAmperes;
	}

	/**
	 * Rempli une bo�te de disjoncteurs avec des valeurs al�atoires.
	 * Aucune demande n'est g�n�r� al�atoirement
	 */
	public void remplirAlea() {
		int ampere, tension = 0;

		for (int i = 0; i < NB_COLONNES; i++) {
			for (int j = 0; j < NB_LIGNES_MAX; j++) {
				
				//Cr�e un disjoncteur en fonction de POURC_REMPLI
				if (UtilitaireMath.reelAlea(0, 100) <= POURC_REMPLI * 100) {
					ampere = Disjoncteur.AMPERAGES_PERMIS[UtilitaireMath
							.entierAlea(0,
									Disjoncteur.AMPERAGES_PERMIS.length - 1)];
					
					if (UtilitaireMath.reelAlea(0, 100) > POURC_TENSION_ENTREE
							* 100) {
						tension = Disjoncteur.TENSION_PHASE;
						this.nbDisjoncteursPhase += 1;
						
					} else
						tension = Disjoncteur.TENSION_ENTREE;

					this.tabDisjoncteurs[i][j] = new Disjoncteur(ampere,
							tension);
					this.nbDisjoncteurs += 1;
				}
			}

		}
	}

	/**
	 * 
	 * @return les coordonn�es, s'il y a lieu du prochain emplacement 
	 * disponible pour ajouter un disjoncteur
	 * 
	 */
	public Coord getEmplacementDisponible() {

		// retourne (-1,-1) si aucun emplacement disponible
		Coord c = new Coord();
		c.colonne = -1;
		c.ligne = -1;

		for (int i = 0; i < NB_COLONNES; i++) {
			for (int j = 0; j < tabDisjoncteurs[i].length; j++) {

				if (getEmplacementEstVide(i, j)) {
					c.colonne = i;
					c.ligne = j;
					return c;
				}
			}
		}
		return c;
	}

	/**
	 * 
	 * Ajoute le disjoncteur {d} dans la bo�te � la position (colonne,ligne)
	 * 
	 * @param colonne
	 * @param ligne
	 * @param d: disjoncteur
	 */
	public void ajouterDisjoncteur(int colonne, int ligne, Disjoncteur d) {
		tabDisjoncteurs[colonne][ligne] = d;
	}

	/**
	 * ajoute une demande au disjoncteur � la position i,j de la bo�te
	 * @param i:colonne
	 * @param j:ligne
	 * @param demande
	 */
	public void ajouterDemande(int i, int j, double demande) {
		this.tabDisjoncteurs[i][j].AjoutDemande(demande);
	}

	/**
	 * retire une puissance de la liste de demande du disjoncteur
	 * � la position i,j.
	 * @param i:colonne
	 * @param j:ligne
	 * @param demande
	 */
	public void retirerPuissance(int i, int j, double demande) {
		this.tabDisjoncteurs[i][j].RetirerDemande(demande);
	}

	/**
	 * 
	 * @return le nombre de disjoncteurs total de la bo�te
	 */
	public int getNbDisjoncteurs() {
		return this.nbDisjoncteurs;
	}

	/**
	 * 
	 * @return nombre de disjoncteur Phase (Tension = 120V)
	 */
	public int getNbDisjoncteursPhase() {
		return this.nbDisjoncteursPhase;
	}

	/**
	 * 
	 * @return nombre de disjoncteur Entr�e (Tension = 240V)
	 */
	public int getNbDisjoncteursEntree() {
		return this.nbDisjoncteurs - this.nbDisjoncteursPhase;
	}

	/**
	 * 
	 * @param colonne
	 * @param ligne
	 * @return true s'il n'y a pas de disjoncteur � cette position
	 */
	public boolean getEmplacementEstVide(int colonne, int ligne) {

		return this.tabDisjoncteurs[colonne][ligne] == null;
	}
}