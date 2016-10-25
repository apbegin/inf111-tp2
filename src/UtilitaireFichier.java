/**
 * Classe utilitaire qui permet de sauvegarder dans un fichier binaire ou texte.
 * Elle petmer aussi de de r�cup�rer une boite.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UtilitaireFichier {

	// Permet d'�crire dans un fichier texte en colonne et l'ouvrir dans Excel.
	// Il suffit d'�crire un TAB pour changer de colonne.
	private static final String TAB = "\t";

	/**
	 * Sauvegarde la bo�te dans un fichier texte dont on re�oit le nom.
	 * 
	 */
	public static void sauvegarderDsFichierTexte(Boite boite, String nomFic){
		
		File fichier = new File(nomFic);
		try {
			FileInputStream write = new FileInputStream(fichier);
			
		
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
	 * @param nomFic o� sauvegarder la bo�te.
	 * @param boite La bo�te � sauvegarder.
	 */
	public static void sauvegarderBoite(Boite boite, String nomFic){

		

	}
	
	/**
	 * Ouvre le fichier dont le nom correspond � celui re�u.
	 * 
	 * Exception : Le fichier doit contenir une bo�te sauvegarder par
	 * la m�thode sauvegarderBoite.
	 * 
	 * @param nomFic Le nom du fichier � ouvrir
	 * @return La bo�te contenu dans le fichier.
	 */
	public static Boite recupererBoite(String nomFic){

		Boite boite = null;
		
		return boite;
	}
}