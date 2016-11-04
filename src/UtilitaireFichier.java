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
 * Elle petmer aussi de de r�cup�rer une boite.
 * 
 * @author Antoine Proulx-B�gin
 */
public class UtilitaireFichier {

	// Permet d'�crire dans un fichier texte en colonne et l'ouvrir dans Excel.
	// Il suffit d'�crire un TAB pour changer de colonne.
	private static final String TAB = "\t";

	/**
	 * Sauvegarde la bo�te dans un fichier texte dont on re�oit le nom.
	 * 
	 */
	public static void sauvegarderDsFichierTexte(Boite boite, String nomFic) {
		try {
			PrintWriter f = new PrintWriter(nomFic);
			f.println("Nombre d'amp�res de la bo�te: " + boite.getMaxAmperes());
			f.println("Temps de l'UPS: " + boite.temps_ups());
			f.println("Puissance totale consomm�e (W): "
					+ boite.getConsommationTotalEnWatt());
			f.println("Position (colonne-ligne)" + TAB + "Tension" + TAB
					+ "Amp�rage utilis� (W)" + TAB + "Ratio d'utilisation");

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
	 * Sauvegarde la bo�te dans le fichier fichier binaire avec le nom re�u.
	 * 
	 * On pr�sume le nom de fichier valide.
	 * 
	 * @param nomFic
	 *            � sauvegarder la bo�te.
	 * @param boite
	 *            La bo�te � sauvegarder.
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
	 * Ouvre le fichier dont le nom correspond � celui re�u.
	 * 
	 * Exception : Le fichier doit contenir une bo�te sauvegarder par la m�thode
	 * sauvegarderBoite.
	 * 
	 * @param nomFic
	 *            Le nom du fichier � ouvrir
	 * @return La bo�te contenu dans le fichier.
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