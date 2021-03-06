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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javax.swing.plaf.OptionPaneUI;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JDialog;

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
import threadFonction.DeepSupprThread;

import java.awt.Toolkit;


/**
*
* Classe pour la gestion de la GUI<br><br>
* @version v0.2.0
* @author Anthony Fernandez

* */
public class GuiMain{
	private final String pathManuel_global="https://github.com/forelli1987/Jokern/raw/main/manuel.pdf"; //Chemin pour r??cup??rer le manuel.
	private FichierGui fichierSelection_global;
	private cryptographieFazy CF=new cryptographieFazy();
	private operationsJokern OJ=new operationsJokern(); //Poss??de des m??thodes pour g??rer facilement l'ouverture, la sauvegarde et la cr??ation des fichiers crypt??s.
	private List <dataJokern> donneesChamps_global=new ArrayList<dataJokern>();
	
	//Le menu
	private JMenuBar menuBar_global;
	private JMenu mnManageChamps_global;
	private JMenuBar barreMenu_global;
	private JMenuItem mntmSauvegarder_global;
	private JMenuItem mntmFermer_global;
	private JMenuItem mntmAide_global;
	
	private JMenuItem mntmRmChamp_global;
	private JMenuItem mntmAddChamp_global;
	private JMenuItem mntmLicence_global;
	private JMenuItem mntmOuvrir_global;
	private JMenu mnAbout_global;
	private JMenu mnCryptage_global;
	private JMenuItem mntmCryptageFichier_global;
	private JMenuItem mntmDeCryptageFichier_global;
	private JMenuItem mntmDeepSuppression_global;
	
	private JMenuItem mntmSauvegarderSous_global;
	private JMenu fichierMenu_global;
	
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
	private final long tpsMs_global=500;

	
	//*** Langue et texte de l'application ***
	private StrGuiMain langueApp_global=new StrGuiMain();
	
	//Bouton modifier/sauvegarder.
	
	//Champ s??lectionn??
	private int indexSelection_global =-1;
	
	//Titre de la fen??tre de cr??ation de champ
	
	//Texte bo??te de confirmation de suppression
	
	//Bouton pour voir les mots de passes
	private JButton btnViewMdp_global;
	
	//Gestion des fichiers
	private FichierGui FG=new FichierGui();
	
	public GuiMain() {		

		fichierSelection_global=new FichierGui();
		
		frmJokern_global = new JFrame();
		frmJokern_global.setIconImage(Toolkit.getDefaultToolkit().getImage(GuiMain.class.getResource("/licences/jokern_icon.png")));
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
				
				/*Les conditions suivantes permettent de fournir deux fonctions ?? un m??me bouton.
				 * La condition se fait sur le texte du bouton
				*/
				if(btnModifySave_global.getText().equals(langueApp_global.gMain_txtModifier_global[langueApp_global.getLocale()])) {
					
					//Modification du texte du bouton, activation des champs.
					
					btnModifySave_global.setText(langueApp_global.gMain_txtSauvegarder_global[langueApp_global.getLocale()]);
					txtTitre_global.setEditable(true);
					txtCommentaire_global.setEditable(true);
				}
				
				else if(btnModifySave_global.getText().equals(langueApp_global.gMain_txtSauvegarder_global[langueApp_global.getLocale()])) {
					
					//Modification du texte du bouton, d??sactivation des champs.
					
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
		
		//On place la fen??tre au centre
		frmJokern_global.setLocationRelativeTo(null);
		
		this.barreMenu_global=this.gestionMenu();
		frmJokern_global.setJMenuBar(this.barreMenu_global);

		frmJokern_global.setVisible(true);
		
		if(!new LicenceFenetre().licenceLue()) {
			new LicenceFenetre(frmJokern_global);
		}
		
		
		
	}
	
	private void ouvrirFichier() {
	
		this.fichierOuverture_global=fichierSelection_global.openFichier();
		
		//Si un fichier est s??lectionn??
		if(!this.fichierOuverture_global.equals("-1") && !this.fichierOuverture_global.equals("-2")){
			
			//Demander le mot de passe de cryptage
			PasswdFileDlg PassDLG = new PasswdFileDlg();
			if(!PassDLG.getMdp().equals("")) {
				this.motDePasse_global=PassDLG.getMdp();
				cleSecreteMdp_global=CF.genCleMDP(motDePasse_global);
				motDePasse_global="-1";
				
				try {
					this.donneesChamps_global=this.OJ.lectureListDecrypt(this.fichierOuverture_global, cleSecreteMdp_global);
					
					//Si aucune donn??e, alors on l??ve une exception.
					if(this.donneesChamps_global==null) {
						throw new NullPointerException();
					}
					
					this.OJ.ecrireListeFichier(this.fichierOuverture_global, this.cleSecreteMdp_global, this.donneesChamps_global); //R????criture obligatoire apr??s la suppression du fichier.
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
		listModelChamp_global.clear(); //Purge des champs au cas o??.
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
	 * R??aliser la purge des variables globales et remet la GUI ?? son ??tat initial.
	 */
	private void closeFichier() {
		
		//Nettoyage des variables globales.
		this.donneesChamps_global.removeAll(this.donneesChamps_global);
		this.motDePasse_global="-1";
		this.cleSecreteMdp_global=null;
		this.fichierOuverture_global="";
		
		//Nettoyage de la JList
		this.listModelChamp_global.clear();
				
		//D??sactivation des champs textes, ainsi que la remise en place des textes d'origines.
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
	 * Permet de g??rer la barre de menu et d'all??ger le code d??di?? ?? la fen??tre.
	 * @return Objet de type <b>JMenuBar</b>
	 */
	private JMenuBar gestionMenu() {
		menuBar_global = new JMenuBar();
		
		//Fichier
		fichierMenu_global = new JMenu(langueApp_global.gMain_txtMnFichier_global[langueApp_global.getLocale()]);
		menuBar_global.add(fichierMenu_global);
		
		//Fichier -> Ouvrir
		mntmOuvrir_global = new JMenuItem(langueApp_global.gMain_txtMnItmOuvrir_global[langueApp_global.getLocale()]);
		mntmOuvrir_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ouvrirFichier();
			}
		});
		fichierMenu_global.add(mntmOuvrir_global);
				
		//Fichier -> Sauvegarder
		mntmSauvegarder_global = new JMenuItem(langueApp_global.gMain_txtMnItmSauv_global[langueApp_global.getLocale()]);
		mntmSauvegarder_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegardeRapide();
			}
		});
		mntmSauvegarder_global.setEnabled(false);
		fichierMenu_global.add(this.mntmSauvegarder_global);
		
		//Menu sauvegarder sous
		mntmSauvegarderSous_global = new JMenuItem(langueApp_global.gMain_txtMnItmSauvSous_global[langueApp_global.getLocale()]);
		mntmSauvegarderSous_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegarderSous();
			}
		});
		fichierMenu_global.add(mntmSauvegarderSous_global);
		
		mntmFermer_global = new JMenuItem(langueApp_global.gMain_txtMnItmFermer_global[langueApp_global.getLocale()]);
		mntmFermer_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFichier();
			}
		});
		fichierMenu_global.add(mntmFermer_global);
				
		mnManageChamps_global = new JMenu(langueApp_global.gMain_txtMnGestionChamp_global[langueApp_global.getLocale()]);
		menuBar_global.add(mnManageChamps_global);
		
		//Menu ajouter un champ.
		mntmAddChamp_global = new JMenuItem(langueApp_global.gMain_titreFenCreation_global[langueApp_global.getLocale()]);
		mntmAddChamp_global.addActionListener(new ActionListener() {
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
		mnManageChamps_global.add(mntmAddChamp_global);
		
		//Menu suppression d'un champ.
		mntmRmChamp_global = new JMenuItem(langueApp_global.gMain_txtMnItmSupChamp_global[langueApp_global.getLocale()]);
		mntmRmChamp_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String question=langueApp_global.gMain_texteSuppression_global[langueApp_global.getLocale()][1]+"\""+donneesChamps_global.get(indexSelection_global).getTitle()+"\"";
				if(JOptionPane.showConfirmDialog(null, question, langueApp_global.gMain_texteSuppression_global[langueApp_global.getLocale()][0],
			            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null)==0) {
					suppressionChamp(indexSelection_global);
				}
			}
		});
		mnManageChamps_global.add(mntmRmChamp_global);
				
		//A propos
		mnAbout_global = new JMenu(langueApp_global.gMain_txtMnAPropos_global[langueApp_global.getLocale()]);
		menuBar_global.add(mnAbout_global);
				
		//Menu licence
		mntmLicence_global = new JMenuItem(langueApp_global.gMain_txtMnItmLicence_global[langueApp_global.getLocale()]);
		mntmLicence_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmJokern_global.setVisible(false); //Rend invisible la fen??tre GuiMain temporairement. Celle-ci est de nouveau visible ?? la fermeture de l'objet LicenceFenetre.
				new LicenceFenetre(frmJokern_global);
			}
		});
		mnAbout_global.add(mntmLicence_global);
		
		mntmAide_global = new JMenuItem(langueApp_global.gMain_txtMnAide_global[langueApp_global.getLocale()]);
		mntmAide_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telechargerNotice();
			}
		});
		
		fichierMenu_global.addSeparator();
		
		//Fichier -> Cryptage de fichier
		mnCryptage_global=new JMenu(langueApp_global.gMain_txtMnCryptage_global[langueApp_global.getLocale()]);
		
		//Cryptage de fichier -> Crypter un fichier
		mntmCryptageFichier_global=new JMenuItem(langueApp_global.gMain_txtMnItmCryptFichier_global[langueApp_global.getLocale()]);
		
		mntmCryptageFichier_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cryptageFichierNormal('c');
			}
		});
		
		//Cryptage de fichier -> D??crypter un fichier
		mntmDeCryptageFichier_global=new JMenuItem(langueApp_global.gMain_txtMnItmDecryptFichier_global[langueApp_global.getLocale()]);
		mntmDeCryptageFichier_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cryptageFichierNormal('d');
			}
		});	
		
		mnCryptage_global.add(mntmCryptageFichier_global);
		mnCryptage_global.add(mntmDeCryptageFichier_global);
		fichierMenu_global.add(mnCryptage_global);
		
		//Fichier -> Suppression de bas niveau
		mntmDeepSuppression_global=new JMenuItem(langueApp_global.gMain_txtMnItmDeepSupr_global[langueApp_global.getLocale()]);
		mntmDeepSuppression_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deepSuppr();
			}
		});
		fichierMenu_global.add(mntmDeepSuppression_global);
		fichierMenu_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});	

		
		mnAbout_global.add(mntmAide_global);
		
		return menuBar_global;
		
	}
	
	/**
	 * Modification d'un champ <i>commentaire</i> et <i>titre</i> uniquement.
	 * @param titre : Nouveau titre <b>String</b>
	 * @param commentaire : Nouveau commentaire <b>String</b>
	 * @param index : Occurence qui va ??tre modifi??e <b>byte</b>
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
			
			//Si l'on a pas cliqu?? sur ANNULER.
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
		faireListeChamps();//Rafra??chir la liste.
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
	
	/**
	 * T??l??chargement sur github, du manuel.
	 */
	private void telechargerNotice() {
		String sauvegardeNotice="";
		sauvegardeNotice=fichierSelection_global.saveFichierDirectory()+"/JokernManual.pdf";
		
		if(!sauvegardeNotice.equals("-2")) {
			URL url;
			try {
				url = new URL(pathManuel_global);
				InputStream in = url.openStream();
	            Files.copy(in, Paths.get(sauvegardeNotice));
	            JOptionPane.showMessageDialog(null, langueApp_global.gMain_confirmTel[langueApp_global.getLocale()]+"\n"+sauvegardeNotice);
			} 
			catch (MalformedURLException e) {}
			catch (IOException e) {}

		}		
	}
	
	/**
	 * M??thode pour la suppression bas niveau d'un fichier.<br>
	 * <ol>
	 * <li>Ecriture a al??atoire dans tout le fichier.</li>
	 * <li>Suppression naturel apr??s le <i>souillage</i> du fichier.</li>
	 * </ol>
	 *
	 */
	private void deepSuppr(){
		String fichierAsupprimer=fichierSelection_global.openFichier();
		
		if(!fichierAsupprimer.equals("-2")) {
			DeepSupprThread DG = new DeepSupprThread(fichierAsupprimer);
			DG.start();
		}		
		
	}
	
	//TODO ajouter des JOptionPane pour indiquer si le cryptage a fonctionn?? ou non.
	private void cryptageFichierNormal(char choix) {
		String fichier=FG.openFichier();
		String mdp;
		
		//'c' pour cryptage
		if(choix=='c') {
			
			//Si on annule l'import du fichier.
			if(!fichier.equals("-2")) {
				mdp=new PasswdFileDlg().getMdp();
				CF.cryptAES(fichier, CF.genCleMDP(mdp), "crypt");
				
				CF.injectionAscii(fichier,langueApp_global.gMain_cryptSignature, CF.tailleFichier(fichier));
				
				JOptionPane.showMessageDialog(null, langueApp_global.gMain_cryptDone[langueApp_global.getLocale()]);
			}
		}
		
		//'d' pour d??cryptage
		else if(choix=='d') {
			
			//R??cup??ration de la signature en fin de fichier.
			String sigLocale = CF.lectAscii(fichier, (CF.tailleFichier(fichier)-4L), 4);
			
			//Si on annule l'import du fichier et que la signature de cryptage est pr??sente.
			if(!fichier.equals("-2")) {
				
				if(sigLocale.equals(langueApp_global.gMain_cryptSignature)){
					mdp=new PasswdFileDlg().getMdp();
					
					//Suppression des 4 derniers octets.
					CF.ecritureFichierLessSignature(fichier, CF.lectureFichierComplete(fichier));
					
					//D??cryptage
					CF.cryptAES(fichier, CF.genCleMDP(mdp), "decrypt");
					
					JOptionPane.showMessageDialog(null, langueApp_global.gMain_decryptDone[langueApp_global.getLocale()]);
				}
				
				else {
					JOptionPane.showMessageDialog(null, langueApp_global.gMain_decryptErrorMissingSig[langueApp_global.getLocale()]);
				}
				
			}
		}
		
		mdp="0";
		
	}

}
