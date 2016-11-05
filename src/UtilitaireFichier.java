import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 * Classe utilitaire qui permet de sauvegarder dans un fichier binaire ou texte.
 * Elle petmer aussi de de récupérer une boite.
 * 
 * @author Antoine Proulx-Bégin
 */
public class UtilitaireFichier {

	// Permet d'écrire dans un fichier texte en colonne et l'ouvrir dans Excel.
	// Il suffit d'écrire un TAB pour changer de colonne.
	private static final String TAB = "\t";

	/**
	 * Sauvegarde la boîte dans un fichier texte dont on reçoit le nom.
	 * 
	 */
	public static void sauvegarderDsFichierTexte(Boite boite, String nomFic) {
		try {
			PrintWriter f = new PrintWriter(nomFic);
			f.println("Nombre d'ampères de la boîte: " + boite.getMaxAmperes());
			f.println("Temps de l'UPS: " + boite.temps_ups());
			f.println("Puissance totale consommée (W): "
					+ boite.getConsommationTotalEnWatt());
			f.println("Position (colonne-ligne)" + TAB + "Tension" + TAB
					+ "Ampérage utilisé (W)" + TAB + "Ratio d'utilisation");

			for (int i = 0; i < Boite.NB_COLONNES; i++) {
				for (int j = 0; j < Boite.NB_LIGNES_MAX; j++) {
					if (!boite.getEmplacementEstVide(i, j)) {
						f.println(i + "-" + j + TAB
								+ boite.getDisjoncteur(i, j).getTension() + TAB
								+ boite.getDisjoncteur(i, j).getAmpere() + TAB
								+ boite.getDisjoncteur(i, j).getRatio() + "%");
					}
				}
			}
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sauvegarde la boîte dans le fichier fichier binaire avec le nom reçu.
	 * 
	 * On présume le nom de fichier valide.
	 * 
	 * @param nomFic
	 *            à sauvegarder la boîte.
	 * @param boite
	 *            La boîte à  sauvegarder.
	 */
	public static void sauvegarderBoite(Boite boite, String nomFic) {
		try {
			File fichier = new File(nomFic);
			FileOutputStream f = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(boite);
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre le fichier dont le nom correspond à  celui reçu.
	 * 
	 * Exception : Le fichier doit contenir une boîte sauvegarder par la méthode
	 * sauvegarderBoite.
	 * 
	 * @param nomFic
	 *            Le nom du fichier à  ouvrir
	 * @return La boîte contenu dans le fichier.
	 * @throws ClassNotFoundException
	 */
	public static Boite recupererBoite(String nomFic)
			throws ClassNotFoundException {
		Boite b = null;

		try {
			FileInputStream f = new FileInputStream(nomFic);
			ObjectInputStream ois = new ObjectInputStream(f);

			b = (Boite) (ois.readObject());

			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}
}