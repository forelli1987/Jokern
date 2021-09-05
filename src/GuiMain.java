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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import outilsFichiers.cryptographieFazy;
import outilsFichiers.FichierGui;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.Desktop;
import java.awt.Dimension;

import texteOnApp.StrGuiMain;
import javax.swing.ScrollPaneConstants;
import licences.LicenceFenetre;

/**
*
* Classe pour la gestion de la GUI<br><br>
* @version v0.1.0
* @author Anthony Fernandez

* */
public class GuiMain{
	private FichierGui fichierSelection_global;
	private cryptographieFazy CF=new cryptographieFazy();
	private operationsJokern OJ=new operationsJokern(); //Possède des méthodes pour gérer facilement l'ouverture, la sauvegarde et la création des fichiers cryptés.
	private List <dataJokern> donneesChamps_global=new ArrayList<dataJokern>();
	
	//Le menu
	private JMenuBar menuBar_global;
	private JMenu mnManageChamps_global;
	private JMenuBar barreMenu_global;
	private JMenuItem mntmSauvegarder_global;
	private JMenuItem mntmFermer_global;
	
	//champs liste
	private JList<dataJokern> listChamp_global;
	private DefaultListModel listModelChamp_global=new DefaultListModel(); //Occurence qui contient le titre.
	private DefaultListModel listModelOccurence_global=new DefaultListModel(); //Occurence qui contient login et mdp.
	
	private JTextField txtTitre_global;
	private JEditorPane txtCommentaire_global;
	private JButton btnModifySave_global;
	private String motDePasse_global="-1";
	private SecretKey cleSecreteMdp_global=null;
	private String fichierOuverture_global="-1";
	
	private JScrollPane scrollPane_global;
	private JFrame frmJokern_global;

	
	//*** Langue et texte de l'application ***
	private StrGuiMain langueApp_global=new StrGuiMain();
	
	//Bouton modifier/sauvegarder.
	
	//Champ sélectionné
	private int indexSelection_global =-1;
	
	//Titre de la fenêtre de création de champ
	
	//Texte boîte de confirmation de suppression
	
	//Bouton pour voir les mots de passes
	private JButton btnViewMdp_global;
	
	//Gestion des fichiers
	private FichierGui FG=new FichierGui();
	
	public GuiMain() {		

		fichierSelection_global=new FichierGui();
		
		frmJokern_global = new JFrame();
		frmJokern_global.setResizable(false);
		frmJokern_global.setTitle(langueApp_global.gMain_titreFenetreMain_global);
		frmJokern_global.setBounds(100, 100, 357, 414);
		frmJokern_global.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJokern_global.getContentPane().setLayout(null);
		
		JPanel panel_list_champ = new JPanel();
		
		panel_list_champ.setBounds(12, 12, 126, 332);
		frmJokern_global.getContentPane().add(panel_list_champ);
		
		this.listChamp_global = new JList(this.listModelChamp_global);
		listChamp_global.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					indexSelection_global=listChamp_global.getSelectedIndex();
					faireListeChampsMdp(indexSelection_global);
					btnModifySave_global.setEnabled(true);
					btnViewMdp_global.setEnabled(true);
				}
				
				catch(IndexOutOfBoundsException I) {
					
				}
				
			}
		});
		this.listChamp_global.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_global=new JScrollPane(listChamp_global);
		scrollPane_global.setPreferredSize(new Dimension(110, 320));
		scrollPane_global.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_list_champ.add(scrollPane_global);
				
		JPanel panel_list_mdp = new JPanel();
		panel_list_mdp.setBounds(150, 12, 192, 332);
		frmJokern_global.getContentPane().add(panel_list_mdp);
		panel_list_mdp.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 20));
		
		//*** TITRE ***
		txtTitre_global = new JTextField();
		txtTitre_global.setText(langueApp_global.gMain_titreChampTxt_global[langueApp_global.getLocale()]);
		txtTitre_global.setEditable(false);
		panel_list_mdp.add(txtTitre_global);
		txtTitre_global.setColumns(10);
		
		//*** COMMENTAIRE ***
		txtCommentaire_global = new JEditorPane();
		txtCommentaire_global.setPreferredSize(new Dimension(130, 170));
		txtCommentaire_global.setText(langueApp_global.gMain_titreCommentaireTxt_global[langueApp_global.getLocale()]);
		txtCommentaire_global.setEditable(false);
		panel_list_mdp.add(txtCommentaire_global);
		
		//*** BOUTON MODIFIER ***
		btnModifySave_global = new JButton(langueApp_global.gMain_txtModifier_global[langueApp_global.getLocale()]);
		btnModifySave_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*Les conditions suivantes permettent de fournir deux fonctions à un même bouton.
				 * La condition se fait sur le texte du bouton
				*/
				if(btnModifySave_global.getText().equals(langueApp_global.gMain_txtModifier_global[langueApp_global.getLocale()])) {
					
					//Modification du texte du bouton, activation des champs.
					
					btnModifySave_global.setText(langueApp_global.gMain_txtSauvegarder_global[langueApp_global.getLocale()]);
					txtTitre_global.setEditable(true);
					txtCommentaire_global.setEditable(true);
				}
				
				else if(btnModifySave_global.getText().equals(langueApp_global.gMain_txtSauvegarder_global[langueApp_global.getLocale()])) {
					
					//Modification du texte du bouton, désactivation des champs.
					
					btnModifySave_global.setText(langueApp_global.gMain_txtModifier_global[langueApp_global.getLocale()]);
					txtTitre_global.setEditable(false);
					txtCommentaire_global.setEditable(false);
					modifyChamp(txtTitre_global.getText(),txtCommentaire_global.getText(),(byte)indexSelection_global);
				}
			}
		});
		btnModifySave_global.setEnabled(false);
		panel_list_mdp.add(btnModifySave_global);
		
		btnViewMdp_global = new JButton(langueApp_global.gMain_txtBtnViewPass_global[langueApp_global.getLocale()]);
		btnViewMdp_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(indexSelection_global!=-1) {
					new CoupleDlg(donneesChamps_global.get(indexSelection_global));
				}
			}
		});
		btnViewMdp_global.setEnabled(false);
		panel_list_mdp.add(btnViewMdp_global);
		
		//On place la fenêtre au centre
		frmJokern_global.setLocationRelativeTo(null);
		
		this.barreMenu_global=this.gestionMenu();
		frmJokern_global.setJMenuBar(this.barreMenu_global);

		frmJokern_global.setVisible(true);
	}
	
	private void ouvrirFichier() {
	
		this.fichierOuverture_global=fichierSelection_global.openFichier();
		
		//Si un fichier est sélectionné
		if(!this.fichierOuverture_global.equals("-1") && !this.fichierOuverture_global.equals("-2")){
			
			//Demander le mot de passe de cryptage
			PasswdFileDlg PassDLG = new PasswdFileDlg();
			if(!PassDLG.getMdp().equals("")) {
				this.motDePasse_global=PassDLG.getMdp();
				cleSecreteMdp_global=CF.genCleMDP(motDePasse_global);
				motDePasse_global="-1";
				
				try {
					this.donneesChamps_global=this.OJ.lectureListDecrypt(this.fichierOuverture_global, cleSecreteMdp_global);
					
					//Si aucune donnée, alors on lève une exception.
					if(this.donneesChamps_global==null) {
						throw new NullPointerException();
					}
					
					this.OJ.ecrireListeFichier(this.fichierOuverture_global, this.cleSecreteMdp_global, this.donneesChamps_global); //Réécriture obligatoire après la suppression du fichier.
					this.faireListeChamps();
					
					activerMenu(true);
					
				}
				
				
				catch(NullPointerException E) {
					JOptionPane.showMessageDialog(null, langueApp_global.gMain_errOuverture_global[langueApp_global.getLocale()]);
					
				}
			}
			
		}
	}
	
	private void faireListeChamps() {
		listModelChamp_global.clear(); //Purge des champs au cas où.
		for (byte i=0;i<this.donneesChamps_global.size();i++) {
			this.listModelChamp_global.addElement(this.donneesChamps_global.get(i).getTitle());
		}
	}
	
	private void faireListeChampsMdp(int index) {
		String titreLocal;
		String commentaireLocal;
		
		titreLocal=this.donneesChamps_global.get(index).getTitle();
		commentaireLocal=this.donneesChamps_global.get(index).getComment();
		
		//Maj des champs textes sur le panneau de droite.
		this.txtTitre_global.setText(titreLocal);
		this.txtCommentaire_global.setText(commentaireLocal);
		
	}
	
	/**
	 * Fermeture d'un fichier.
	 * Réaliser la purge des variables globales et remet la GUI à son état initial.
	 */
	private void closeFichier() {
		
		//Nettoyage des variables globales.
		this.donneesChamps_global.removeAll(this.donneesChamps_global);
		this.motDePasse_global="-1";
		this.cleSecreteMdp_global=null;
		this.fichierOuverture_global="";
		
		//Nettoyage de la JList
		this.listModelChamp_global.clear();
				
		//Désactivation des champs textes, ainsi que la remise en place des textes d'origines.
		this.txtTitre_global.setEditable(false);
		this.txtTitre_global.setText(langueApp_global.gMain_titreChampTxt_global[langueApp_global.getLocale()]);
		this.txtCommentaire_global.setEditable(false);
		this.txtCommentaire_global.setText(langueApp_global.gMain_titreCommentaireTxt_global[langueApp_global.getLocale()]);
		
		this.mntmSauvegarder_global.setEnabled(false);
		this.indexSelection_global=-1;
		
		this.frmJokern_global.setTitle(langueApp_global.gMain_titreFenetreMain_global);
		
		activerMenu(false);

		
	}
	
	/**
	 * Permet de gérer la barre de menu et d'alléger le code dédié à la fenêtre.
	 * @return Objet de type <b>JMenuBar</b>
	 */
	private JMenuBar gestionMenu() {
		menuBar_global = new JMenuBar();
		
		//Fichier
		JMenu fichierMenu = new JMenu(langueApp_global.gMain_txtMnFichier_global[langueApp_global.getLocale()]);
		menuBar_global.add(fichierMenu);
		
		//Fichier -> Ouvrir
		JMenuItem mntmOuvrir = new JMenuItem(langueApp_global.gMain_txtMnItmOuvrir_global[langueApp_global.getLocale()]);
		mntmOuvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ouvrirFichier();
			}
		});
		fichierMenu.add(mntmOuvrir);
		
		//Fichier -> Sauvegarder
		this.mntmSauvegarder_global = new JMenuItem(langueApp_global.gMain_txtMnItmSauv_global[langueApp_global.getLocale()]);
		mntmSauvegarder_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegardeRapide();
			}
		});
		this.mntmSauvegarder_global.setEnabled(false);
		fichierMenu.add(this.mntmSauvegarder_global);
		
		//Menu sauvegarder sous
		JMenuItem mntmSauvegarderSous = new JMenuItem(langueApp_global.gMain_txtMnItmSauvSous_global[langueApp_global.getLocale()]);
		mntmSauvegarderSous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegarderSous();
			}
		});
		fichierMenu.add(mntmSauvegarderSous);
		
		mntmFermer_global = new JMenuItem(langueApp_global.gMain_txtMnItmFermer_global[langueApp_global.getLocale()]);
		mntmFermer_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFichier();
			}
		});
		fichierMenu.add(mntmFermer_global);
				
		mnManageChamps_global = new JMenu(langueApp_global.gMain_txtMnGestionChamp_global[langueApp_global.getLocale()]);
		menuBar_global.add(mnManageChamps_global);
		
		//Menu ajouter un champ.
		JMenuItem mntmAddChamp = new JMenuItem(langueApp_global.gMain_titreFenCreation_global[langueApp_global.getLocale()]);
		mntmAddChamp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String recupCreationChamp;
				String recupCreationChampDecoupe[];
				
				try {
					recupCreationChamp=new CreateFieldDlg(langueApp_global.gMain_txtMnItmAjtChamp_global[langueApp_global.getLocale()]).toString();
					recupCreationChampDecoupe=recupCreationChamp.split(";");
					ajouterChamp(recupCreationChampDecoupe[0],recupCreationChampDecoupe[1],recupCreationChampDecoupe[2],recupCreationChampDecoupe[3]);
				}
				
				catch(NullPointerException E) {
					
				}

			}
		});
		mnManageChamps_global.add(mntmAddChamp);
		
		//Menu suppression d'un champ.
		JMenuItem mntmRmChamp = new JMenuItem(langueApp_global.gMain_txtMnItmSupChamp_global[langueApp_global.getLocale()]);
		mntmRmChamp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String question=langueApp_global.gMain_texteSuppression_global[langueApp_global.getLocale()][1]+"\""+donneesChamps_global.get(indexSelection_global).getTitle()+"\"";
				if(JOptionPane.showConfirmDialog(null, question, langueApp_global.gMain_texteSuppression_global[langueApp_global.getLocale()][0],
			            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null)==0) {
					suppressionChamp(indexSelection_global);
				}
			}
		});
		mnManageChamps_global.add(mntmRmChamp);
		
		//Aide
		JMenu mnAide = new JMenu(langueApp_global.gMain_txtMnAide_global[langueApp_global.getLocale()]);
		mnAide.setEnabled(false);
		menuBar_global.add(mnAide);
		
		//A propos
		JMenu mnAbout = new JMenu(langueApp_global.gMain_txtMnAPropos_global[langueApp_global.getLocale()]);
		menuBar_global.add(mnAbout);
				
		//Menu licence
		JMenuItem mntmLicence = new JMenuItem(langueApp_global.gMain_txtMnItmLicence_global[langueApp_global.getLocale()]);
		mntmLicence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmJokern_global.setVisible(false); //Rend invisible la fenêtre GuiMain temporairement. Celle-ci est de nouveau visible à la fermeture de l'objet LicenceFenetre.
				new LicenceFenetre(frmJokern_global);
			}
		});
		mnAbout.add(mntmLicence);
		
		return menuBar_global;
		
	}
	
	/**
	 * Modification d'un champ <i>commentaire</i> et <i>titre</i> uniquement.
	 * @param titre : Nouveau titre <b>String</b>
	 * @param commentaire : Nouveau commentaire <b>String</b>
	 * @param index : Occurence qui va être modifiée <b>byte</b>
	 */
	private void modifyChamp(String titre, String commentaire,byte index) {
		this.donneesChamps_global.get(index).setTitle(titre);
		this.donneesChamps_global.get(index).setComment(commentaire);
		this.listModelChamp_global.set(index, titre);//MAJ de la liste
		
		//this.sauvegardeRapide();
	}
	
	private void sauvegardeRapide() {
		
		//Condition d'enregistrement.
		if(!this.cleSecreteMdp_global.equals(null) && !this.fichierOuverture_global.equals("") && this.donneesChamps_global.size()>0){
			this.OJ.ecrireListeFichier(this.fichierOuverture_global, this.cleSecreteMdp_global, this.donneesChamps_global);
		}
		
	}
	
	private void sauvegarderSous() {
				
		if(donneesChamps_global.size()>0) {
			fichierOuverture_global=FG.saveFichier();
			
			//Si l'on a pas cliqué sur ANNULER.
			if(!fichierOuverture_global.equals("-2")) {
				
				motDePasse_global=new PasswdFileDlg().getMdp();
				cleSecreteMdp_global=OJ.genCleMDP(motDePasse_global);
				motDePasse_global="-1";
				frmJokern_global.setTitle(fichierOuverture_global);
				activerMenu(true);
				
				OJ.newFichier(fichierOuverture_global, cleSecreteMdp_global, donneesChamps_global);
			}
			

		}
		
		else {
			JOptionPane.showMessageDialog(null, langueApp_global.gMain_errPasDeDonnees[langueApp_global.getLocale()],null,JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void ajouterChamp(String titre,String commentaire,String login,String motDePasse) {
		donneesChamps_global.add(new dataJokern(titre,commentaire,login,motDePasse));
		faireListeChamps();//Rafraîchir la liste.
	}
	
	private void suppressionChamp(int indexSuppression) {
		donneesChamps_global.remove(indexSuppression);
		listModelChamp_global.remove(indexSuppression);
	}
	
	private void activerMenu(boolean menuActivation) {
		
		btnViewMdp_global.setEnabled(menuActivation);
		mntmSauvegarder_global.setEnabled(menuActivation);
		btnModifySave_global.setEnabled(menuActivation);
		frmJokern_global.setTitle(fichierOuverture_global);

	}
}
