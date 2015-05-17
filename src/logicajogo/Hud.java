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
	
	private void gerarHud(){
		adicionarIconeExperiencia();
		adicionarIconeVidaCheia();
		preencherEspacosVazios();
		
	}
	
	private void preencherEspacosVazios(){
		int total = quantidadeDeVidaInicial+ 4;
		for( ; total < tabuleiro.getNumeroColunas(); ++total){
			hud.add(new JLabel(fabricaIcones.obterIcone(Elemento.NADA)));
		}
	}
	
	private void adicionarIconeVidaCheia(){
		for(int k = 0; k < quantidadeDeVidaInicial; k++){
			vidas.add(new JLabel(fabricaIcones.obterIcone(Elemento.VIDA)));
			hud.add(vidas.get(k));
		}
	}
	
	private void atualizarHud(){
		hud.removeAll();
		adicionarIconeExperiencia();
		for(int k = 0; k< quantidadeDeVidaInicial; k++){
			hud.add(vidas.get(k));
		}
		preencherEspacosVazios();
		hud.updateUI();// Atualiza o JPanel
	}
	
	private void adicionarIconeExperiencia(){
		
		Font font = new Font("SansSerif", Font.BOLD, 40);
		Font font2 = new Font("SansSerif", Font.BOLD, 35);
		hud.add(new JLabel(fabricaIcones.obterIcone(Elemento.MACA)));
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
	
	public void removerVida(){
		//vidas. = new JLabel(fabricaIcones.obterIcone(Elemento.SEMVIDA));
		vida = vida - 1;
		vidas.set(vida, new JLabel(fabricaIcones.obterIcone(Elemento.SEMVIDA)));
		atualizarHud();
		
	}
	
	public boolean adicionarVida(){
		if(verificarSeVidaEstaCheia()==false){
			vidas.set(vida, new JLabel(fabricaIcones.obterIcone(Elemento.VIDA)));
			vida = vida + 1;
			atualizarHud();
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
