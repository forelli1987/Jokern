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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.crypto.SecretKey;

import outilsFichiers.cryptographieFazy;
import texteOnApp.StrLdc;

/**
 * Regroupe les opérations et les outils necessaires pour faire tourner Jokern.
 * @author Anthony Fernandez
 * @version v0.1.0
 */

public class operationsJokern extends cryptographieFazy{
	
	/*
	 * Initialisation des variables de base du programme.
	 * 
	 * */
	
	
	private String pathFichierEnregistrement=""; //CHemin complet pour enregistrer le fichier.
	private String mdpCryptageAES; //Mot de passe de cryptage.
	private String prompt=""; //Affichage du shell, doit varier en fonction du menu que l'on a.
	private String systemOs=System.getProperty("os.name");
	private String nomDuser=System.getProperty("user.name");
	private StrLdc langueLdc=new StrLdc();
	
	
	//Instanciation de l'objet pour la saisie.
	private Scanner sc=new Scanner(System.in);
	
	//La liste de donnée d'un champ.
	private List <dataJokern> jokernChamp=new ArrayList<dataJokern>();
	
	/**
	 * Constructeur "vide" volontairement.
	 */
	public operationsJokern() {
		
	}
	
	/**
	 * @param mdpCryptage Mot de passe pour le cryptage ou décryptage AES.
	 */
	public operationsJokern(String mdpCryptage) {
		this.mdpCryptageAES=mdpCryptage;
	}
	
	/**
	 * Ecrire la liste de champ et crypter le fichier.<br>
	 * Version avec une SecretKey à la place du mot de passe.
	 * @param pathFichier Le chemin absolu du fichier. <b>STRING</b>
	 * @param cleMdp L'image du mot de passe. <b>SecretKey</b>
	 * @param champs Les données de type <b>List <i>dataJokern</i></b>
	 */
	/*public void ecrireListeFichier(String pathFichier, String mdp,List <dataJokern> champs) {
		
		try (FileOutputStream fos = new FileOutputStream(pathFichier);
			    ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			    oos.writeObject(champs);
			    		    
				this.cryptAES(pathFichier, this.genCleMDP(mdp), "crypt");
				
			    fos.close();

			} 
		catch (IOException ex) {
			    ex.printStackTrace();
		}
	}*/
	
	/**
	 * Ecrire la liste de champ et crypter le fichier.<br>
	 * Version avec une SecretKey à la place du mot de passe.
	 * @param pathFichier Le chemin absolu du fichier. <b>STRING</b>
	 * @param cleMdp L'image du mot de passe. <b>SecretKey</b>
	 * @param champs Les données de type <b>List <i>dataJokern</i></b>
	 */
	public void ecrireListeFichier(String pathFichier, SecretKey cleMdp,List <dataJokern> champs) {
		
		try (FileOutputStream fos = new FileOutputStream(pathFichier);
			    ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			    oos.writeObject(champs);
			    		    
				this.cryptAES(pathFichier, cleMdp, "crypt");
				
			    fos.close();

			} 
		catch (IOException ex) {
			    ex.printStackTrace();
		}
	}
	
	
	/**
	 * Permet de lire un fichier décrypté de la liste.
	 * @param pathFichier Chemin du fichier à décrypter. <b>String</b>
	 * @param cleMdp Mot de passe pour le décryptage. <b>SecretKey</b>
	 * @return Une liste de type <b>ArrayList</b> si c'est <b>null</b> alors c'est que la récupération s'est mal passé.
	 */
	public ArrayList<dataJokern> lectureListDecrypt(String pathFichier, SecretKey cleMdp) {
		
		ArrayList<dataJokern> objetRetour=null;
		
		//Décryptage du fichier puis, réenregistrement au même emplacement.
		int retourCryptage=this.cryptAES(pathFichier, cleMdp,"decrypt");

		
		if(retourCryptage==0) {
			try (FileInputStream fis = new FileInputStream(pathFichier);
					ObjectInputStream ois = new ObjectInputStream(fis)) {
				
				objetRetour=(ArrayList)ois.readObject();
				    
				//Si on a récupéré une liste d'objet, alors on peut détruire profondément le fichier.
				if(!objetRetour.isEmpty()) {
				  this.remplAleatoire(pathFichier, 3, 'm'); //On "souille" le fichier avant sa destruction, avec des octets aléatoires.
			 	  fis.close();
				  File fichierSuppression = new File(pathFichier); //Suppression du fichier après "souillage".
				  fichierSuppression.delete();
				  }
				    
				    
				} 
			catch (IOException ex) {
				ex.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
				
		return objetRetour;
		
	}
	
	//************* INTERFACE EN LIGNE DE COMMANDE *****************
	
	/**
	 * Invite de commande pour utiliser Jokern<br>
	 * <i>Désactivée volontairement pour l'instant</i>
	 */
	private void interfaceLdcPrincipale() {
		String commande="";
		
		this.initialisationPrompt();
		
		while(!commande.equals("exit")) {
			commande=this.saisiePerso(sc, prompt);
			/*System.out.print(prompt);
			commande=this.sc.nextLine();*/
			
			switch (commande) {
				case "help":{
					this.showAide();
					break;
				}
				
				//Ajout d'un champ
				case "add":{
					this.addChamp();
					break;
				}
								
				//Lister les champs
				case "ls":{
					this.listChamp();
					break;
				}
				
				//Lister les couples Login et Mot de passe d'un champ.
				case "get":{
					this.getChamp();
					break;
				}
				
				
				//Modifier un champ
				case "set":{
					this.setChamp();
					break;
				}
				
				//Suppression d'un champ
				case "rm":{
					byte champAsupprimer = this.saisiePersoByte(sc, "N° de champ à supprimer >\t");
					this.jokernChamp.remove(champAsupprimer);
					break;
				}
				
				case "save":{
					this.saveFichier(false);
					this.initialisationPrompt();
					break;
				}
				
				case "pass":{
					this.newFichier(this.pathFichierEnregistrement);
					break;
				}
				
				//Ouvrir un fichier
				case "open":{
					if(this.openFichier()==0) { //Condition permettant de remettre à jour le prompt, si l'ouverture du fichier s'est bien déroulée.
						this.initialisationPrompt();
					}
					
					break;
				}
							
				case "exit":{
					this.closeFichier(true);//On brouille les données avant la fermeture (pour éviter de récupérer certaines données dans la ram
					break;
				}
				
				//Fermeture du fichier en cours d'utilisation.
				case "close":{
					this.closeFichier(true);
					this.initialisationPrompt();
					break;
				}
				
				//Fermeture du fichier en cours d'utilisation, sans purger les données.
				case "closeup":{
					this.closeFichier(false);
					this.initialisationPrompt();
					break;
				}
				
				default :{
					System.out.println("Commande non reconnue");
				}
			}
		}

	}
	
	/***METHODE QUI EXECUTENT LES COMMANDES***/
	

	public void listChamp() {
		for(int i=0; i<this.jokernChamp.size();i++) {
			System.out.println("["+i+"]\t"+this.jokernChamp.get(i).getTitle()+"\n\t"+this.jokernChamp.get(i).getComment()+"\n");
		}
	}
	
	private void addChamp() {
		String titre,commentaire,login,mdp;
		
		System.out.print(langueLdc.ldcStr_saisirTitre_global[langueLdc.getLocale()]);
		titre=this.sc.nextLine();
		
		System.out.print(langueLdc.ldcStr_saisirCommentaire_global[langueLdc.getLocale()]);
		commentaire=this.sc.nextLine();
		
		System.out.print(langueLdc.ldcStr_saisirLogin_global[langueLdc.getLocale()]);
		login=this.sc.nextLine();
		
		System.out.print(langueLdc.ldcStr_saisirMdp_global[langueLdc.getLocale()]);
		mdp=this.sc.nextLine();
		
		this.jokernChamp.add(new dataJokern (titre,commentaire,login,mdp));
	}
	
	/**
	 * Affichage de tous les couples <b>Login</b> et <b>Mot de passe<b/>, d'un champ.
	 */
	private byte getChamp() {
		
		this.listChamp();
		
		byte numChamp=0;
		String coupleRecuperation="";
		String couple[];
		numChamp=this.saisiePersoByte(sc, langueLdc.ldcStr_champALire[langueLdc.getLocale()]);
		
		if(numChamp!=-1) {
		
			int tailleListe=this.jokernChamp.size();
		
			System.out.println("\t---"+this.jokernChamp.get(numChamp).getTitle()+"---\n"); //Affichage du titre du champ.
		
			//Vérifier la présence des champs.
			if(numChamp<tailleListe && tailleListe>0) {
				try {
					for(int i=0;i<this.jokernChamp.get(numChamp).size();i++) {
						coupleRecuperation=this.jokernChamp.get(numChamp).getOneLoginTxt(i);
						couple=coupleRecuperation.split(";");
						System.out.println("\t["+i+"]\t"+langueLdc.ldcStr_saisirLogin_global[langueLdc.getLocale()]+"\t\t"+couple[0]);
						System.out.println("\t"+langueLdc.ldcStr_saisirMdp_global[langueLdc.getLocale()]+"\t\t"+couple[1]+"\n");
				
					}
				}
				
				catch(ArrayIndexOutOfBoundsException E) {
					System.out.println(langueLdc.ldcStr_saisirMdp_global[langueLdc.getLocale()]);
				}

				this.effacerEcran(sc);
			}
		
			else {
				System.out.println(langueLdc.ldcStr_pasDeCouple[langueLdc.getLocale()]);
				numChamp=-1;
			}
		}
		
		return numChamp;
		
	}
	
	/**
	 * Modification d'un champ, cette méthode embarque une petite interface en ldc complète, avec plusieurs possibilités :<br>
	 * <li>Modifier le titre</li>
	 * <li>Le commentaire</li>
	 * <li>Modifier un couple de <i>login</i> et <i>mot de passe</i></li>
	 * <li>Ajouter un couple de <i>login</i> et <i>mot de passe</i></li>
	 *
	 */
	private void setChamp() {
		
		this.listChamp();		
		byte numChamp=0;
						
		while(numChamp!=5) {
			System.out.println(langueLdc.ldcStr_menuModif_global[langueLdc.getLocale()]);

			numChamp=sc.nextByte();
			sc.nextLine();
			
			switch(numChamp) {
			
				case 0:{
					byte champAmodifier=this.saisiePersoByte(sc, "Champ à modifier (-1 pour annuler) >\t");
					
					if(champAmodifier!=-1) {
						String nouveauTitre=this.saisiePerso(sc, "Nouveau titre >\t");
						this.jokernChamp.get(champAmodifier).setTitle(nouveauTitre);
					}
					
					break;
				}
				
				case 1:{
					byte champAmodifier=this.saisiePersoByte(sc, "Champ à modifier (-1 pour annuler) >\t");
					
					if(champAmodifier!=-1) {
						String nouveauCommentaire=this.saisiePerso(sc, "Nouveau commentaire >\t");
						this.jokernChamp.get(champAmodifier).setComment(nouveauCommentaire);
					}				

					break;
				}
				
				case 2:{
					byte champAmodifier=this.getChamp();
					byte champCoupleAmodifier=this.saisiePersoByte(sc, "Champ du couple à modifier >\t");
					String nouveauLogin=this.saisiePerso(sc, "Nouveau login >\t");
					String nouveauPassePasse=this.saisiePerso(sc, "Nouveau mot de passe >\t");
					this.jokernChamp.get(champAmodifier).setLoginCouple(champCoupleAmodifier, nouveauLogin, nouveauPassePasse);
					break;
				}
				
				case 3:{
					this.listChamp();
					byte champAmodifier=this.saisiePersoByte(sc, "Champ dans lequel on ajoute un couple (-1 pour annuler) >\t");
					
					if(champAmodifier!=-1) {
						String nouveauLogin=this.saisiePerso(sc, "Nouveau login >\t");
						String nouveauPassePasse=this.saisiePerso(sc, "Nouveau mot de passe >\t");
						this.jokernChamp.get(champAmodifier).addLoginCouple(nouveauLogin, nouveauPassePasse);
					}				

					break;
				}
				
				case 4:{
					byte champAmodifier=this.getChamp();
					byte champCoupleAmodifier=this.saisiePersoByte(sc, "Champ du couple à supprimer >\t");
					this.jokernChamp.get(champAmodifier).rmLoginCouple(champCoupleAmodifier);
					break;
				}
				
				case 5:{
					break;
				}
				
				default:{
					System.out.println("Invalide dans le menu.");
					break;
				}
			
			}
		}
		
		
		
	}
	
	private void showAide() {
		String aideLocale=langueLdc.ldcStr_aideLdc[langueLdc.getLocale()];
		
		System.out.println(aideLocale);
	}
	

	/**
	 * Ouverture d'un fichier crypté.
	 * @return <b>0</b>, si l'ouverture s'est bien déroulé.<br>
	 * <b>-1</b>, si il y a un eu un souci à l'ouverture : <i>Mauvais mot de passe, fichier inexistant etc...</i>
	 */
	private int openFichier() {
		
		String nomFichier;
		int errorRetour=0;
		boolean existenceFichier;
		
		System.out.print(langueLdc.ldcStr_saisirPathFichier[langueLdc.getLocale()]);
		nomFichier=this.sc.nextLine();
		
		this.pathFichierEnregistrement=nomFichier;
		System.out.println("Ouverture du fichier : "+this.pathFichierEnregistrement+"\n");
		
		existenceFichier=this.fichierExiste(this.pathFichierEnregistrement); //Tester l'existence du fichier d'enregistrement.
		

		if(existenceFichier==true) {
			System.out.print(langueLdc.ldcStr_saisirMdp_global[langueLdc.getLocale()]);
			this.mdpCryptageAES=this.sc.nextLine();	
			
			//Lève une exception si l'objet est nul.
			try {
				//Récupération de l'objet
				this.jokernChamp=this.lectureListDecrypt(this.pathFichierEnregistrement,this.genCleMDP(this.mdpCryptageAES));
				this.mdpCryptageAES="-1";
				
				//On force une exception, si l'objet est nul.
				if(this.jokernChamp==null) {
					throw new NullPointerException();
				}
								
				System.out.println(langueLdc.ldcStr_fichierOuvert[langueLdc.getLocale()]);
				this.saveFichier(true);//La méthode précédente a détruit le fichier, il faut donc en créer un nouveau.
				this.effacerEcran(sc);
				
			}
			
			catch(NullPointerException E) {
				System.out.println(langueLdc.ldcStr_errFichier[langueLdc.getLocale()]);
				return -1;
			}

			
		}
		
		else {
			System.out.println(langueLdc.ldcStr_fichierInexistant[langueLdc.getLocale()]);
			errorRetour=-1;
		}
		
		return errorRetour;
		
	}
	
	//TODO finir la traduction
	/**
	 * Méthode servant à la création d'un fichier crypté.
	 */
	private void newFichier(String nomFichier) {
		
		if(nomFichier.equals("-NO-")) {
			nomFichier=this.saisiePerso(sc, "Saisir le nom du fichier > ");
			this.pathFichierEnregistrement=nomFichier;
		}
				
		System.out.println("Enregistrement sur : "+this.pathFichierEnregistrement+"\n");
		
		this.mdpCryptageAES=this.saisiePerso(sc, "Mot de passe du cryptage > ");
				
		this.ecrireListeFichier(this.pathFichierEnregistrement,this.genCleMDP(this.mdpCryptageAES),this.jokernChamp);
		System.out.println("Enregistrement sur : "+this.pathFichierEnregistrement+"\n");	
				
	}
	
	/**
	 * Méthode servant à la création d'un fichier crypté.
	 */
	public void newFichier(String pathFichier,String motDePasse,List <dataJokern > listeChamps) {
		//TODO Vérifier la bonne utilité de cette méthode.		
		this.ecrireListeFichier(pathFichier,this.genCleMDP(motDePasse),listeChamps);
				
	}
	
	/**
	 * Méthode servant à la création d'un fichier crypté.
	 */
	public void newFichier(String pathFichier,SecretKey cleSecrete,List <dataJokern > listeChamps) {
		//TODO Vérifier la bonne utilité de cette méthode.		
		this.ecrireListeFichier(pathFichier,cleSecrete,listeChamps);
				
	}

	
	/**
	 * Sauvegarde d'un fichier existant.<br>
	 * Si le fichier n'existe pas, un nouveau est créé automatiquement.
	 * @param forcerEnregistrement Utilisé <b>uniquement</b> après une lecture crypté.
	 */
	private void saveFichier(boolean forcerEnregistrement) {
		
		boolean fileExiste=this.fichierExiste(this.pathFichierEnregistrement);
		
		if(fileExiste==true || forcerEnregistrement==true) {
			this.ecrireListeFichier(this.pathFichierEnregistrement,this.genCleMDP(mdpCryptageAES),this.jokernChamp);
			System.out.println("Enregistrement sur : "+this.pathFichierEnregistrement+" réalisé.\n");
		}
		
		else {
			this.newFichier("-NO-");
		}
		
	}
	
	/**
	 * Sauvegarde d'un fichier existant.<br>
	 * Si le fichier n'existe pas, un nouveau est créé automatiquement.
	 * @param forcerEnregistrement Utilisé <b>uniquement</b> après une lecture crypté.
	 */
	public void saveFichier(String pathFichier,boolean forcerEnregistrement) {
		
		boolean fileExiste=this.fichierExiste(pathFichier);
		
		if(fileExiste==true || forcerEnregistrement==true) {
			this.ecrireListeFichier(pathFichier,this.genCleMDP(this.mdpCryptageAES),this.jokernChamp);
		}
		
		else {
			this.newFichier("-NO-");
		}
		
	}
	
	
	/**
	 * Fermeture du fichier, vide le chemin du fichier et la liste de type <b>jokernChamp</b>, présente en mémoire.
	 */
	private void closeFichier(boolean purgeChamp) {
		
		this.pathFichierEnregistrement="";
		
		if(purgeChamp==true) {
			try {
				this.jokernChamp.removeAll(jokernChamp);
			}
			catch (NullPointerException E) {
				System.out.println("-Champ vide-");
			}
		}
		
	}
	
	
	/**
	 * Méthode permettant l'initialisation de la variable de prompt.
	 */
	private void initialisationPrompt() {
		//Caractère du shell qui indique si nous sommes root ou non.
		String shellCharac=" $";
		
		if(this.nomDuser.equals("root")) {
			shellCharac=" #";
		}
		
		//Déclaration et initilisation de l'objet prompt, qui sera l'invite de commande du SHELL JOKERN.
		this.prompt="-JokernShell-\n"+this.nomDuser+"@"+this.systemOs+"["+this.pathFichierEnregistrement+"]"+">\n"+shellCharac+" ";	
	}
	

	
		
	
}