/* Copyright 2021 Anthony Fernandez <forelli87@gmail.com> : GPL-3.0-or-later
 * 
 *   This file is part of Jokern.

    Jokern is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, and
    any later version.

    Jokern is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Jokern.  If not, see <https://www.gnu.org/licenses/>
 * 
 * */

package texteOnApp;

import java.util.Locale;

/**
 * Classe contenant toutes les chaînes de caractères du programme.
 * @author Anthony Fernandez
 * @version v0.1.0
 */
public class StrLdc extends StrManage {
	
	//langueLdc.cplDlg_txtBtnAddField_global[langueLdc.getLocale()]
	
	//*** getChamp ***
	public final String ldcStr_champALire[]= {"N° de champ à lire (-1 pour annuler)>\t","Field\'s number to read (-1 to cancel)>\t"};
	
	//*** addChamp ***
	public final String ldcStr_saisirTitre_global[]={"Titre du champ > ","Field\'s title > "};
	public final String ldcStr_saisirCommentaire_global[]={"Commentaire > ","Field\'s comment > "};
	public final String ldcStr_saisirLogin_global[]={"Pseudo > ","Login > "};
	public final String ldcStr_saisirMdp_global[]={"Mot de passe > ","Password > "};
	
	//*** setChamp ***
	public final String ldcStr_menuModif_global[]= {"--- MENU DE MODIFICATION ---\n\n"
			+ "[0]\tTitre\n"
			+ "[1]\tCommentaire\n"
			+ "[2]\tCouple\n"
			+ "[3]\tAjouter un couple\n"
			+ "[4]\tSupprimer un couple\n"
			+ "[5]\tQuitter la modification\n\n"
			+ "Choix MENU > ","--- SET MENU ---\n"
					+ "[0]\tTitle\n"
					+ "[1]\tComment\n"
					+ "[2]\tCouple\n"
					+ "[3]\tAdd a couple\n"
					+ "[4]\tRemove a couple\n"
					+ "[5]\tExit set menu\n\n"
					+ "Choice > "};
	
	//*** Erreurs ***
	public final String ldcStr_errGraveRecupChamp[]={"-ERREUR GRAVE à la récupération du champ.","FATAL ERROR to get a field."};
	public final String ldcStr_pasDeCouple[]={"-ERREUR, aucun couple à lire.","ERROR, no data to read."};
	public final String ldcStr_errFichier[]= {"Le fichier n'a pas pu être ouvert.","Cannot open the file."};
	public final String ldcStr_fichierInexistant[]= {"Le fichier n\'existe pas.","The file doesn\'t exist."};
	
	//*** showAide ***
	public final String ldcStr_aideLdc[]= {""
			+ "add\tAjouter un champ\n"
			+ "ls\tLister les champs\n"
			+ "get\tAfficher les couples Pseudo + mot de passe enregistrés\n"
			+ "set\tModification d\'un champ, ouverture d\'un sous-menu de gestion.\n"
			+ "rm\tSuppression d\'un champ\n"
			+ "save\tSauvegerder/Créer un fichier\n"
			+ "pass\tModifier le mot de passe de cryptage du fichier courant\n"
			+ "open\tOuvrir un fichier crypté\n"
			+ "close\tFermer le fichier courant\n"
			+ "closeup\tFermer le fichier courant mais garder en mémoire les champs\n"
			+ "exit\tQuitter Jokern",""
					+ "add\tAdd a field\n"
					+ "ls\tShow current(s) field(s)\n"
					+ "get\tShow couples LOGIN + PASSWORD wich are recorded\n"
					+ "set\tSet a field, this open a sub menu.\n"
					+ "rm\tRemove a field\n"
					+ "pass\tChange the password, of the current file\n"
					+ "open\tOpen an encrypted file\n"
					+ "close\tClose the current file\n"
					+ "closeup\tClose the current file, but doesn\'t purge data on memory.\n"
					+ "exit\tExit Jokern"};
	
	//*** openFichier ***
	public final String ldcStr_saisirPathFichier[]= {"Saisir le chemin complet du fichier > ","Complete path of path > "};
	public final String ldcStr_fichierOuvert[]= {"\tLe fichier est ouvert avec succès","\tThe file is open with success"};

}
