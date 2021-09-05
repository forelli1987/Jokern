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

import java.io.Serializable;
import java.util.*;

/**
 *
 * Classe exclusivement utilisée pour les données d'un champ.<br><br>
 * @version v0.1.0
 * @author Anthony Fernandez

 * */
public class dataJokern implements Serializable{
	
	private String title;
	private String comment;
	private List<loginCouple> LC=new ArrayList<loginCouple>();

	/**
	 * Premier constructeur
	 * <b>Exemple</b> : <br><br>
	 * {@code dataJokern champ = new dataJokern()}
	 */
	
	public dataJokern() {
		
	}
	
	/**
	 * Deuxième constructeur
	 * <b>Exemple</b> : <br><br>
	 * {@code dataJokern champ = new dataJokern("Pour acheter en ligne","login";"mdp")}
	 * 
	 * @param titre : Titre du champ.
	 * @param commentaire : Commentaire qui peuvent être utile.
	 * @param log : Couple login et mot de passe.
	 * @param mdp : Le mdp
	 */
	
	
	public dataJokern(String titre,String commentaire,String log,String mdp) {
		this.title=titre;
		this.comment=commentaire;
		this.LC.add(new loginCouple(log,mdp));
	}
	
	/**
	 * Troisième constructeur demandant un objet de type loginCouple à la place du <b>MDP</b> et du <b>LOGIN</b><br><br>
	 * <b>Exemple</b> : <br><br>
	 * {@code dataJokern champ = new dataJokern("Pour acheter en ligne",ObjetTypeLoginCouple)}
	 * @param titre : Titre du champ
	 * @param commentaire : Description du champ pour donner des informations à l'utilisateur.
	 * @param LC_local : L'objet de type <b>loginCouple</b> qui contient le <i>login</i> et le <i>mot de passe</i>
	 */
	
	public dataJokern(String titre,String commentaire,loginCouple LC_local) {
		this.title=titre;
		this.comment=commentaire;
		this.LC.add(LC_local);
	}
	
	
	//*** SETTER ***
	
	/**
	 * Permet d'ajouter un couple login et mot de passe.
	 * @param log : Le nouveau <b>LOGIN</b>
	 * @param mdp : Le nouveau <b>MDP</b>
	 */
	public void addLoginCouple(String log, String mdp) {
		this.LC.add(new loginCouple(log,mdp));		
	}
	
	/**
	 * 
	 * Permet de modifier un couple <i>login</i> et <i>mot de passe</i>
	 * 
	 * @param Index : Le numéro de l'occurence à modifier
	 * @param nouveauLog : Le <i>login</i> qui remplacera l'ancien
	 * @param nouveauMdp : Le <i>mot de passe</i> qui remplacera l'ancien.
	 */
	
	
	public void setLoginCouple(int Index,String nouveauLog, String nouveauMdp) {
		this.LC.set(Index,new loginCouple(nouveauLog,nouveauMdp));
	}
	
	/**
	 * Modifier le commentaire d'un champ.
	 * @param nouveauCommentaire Nouveau commentaire qui remplacera l'ancien.
	 */
	
	public void setComment(String nouveauCommentaire) {
		this.comment=nouveauCommentaire;
	}
	
	/**
	 * Modifier le titre d'un champ.
	 * @param nouveauTitre Titre de remplacement du champ.
	 */
	
	public void setTitle(String nouveauTitre) {
		this.title=nouveauTitre;
	}
	
	/**
	 * Supprimer un couple : <b>LOGIN</b> et <b>MDP</b>
	 * @param Index : index de l'occurence à supprimer.
	 */
	
	public void rmLoginCouple(int Index) {
		this.LC.remove(Index);
	}
	
	/**
	 * Supprimer tous les couples <i>login</i> et <i>mot de passe</i>
	 */
	
	public void purgeLoginCouple() {
		this.LC.removeAll(LC);
	}
	
	//*** GETTER ***
	
	/**
	 * Permet de récupérer tous les couples de type <b>loginCouple</b>, présent dans le champ.
	 * @return Une liste de type <b>loginCouple</b>
	 */
	public loginCouple getListLogin(){
		return (loginCouple) this.LC;
	}
	
	/**
	 * Permet de récupérer un couple de type <b>loginCouple</b>, présent dans le champ.
	 * @param Index L'occurence à récupérer.
	 * @return Une liste de type <b>loginCouple</b>
	 */
	
	public loginCouple getOneLogin(int Index) {
		return (loginCouple) this.LC.get(Index);
	}
	
	public String getOneLoginTxt(int Index) {
		return this.LC.get(Index).toString();
	}
	
	/**
	 * 
	 * @return Donne le titre du champ.
	 */
	
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 
	 * @return Donne le commentaire du champ.
	 */
	public String getComment() {
		return this.comment;
	}
	
	public String toString() {
		loginCouple loopLC;
		String chaineRetour=this.title+";"+this.comment+";"+this.LC.size();
		
		return chaineRetour;
	}
	
	/**
	 * 
	 * @return Nombre de couple login et mot de passe.
	 */
	public int size() {
		return this.LC.size();
	}
	
			
}