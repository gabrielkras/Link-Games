package logicajogo;

import javax.swing.JLabel;

import apresentacao.Hud;
import apresentacao.TelaJogo;

public class Tabuleiro {

	private MapaGlobal estruturaMapas;
	private Elemento[][] personagemMapa;
	
	private SaidaJogo saida;
	private SaidaJogador saidaJogador;
	private Movimentacao mov;
	
	public Tabuleiro(MapaGlobal estruturaMapas, int quantidadeDeVidaInicial) {
		this.estruturaMapas = estruturaMapas;
		saidaJogador = new Jogador(quantidadeDeVidaInicial);
	}

	public void setSaida(TelaJogo saida) {
		this.saida = saida;
	}

	public void iniciarJogo() {
		ocultarPortal();
		saida.iniciarJogo();
		this.mov = new Movimentacao(this,estruturaMapas,this.saida, saidaJogador);
		
	}
	
	public SaidaJogador getSaidaJogador(){
		return saidaJogador;
	}
	
	public SaidaJogo getSaida(){
		return this.saida;
	}
	
	public int getNumeroLinhas() {
		return estruturaMapas.getMapa().getLinha();
	}

	public int getNumeroColunas() {
		return estruturaMapas.getMapa().getColuna();
	}

	public Elemento elementoEm(Posicao posicao) {
		return estruturaMapas.getMapa().getMapa()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void fazerMovimento(Direcao d){
		if((personagemRecolheuTodosOsRubis()) && (estruturaMapas.getMapa().temPortalNoMapa()) == true){
			reexibirPortal();
		}
		mov.fazerMovimento(d, this.saida);
		
	}

	public boolean personagemRecolheuTodosOsRubis(){
		return(estruturaMapas.getMapa().obterQuantidadeTotalDePontosNoMapa() <= 
				saidaJogador.obterQuantidadeDeRubisColetados());
	}

	private void ocultarPortal() {
		if(estruturaMapas.getMapa().temPortalNoMapa() == true){
			alterarElemento(estruturaMapas.getMapa().obterPosicaoPortalMapa(), Elemento.PEDRAENFEITE);
		}
		else{
			return;
		}
		
	}

	void reexibirPortal() {
		if(estruturaMapas.getMapa().temPortalNoMapa()==true){
			alterarElemento(estruturaMapas.getMapa().obterPosicaoPortalMapa(), Elemento.PORTAL);
		}
		
	}

	void alterarElemento(Posicao posicao, Elemento e) {
			estruturaMapas.getMapa().getMapa()[posicao.getLinha()][posicao.getColuna()] = e;
			saida.alterarElemento(posicao, e);
	}

	Posicao acharPosicaoDe(Elemento elemento) {
		for (int i = 0; i < estruturaMapas.getMapa().getMapa().length; i++) {
			for (int j = 0; j < estruturaMapas.getMapa().getMapa()[i].length; j++) {
				if (estruturaMapas.getMapa().getMapa()[i][j] == elemento) {
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
	/**
	 * Método responsável por executar a mudança de estrutura de mapa que está sendo exibida ao usuário.
	 * @param Posicao posicaoNova, Posicao posicaoAntiga, Elemento spritePersonagem, Direcao d
	 * @return boolean*/
	public boolean navegar(Posicao posicaoNova, Posicao posicaoAntiga, Elemento spritePersonagem, Direcao d){
		if((posicaoNova.getColuna() == getNumeroColunas()) && (Direcao.DIREITA == d)){
			if((estruturaMapas.getMapaDireita() != null) && (estruturaMapas.getMapa().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				estruturaMapas = estruturaMapas.navegarParaDireita();
				
				mov.setEstruturaMapa(estruturaMapas);
				
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
			if((estruturaMapas.getMapaEsquerda() != null) && (estruturaMapas.getMapa().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				estruturaMapas = estruturaMapas.navegarParaEsquerda();
				
				mov.setEstruturaMapa(estruturaMapas);
				
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
			if((estruturaMapas.getMapaCima() != null) && (estruturaMapas.getMapa().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				estruturaMapas = estruturaMapas.navegarParaCima();
				
				mov.setEstruturaMapa(estruturaMapas);
				
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
			if((estruturaMapas.getMapaBaixo() != null)&& (estruturaMapas.getMapa().getIndiceDoMapaAtual() == 0)){
				alterarElemento(posicaoAntiga, spritePersonagem);
				estruturaMapas = estruturaMapas.navegarParaBaixo();
				
				mov.setEstruturaMapa(estruturaMapas);
				
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
