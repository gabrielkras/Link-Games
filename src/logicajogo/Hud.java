package logicajogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import apresentacao.FabricaIcones;
/**
 * Classe Responsável por efetuar o gerenciamento do "HUD"(Tela responsável por
 * exibir as informações do usuário)
 * @author Francisco Gonçalves da Mota Longhini
 * @author Gabriel Sousa Kraszczuk*/
public class Hud {
	
	private JPanel hud;
	private Tabuleiro tabuleiro;
	private FabricaIcones fabricaIcones;
	private GridBagLayout grid;
	
	private JLabel labelPontos;
	private List<JLabel> vidas = new ArrayList<JLabel>();
	
	private int quantidadeDeVidaInicial = 5;
	
	
	private int vida = 5;
	private int pontos = 0;
	
	private String nomeJogador;
	
	public Hud(Tabuleiro tabuleiro){
		this.hud = new JPanel(new GridLayout());
		hud.setBackground(Color.GRAY);
		this.tabuleiro = tabuleiro;
		this.fabricaIcones = new FabricaIcones();
		gerarHud();
	}
	/**
	 * Método Responsável por gerar o Hud, Executa métodos auxiliares para mostrar a ordem
	 * na tela.*/
	private void gerarHud(){
		adicionarIconeExperiencia();
		adicionarIconeVidaCheia();
		preencherEspacosVazios();
	}
	/**
	 * Método Responsável por preencher os espaços do restantes do gridlayout, para mater a proporção
	 * do mesmo, já que o tamanho (quantidade de linhas e colunas do mapa) é variável.*/
	private void preencherEspacosVazios(){
		int total = quantidadeDeVidaInicial+ 4;
		for( ; total < tabuleiro.getNumeroColunas(); ++total){
			hud.add(new JLabel(fabricaIcones.obterIcone(Elemento.NADA)));
		}
	}
	/**
	 * Método que monta os icones de vida na tela*/
	private void adicionarIconeVidaCheia(){
		for(int k = 0; k < quantidadeDeVidaInicial; k++){
			vidas.add(new JLabel(fabricaIcones.obterIcone(Elemento.VIDA)));
			hud.add(vidas.get(k));
		}
	}
	/**
	 * Método de atualiza o HUD.
	 * 
	 * Geralmente Executado a cada altração do objeto*/
	private void atualizarHud(){
		hud.removeAll();
		adicionarIconeExperiencia();
		for(int k = 0; k< quantidadeDeVidaInicial; k++){
			hud.add(vidas.get(k));
		}
		preencherEspacosVazios();
		hud.updateUI();// Atualiza o JPanel
	}
	
	/**
	 * Método que monta o layout gráfico dos Pontos, Icones e Layer*/
	private void adicionarIconeExperiencia(){
		
		Font font = new Font("SansSerif", Font.BOLD, 40);
		Font font2 = new Font("SansSerif", Font.BOLD, 35);
		hud.add(new JLabel(fabricaIcones.obterIcone(Elemento.RUBI)));
		JLabel labelX = new JLabel();
		labelPontos = new JLabel();
		labelPontos.setForeground(Color.WHITE);
		labelX.setFont(font);
		labelX.setText("X");
		labelX.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontos.setFont(font2);
		labelPontos.setText(" "+pontos);
		
		
		hud.add(labelX);
		hud.add(labelPontos);
		hud.add(new JLabel(fabricaIcones.obterIcone(Elemento.NADA))); // Separador
	}
	/**
	 * Método Responsável por remover os pontos de vida*/
	public void removerVida(){
		//vidas. = new JLabel(fabricaIcones.obterIcone(Elemento.SEMVIDA));
		vida = vida - 1;
		vidas.set(vida, new JLabel(fabricaIcones.obterIcone(Elemento.SEMVIDA)));
		atualizarHud();
		
	}
	/**
	 * Método que incrementa a vida.*/
	public void adicionarVida(){
		vidas.set(vida, new JLabel(fabricaIcones.obterIcone(Elemento.VIDA)));
		vida = vida + 1;
		atualizarHud();
	}
	
	public boolean vidaEstaCheia(){
		if(vida >= quantidadeDeVidaInicial){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean verificarSeVidaEstaCheia(){
		if(vida >= quantidadeDeVidaInicial){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getVida(){
		return vida;
	}
	
	public JPanel getHud(){
		return hud;
	}
	
	public void incrementarPontuacao(){
		hud.updateUI();
		this.pontos++;
		labelPontos.setText(" "+pontos);
		hud.updateUI();
	}
	public void incrementarPontuacao(int pontos){
		this.pontos += pontos;
		labelPontos.setText(" "+this.pontos);
		hud.updateUI();
	}
	
	public int getPontuacao(){
		return pontos;
	}
	
	

}
