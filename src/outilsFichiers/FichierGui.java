/* Copyright 2021 Anthony Fernandez <forelli87@gmail.com> : GPL-3.0-or-later
 * 
 *   This file is part of Jokern.

    Jokern is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Jokern is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Jokern.  If not, see <https://www.gnu.org/licenses/>
 * 
 * */

package outilsFichiers;

import javax.swing.filechooser.*;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;

/**
 * Boîte à outil, pour manipuler les fichiers avec une GUI.
 * @author Anthony Fernandez
 * @version v0.1.0
 *
 */
public class FichierGui {
	
	private String nomDutilisateur=System.getProperty("user.name");
	
	/**
	 * Ouverture d'une boîte de dialogue pour récupérer un nom de fichier. 
	 * @return <b>Chaîne</b> contenant le chemin complet du fichier.
	 */
	public String openFichier() {
		JFileChooser fileGui=new JFileChooser();
		int resultat=fileGui.showOpenDialog(null);
				
		String cheminFichier="-1";
		
		
		try {
			File fichierOuverture=new File(new File("/home/"+nomDutilisateur).getCanonicalPath());
			
			if(resultat==JFileChooser.APPROVE_OPTION) {
				cheminFichier=fileGui.getSelectedFile().getAbsolutePath();
			}
			
			else if(resultat==JFileChooser.CANCEL_OPTION) {
				cheminFichier="-2";
			}
			
		}
		
		catch(NullPointerException E) {
			
		}
		
		catch (IOException E) {
			
		}
		
		
		return cheminFichier;
	}
	
	
	/**
	 * Ouvre une fenêtre de sauvegarde.
	 * @return <b>Chaîne</b> contient le chemin de sauvegarde
	 */
	public String saveFichier() {
		JFileChooser fileGui=new JFileChooser();
		int resultat=fileGui.showSaveDialog(null);
		String cheminFichier="-1";
		
		try {
			File fichierSauvegarde=new File(new File("/home/"+nomDutilisateur).getCanonicalPath());
			
			//Action bouton Ok
			if(resultat==JFileChooser.APPROVE_OPTION) {
				cheminFichier=fileGui.getSelectedFile().getAbsolutePath();
			}
			
			//Action bouton Cancel
			else if(resultat==JFileChooser.CANCEL_OPTION) {
				cheminFichier="-2";
			}
		}


		catch(NullPointerException E) {
			
		}
		
		catch (IOException E) {
			
		}	
		
		return cheminFichier;
	}
	
	public String saveFichierDirectory() {
		JFileChooser fileGui=new JFileChooser();
		fileGui.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Seul le répertoire est sélectionné.
		int resultat=fileGui.showSaveDialog(null);
		String cheminFichier="-1";
		
		try {
			//File fichierSauvegarde=new File(new File("/home/"+nomDutilisateur).getCanonicalPath());
			
			//Action bouton Ok
			if(resultat==JFileChooser.APPROVE_OPTION) {
				cheminFichier=fileGui.getSelectedFile().getAbsolutePath();
			}
			
			//Action bouton Cancel
			else if(resultat==JFileChooser.CANCEL_OPTION) {
				cheminFichier="-2";
			}
		}


		catch(NullPointerException E) {
			
		}
				
		return cheminFichier;
	}
	
	

}
