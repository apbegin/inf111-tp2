import java.io.File;
import java.nio.file.Files;
import java.sql.Time;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.jmx.snmp.Timestamp;

import javafx.scene.Parent;
import jdk.nashorn.internal.codegen.CompilerConstants.Call;

/**
 * Classe qui contient les SP pour g�rer les boutons d'options de menu.
 * 
 * S'il y a ajout de bouton, il faut modifier cette classe et y ajouter le
 * comportement d�sir�.
 * 
 * @author Pierre B�lisle (copyright A2016)
 *
 */
public class UtilitaireGestionMenu {

	// Extension pour les noms de fichier texte de type classeurs
	public static final String EXTENSION_FICHIER_TEXTE = "csv";

	// Extension choisie arbitrairement pour les noms de fichier contenant
	// une bo�te.
	public static final String EXTENSION_BOITE = "bte";

	public static final String DESC_EXTENSION = "*." + EXTENSION_BOITE;

	/**
	 * L'utilisateur a quitt�, on lui demande si c'est bien ce qu'il veut et
	 * s'il veut sauvegarder avant de quitter.
	 * 
	 * return Si l'utilisateur poursuit dans sa d�marche de quitter.
	 */
	public static boolean veutSortir(Boite boite) {

		if (JOptionPane.showConfirmDialog(null,
				"Voulez-vous quitter l'application?", "ATTENTION",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			if (JOptionPane.showConfirmDialog(null,
					"Voulez-vous sauvegarder avant de quitter?", "ATTENTION",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				sauvegarderBoite(boite);
			}
			return true;
		}
		return false;
	}

	/**
	 * Ajoute un disjoncteur � la bo�te.
	 * 
	 * @param boite
	 */
	public static void ajouterDisjoncteur(Boite boite) {

		if (boite.getEmplacementEncoreDisponible()) {

			double ampere = UtilitaireEntreeSortie.ampereValide();
			double tension = UtilitaireEntreeSortie.tensionValide();
			Disjoncteur d = new Disjoncteur(ampere, tension);
			Coord c = boite.getEmplacementDisponible();
			boite.ajouterDisjoncteur(c.colonne, c.ligne, d);

		} else
			JOptionPane.showMessageDialog(null, "La bo�te");
	}

	/**
	 * Ajoute une demande � un disjoncteur. Si la demande est trop grande, le
	 * disjoncteur est �teint.
	 * 
	 * @param boite
	 *            La boite � consid�rer.
	 */
	public static void ajouterDemande(Boite boite) {
		int colonne = -1;
		int ligne = -1;
		do {
			colonne = UtilitaireEntreeSortie.entierValide(
					"Entrez la colonne de la bo�te", 1, (Boite.NB_COLONNES));
		} while (colonne == -1);
		if (colonne != 0) {
			do {
				ligne = UtilitaireEntreeSortie.entierValide(
						"Entrez la ligne de la bo�te", 1,
						(Boite.NB_LIGNES_MAX));
			} while (ligne == -1);
			if (ligne != 0) {
				if (!boite.getEmplacementEstVide(colonne - 1, ligne - 1)) {
					int ampereMax = (int) (boite
							.getDisjoncteur(colonne - 1, ligne - 1).getAmpere()
							* Disjoncteur.POURCENTAGE_PUISSANCE_MAX);
					int demande = UtilitaireEntreeSortie.ampereValide();
					if (demande > 0)
						boite.ajouterDemande(colonne - 1, ligne - 1, demande);
					else
						boite.retirerPuissance(colonne - 1, ligne - 1, demande);
				} else
					JOptionPane.showMessageDialog(null,
							"Aucun disjoncteur � cette position");
			}

		}

	}

	/**
	 * Sert � l'interaction avec l'utilisateur pour obtenir le nom du fichier de
	 * sauvegarde et sa validation.
	 * 
	 * @return La boite r�cup�rer ou null.
	 */
	public static Boite recupererBoite() {

		Boite boite = null;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("",
				UtilitaireGestionMenu.EXTENSION_BOITE);
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION
				&& chooser.getSelectedFile().getName().contains(".bte")) {

			try {
				boite = UtilitaireFichier.recupererBoite(
						chooser.getSelectedFile().getAbsolutePath());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return boite;
	}

	/**
	 * Sert � l'interaction avec l'utilisateur pour obtenir le nom du fichier de
	 * sauvegarde et sa validation.
	 * 
	 * @param La
	 *            bo�te � sauvegarder.
	 */
	public static void sauvegarderBoite(Boite boite) {

		JFileChooser chooser = new JFileChooser();

		int result = chooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String nomFichier = chooser.getSelectedFile().getAbsolutePath();

			UtilitaireFichier.sauvegarderDsFichierTexte(boite,
					(nomFichier + "." + EXTENSION_FICHIER_TEXTE));
			UtilitaireFichier.sauvegarderBoite(boite,
					(nomFichier + "." + EXTENSION_BOITE));
		}

	}

}