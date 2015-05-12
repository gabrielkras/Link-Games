package apresentacao;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logicajogo.Elemento;
import logicajogo.Posicao;
import logicajogo.SaidaJogo;
import logicajogo.Tabuleiro;

public class TelaJogoHUD implements SaidaJogo {
	
	private Tabuleiro tabuleiro;
	private FabricaIcones fabrica;
	private JFrame telaJogo;
	private JPanel jogo;
	private JPanel hud;
	
	public TelaJogoHUD(Tabuleiro tabuleiro, FabricaIcones fabrica){
		
		this.tabuleiro = tabuleiro;
		this.fabrica = fabrica;
		
		telaJogo = new JFrame();
		
		
		jogo = new JPanel();
		jogo.setLayout(new GridLayout(tabuleiro.getNumeroLinhas(),tabuleiro.getNumeroColunas()));
		
		hud = new JPanel();
		//hud.setLayout(new GridLayout(2,tabuleiro.getNumeroColunas()));
		//for(int k = 0; k<1; k++){
			//for(int l = 0; l<tabuleiro.getNumeroColunas(); l++){
				hud.add(new JLabel(fabrica.obterIcone(Elemento.NADA)));
			//}
		//}
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		preencherTela();
		
		container.add(jogo);
		container.add(hud);
		
		//telaJogo.add(jogo);
		//telaJogo.add(hud);
		telaJogo.add(container);
		telaJogo.pack();
	}
	
	private void preencherTela(){
		for (int i = 0; i < tabuleiro.getNumeroLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getNumeroColunas(); j++) {
				 jogo.add(new JLabel(fabrica.obterIcone(tabuleiro.elementoEm(new Posicao(i, j)))));
			}
		}
	}
	
	public void iniciarJogo(){
		telaJogo.setVisible(true);
	}

	@Override
	public void alterarElemento(Posicao posicao, Elemento novo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void passarDeFase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perderJogo() {
		// TODO Auto-generated method stub
		
	}

}
