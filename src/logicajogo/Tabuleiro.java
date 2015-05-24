package logicajogo;

import javax.swing.JLabel;

import apresentacao.TelaJogo;

public class Tabuleiro {

	private EstruturaMapas estruturaMapas;
	
	private SaidaJogo saida;
	private Hud hud;
	private Movimentacao mov;
	
	public Tabuleiro(EstruturaMapas estruturaMapas) {
		this.estruturaMapas = estruturaMapas;
	}

	public void setSaida(TelaJogo saida) {
		this.saida = saida;
	}

	public void iniciarJogo() {
		ocultarPortal();
		saida.iniciarJogo();
		this.mov = new Movimentacao(this,estruturaMapas,this.hud,this.saida);
		
	}
	
	public SaidaJogo getSaida(){
		return this.saida;
	}
	
	public int getNumeroLinhas() {
		return estruturaMapas.getMapaAtual().getLinha();
	}

	public int getNumeroColunas() {
		return estruturaMapas.getMapaAtual().getColuna();
	}

	public Elemento elementoEm(Posicao posicao) {
		return estruturaMapas.getMapaAtual().getMapa()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void fazerMovimento(Direcao d){
		mov.fazerMovimento(d, this.saida);
		
	}


	public void setHud(Hud hud){
		this.hud = hud;
	}


	private void ocultarPortal() {
		alterarElemento(estruturaMapas.getMapaAtual().obterPosicaoPortalMapa(), Elemento.GRAMA);
	}

	void reexibirPortal() {
		if(estruturaMapas.getMapaAtual().temPortalNoMapa()==true){
			alterarElemento(estruturaMapas.getMapaAtual().obterPosicaoPortalMapa(), Elemento.PORTAL);
		}
		
	}

	void alterarElemento(Posicao posicao, Elemento e) {
			estruturaMapas.getMapaAtual().getMapa()[posicao.getLinha()][posicao.getColuna()] = e;
			saida.alterarElemento(posicao, e);
	}

	private int quantidadeMacasRestantes() {
		int ret = 0;

		for (int i = 0; i < estruturaMapas.getMapaAtual().getMapa().length; i++) {
			for (int j = 0; j < estruturaMapas.getMapaAtual().getMapa()[i].length; j++) {
				if (estruturaMapas.getMapaAtual().getMapa()[i][j] == Elemento.RUBI) ++ret;
			}
		}

		return ret;
	}

	Posicao acharPosicaoDe(Elemento elemento) {
		for (int i = 0; i < estruturaMapas.getMapaAtual().getMapa().length; i++) {
			for (int j = 0; j < estruturaMapas.getMapaAtual().getMapa()[i].length; j++) {
				if (estruturaMapas.getMapaAtual().getMapa()[i][j] == elemento) {
					return new Posicao(i, j);
				}
			}
		}

		return null;
	}

	boolean posicaoEhInvalida(Posicao p) {
		return p.getLinha() < 0 || p.getLinha() >= getNumeroLinhas()
				|| p.getColuna() < 0 || p.getColuna() >= getNumeroColunas();
	}
	
	public boolean navegar(Posicao posicaoNova, Posicao posicaoAntiga, Elemento spritePersonagem, Direcao d){
		if((posicaoNova.getColuna() == getNumeroColunas()) && (Direcao.DIREITA == d)){
			if((estruturaMapas.getDireita() != null) && (estruturaMapas.getMapaAtual().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				this.estruturaMapas = estruturaMapas.navegarParaDireita();
				Elemento personagem;
				personagem = mov.retornarElementoDaPosicaoAntiga((mov.encontrarPosicaoPersonagem()));
				alterarElemento(mov.encontrarPosicaoPersonagem(), personagem);
				posicaoNova = new Posicao(posicaoAntiga.getLinha(),0);
				alterarElemento(posicaoNova,spritePersonagem);
				saida.recarregarMapa();
				return true;
			}
			else{
				return false;
			}
		}
		else if((posicaoNova.getColuna() < 0) && (Direcao.ESQUERDA == d)){
			if((estruturaMapas.getEsquerda() != null) && (estruturaMapas.getMapaAtual().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				this.estruturaMapas = estruturaMapas.navegarParaEsquerda();
				Elemento personagem;
				personagem = mov.retornarElementoDaPosicaoAntiga((mov.encontrarPosicaoPersonagem()));
				alterarElemento(mov.encontrarPosicaoPersonagem(), personagem);
				posicaoNova = new Posicao(posicaoAntiga.getLinha(),getNumeroColunas()-1);
				alterarElemento(posicaoNova,spritePersonagem);
				saida.recarregarMapa();
				return true;
			}
			else{
				return false;
			}
		}
		else if((posicaoNova.getLinha() < 0) &&  (Direcao.CIMA == d)){
			if((estruturaMapas.getCima() != null) && (estruturaMapas.getMapaAtual().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				this.estruturaMapas = estruturaMapas.navegarParaCima();
				Elemento personagem;
				personagem = mov.retornarElementoDaPosicaoAntiga((mov.encontrarPosicaoPersonagem()));
				alterarElemento(mov.encontrarPosicaoPersonagem(), personagem);
				posicaoNova = new Posicao(getNumeroLinhas()-1,posicaoNova.getColuna());
				alterarElemento(posicaoNova,spritePersonagem);
				saida.recarregarMapa();
				return true;
			}
			else{
				return false;
			}
		}
		else if((posicaoNova.getLinha() >= getNumeroLinhas()) &&  (Direcao.BAIXO == d)){
			if((estruturaMapas.getBaixo() != null)&& (estruturaMapas.getMapaAtual().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				this.estruturaMapas = estruturaMapas.navegarParaBaixo();
				Elemento personagem;
				personagem = mov.retornarElementoDaPosicaoAntiga((mov.encontrarPosicaoPersonagem()));
				alterarElemento(mov.encontrarPosicaoPersonagem(), personagem);
				posicaoNova = new Posicao(0,posicaoNova.getColuna());
				alterarElemento(posicaoNova,spritePersonagem);
				saida.recarregarMapa();
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

}
