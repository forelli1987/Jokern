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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import texteOnApp.StrCreateFieldDlg;

/**
 *  Boîte de dialogue pour la création d'un champ
 * @version v0.1.0
 * @author Anthony Fernandez
 */
public class CreateFieldDlg extends JDialog {
	
	private final StrCreateFieldDlg langueCreateFieldDlg_global=new StrCreateFieldDlg();
	
	private final JPanel contentPanel_global = new JPanel();
	private JDialog fenetre_global=new JDialog();
	private JPanel buttonPane_global;
	private JTextField txtFTitre_global;
	private JTextArea txtAComment_global;
	private String retourChamp_global;
	private String titreFenetre_global;
	private String nouveauLogMdp_global=langueCreateFieldDlg_global.crDlg_nouveauLogMdp_global[langueCreateFieldDlg_global.getLocale()];
	
	/**
	 * Constructeur mais qui ne dévoile pas la fenêtre.
	 * @param titre Permet de définir le titre de la fenêtre (<b>String</b>)
	 */
	
	public CreateFieldDlg(String titre) {
		fenetre_global.setModal(true); //Fenêtre au premier plan qui domine le reste.
		fenetre_global.setResizable(false);
		
		fenetre_global.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		fenetre_global.setTitle(titre);

		fenetre_global.setBounds(100, 100, 270, 317);
		fenetre_global.getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel_global = new FlowLayout();
		fl_contentPanel_global.setVgap(10);
		fl_contentPanel_global.setHgap(20);
		contentPanel_global.setLayout(fl_contentPanel_global);
		contentPanel_global.setBorder(new EmptyBorder(5, 5, 5, 5));
		fenetre_global.getContentPane().add(contentPanel_global, BorderLayout.CENTER);
		{
			JLabel lblTitre = new JLabel(langueCreateFieldDlg_global.crDlg_txtLblTitre[langueCreateFieldDlg_global.getLocale()]);
			contentPanel_global.add(lblTitre);
		}
		{
			txtFTitre_global = new JTextField();
			contentPanel_global.add(txtFTitre_global);
			txtFTitre_global.setColumns(20);
		}
		{
			JLabel lblComment = new JLabel(langueCreateFieldDlg_global.crDlg_txtLblCommentaire[langueCreateFieldDlg_global.getLocale()]);
			contentPanel_global.add(lblComment);
		}
		{
			txtAComment_global = new JTextArea();
			txtAComment_global.setColumns(20);
			txtAComment_global.setRows(10);
			txtAComment_global.setTabSize(2);
			contentPanel_global.add(txtAComment_global);
		}
		{
			buttonPane_global = new JPanel();
			FlowLayout fl_buttonPane_global = new FlowLayout(FlowLayout.RIGHT);
			fl_buttonPane_global.setHgap(20);
			buttonPane_global.setLayout(fl_buttonPane_global);
			fenetre_global.getContentPane().add(buttonPane_global, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(langueCreateFieldDlg_global.crDlg_txtBtnOk[langueCreateFieldDlg_global.getLocale()]);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						getChamp();
						fenetre_global.dispose();
					}
				});
				buttonPane_global.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		fenetre_global.setLocationRelativeTo(null);
		fenetre_global.setVisible(true);
	}
	
	private void getChamp() {
		retourChamp_global = txtFTitre_global.getText()+";"+txtAComment_global.getText()+";"+nouveauLogMdp_global+";"+nouveauLogMdp_global;
	}
	
	public String toString() {
		return retourChamp_global;
	}
	
}
