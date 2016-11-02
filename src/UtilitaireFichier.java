import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public static void sauvegarderDsFichierTexte(Boite boite, String nomFic){
		try {
			PrintWriter f = new PrintWriter(nomFic);
			f.println("Position (colonne-ligne)"+TAB+
					"Tension"+TAB+
					"Amp�rage utilis� (W)"+TAB+
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
	 * Sauvegarde la bo�te dans le fichier fichier binaire avec le nom re�u.
	 * 
	 * On pr�sume le nom de fichier valide.
	 * 
	 * @param nomFic � sauvegarder la bo�te.
	 * @param boite La bo�te � sauvegarder.
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