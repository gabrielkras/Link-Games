package logicajogo;

public class Tabuleiro {

	private Elemento[][] matriz;
	private SaidaJogo saida;
	private Posicao posicaoDoPortalOculto;

	public Tabuleiro(Elemento[][] matriz) {
		this.matriz = matriz;
	}

	public void setSaida(SaidaJogo saida) {
		this.saida = saida;
	}

	public void iniciarJogo() {
		ocultarPortal();
		saida.iniciarJogo();
	}

	public int getNumeroLinhas() {
		return matriz.length;
	}

	public int getNumeroColunas() {
		return matriz[0].length;
	}

	public Elemento elementoEm(Posicao posicao) {
		return matriz[posicao.getLinha()][posicao.getColuna()];
	}

	public void fazerMovimento(Direcao d) {
		
		Posicao posicaoAntiga = null;
		Posicao posicaoNova = null;
		Elemento elementoAlcancado = null;
		
		/*if(acharPosicaoDe(Elemento.PERSONAGEM) != null){
			posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEM);
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);

			alterarElemento(posicaoAntiga, Elemento.GRAMA);
			alterarElemento(posicaoNova, Elemento.PERSONAGEM);
		}*/
		
		//==== Troca de Imagem por diferentes direções de locomoção ===
		if(Direcao.CIMA == d){
				 if(acharPosicaoDe(Elemento.PERSONAGEM)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEM);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMUP)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUP);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWN)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWN);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFT)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFT);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHT)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHT);
				 }
				posicaoNova = posicaoAntiga.somar(d);
				if (posicaoEhInvalida(posicaoNova)) return;

				elementoAlcancado = elementoEm(posicaoNova);

				alterarElemento(posicaoAntiga, Elemento.GRAMA);
				alterarElemento(posicaoNova, Elemento.PERSONAGEMUP);
			
		}
		else if(Direcao.BAIXO == d){
			 if(acharPosicaoDe(Elemento.PERSONAGEM)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEM);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMUP)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUP);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWN)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWN);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFT)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFT);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHT)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHT);
			 }
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);

			alterarElemento(posicaoAntiga, Elemento.GRAMA);
			alterarElemento(posicaoNova, Elemento.PERSONAGEMDOWN);
		
		}
		else if(Direcao.ESQUERDA == d){
			 if(acharPosicaoDe(Elemento.PERSONAGEM)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEM);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMUP)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUP);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWN)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWN);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFT)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFT);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHT)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHT);
			 }
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);

			alterarElemento(posicaoAntiga, Elemento.GRAMA);
			alterarElemento(posicaoNova, Elemento.PERSONAGEMLEFT);
		
		}
		else if(Direcao.DIREITA == d){
			 if(acharPosicaoDe(Elemento.PERSONAGEM)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEM);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMUP)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUP);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWN)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWN);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFT)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFT);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHT)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHT);
			 }
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);
			
			alterarElemento(posicaoAntiga, Elemento.GRAMA);
			alterarElemento(posicaoNova, Elemento.PERSONAGEMRIGHT);
		
		}
		//=======================//
		
		
		
		//Posicao posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEM);
		//Posicao posicaoNova = posicaoAntiga.somar(d);

		//if (posicaoEhInvalida(posicaoNova)) return;

		//Elemento elementoAlcancado = elementoEm(posicaoNova);

		//alterarElemento(posicaoAntiga, Elemento.GRAMA);
		//alterarElemento(posicaoNova, Elemento.PERSONAGEM);

		switch (elementoAlcancado) {
		case AGUA:
			if(Direcao.CIMA == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMUPWATER);
				saida.perderJogo();
			}
			else if(Direcao.BAIXO == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMDOWNWATER);
				saida.perderJogo();
			}
			else if(Direcao.ESQUERDA == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMLEFTWATER);
				saida.perderJogo();
			}
			else if(Direcao.DIREITA == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMRIGHTWATER);
				saida.perderJogo();
			}
			break;

		case MACA:
			if (quantidadeMacasRestantes() == 0) reexibirPortal();
			break;

		case PORTAL:
			saida.passarDeFase();
			break;

		default:
			break;
		}

	}

	private void ocultarPortal() {
		posicaoDoPortalOculto = acharPosicaoDe(Elemento.PORTAL);
		alterarElemento(posicaoDoPortalOculto, Elemento.GRAMA);
	}

	private void reexibirPortal() {
		alterarElemento(posicaoDoPortalOculto, Elemento.PORTAL);
	}

	private void alterarElemento(Posicao posicao, Elemento e) {
			matriz[posicao.getLinha()][posicao.getColuna()] = e;
			saida.alterarElemento(posicao, e);
	}

	private int quantidadeMacasRestantes() {
		int ret = 0;

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] == Elemento.MACA) ++ret;
			}
		}

		return ret;
	}

	private Posicao acharPosicaoDe(Elemento elemento) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] == elemento) {
					return new Posicao(i, j);
				}
			}
		}

		return null;
	}

	private boolean posicaoEhInvalida(Posicao p) {
		return p.getLinha() < 0 || p.getLinha() >= getNumeroLinhas()
				|| p.getColuna() < 0 || p.getColuna() >= getNumeroColunas();
	}

}
