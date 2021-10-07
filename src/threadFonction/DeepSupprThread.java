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

package threadFonction;

import javax.swing.JOptionPane;

import outilsFichiers.operationFichier;
import texteOnApp.StrDeepSuppr;

/**
 * Suppression profonde
 * @version v0.2.0
 * @author Anthony Fernandez
 */
public class DeepSupprThread extends Thread{
	private operationFichier OF=new operationFichier();	
	private String fichierAsupprimer_global;
	private StrDeepSuppr langueDspr=new StrDeepSuppr();
	
	public DeepSupprThread(String fichierSuppr) {
		fichierAsupprimer_global=fichierSuppr;
	}
	
	public void run() {
		OF.suppressionProfonde(fichierAsupprimer_global);
		JOptionPane.showMessageDialog(null,langueDspr.DpSpr_titreDialog[langueDspr.getLocale()] ,langueDspr.DpSpr_message[langueDspr.getLocale()], JOptionPane.INFORMATION_MESSAGE); 	
	}
}
