package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import algorithm.Cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

	private JFrame frame;
	private JTextField keyField;
	private String plainText;
	private String key;
	private String enryptedText;
	private File filePath;
	private String encryptedTextRead;
	private String decryptedText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 481, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JLabel pathLabel = new JLabel();
		pathLabel.setText("(Select a text file)");
		pathLabel.setBounds(110, 67, 345, 49);
		frame.getContentPane().add(pathLabel);
		
		JLabel lblProductCipher = new JLabel("Product Cipher ");
		lblProductCipher.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProductCipher.setBounds(171, 28, 121, 28);
		frame.getContentPane().add(lblProductCipher);
		
		JButton btnNewButton = new JButton("Choose File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		        fc.setFileFilter(filter);
				int ss = fc.showOpenDialog(null);
				if(ss == JFileChooser.APPROVE_OPTION){
					System.out.println(fc.getSelectedFile().getAbsolutePath());
					pathLabel.setText(fc.getSelectedFile().getAbsolutePath());
					filePath = fc.getSelectedFile();
				}
			}
		});
		btnNewButton.setBounds(110, 117, 111, 28);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Encypt");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (keyField.getText().length()<6){
					JOptionPane.showConfirmDialog(null,"key should have a length of at least 6 characters", 
							"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}else{
					plainText = "";
					String line=null;
					try {
						FileReader fReader = new FileReader(filePath);
						BufferedReader bufferedReader =new BufferedReader(fReader);
		                while((line = bufferedReader.readLine()) != null) {
		                    plainText+=line;
		                }   
		                bufferedReader.close();
					} catch (FileNotFoundException e) {
						JOptionPane.showConfirmDialog(null,"File not found", 
								"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
						System.out.println("File not found");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Failed to read file");
						JOptionPane.showConfirmDialog(null,"Failed to read file", 
								"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					}
					
					Cipher cipher = new Cipher();
					try{
						enryptedText = cipher.encryptText(plainText, keyField.getText());
						try {
							String name = filePath.getName();
							String directory= filePath.getParent();
							String newFile = "out.txt";
							int pos = name.lastIndexOf(".");
							if (pos > 0) {
							    newFile = directory+"\\"+name.substring(0, pos)+"_encrypted.txt";
							}
				            FileWriter fileWriter =new FileWriter(newFile);
				            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				            bufferedWriter.write(enryptedText);
				            bufferedWriter.close();
				            JOptionPane.showMessageDialog(null, "Encrypted file saved successfully");
				            keyField.setText("");
				    		pathLabel.setText("(Select a text file)");
				        }
				        catch(IOException ex) {
				        	JOptionPane.showConfirmDialog(null,"Error writing to file", 
									"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				            System.out.println("Error writing to file");
				        }
						
					} catch (Exception e) {
						JOptionPane.showConfirmDialog(null,"Failed to encrypt file", 
								"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
						System.out.println("Failed to encrypt file");
					}
				}
		}});
		btnNewButton_2.setBounds(100, 211, 111, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Decrypt");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				encryptedTextRead = "";
				String line=null;
				try {
					FileReader fReader = new FileReader(filePath);
					BufferedReader bufferedReader =new BufferedReader(fReader);
	                while((line = bufferedReader.readLine()) != null) {
	                	encryptedTextRead+=line;
	                }   
	                bufferedReader.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showConfirmDialog(null,"File not found", 
							"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE); 
					System.out.println("File not found");
				} catch (IOException e) {
					System.out.println("Failed to read file");
					JOptionPane.showConfirmDialog(null,"Error reading file", 
							"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
				Cipher cipher = new Cipher();
				try{
					decryptedText = cipher.decryptText(encryptedTextRead, keyField.getText());
					try {
						String name = filePath.getName();
						String directory= filePath.getParent();
						String newFile = "out.txt";
						int pos = name.lastIndexOf(".");
						if (pos > 0) {
						    newFile = directory+"\\"+name.substring(0, pos)+"_decrypted.txt";
						}
			            FileWriter fileWriter =new FileWriter(newFile);
			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			            bufferedWriter.write(decryptedText);
			            bufferedWriter.close();
			            JOptionPane.showMessageDialog(null, "Decrypted file saved successfully");
			            keyField.setText("");
			            pathLabel.setText("(Select a text file)");
			        }
			        catch(IOException ex) {
			        	JOptionPane.showConfirmDialog(null,"Error writing to file", 
								"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			            System.out.println("Error writing to file");
			        }
					
				} 
				catch (Exception e) {
					JOptionPane.showConfirmDialog(null,"Failed to decrypt file", 
							"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					System.out.println("Failed to decrypt file");
				}
			}
		});
		btnNewButton_3.setBounds(275, 211, 104, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		
		keyField = new JTextField();
		keyField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ev) {
				char ch = ev.getKeyChar();
				if(ch == ev.VK_SPACE || ch == ev.VK_BACK_SLASH){
					JOptionPane.showConfirmDialog(null,"Key cannot contain white spaces or back slashes(\\)", 
							"Error",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		keyField.setBounds(110, 164, 269, 20);
		frame.getContentPane().add(keyField);
		keyField.setColumns(10);
		
		JLabel lblTextFile = new JLabel("Text File");
		lblTextFile.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTextFile.setBackground(Color.WHITE);
		lblTextFile.setBounds(20, 81, 72, 20);
		frame.getContentPane().add(lblTextFile);
		
		JLabel lblSecretKey = new JLabel("Secret Key");
		lblSecretKey.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSecretKey.setBackground(Color.WHITE);
		lblSecretKey.setBounds(20, 166, 76, 14);
		frame.getContentPane().add(lblSecretKey);
		
		
	}
}
