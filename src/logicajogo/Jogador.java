package logicajogo;

public class Jogador implements SaidaJogador {
	private int pontosVida;
	private int quantidadeDeVidaTotal;
	private int quantidadeDeRubisColetados;
	
	public Jogador (int quantidadeDeVidaInicial){
		pontosVida = quantidadeDeVidaInicial;
		quantidadeDeVidaTotal = quantidadeDeVidaInicial;
		quantidadeDeRubisColetados = 0;
	}
	@Override
	public void coletarRubi(){
		quantidadeDeRubisColetados++;
	}
	@Override
	public void coletarVida(){
		pontosVida++;
	}
	@Override
	public void sofrerDano(int quantidadeDeDanoSofrido){
		pontosVida = pontosVida - quantidadeDeDanoSofrido;
	}
	
	@Override
	public int obterQuantidadeDeVida() {
		return pontosVida;
	}
	@Override
	public int obterQuantidadeDeRubisColetados() {
		return quantidadeDeRubisColetados;
	}
	@Override
	public boolean vidaEstaCheia() {
		return quantidadeDeVidaTotal == pontosVida;
	}

}
