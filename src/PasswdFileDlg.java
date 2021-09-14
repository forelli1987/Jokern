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
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import texteOnApp.StrPasswdFileDlg;
import java.awt.Toolkit;

/**
 * Prompt pour saisir le mot de passe de cryptage ou décryptage.
 * @author Anthony Fernandez
 * @version v0.1.1
 */
public class PasswdFileDlg extends JDialog {

	private final JPanel contentPanel_global = new JPanel();
	private StrPasswdFileDlg langueDmdMdp=new StrPasswdFileDlg();
	private JPasswordField passwordField_global;
	private String motDePasse_globale="";
	private String txtBtnValide_global=langueDmdMdp.passDlg_nouveauLogMdp_global[langueDmdMdp.getLocale()];
	private String titreFenetre_global=langueDmdMdp.passDlg_txtLblTitre[langueDmdMdp.getLocale()];

	/**
	 * Constructeur de la classe qui affiche le dialogue du MDP
	 */
	public PasswdFileDlg() {
		JDialog FenDlg = new JDialog();
		FenDlg.setIconImage(Toolkit.getDefaultToolkit().getImage(PasswdFileDlg.class.getResource("/licences/jokern_icon.png")));

		FenDlg.setResizable(false);
		FenDlg.setTitle(titreFenetre_global);
				
		FenDlg.setBounds(100, 100, 273, 119);
		FenDlg.getContentPane().setLayout(new BorderLayout());
		
		FenDlg.getContentPane().add(contentPanel_global, BorderLayout.CENTER);
		FenDlg.setLocationRelativeTo(null);
		FenDlg.setModal(true);
		FlowLayout fl_contentPanel = new FlowLayout(FlowLayout.CENTER, 50, 15);
		contentPanel_global.setLayout(fl_contentPanel);
		
		passwordField_global = new JPasswordField();
		
		//L'appuie sur la touche ENTREE réalise les mêmes actions que la bouton VALIDER.
		passwordField_global.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					motDePasse_globale=new String(passwordField_global.getPassword());
					FenDlg.dispose();
				}
			}
		});
		passwordField_global.setColumns(15);
		contentPanel_global.add(passwordField_global);
		
		JButton btnOk = new JButton(txtBtnValide_global);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				motDePasse_globale=new String(passwordField_global.getPassword());
				FenDlg.dispose();
			}
		});
		contentPanel_global.add(btnOk);
		
		FenDlg.setVisible(true);
	}
	
	public String getMdp() {
		return this.motDePasse_globale;
	}
}
