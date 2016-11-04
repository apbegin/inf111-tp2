import javax.swing.JOptionPane;

/**
 * 
 * @author Antoine Proulx-B�gin
 *
 *         Utilitaire pour la validation des valeurs saisies par l'utilisateur
 *
 */
public class UtilitaireEntreeSortie {

	/**
	 * Fonction pour saisir et valider la tension d'un disjoncteur.
	 */
	public static int tensionValide() {

		/*
		 * Strat�gie : On utilise JOptionPane pour la saisie et les constantes
		 * pr�vues.
		 */

		// JOptionPane retourne un String.
		String tension;

		// Utilis� pour la validation avec les String.
		String tensionEntree = String.valueOf(Disjoncteur.TENSION_ENTREE);
		String tensionPhase = String.valueOf(Disjoncteur.TENSION_PHASE);

		do {
			tension = JOptionPane.showInputDialog("Entrez une tension valide "
					+ Disjoncteur.CHAINE_TENSION_PERMISE);

		} while (tension != null && !tension.equals(tensionEntree)
				&& !tension.equals(tensionPhase));

		return (tension == null) ? 0 : Integer.parseInt(tension);
	}

	/**
	 * Fonction locale pour saisir et valider l'amp�rage d'un disjoncteur.
	 */
	public static int ampereValide() {
		String ampere;
		boolean valide = false;

		do {
			ampere = JOptionPane.showInputDialog("Entrez un ampere valide "
					+ Disjoncteur.CHAINE_AMPERAGE_PERMIS);
			
			if (!(ampere == null) && !ampere.equals("")) {
				for (int i = 0; i < Disjoncteur.AMPERAGES_PERMIS.length; i++) {
				
					/* ampere peut avoir valeur n�gative si l'on retire une
					 * demande d'un disjoncteur
					 */
					if (Disjoncteur.AMPERAGES_PERMIS[i] == Math
							.abs(Integer.parseInt(ampere)))
						valide = true;
				//si l'utilisateur � saisie des caract�res non-num�riques
				}
			}
		} while (!(ampere == null) && !valide);

		//valeur al�atoire si l'usager annule 
		return (ampere == null)
				? Disjoncteur.AMPERAGES_PERMIS[UtilitaireMath.entierAlea(0,
						Disjoncteur.AMPERAGES_PERMIS.length - 1)]
				: Integer.parseInt(ampere);
	}

	/**
	 * Saisit et valide un entier entre min et max. La fonction retourne min - 1
	 * si l'utilisateur annule.
	 * 
	 * 
	 * @param msgSollic
	 *            Le message affich�.
	 * @param min
	 *            La plus petite valeur permise.
	 * @param max
	 *            La plus grande valeur permise.
	 * 
	 * @return L'entier saisit ou min-1 si l'utilisateur annule.
	 */
	public static int entierValide(String msgSollic, int min, int max) {

		String entier = null;

		// Version String des valeurs re�ues.
		String minString = String.valueOf(min);
		String maxString = String.valueOf(max);

		do {
			entier = JOptionPane.showInputDialog(
					msgSollic + " entre " + minString + " et " + maxString);

			// V�rifier si c'est convertissable en entier.
			try {

				if (entier != null) {
					int x = Integer.parseInt(entier);
				}
			} catch (Exception e) {

				// Dans le cas d'une exception, on remet un entier invalide.
				entier = String.valueOf(min - 1);
			}

		} while (entier != null && (Integer.parseInt(entier) < min
				|| Integer.parseInt(entier) > max));

		return (entier == null) ? min - 1 : Integer.parseInt(entier);
	}

	/**
	 * Saisit et valide un r�el entre min et max. La fonction retourne
	 * Double.NaN si l'utilisateur annule.
	 * 
	 * 
	 * @param msgSollic
	 *            Le message affich�.
	 * @param min
	 *            La plus petite valeur permise.
	 * @param max
	 *            La plus grande valeur permise.
	 * 
	 * @return L'entier saisit ou min-1 si l'utilisateur annule.
	 */
	public static double reelValide(String msgSollic) {

		String reel = null;

		reel = JOptionPane.showInputDialog(msgSollic);

		// V�rifier si c'est convertissable en r�el.
		try {

			if (reel != null) {
				// Tentative de conversion.
				double x = Double.parseDouble(reel);
			}
		} catch (Exception e) {

			// Dans le cas d'une exception, reel == null.
			reel = null;
		}
		return (reel == null) ? Double.NaN : Double.parseDouble(reel);
	}

}