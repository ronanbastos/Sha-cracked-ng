package Sha.cracked.exe;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class Shacracked extends JFrame{
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	//CRIAR UM MENU criar senha  verfica senha, CRIA UM ARQUIVO TXT COM CONTEUDO DO SHA-256 que pin,gerar um txt com senha;
		 /* Componentes devem estar no contexto da instância,
	    para que possam ser acessados em todos os métodos 
	    não-estáticos da classe
	*/
	private static JTextArea texto = new JTextArea();
	JPanel painel;
	public static String senhawordlist;
	JLabel lblTitulo;
	
	 public Shacracked(){
	   //Define o título da janela
	   super("Sha cracked-ng");

		Adiciona();
		setContentPane(painel);
		setLocation(150, 180);
		pack();
	 } 

	 public void Adiciona() {
		 painel = new JPanel();
		 painel.add(texto);
		 texto.setBounds(250,250, 550, 150);
		 painel.setBackground(Color.black);
		texto.setBackground(Color.green);
		texto.setAutoscrolls(true);
	 }
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		Shacracked  janela = new Shacracked();
	       janela.setSize(950,250);
	       janela.setVisible(true);
	       janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String  local = JOptionPane.showInputDialog("Coloque um local para salvar a senha:");	
		String  SHA = JOptionPane.showInputDialog("Coloque o sha da senha:");	
		String senha = JOptionPane.showInputDialog("Coloque senha SHA-"+SHA+":");
		

	
	System.out.print(senha + "\n");
	
	

	
	String arqsenha = JOptionPane.showInputDialog("Digite o local da csv:");
	Reader reader = Files.newBufferedReader(Paths.get(arqsenha));
    CSVReader csvReader = new CSVReaderBuilder(reader)
                            .withSkipLines(0)//para o caso do CSV ter cabeçalho.
                            .build();

    List<String[]> linhas = csvReader.readAll();
    for (String[] linha : linhas)
    {
    	for (String coluna : linha)
    		{
    	String senhawordlist = coluna;
 	
	MessageDigest algorithm2 = MessageDigest.getInstance("SHA-"+SHA);
	byte messageDigest2[] = algorithm2.digest(coluna.getBytes("UTF-8"));
		
	StringBuilder hexString2 = new StringBuilder();
	for (byte a : messageDigest2) {
		
	hexString2.append(String.format("%02X", 0xFF & a));
	}
		coluna = hexString2.toString();
		coluna = coluna.toLowerCase();
		if(senha.equals(coluna)){
			
			JOptionPane.showMessageDialog(null,"[+]Senha SHA-"+SHA+"  :"+ senha +"  =  "+ coluna+"("+ " senha do wordlist: "+ senhawordlist+")");
		
			try {
				//conteudo
				String content = "[+]Senha SHA-"+SHA+" :"+ senha +" = "+ coluna+" "	+ " /"+ " senha do wordlist :"+senhawordlist;
				
				//Criar arquivo
				File file = new File(local);
				
				//Se o arquivo nao existir, ele gera
				if (!file.exists()) {
					file.createNewFile();
					
				}
				
		
			//Prepara para escrever no arquivo
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			//Escrever e fecha  arquivo
			bw.write(content);
			bw.close();

			}catch (IOException e) {
				e.printStackTrace();
			}
			
	
			
				}
		
				else{
				
					 texto.setText("[-]linhas:"+texto.getText().length()+"senha SHA-"+SHA+ ": \n não igual:  "+senha +" = "+coluna);
				}
		
		
		
			}
    	}
	}
	

}
