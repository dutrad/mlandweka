package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

import OpenCV.ImageExtractor;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Preditor extends JFrame {

	private JPanel contentPane;
	private JTextField txtFilePath;
	
	JLabel lblCamisaBart = new JLabel("");
	JLabel lblCalcaoBart = new JLabel("");
	JLabel lblSapatoBart = new JLabel("");
	JLabel lblBocaHomer = new JLabel("");
	JLabel lblCalcaHomer = new JLabel("");
	JLabel lblSapatoHomer = new JLabel("");
	JLabel lblNaiveBart = new JLabel("");
	JLabel lblNaiveHomer = new JLabel("");
	
	private Instances instances;
	
	private void loadWeka() {
		DataSource ds;
		try {
			ds = new DataSource("caracteristicas.arff");
			instances = ds.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void naiveBayes() throws Exception {
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(instances);
		
		Instance newInstance = new DenseInstance(instances.numAttributes());
		newInstance.setDataset(instances);
		
		newInstance.setValue(0, Float.parseFloat(lblCamisaBart.getText()));
		newInstance.setValue(1, Float.parseFloat(lblCalcaoBart.getText()));
		newInstance.setValue(2, Float.parseFloat(lblSapatoBart.getText()));
		newInstance.setValue(3, Float.parseFloat(lblBocaHomer.getText()));
		newInstance.setValue(4, Float.parseFloat(lblCalcaHomer.getText()));
		newInstance.setValue(5, Float.parseFloat(lblSapatoHomer.getText()));
		
		double resultado[] = nb.distributionForInstance(newInstance);
		DecimalFormat df = new DecimalFormat("#,###.0000");
		lblNaiveBart.setText(df.format(resultado[0]));
		lblNaiveHomer.setText(df.format(resultado[1]));
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Preditor frame = new Preditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Preditor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSelecionarImagem = new JButton("Selecionar Imagem");
		btnSelecionarImagem.setBounds(10, 11, 167, 23);
		contentPane.add(btnSelecionarImagem);
		
		txtFilePath = new JTextField();
		txtFilePath.setBounds(205, 12, 454, 20);
		contentPane.add(txtFilePath);
		txtFilePath.setColumns(10);
		
		JLabel labelImage = new JLabel("");
		labelImage.setBounds(20, 55, 226, 326);
		contentPane.add(labelImage);
		
		JLabel lblCaractersticasBart = new JLabel("Caracter\u00EDsticas Bart");
		lblCaractersticasBart.setBounds(296, 87, 176, 28);
		contentPane.add(lblCaractersticasBart);
		
		JLabel lblCaractersticasHomer = new JLabel("Caracter\u00EDsticas Homer");
		lblCaractersticasHomer.setBounds(528, 87, 152, 28);
		contentPane.add(lblCaractersticasHomer);
		
		lblCamisaBart.setBounds(296, 126, 83, 14);
		contentPane.add(lblCamisaBart);
		
		lblCalcaoBart.setBounds(296, 163, 83, 14);
		contentPane.add(lblCalcaoBart);
		
		lblSapatoBart.setBounds(296, 200, 83, 14);
		contentPane.add(lblSapatoBart);
		
		lblBocaHomer.setBounds(528, 126, 83, 14);
		contentPane.add(lblBocaHomer);
		
		lblCalcaHomer.setBounds(528, 163, 83, 14);
		contentPane.add(lblCalcaHomer);
		
		lblSapatoHomer.setBounds(528, 200, 83, 14);
		contentPane.add(lblSapatoHomer);
		
		JButton btnExtrair = new JButton("Extrair");
		btnExtrair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageExtractor extractor = new ImageExtractor();
				
				float features[] = extractor.extract(txtFilePath.getText());
				
				lblCamisaBart.setText(String.valueOf(features[0]));
				lblCalcaoBart.setText(String.valueOf(features[1]));
				lblSapatoBart.setText(String.valueOf(features[2]));
				lblBocaHomer.setText(String.valueOf(features[3]));
				lblCalcaHomer.setText(String.valueOf(features[4]));
				lblSapatoHomer.setText(String.valueOf(features[5]));
			}
		});
		btnExtrair.setBounds(290, 55, 89, 23);
		contentPane.add(btnExtrair);
		
		JButton btnClassificar = new JButton("Classificar");
		btnClassificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadWeka();
				try {
					naiveBayes();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnClassificar.setBounds(383, 55, 103, 23);
		contentPane.add(btnClassificar);
		
		JLabel lblNaiveBayes = new JLabel("Naive Bayes");
		lblNaiveBayes.setBounds(296, 241, 71, 14);
		contentPane.add(lblNaiveBayes);
		
		lblNaiveBart.setBounds(296, 266, 71, 14);
		contentPane.add(lblNaiveBart);
		
		lblNaiveHomer.setBounds(296, 281, 71, 14);
		contentPane.add(lblNaiveHomer);
		
		btnSelecionarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir") + 
						System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "imagens"));
				int retorno = fc.showDialog(btnSelecionarImagem, "Selecione imagem");
				
				if(retorno == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					txtFilePath.setText(file.getPath());
					
					BufferedImage bImage = null;
					try {
						bImage = ImageIO.read(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ImageIcon imageLabel = new ImageIcon(bImage);
					labelImage.setIcon(new ImageIcon(imageLabel.getImage().getScaledInstance(labelImage.getWidth(), 
							labelImage.getHeight(), Image.SCALE_DEFAULT)));
					
				}
			}
		});
	}
}
