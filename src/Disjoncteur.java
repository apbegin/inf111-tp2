import java.io.Serializable;

/**
* Classe qui regroupe tout qui concerne un
* disjoncteur dans le projet.
*
* On y retrouve les constantes et les  sous-programmes
* lié à un disjoncteur.
* 
* Implémente l'interface Serializable pour la sauvegarde
* dans un fichier binaire. 
* 
* 
* @author Antoine Proulx-Bégin
*/
public class Disjoncteur implements Serializable{

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
	public static final int AMPERAGES_PERMIS[] =
		                         {MIN_AMPERAGE, 20, 40, 50, MAX_AMPERAGE};

	// Construction d'une chaîne avec les ampérages permis. Sert à valider.
	public static final  String CHAINE_AMPERAGE_PERMIS = 
			"15/20/40/50/60";
	
	// Les tensions possibles.
	public static final int TENSION_ENTREE = 240;
	public static final int TENSION_PHASE = 120;

	// Construction d'une chaîne avec les tensions permises. Sert à valider.
	public static final  String CHAINE_TENSION_PERMISE = 
			"120/240";
	
	// Pourcentage de la puissance MAX toléré
	public static final double POURCENTAGE_PUISSANCE_MAX = 0.80d;
	
	/******************************
	 * * Les attributs d'un disjoncteur
	 ********************************/
	
	private double ampere;
    private double tension;

	// Une liste qui contient les demandes (charge) sur le circuit.
	private Liste demandeDuCircuit;
	
	// ALLUME ou ETEINT.	
    private int etat;

	public Disjoncteur() {
		// TODO Auto-generated constructor stub
	}
	
	public Disjoncteur(double ampere, double tension){
		this.ampere=ampere;
		this.tension=tension;
		this.etat = ETEINT;
		this.demandeDuCircuit = new Liste();
	}

	public double getAmpere() {
		return this.ampere;
	}

	public int getTension() {
		return (int)this.tension;
	}

	public double getPuissanceEnWatt() {
		return this.getAmpere()*this.getTension()*POURCENTAGE_PUISSANCE_MAX;
	}

	public int getEtat() {
		return this.etat;
	}

	public double getRatio() {
		return (this.totalAmpere()/
				(this.getAmpere()*POURCENTAGE_PUISSANCE_MAX))*100;
	}
    
	
	public void AjoutDemande(double demande){
		
	double ampereTotal = this.totalAmpere();
		this.demandeDuCircuit.insererApres(demande);
		
		if(ampereTotal > this.ampere * POURCENTAGE_PUISSANCE_MAX){
			this.etat = ETEINT;
		}
		else{
			this.demandeDuCircuit.insererALaPosition(demande);
		}
	}
	
	public void RetirerPuissance(double demande){
		if(this.trouverElement(demande)){
			
			this.demandeDuCircuit.supprime();
			
			if(this.totalAmpere() < this.getAmpere()*
					POURCENTAGE_PUISSANCE_MAX){
				this.etat = ALLUME;
			}
		}
	}
	
	 /**
	  * Calcul et retourne le nombre total d'ampère de la liste
	  * garde la position de la liste à la position suivante, prêt pour
	  * insérer un autre objet dans la liste.
	  * 
	  * @return le nombre total d'ampere dans la liste
	  */
	 public double totalAmpere(){
		 double total=0;
		 if(!demandeDuCircuit.estVide()){
			 demandeDuCircuit.setPosDebut();
			 
			 for(int i=0; i < demandeDuCircuit.getNbElements(); i++){
				 total+= (double)demandeDuCircuit.getElement();
				 demandeDuCircuit.setPosSuivant();
			 }
		 }
		 return total;
		 
	 }

	 /**
	  * Cherche si le nombre d'ampere recus en parametre se trouve
	  * dans la liste.
	  * 
	  * @param nbAmpere
	  * @return true si une demande de "nbAmpere" existe dans la liste,
	  *  garde la position trouver à la position courante.
	  *  
	  * @return false si "nbAmpere" n'existe pas dans la liste.
	  */
	 public boolean trouverElement(double nbAmpere){
		 boolean trouve = false;
			int compteur = demandeDuCircuit.getNbElements();
			demandeDuCircuit.setPosDebut();
			
			while(!trouve && compteur > 0){
				if((double)demandeDuCircuit.getElement() == Math.abs(nbAmpere)){
					return true;
				}
			}
			return false;
		 
	 }
	
	
}
    
