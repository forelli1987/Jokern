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

/**
 * Type de donnée qui permet de stocker un <b>login</b> et un <b>mot de passe</b>
 * @author Anthony Fernandez
 * @version v0.1.0
 *
 */

public class loginCouple implements Serializable{
	
	private String login;
	private String motDePasse;

	
	/**
	 * Constructeur principal
	 * @param log Le login de type <b>String</b>
	 * @param mdp Le mot de passe de type <b>String</b>
	 */
	public loginCouple(String log, String mdp) {
		this.login=log;
		this.motDePasse=mdp;
	}
	
	
	public String toString(){
		return this.login+";"+this.motDePasse;
	}
}