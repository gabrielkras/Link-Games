package apresentacao;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logicajogo.Elemento;
import logicajogo.SaidaJogador;
import logicajogo.Tabuleiro;
public class Hud extends JPanel {
	
	private FabricaIcones fabricaIcones;
	private SaidaJogador saidaJogador;
	private int larguraDoHud;
	private final int quantidadeDeIconesVida;
	public Hud(SaidaJogador saidaJogador, int larguraDoHud){
		this.larguraDoHud = larguraDoHud;
		this.saidaJogador = saidaJogador;
		this.quantidadeDeIconesVida = saidaJogador.obterQuantidadeDeVida();
		this.setLayout(new GridLayout());
		this.setBackground(Color.GRAY);
		this.fabricaIcones = new FabricaIcones();
		gerarHud();
	}
	
	public void atualizarHud(){
		this.removeAll();
		adicionarIconesDaQuantidadeDeRubisColetados();
		adicionarIconesVida();
		preencherEspacosVazios();
		this.updateUI();
	}
	
	private void gerarHud(){
		adicionarIconesDaQuantidadeDeRubisColetados();
		adicionarIconesVida();
		preencherEspacosVazios();
	}
	
	private void preencherEspacosVazios(){
		int total = quantidadeDeIconesVida + 4; 
		/* 4 -> Refere-se a quantidade de JLabels necessários para mostrar a quantidade
		 * de rubis coletados no mapa, sendo elas
		 * 1ª -> Imagem do Rubi, 2ª -> Label X, 3ª -> Label quantidades pontos, 4ª -> Espaço Separador*/
		
		for( ; total < larguraDoHud; ++total){
			this.add(new JLabel(fabricaIcones.obterIcone(Elemento.NADA)));
		}
	}
	
	private void adicionarIconesVida(){
		int vidasCheias = saidaJogador.obterQuantidadeDeVida();
			for(int k = 0; k < quantidadeDeIconesVida; k++){
				if(vidasCheias > 0){
					this.add(new JLabel(fabricaIcones.obterIcone(Elemento.VIDA)));
					vidasCheias--;
				}
				else{
					this.add(new JLabel(fabricaIcones.obterIcone(Elemento.SEMVIDA)));
				}
		}
	}
	
	private void adicionarIconesDaQuantidadeDeRubisColetados(){
		Font font = new Font("SansSerif", Font.BOLD, 40);
		Font font2 = new Font("SansSerif", Font.BOLD, 35);
		this.add(new JLabel(fabricaIcones.obterIcone(Elemento.RUBI)));
		JLabel labelX = new JLabel();
		JLabel labelPontos = new JLabel();
		labelPontos.setForeground(Color.WHITE);
		labelX.setFont(font);
		labelX.setText("X");
		labelX.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontos.setFont(font2);
		labelPontos.setText(" "+saidaJogador.obterQuantidadeDeRubisColetados());
		this.add(labelX);
		this.add(labelPontos);
		this.add(new JLabel(fabricaIcones.obterIcone(Elemento.NADA)));
	}
}
