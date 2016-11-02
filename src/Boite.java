import java.io.Serializable;
import java.util.Random;

/**
* Module qui permet la gestion d'une bo�te �lectrique
* avec disjoncteurs.
*
* La boite doit d'abord �tre initialis�e au nombre d'amp�res voulus 
* ainsi que son nombre de disjoncteurs maximum possibles.
*
* Impl�mente l'interface Serializable pour la sauvegarde
* dans un fichier binaire. 
* 
* @author Antoine Proulx-B�gin
* 
*/
public class Boite implements Serializable{
	
	/**
	 * Enl�ve un "warning". On ne g�re pas les versions.
	 */
	private static final long serialVersionUID = 1L;
	
	/*********************************
	 *  LES CONSTANTES DE LA BOITE
	 *********************************/
	// La modification a un effet direct sur l'affichage.
	public static final int MAX_DISJONCTEURS  = 60;
	public static final int NB_COLONNES  = 2;
	
	public static final int NB_LIGNES_MAX  = 
			MAX_DISJONCTEURS/NB_COLONNES;
	
	// Pour le remplissage de d�part.
    public static final double POURC_REMPLI = 0.6;
	public static final double POURC_TENSION_ENTREE = .3;
	
	public static final int AMPERAGE_MIN= 100;
	public static final int AMPERAGE_MAX = 400;
	
	/*********************************
	 *  LES ATTRIBUTS DE LA BOITE
	 *********************************/
	private int maxAmperes;
	
	// Le tableau est 2D mais il est � l'envers de la r�alit� (ligne-colonne).
	// Toutes les m�thodes qui n�cessitent la position, re�oivent (colonne-ligne).  
	private Disjoncteur[][] tabDisjoncteurs;	
	private int nbDisjoncteurs;
	
	// On d�duit les disjoncteurs TENSION_ENTREE par
	// nbDisjoncteurs - nbDisjoncteursPhase  
	private int nbDisjoncteursPhase;
	
	
	// Vous devez �crire les m�thodes manquantes.
	
	public Boite(int max_amperes) {
		// TODO Auto-generated constructor stub
		this.nbDisjoncteurs=0;
        this.nbDisjoncteursPhase=0;
        this.maxAmperes = max_amperes;
        this.tabDisjoncteurs = new Disjoncteur[NB_COLONNES][NB_LIGNES_MAX];
	}

	/**
	 * @return La consommation totale en Watts de la bo�te.
	 */
	public double getConsommationTotalEnWatt(){

		double total=0;
		int ligne=0;
		for(int i=0; i < NB_COLONNES; i++ ){
			while(ligne<NB_LIGNES_MAX && !getEmplacementEstVide(i, ligne)){
				total += tabDisjoncteurs[i][ligne].getPuissanceEnWatt();
				ligne++;
			}
		}
	    return total;

	}

	/**
	 * @return la puissance totale consomm�e sur les disjoncteurs. 
	 */
	public double puissance_total_boite(){
		
		double puissanceTotal=0;
		
		int ligne=0;
		for(int i=0; i < NB_COLONNES; i++ ){
			while(ligne<NB_LIGNES_MAX && !getEmplacementEstVide(i, ligne)){
				puissanceTotal += tabDisjoncteurs[i][ligne].totalAmpere() * 
						tabDisjoncteurs[i][ligne].getTension();
			}
		}
		return puissanceTotal;

	}

	/*
	 * 
	 * @return  Le temps de support de la charge.
	 */
	public double temps_ups(){

		return (this.getMaxAmperes()*Disjoncteur.TENSION_ENTREE)
				/this.getConsommationTotalEnWatt();
	}

	public boolean getEmplacementEncoreDisponible() {
		return (this.getEmplacementDisponible().colonne==-1)?false:true;
	}

	public Disjoncteur getDisjoncteur(int j, int i) {
		
		return this.tabDisjoncteurs[j][i];
	}


	public int getMaxAmperes() {
		return this.maxAmperes;
	}

	public void remplirAlea() {
		Random rnd = new Random(); 
        int ampere,tension =0;
        
        for(int i=0; i<NB_COLONNES; i++){
            for(int j=0; j<NB_LIGNES_MAX; j++){
            	if(rnd.nextInt(101) <= POURC_REMPLI * 100){
	                ampere = Disjoncteur.AMPERAGES_PERMIS[rnd.nextInt(Disjoncteur.AMPERAGES_PERMIS.length)];
	                if(rnd.nextInt(101) >= POURC_TENSION_ENTREE*100){
	                	tension = Disjoncteur.TENSION_PHASE;
	                	this.nbDisjoncteursPhase++;
	                }
	                else
	                	tension = Disjoncteur.TENSION_ENTREE;
	               
	               this.tabDisjoncteurs[i][j]= new Disjoncteur(ampere,tension);
	               this.nbDisjoncteurs++;
            	}
            }
 
        }
	}


	public Coord getEmplacementDisponible() {
		
		//retourne (-1,-1) si aucun emplacement disponible
		Coord c = new Coord();
		c.colonne = -1;
		c.ligne = -1;
		
		for(int i=0; i < NB_COLONNES; i++ ){
			for(int j=0; j < tabDisjoncteurs[i].length; j++){
				
				if(getEmplacementEstVide(i, j)){
					c.colonne=i;
					c.ligne=j;
					return c;
				}
			}
		}
		return c;
	}

	public void ajouterDisjoncteur(int colonne, int ligne, Disjoncteur d) {
		tabDisjoncteurs[ligne][colonne]=d;
	}

	public void ajouterDemande(int i, int j, double demande) {
		this.tabDisjoncteurs[i][j].AjoutDemande(demande);
	}

	public void retirerPuissance(int i, int j, double demande) {
		this.tabDisjoncteurs[i][j].RetirerPuissance(demande);
	}

	public int getNbDisjoncteurs() {
		return this.nbDisjoncteurs;
	}

	public int getNbDisjoncteursPhase() {
		return this.nbDisjoncteursPhase;
	}

	public int getNbDisjoncteursEntree() {
		return this.nbDisjoncteurs-this.nbDisjoncteursPhase;
	}

	public boolean getEmplacementEstVide(int colonne, int ligne) {
		
		return this.tabDisjoncteurs[colonne][ligne]==null;
	}
}