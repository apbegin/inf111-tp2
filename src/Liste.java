/**
 * Classe qui impl�mente une liste
 * avec une position courante.  En tout temps les op�rations
 * se font par rapport � la position courante qui fait partie int�grante de la liste.
 *
 * Les m�thodes pour d�placer la position courante font partie de la classe.
 *
 * Pour l'insertion, la position courante est toujours sur l'objet ajout�.
 * Les m�thodes de suppression ne sont pas impl�ment�es
 *
 * On ne l�ve aucune exception pour cet exemple.  Seul les exceptions syst�mes
 * seront lev�es s'il y a un probl�me.
 *
 * @author  <a href="mailto:pierre.belisle@etsmtl.ca">Pierre B�lisle</a>
 * @version A2016
 */
public class Liste {

	/*
	 * STRAT�GIE : On garde une variable pour retenir  la position courante
	 * et une pour le nombre d'�l�ments qui est mise � jour apr�s chaque
	 * insertion ou supression.
	 *
	 * L'impl�mentation est dans un tableau statique.
	 *
	 * Les stat�gie pour chaque m�thodes sont d�crites dans leur commentaire
	 * de strat�gie respectf.
	 */

	//Nombre d'�l�ments possibles au maximum par d�faut
	public static final int MAX_ELEMENTS = 100;

	//La liste avec les objets
	private Object [] liste;

	 //La position o� sont effectu� les op�ration.
	 private int positionCourante;


	 //Maintenu � jour apr�s une insertion ou une suppresion.
	 private int nbElements;


	 /**
	  * Cr�e une liste vide de MAX_ELEMENTS au maximum.
	  */
	 public Liste(){

	    	/**
	    	 * STRAT�GIE : On utilise le constructeur suivant (bonne pratique)
	    	 */

		      this(MAX_ELEMENTS);

	 }

	 /**
	  * Cr�e une liste vide de la taille fournie au maximum.
	  */
	 public Liste(int taille){

	    	/**
	    	 * STRAT�GIE : On initialise explicitement les valeurs plut�t que d'utiliser
	    	 * l'initialisation automatique de l'environnement Eclipse.
	    	 */

		       liste = new Object[taille];
		       positionCourante = 0;
		       nbElements = 0;

	 }



	 /**
	  * Retourne si la liste est vide.
	  *
	  * Ant�c�dent : Aucun.
	  *
	  * Cons�quent : Aucun.
	  *
	  * @return Si true la liste est vide et false autrement
	  */
	 public boolean estVide(){

		 /*
		  * STRAT�GIE : On retourne simplement l'�valuation bool�enne de la
		  *  comparaison du nombre d'�l�ments avec 0.
		  */
	     return nbElements == 0;
	 }


	 //LES M�THODES DE D�PLACEMENT
	 /**
	  * Passe la position courante au suivant dans la liste.
	  *
	  * Ant�c�dent : Aucun.
	  *
	  * Cons�quent : La position est d�plac�e sur l'�l�ment suivant s'il existe.
	  * Sinon position courante est laiss�e � la fin
 	  *
	  */
	 public void setPosSuivant() {


		 /*
		  * STRAT�GIE : Si c'est la fin, on ne d�place pas, sinon on passe au suivant.
		  */
		 if(positionCourante != liste.length - 1){

    	     positionCourante++;

		 }

	 }


	 /**
	  * Met la position courante au d�but de la  liste.
	  *
	  */
	 public void setPosDebut() {


         /*
          * STRAT�GIE : On d�place la position courante sur la 1i�re case.
          */

        positionCourante = 0;

	 }

	 /**
	  * Met la position courante � la fin de la liste.
	  *
	  *
	  */
	 public void setPosFin() {


         /*
          * STRAT�GIE : on d�place la position courante au noeud point� par fin.
          */
		 positionCourante = liste.length-1;


	 }

	 /**
	  * Passe la position courante au pr�c�dent dans la liste.
	  *
	  * Ant�c�dent : La liste ne doit pas �tre vide.
	  *
	  */
	 public void setPosPrecedent(){


		 /*
		  *STRAT�GIE : On place une variable lovale temporaire sur le premier
		  *noeud et on parcrous le cha�nege jusqu'� ce que le noeud suivant soit celui
		  *point� par la position courante.
		  *
		  *On ne fait rien si la position courante est au d�but
		  *
		  ***********************************************************/

		 //on ne d�place rien si on est au d�but
		 if(positionCourante > 0)
			 positionCourante--;
	 }


	 /**
	  * D�cale les donn�es d'un tableau d'une case vers la droite pour les cases
	  * de d�but � fin.  D�but et fin sont consid�r�s comme valides.
	  */
	 private void decalerDroite(Object[] tab, int debut, int fin){

		 for(int i = fin; i >= debut;i--){
		   tab[i+1] = tab[i];
		 }

	 }


	 /**
	  * D�cale les donn�es d'un tableau d'une case vers la gauche pour les cases
	  * de d�but � fin.  D�but et fin sont consid�r�s comme valides.
	  */
	 private void decalerGauche(Object[] tab, int debut, int fin){

		 for(int i = debut; i <= fin;i++){
			   tab[i-1] = tab[i];
		 }

	 }


	 /**
	  * Ins�re l'�l�ment re�u � la position courante apr�s avoir d�plac�
	  * tous les �l�ments vers la droite d'une case
      *
      * Ant�d�cent : Aucun.
      *
	  * Cons�quent : nbElement = nbElement + 1 & liste.getElement() == element.
	  *                       La position courante reste inchang�e
	  *
	  * @param element L'�l�ment � ins�rer � la position courante.
	  *
	  */
	 public void insererALaPosition(Object element){

		 /*
		  * STRAT�GIE : On utilise le nombre d'�l�ments pour tester ss'il reste
		  * de  la place. Si c'est le cas, on d�cale les donn�es � l'aide de la classe
		  * Arrays et on met l'�l�ment � la position courante qui sera d�plac�e
		  * sur le nouvel �l�ment ins�r�.
		  */

		 //Si la liste n'est pas pleine seulement
		 if(nbElements < liste.length){

			 decalerDroite(liste, positionCourante, nbElements-1);

			 liste[positionCourante] = element;

		    //un �l�ment de plus
		    nbElements++;
		 }

	 }

	 /**
	  * Ins�re l'�l�ment re�u avant la position courante
	  *
      * Ant�d�cent : Aucun.
      *
	  * Cons�quent : nbElement = nbElement + 1 & liste.getElement() == element.
	  *
	  * @param element L'�l�ment � ins�rer dans la liste avant la position courante.
	  */
	 public void insererApres(Object element){

		 /*
		  * STRAT�GIE : On utilise insererApres en utilisant positionCourante -1
		  */

		 setPosSuivant();
		 insererALaPosition(element);


	 }

	 /**
	 * Supprime l'�l�ment � la position courante.
	 */
	 public void supprime(){

	     decalerGauche(liste, positionCourante+1, nbElements -1);
	     nbElements--;
	 }

	 //LES AUTRES M�THODES
	 /**
	  * Retourne l'�l�ment � la position courante
	  *
	  * Ant�c�dent : La liste ne doit pas �tre vide.
	  *
	  * Cons�quent : Le contenu de la liste est inchang� et la position courante
	  * reste inchang�e.
	  *
	  * @return L'�l�ment � la position courante
	  */
	 public Object getElement(){

		      /*STRAT�GIE : Retourne simplement l'�l�ment � la
		       * position courante.
		       */
			 return liste[positionCourante];
	 }

	 /**
	  * Retourne le nombre d'�l�ments actuellement dans la liste.
	  *
	  * Ant�c�dent : aucun.
	  * Cons�quent : aucun.
	  *
	  * @return Le nombre d'�l�ments de la liste.
	  */
	 public int getNbElements(){

		 return nbElements;
	 }


}