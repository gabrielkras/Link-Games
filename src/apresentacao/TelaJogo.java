package apresentacao;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import logicajogo.Direcao;
import logicajogo.Elemento;
import logicajogo.Posicao;
import logicajogo.SaidaJogo;
import logicajogo.Tabuleiro;

public class TelaJogo implements SaidaJogo {

	private Tabuleiro tabuleiro;
	private FabricaIcones fabricaIcones;
	private JFrame frame;
	
	private JPanel jogo;
	private JPanel hud;
	
	

	public TelaJogo(Tabuleiro tabuleiro, FabricaIcones fabricaIcones) {
		this.tabuleiro = tabuleiro;
		this.fabricaIcones = fabricaIcones;

		frame = new JFrame();
		
		// Cria um Container
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		// Cria o JPanel do Jogo
		jogo = new JPanel();
		jogo.setLayout(new GridLayout(tabuleiro.getNumeroLinhas(), tabuleiro.getNumeroColunas()));
		jogo.addKeyListener(new TecladoListener());
		
		// Cria o JPanel do HUD
		hud = new JPanel();
		hud.add(new JLabel(fabricaIcones.obterIcone(Elemento.NADA)));
		
		container.add(hud);
		container.add(jogo);
		
		frame.add(container);
		
		//frame.setLayout(new GridLayout(tabuleiro.getNumeroLinhas(), tabuleiro.getNumeroColunas()));
		//frame.addKeyListener(new TecladoListener());
		
		
		
		frame.setTitle("Pamonha Games");

		preencherTela();

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
	}

	private void preencherTela() {
		for (int i = 0; i < tabuleiro.getNumeroLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getNumeroColunas(); j++) {
				 jogo.add(new JLabel(fabricaIcones.obterIcone(tabuleiro.elementoEm(new Posicao(i, j)))));
			}
		}
	}

	@Override
	public void iniciarJogo() {
		frame.setVisible(true);
	}

	@Override
	public void alterarElemento(Posicao posicao, Elemento elemento) {
		int indice = tabuleiro.getNumeroColunas() * posicao.getLinha() + posicao.getColuna();
		//Component a = jogo.getComponent(indice);
		//((JLabel) a).setIcon(fabricaIcones.obterIcone(elemento));
		//jogo.add(a,indice);
		//((JLabel) frame.getContentPane().getComponent(indice)).setIcon(fabricaIcones.obterIcone(elemento));
		//((JLabel) jogo.getComponent(indice)).setIcon(fabricaIcones.obterIcone(elemento));
		((JLabel) jogo.getComponent(indice)).setIcon(fabricaIcones.obterIcone(Elemento.GRAMA));
	}

	@Override
	public void passarDeFase() {
		JOptionPane.showMessageDialog(frame, "Ganhou!", "Ganhou!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	@Override
	public void perderJogo() {
		JOptionPane.showMessageDialog(frame, "Perdeu!", "Perdeu!", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	private class TecladoListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			Direcao direcao = Direcao.comCodigo(e.getKeyCode());
			if (direcao != null) tabuleiro.fazerMovimento(direcao);
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

	}

}
