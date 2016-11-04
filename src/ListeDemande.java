import java.io.Serializable;

/**
 * Classe qui impl�mente une liste cha�n� simple et ayant des m�thodes pour
 *  l'ajout et la suppresions d'�l�ments de la liste 
 *
 *impl�mentation de Serializable pour enregistrer chaque demande de la liste
 *
 *@author Antoine Proulx-B�gin
 */
public class ListeDemande implements Serializable {
	/**
	 * Enl�ve un "warning". On ne g�re pas les versions.
	 */
	private static final long serialVersionUID = 1L;

	private Noeud debut;
	private Noeud fin;
	private Noeud posCourante;
	private int nbElements;
	
	public ListeDemande() {
		debut = null;
		fin = null;
		posCourante = null;
		nbElements = 0;
	}
	
	/**
	 * classe interne Noeud pour faire le lien entre les 
	 * �l�ments de la liste.
	 * 
	 * @author Antoine Proulx-B�gin
	 *
	 */
	private class Noeud implements Serializable {

		private Object element;
		private Noeud suivant;
		
		public Noeud(Object element, Noeud suivant) {
			this.suivant = suivant;
			this.element = element;
		}
	}


	/**
	 * 
	 * @return nombre d'�l�ments de la liste
	 */
	public int getNbElements() {
		return nbElements;
	}

	/**
	 * 
	 * @return true si la liste est vide
	 */
	public boolean estVide() {
		return nbElements == 0;
	}

	/**
	 * Change la position du noeud courant � celui du Noeud debut
	 */
	public void setPosDebut() {
		posCourante = debut;
	}

	/**
	 * Change la position du noeud courant � celui du Noeud suivant
	 * du noeud courant
	 */
	public void setPosSuivant() {
		if (posCourante.suivant != null)
			posCourante = posCourante.suivant;
	}

	/**
	 * Change la position du noeud courant � celui du Noeud fin
	 */
	public void setPosFin() {
		posCourante = fin;
	}

	/**
	 * 
	 * @return l'�lement du noeud courant
	 */
	public Object getElement() {
		return posCourante.element;
	}

	/**
	 * ajouter un �l�ment � la liste
	 * @param element
	 */
	public void ajouterElement(Object element) {
		if (estVide()) {
			debut = new Noeud(element, null);
			fin = posCourante = debut;
		} else {
			fin.suivant = new Noeud(element, null);
			fin = fin.suivant;
			setPosFin();
		}
		nbElements++;
	}

	/**
	 * supprime l'�l�ment du noeud courant
	 */
	public void supprimerElement() {
		if (debut == null)
			throw new NullPointerException();

		if (nbElements == 1)
			debut = fin = posCourante = null;

		else if (debut == posCourante) {
			debut = debut.suivant;
			posCourante = debut;
		}

		else if (posCourante == fin) {
			Noeud temp = new Noeud(null, null);
			temp = debut;

			while (temp.suivant != fin) {
				posCourante = temp;
			}

			fin = posCourante;
			posCourante.suivant = null;
		}

		else {
			posCourante.element = posCourante.suivant.element;

			if (posCourante.element == null)
				fin = posCourante;
		}
		nbElements--;
	}
}
