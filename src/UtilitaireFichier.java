import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public static void sauvegarderDsFichierTexte(Boite boite, String nomFic){
		try {
			PrintWriter f = new PrintWriter(nomFic);
			f.println("Position (colonne-ligne)"+TAB+
					"Tension"+TAB+
					"Ampérage utilisé (W)"+TAB+
					"Ratio d'utilisation");
			int ligne=0;
			for(int i=0; i < Boite.NB_COLONNES; i++){
				while(ligne < Boite.NB_LIGNES_MAX ||
						!boite.getEmplacementEstVide(i,ligne)){
					f.println(i+"-"+ligne+TAB+
							boite.getDisjoncteur(i, ligne).getTension()+TAB+
							boite.getDisjoncteur(i, ligne).getAmpere()+TAB+
							boite.getDisjoncteur(i, ligne).getRatio()+"%");
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
	 * @param nomFic à sauvegarder la boîte.
	 * @param boite La boîte à  sauvegarder.
	 */
	public static void sauvegarderBoite(Boite boite, String nomFic){
		try{
			File fichier = new File(nomFic);
			
			FileOutputStream f = new FileOutputStream(fichier);
			f.write(Integer.toString(boite.getMaxAmperes()).getBytes());
			f.write(Double.toString(boite.temps_ups()).getBytes());
			f.write(Double.toString
					(boite.getConsommationTotalEnWatt()).getBytes());
			
			int ligne=0;
			String data="";
			for(int i=0; i < Boite.NB_COLONNES; i++){
				while(ligne < Boite.NB_LIGNES_MAX ||
						!boite.getEmplacementEstVide(i,ligne)){
					data =	i+"-"+ligne+"/"+
							boite.getDisjoncteur(i, ligne).getTension()+"/"+
							boite.getDisjoncteur(i, ligne).getAmpere()+"/"+
							boite.getDisjoncteur(i, ligne).getRatio()+"\n";
					f.write(data.getBytes());
				}
			}
			f.close();
		
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
	 * Exception : Le fichier doit contenir une boîte sauvegarder par
	 * la méthode sauvegarderBoite.
	 * 
	 * @param nomFic Le nom du fichier à  ouvrir
	 * @return La boîte contenu dans le fichier.
	 */
	public static Boite recupererBoite(String nomFic){

		Boite boite = null;
		
		return boite;
	}
}