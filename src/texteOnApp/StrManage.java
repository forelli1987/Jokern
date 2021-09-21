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

/**
 * Boîte à outil permettant la gestion des "locales".
 * @author Anthony Fernandez
 * @version v0.2.0
 */
public class StrManage {
	
	/**
	 * Permet d'indiquer 
	 * @return 0 pour le français et 1 pour les autres langues.
	 */
	public byte getLocale() {
		byte langue=1;
		
		if(System.getProperty("user.language").equals("fr")) {
			langue=0;
		}
		
		else {
			langue=1;
		}
		
		return langue;
	}
	
}
