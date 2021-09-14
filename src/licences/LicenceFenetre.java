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

package licences;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.awt.Desktop;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import texteOnApp.StrLicences;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Window.Type;
import outilsFichiers.operationFichier;
import java.awt.Toolkit;

/**
 * Fenêtre qui affiche l'avis de licence.
 * @author Anthony Fernandez
 * @version v0.1.1
 */
public class LicenceFenetre extends JFrame {

	private JPanel panneauPrincipal_global;
	private JFrame fenetreLicence_global=new JFrame();
	private JScrollPane scrollPane_global;
	private JTextArea texteLicenceArea_global;
	private StrLicences langueLicence_global=new StrLicences();
	private JPanel panelBtn_global;
	private FlowLayout flowLayout_global;
	private JButton btnOk_global;
	private Label lblCopyright_global;
	private Label lblCopyright_global2;
	private Label lblCopyright_global3;
	private operationFichier of_global=new operationFichier();
	
	private Label lblLienGit_global; //Servira de lien hyper texte.
	private JFrame fenetreParente_global=new JFrame();
	private final String cheminFichierLicenceGnuLinux_global="/home/"+System.getProperty("user.name")+"/.config/JokernLicence";
	private final String cheminFichierLicenceWindows_global=System.getenv("AppData")+"\\JokernLicence";
	
	/**
	 * Constructeur vide volontairement.
	 */
	public LicenceFenetre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LicenceFenetre.class.getResource("/licences/jokern_icon.png")));
		
	}
	
	/**
	 * Creation de la fenêtre
	 * @param fenetreAppelante de type <b>JFrame</b>, car la fenêtre affichée, cache l'appelante et la rend visible à la fermeture.
	 */
	public LicenceFenetre(JFrame fenetreAppelante) {
		fenetreParente_global = fenetreAppelante;
		fenetreLicence_global.setType(Type.UTILITY);
		fenetreLicence_global.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fermetureFenetreLicence();
			}
		});
		fenetreLicence_global.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		fenetreLicence_global.setBounds(100, 100, 450, 529);
		fenetreLicence_global.setTitle(langueLicence_global.licUtil_Titre_global[langueLicence_global.getLocale()]);
		fenetreLicence_global.setResizable(false);
		panneauPrincipal_global = new JPanel();
		panneauPrincipal_global.setBorder(new EmptyBorder(5, 5, 5, 5));
		fenetreLicence_global.getContentPane().add(panneauPrincipal_global);
		panneauPrincipal_global.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		creationTexteZone();
		
		panelBtn_global = new JPanel();
		flowLayout_global = (FlowLayout) panelBtn_global.getLayout();
		flowLayout_global.setVgap(20);
		flowLayout_global.setHgap(20);
		flowLayout_global.setAlignment(FlowLayout.TRAILING);
		
		gestionBoutons();
		
		fenetreLicence_global.getContentPane().add(panelBtn_global, BorderLayout.SOUTH);
		fenetreLicence_global.setLocationRelativeTo(null);
		fenetreLicence_global.setVisible(true);
	}
	
	/**
	 * Permet de gérer les textes affichés afin de ne pas surcharger le constructeur.
	 */
	private void creationTexteZone() {
		//Affichage des labels pour les informations de licences, auteur et version
		lblCopyright_global = new Label(langueLicence_global.licUtil_lblCpRgt[0]);
		lblCopyright_global.setFont(new Font("Liberation Mono", Font.PLAIN, 12));
		lblCopyright_global2 = new Label(langueLicence_global.licUtil_lblCpRgt[1]);
		lblCopyright_global2.setFont(new Font("Liberation Mono", Font.PLAIN, 12));
		lblCopyright_global3 = new Label(langueLicence_global.licUtil_lblVersion[langueLicence_global.getLocale()]+langueLicence_global.licUtil_versionNumero);
		lblCopyright_global3.setFont(new Font("Liberation Mono", Font.PLAIN, 12));
		panneauPrincipal_global.add(lblCopyright_global);
		panneauPrincipal_global.add(lblCopyright_global2);
		panneauPrincipal_global.add(lblCopyright_global3);
		
		//Affichage de l'avis de licence dans le JTextArea
		texteLicenceArea_global = new JTextArea();
		texteLicenceArea_global.setFont(new Font("Liberation Mono", Font.PLAIN, 11));
		texteLicenceArea_global.setEditable(false);
		texteLicenceArea_global.setText(langueLicence_global.licUtil_gplv3_global[langueLicence_global.getLocale()]);
		scrollPane_global = new JScrollPane();
		scrollPane_global.setViewportView(texteLicenceArea_global);
		scrollPane_global.setPreferredSize(new Dimension(350, 280));
		panneauPrincipal_global.add(scrollPane_global);

	}
	
	/**
	 * Permet la gestion des boutons pour ne pas surcharger le constructeur.
	 */
	private void gestionBoutons() {
		lblCopyright_global = new Label();
		lblCopyright_global.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 try {
					Desktop.getDesktop().browse(new URI("https://github.com/forelli1987/Jokern"));
				} catch (IOException e1) {
					//e1.printStackTrace();
				} catch (URISyntaxException e1) {
					//e1.printStackTrace();
				}
			}
			@Override
			/**
			 * Méthode servant à modifier le curseur de la souris en doigt (comme un lien)
			 */
			public void mouseEntered(MouseEvent e) {
				fenetreLicence_global.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			/**
			 * Remet le curseur de la souris comme à l'origine.
			 */
			public void mouseExited(MouseEvent e) {
				fenetreLicence_global.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lblCopyright_global.setForeground(Color.BLUE);
		lblCopyright_global.setText(langueLicence_global.licUtil_lblLienGithub[langueLicence_global.getLocale()]);
		
		//Bouton : J'ai compris
		btnOk_global = new JButton(langueLicence_global.licUtil_btnTxtValide_global[langueLicence_global.getLocale()]);
		btnOk_global.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fermetureFenetreLicence();
			}
		});
		panelBtn_global.add(lblCopyright_global);
		panelBtn_global.add(btnOk_global);

	}
	
	private void fermetureFenetreLicence() {
		fenetreParente_global.setVisible(true);
		fenetreLicence_global.setVisible(false);
		fenetreLicence_global.dispose();
	}
	
	/**
	 * @return Un booléen qui indique si la licence a été vue.
	 */
	public boolean licenceLue() {
		boolean retourLicence=false;
		String cheminFichier;
			
		//Détection de l'OS se fait par la longueur de la chaîne, les os de type Gnu Linux s'appelle Linux tout simplement.
		if(System.getProperty("os.name").length()>5){
			cheminFichier=cheminFichierLicenceWindows_global;
		}
		
		else {
			cheminFichier=cheminFichierLicenceGnuLinux_global;
		}
		
		
		if(of_global.fichierExiste(cheminFichier)) {
			retourLicence=true;
		}
		
		else {
			of_global.ecriture1(cheminFichier, 0,1);
		}
		
		return retourLicence;
		
	}
	

}
