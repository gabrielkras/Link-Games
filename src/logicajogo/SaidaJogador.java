package logicajogo;

import javax.swing.JPanel;

public interface SaidaJogador {
	
	void coletarRubi();
	void coletarVida();
	void sofrerDano(int quantidadeDeDanoSofrido);
	int obterQuantidadeDeVida();
	int obterQuantidadeDeRubisColetados();
	boolean vidaEstaCheia();

}
