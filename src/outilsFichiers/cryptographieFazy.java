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

import java.io.File;
import java.io.FileNotFoundException;

//Gestion de fichier

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

//Cryptage

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 *
 * Classe pour faciliter la manipulation des cryptages.<br><br>
 * <i>Exemple d'utilisation : </i><br><br>
 * {@code cryptographieFazy cF=new cryptographieFazy();}<br>
 * {@code cF.cryptAES("/home/nullos/Bureau/cheval.txt", "motdepasse","crypt");}<br><br>
 * <i>OU</i><br><br>
 * {@code cF.cryptAES("/home/nullos/Bureau/cheval.txt", "motdepasse","decrypt");}<br><br>
 * Si ça ne fonctionne pas une erreur de type <b>BadPaddingException</b> est retournée, matérialisée ici par l'erreur F de cryptAES.<br><br>
 * Version avec le constructeur qui stocke le mdp.<br><br>
 * {@code cryptographieFazy cF=new cryptographieFazy("MotDePasse");}<br>
 * {@code cF.cryptAES("/home/nullos/Bureau/cheval.txt","crypt");}<br><br>
 * <i>OU</i><br><br>
 * {@code cF.cryptAES("/home/nullos/Bureau/cheval.txt","decrypt");}<br><br>
 * Si ça ne fonctionne pas une erreur de type <b>BadPaddingException</b> est retournée, matérialisée ici par l'erreur F de cryptAES.<br><br>*  
 * @version v0.1.1
 * @author Anthony Fernandez

 * */
public class cryptographieFazy extends operationFichier{
	
	//Variables d'instance :
	
	private String motDePasse;
	
	public cryptographieFazy() {
		
	}
	
	public cryptographieFazy(String mdp) {
		this.motDePasse=mdp;
	}
	
	
	/**
	 * Permet de crypter/décrypter en AES un fichier en utilisant un mot de passe.
	 * @param cheminFichier : chemin dans lequel se trouve le fichier de type <b>STRING</b>
	 * @param mdpKey : le mot de passe de type <b>SECRET KEY</b>
	 * @param mode : Permet d'indiquer si on crypte ou on décrypte on utilise pour ça : <b>"crypt"</b> et <b>"decrypt"</b>.
	 * @return Une valeur <b>négative</b> indique une erreur.
	 * */
	public int cryptAES(String cheminFichier,SecretKey mdpKey,String mode){
		
		int modIt;
		int errorRetour=0;
		
		switch(mode) {
			case "crypt":{
				modIt=Cipher.ENCRYPT_MODE;
				break;
			}
		
			case "decrypt":{
				modIt=Cipher.DECRYPT_MODE;
				break;
			}
			
			default:{
				modIt=Cipher.DECRYPT_MODE;				
			}
		}
		
		
		//Reconstruction d'un nouveau nom de fichier.
		String cheminFichierSortie=cheminFichier;
						
		try {
						
			//Choix du type de cryptage
			Cipher aesCipher = Cipher.getInstance("AES");
			
			//Initialisation de l'objet aesCipher en mode cryptage ou décryptage, en utilisant la clé de type SecretKey
		    aesCipher.init(modIt, mdpKey);
		    
		    //Récupération du contenu complet du fichier à crypter/décrypter
		    byte contenuFichier[]=this.lectureFichierComplete(cheminFichier);
		    	    
		    //Tableau d'octets après traitement du cryptage/décryptage
		    byte contenuFichierChiffreOuDechiffre[]=aesCipher.doFinal(contenuFichier);
		    
		    //Création d'un fichier avec le contenu, crypté ou décrypté.
		    this.ecritureFichierComplete(cheminFichierSortie, contenuFichierChiffreOuDechiffre);
		    		    
		}
		
		catch (NullPointerException A) {
			//A.getCause();
			//A.printStackTrace();
			errorRetour=-1;
		}
		
		catch(NoSuchAlgorithmException B) {
			//System.out.println("Erreur cryptAES B");
			errorRetour=-1;
		}
		
		catch(NoSuchPaddingException C){
			//System.out.println("Erreur cryptAES C");
			errorRetour=-1;
		}
		
		catch(InvalidKeyException D){
			//System.out.println("Erreur cryptAES D");
			errorRetour=-1;
		}
		
		catch(IllegalBlockSizeException E) {
			//System.out.println("Erreur cryptAES E");
			errorRetour=-1;
		}
		
		catch (BadPaddingException F) {
			//System.out.println("Le mot de passe fourni est FAUX");
			errorRetour=-2;
		}
		
		return errorRetour;

	}
			
	/**
	 * Génère un
	 * @return Retourne un variable de type <b>SecretKey</b> utilisable par d'autre méthode.
	 * @throws NoSuchAlgorithmException
	 */
	private SecretKey genCle() throws NoSuchAlgorithmException {
		int keySize = 256; //128, 192 ou 256
		
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize, SecureRandom.getInstanceStrong());
     
        return keyGen.generateKey();
	}
	
	/**
	 * Permet de créer un objet de type <b>SecretKey</b> utilisé par plusieurs calsse comme CIPHER
	 * 
	 * @param password Le mot de passe de cryptage.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public SecretKey genCleMDP(String password){
		
		MessageDigest sha;
		byte[] key;
		SecretKey secret=null;
		
		try {
			sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(password.getBytes("UTF-8"));
			secret = new SecretKeySpec(key, "AES");
		}
		
		catch(NoSuchAlgorithmException A) {
			System.out.println("Erreur genCleMDP A");
		}
		
		catch(UnsupportedEncodingException B) {
			System.out.println("Erreur genCleMDP B");
		}
		
		finally {
			return secret;
		}
		
	}
	
	
	/**
	 * <b>Setter</b> pour la modification du mot de passe
	 * @param mdp : Modification de la variable du mot de passe.
	 */
	public void setMdp(String mdp) {
		this.motDePasse=mdp;
	}
	
	/**
	 * <b>Getter</b> pour récupérer la variable d'instance mot de passe.
	 * @return Retourrne la variable d'instance de type <b>STRING</b>.
	 */
	
	public String getMdp() {
		return this.motDePasse;
	}
		

}
