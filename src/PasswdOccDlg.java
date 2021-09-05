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

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import texteOnApp.StrPassOccDlg;

/**
 * Prompt pour saisir le mot de passe de cryptage ou décryptage.
 * @author Anthony Fernandez
 * @version v0.1.0
 */

public class PasswdOccDlg extends JDialog {
	
	private StrPassOccDlg langueVoirCouple=new StrPassOccDlg();
	private final JPanel contentPanel_global = new JPanel();
	private JTextField txtLogin_global;
	private JTextField textMdp_global;
	private JTextField txtFldLogin_global;
	private JTextField txtFldMdp_global;
	private JButton btnSetSave_global;
	
	private String txtBtnSet_global=langueVoirCouple.passOccDlg_txtBtnSet_global[langueVoirCouple.getLocale()];
	private String txtBtnSave_global=langueVoirCouple.passOccDlg_txtBtnSave_global[langueVoirCouple.getLocale()];;
	
	private String coupleExtrait_global[];
	
	/**
	 * Constructeur de la fenêtre, utilise des paramètres pour être initialisée correctement.
	 * @param champMdp Contient le couple login mot de passe type <b>loginCouple</b>
	 * @param titre Le titre de la fenêtre <b>String</b>
	 */
	
	public PasswdOccDlg(loginCouple champMdp, String titre) {
		setResizable(false);
		setVisible(true);
		
		coupleExtrait_global=champMdp.toString().split(";");
		
		JDialog passwdOccDial=new JDialog();
		passwdOccDial.setModal(true);
		passwdOccDial.setResizable(false);
		passwdOccDial.setBounds(100, 100, 340, 179);
		passwdOccDial.getContentPane().setLayout(new BorderLayout());
		passwdOccDial.setTitle(titre);
		contentPanel_global.setBorder(new EmptyBorder(5, 5, 5, 5));
		passwdOccDial.getContentPane().add(contentPanel_global, BorderLayout.CENTER);
		contentPanel_global.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 20));
		
		JLabel lblLogin = new JLabel("Login");
		contentPanel_global.add(lblLogin);
		
		txtFldLogin_global = new JTextField();
		txtFldLogin_global.setEditable(false);
		contentPanel_global.add(txtFldLogin_global);
		txtFldLogin_global.setColumns(15);
		txtFldLogin_global.setText(coupleExtrait_global[0]);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		contentPanel_global.add(lblMotDePasse);
		
		txtFldMdp_global = new JTextField();
		txtFldMdp_global.setEditable(false);
		contentPanel_global.add(txtFldMdp_global);
		txtFldMdp_global.setColumns(15);
		txtFldMdp_global.setText(coupleExtrait_global[1]);
		
		btnSetSave_global = new JButton(txtBtnSet_global);
		contentPanel_global.add(btnSetSave_global);

		passwdOccDial.setLocationRelativeTo(null);  //Centrage de la fenêtre.
		passwdOccDial.setVisible(true);
	}
}
