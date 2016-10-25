/**
 * Cet enregistrement repr�sente les coordonn�es 
 * possible dans diff�rents jeux de grille (ligne-colonne).
 * 
 * Les attributs sont utilisables � l'aide des foncitons ou directement (public).
 * (m�me principe que  java.awt.Dimension).
 * 
 *@author pbelisle
 *@version H 2009
 *@revisite A 2016
 */
public class Coord {


	/*
	 * Les items choisis par l'utilisateur 
	 */
	int ligne;
	int colonne;

	public Coord() {
		ligne = 0;
		colonne = 0;
	}
	
	public Coord(int ligne, int colonne){
		this.ligne=ligne;
		this.colonne=colonne;
	}


	/**
	 * @return La ligne
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * @param ligne La ligne � modifier.
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	/**
	 * @return La colonne.
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * @param colonne La colonne � modifier.
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * @return Une cha�ne contenant les infos de la coordonn�e.
	 */
	public String toString(){
		return "(" + colonne + "-" + ligne + ")";
	}
}
