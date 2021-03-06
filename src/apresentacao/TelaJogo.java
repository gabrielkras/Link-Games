package apresentacao;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import logicajogo.Direcao;
import logicajogo.Elemento;
import logicajogo.MapaGlobal;
import logicajogo.Posicao;
import logicajogo.SaidaJogo;
import logicajogo.Tabuleiro;

public class TelaJogo implements SaidaJogo {

	private Tabuleiro tabuleiro;
	private FabricaIcones fabricaIcones;
	private JFrame frame;
	
	private JPanel cenario; // Tela Jogo
	private JPanel jogador;
	private Hud hud; // Tela Hud
	private JPanel container; // Container contendo as Estrutura Jogo e HUD
	
	

	public TelaJogo(Tabuleiro tabuleiro, FabricaIcones fabricaIcones) {
		this.tabuleiro = tabuleiro;
		this.fabricaIcones = fabricaIcones;

		frame = new JFrame();
		// Caracteristicas do Frame (Nome, Propriedades e Icone)
		frame.setTitle("LINK GAMES");
		frame.setResizable(false);
		ImageIcon icon = new ImageIcon(getClass().getResource("/Coracao.png"));
		frame.setIconImage(icon.getImage());
		
		// Cria um Container
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		// Cria o JPanel do Jogo
		cenario = new JPanel();
		cenario.setLayout(new GridLayout(tabuleiro.getNumeroLinhas(), tabuleiro.getNumeroColunas()));
		jogador = new JPanel();
		jogador.setLayout(new GridLayout(tabuleiro.getNumeroLinhas(), tabuleiro.getNumeroColunas()));
		
		jogador.add(new JLabel(fabricaIcones.obterIcone(Elemento.LAVA)));
		jogador.setOpaque(false);
		
		//Adiciona a Detec��o do Teclado
		frame.addKeyListener(new TecladoListener());
		
		
		// Cria o JPanel do HUD
		hud = new Hud(tabuleiro.getSaidaJogador(),tabuleiro.getNumeroColunas());
		container.add(hud);
		container.add(cenario);
		
		
		frame.add(container);
		
		preencherTela();

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
	}

	private void preencherTela() {
		for (int i = 0; i < tabuleiro.getNumeroLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getNumeroColunas(); j++) {
				 cenario.add(new JLabel(fabricaIcones.obterIcone(tabuleiro.elementoEm(new Posicao(i, j)))));
			}
		}
	}
	
	public void recarregarMapa(){
		cenario.removeAll();
		for (int i = 0; i < tabuleiro.getNumeroLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getNumeroColunas(); j++) {
				 cenario.add(new JLabel(fabricaIcones.obterIcone(tabuleiro.elementoEm(new Posicao(i, j)))));
			}
		}
	}
	
	@Override
	public void mostrarMensagem(String mensagem, String titulo, Icon imagem){
		JOptionPane.showMessageDialog(frame, mensagem, titulo, 0, imagem);
	}

	@Override
	public void iniciarJogo() {
		frame.setVisible(true);
	}

	@Override
	public void alterarElemento(Posicao posicao, Elemento elemento) {
		int indice = tabuleiro.getNumeroColunas() * posicao.getLinha() + posicao.getColuna();
		cenario.remove(indice); // Remove a Imagem da Posic��o
		cenario.add(new JLabel(fabricaIcones.obterIcone(elemento)), indice); // Adiciona a nova imagem
		cenario.updateUI();// Atualiza o JPanel
		hud.atualizarHud();
	}

	@Override
	public void passarDeFase() {
		JOptionPane.showMessageDialog(frame, "Ganhou!", "Ganhou!", 0,fabricaIcones.obterIcone(Elemento.VIDA));
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
