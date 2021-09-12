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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import texteOnApp.StrCoupleDlg;
import java.awt.Toolkit;

/**
 *  Boîte de dialogue pour la création/modification des champs de login/mot de passe.
 * @version v0.1.0
 * @author Anthony Fernandez
 */
public class CoupleDlg extends JDialog {
	
	private StrCoupleDlg langageCoupleDlg=new StrCoupleDlg(); //Internationalisation
	private JPanel buttonPane_global;
	private JDialog fenetrePrincipale_global;
	
	//Bouton ModifySave
	private JButton btnModifySave_global;
	private String txtBtnModifySave_global[]=langageCoupleDlg.cplDlg_txtBtnModifySave_global[langageCoupleDlg.getLocale()];
	private JList listOcc_global;
	private JTextField txtFLogin_global;
	private JTextField txtFMdp_global;
	private Label lblLogin_global;
	private Label lblMdp_global;
	private DefaultListModel occurenceMdp_global=new DefaultListModel();
	private dataJokern donneesChamp_global;
	private int index_global=-1;
	
	//Gestion bouton Ajouter supprimer.
	private JPanel panelBtn_global;
	private JButton btnAddField_global;
	private JButton btnRmField_global;
	private String txtBtnAddField_global=langageCoupleDlg.cplDlg_txtBtnAddField_global[langageCoupleDlg.getLocale()];
	private String txtBtnRmField_global=langageCoupleDlg.cplDlg_txtBtnRmField_global[langageCoupleDlg.getLocale()];
	private String txtNouveauChamp_global=langageCoupleDlg.cplDlg_txtNouveau_global[langageCoupleDlg.getLocale()];
	
	/**
	 * Create the dialog.
	 */
	public CoupleDlg(dataJokern LC) {
		
		donneesChamp_global=LC; //Initialisation de la variable globale.
		
		fenetrePrincipale_global=new JDialog();
		fenetrePrincipale_global.setIconImage(Toolkit.getDefaultToolkit().getImage(CoupleDlg.class.getResource("/licences/jokern_icon.png")));
		fenetrePrincipale_global.setResizable(false);
		fenetrePrincipale_global.setTitle(donneesChamp_global.getTitle());
		fenetrePrincipale_global.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		fenetrePrincipale_global.setBounds(100, 100, 404, 185);
		fenetrePrincipale_global.getContentPane().setLayout(new BorderLayout());
		listOcc_global = new JList(occurenceMdp_global);
		fenetrePrincipale_global.getContentPane().add(listOcc_global, BorderLayout.WEST);
		listOcc_global.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index_global=listOcc_global.getSelectedIndex();
				majChampTexte();
				btnModifySave_global.setEnabled(true);
				btnRmField_global.setEnabled(true);

			}
		});
		listOcc_global.setPreferredSize(new Dimension(50, 200));
		{
			panelBtn_global = new JPanel();
			FlowLayout fl_panelBtn_global = (FlowLayout) panelBtn_global.getLayout();
			fl_panelBtn_global.setHgap(10);
			fl_panelBtn_global.setAlignment(FlowLayout.RIGHT);
			fenetrePrincipale_global.getContentPane().add(panelBtn_global, BorderLayout.NORTH);
			{
				btnAddField_global = new JButton(txtBtnAddField_global);
				btnAddField_global.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String nouvelIndexStr=String.valueOf(donneesChamp_global.size());
						occurenceMdp_global.addElement(nouvelIndexStr);
						donneesChamp_global.addLoginCouple(txtNouveauChamp_global,txtNouveauChamp_global);
					}
				});
				panelBtn_global.add(btnAddField_global);
			}
			{
				btnRmField_global = new JButton(txtBtnRmField_global);
				btnRmField_global.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(index_global!=-1) {
							occurenceMdp_global.remove(index_global);
							donneesChamp_global.rmLoginCouple(index_global);
							
							//On force la selection d'un champ pour ne plus afficher les anciens mdp.
							index_global=0;
							listOcc_global.setSelectedIndex(index_global);
							majChampTexte();
						}
					}
				});
				panelBtn_global.add(btnRmField_global);
				btnRmField_global.setEnabled(false);
			}
		}
		{
			buttonPane_global = new JPanel();
			FlowLayout fl_buttonPane_global = new FlowLayout(FlowLayout.RIGHT);
			fl_buttonPane_global.setVgap(10);
			fl_buttonPane_global.setHgap(10);
			buttonPane_global.setLayout(fl_buttonPane_global);
			fenetrePrincipale_global.getContentPane().add(buttonPane_global, BorderLayout.CENTER);
			{
				lblLogin_global = new Label(langageCoupleDlg.cplDlg_txtLblLogin_global[langageCoupleDlg.getLocale()]);
				buttonPane_global.add(lblLogin_global);
			}
			{
				txtFMdp_global = new JTextField();
				buttonPane_global.add(txtFMdp_global);
				txtFMdp_global.setEditable(false);
				txtFMdp_global.setColumns(20);
			}
			{
				lblMdp_global = new Label(langageCoupleDlg.cplDlg_txtLblMdp_global[langageCoupleDlg.getLocale()]);
				buttonPane_global.add(lblMdp_global);
			}
			{
				txtFLogin_global = new JTextField();
				buttonPane_global.add(txtFLogin_global);
				txtFLogin_global.setEditable(false);
				txtFLogin_global.setColumns(20);
			}
			{
				btnModifySave_global = new JButton(txtBtnModifySave_global[0]);
				btnModifySave_global.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if(btnModifySave_global.getText().equals(txtBtnModifySave_global[0])) {
							btnModifySave_global.setText(txtBtnModifySave_global[1]);
							activationChampsTextField(true);
							
						}
						
						else if(btnModifySave_global.getText().equals(txtBtnModifySave_global[1])) {
							btnModifySave_global.setText(txtBtnModifySave_global[0]);
							donneesChamp_global.setLoginCouple(index_global, txtFLogin_global.getText(),txtFMdp_global.getText()); //Application des informations.
							activationChampsTextField(false);
						}
					}
				});
				btnModifySave_global.setEnabled(false);
				buttonPane_global.add(btnModifySave_global);
				fenetrePrincipale_global.getRootPane().setDefaultButton(btnModifySave_global);
			}
		}
		{
			{
				faireListeChamps();
			}
		}
		
		fenetrePrincipale_global.setModal(true);
		fenetrePrincipale_global.setLocationRelativeTo(null);//Centrage de la fenêtre.
		fenetrePrincipale_global.setVisible(true);	
		
	}
	
	private void faireListeChamps() {
		for(int i=0;i<donneesChamp_global.size();i++) {
			occurenceMdp_global.add(i, i);
		}
	}
	
	/**
	 * Modifie les champs de type <b>JTextField</b>
	 */
	private void majChampTexte() {
		
		String decoupeChaine[]=donneesChamp_global.getOneLoginTxt(index_global).toString().split(";");
		
		txtFLogin_global.setText(decoupeChaine[0]);
		txtFMdp_global.setText(decoupeChaine[1]);		
		
	}
	
	private void activationChampsTextField(boolean activation) {
		txtFLogin_global.setEditable(activation);
		txtFMdp_global.setEditable(activation);
	}
}
