package logicajogo;

import javax.swing.JLabel;

import apresentacao.TelaJogo;

public class Tabuleiro {

	private Mapa mapa;
	
	private SaidaJogo saida;
	private Hud hud;
	
	public Tabuleiro(Mapa mapa) {
		this.mapa = mapa;
	}

	public void setSaida(TelaJogo saida) {
		this.saida = saida;
	}

	public void iniciarJogo() {
		ocultarPortal();
		saida.iniciarJogo();
	}

	public int getNumeroLinhas() {
		return mapa.getLinha();
	}

	public int getNumeroColunas() {
		return mapa.getColuna();
	}

	public Elemento elementoEm(Posicao posicao) {
		return mapa.getMapa()[posicao.getLinha()][posicao.getColuna()];
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
				 
				 
				 else if(acharPosicaoDe(Elemento.PERSONAGEMUPWATER)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUPWATER);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER);
				 }
				 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER)!= null){
					 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER);
				 }
				 
				posicaoNova = posicaoAntiga.somar(d);
				if (posicaoEhInvalida(posicaoNova)) return;

				elementoAlcancado = elementoEm(posicaoNova);
				
				

				Elemento elem = mapa.checarElementoPosicao(posicaoAntiga);
				if(elementoAlcancado == Elemento.PASSAGEM){
					alterarElemento(posicaoNova, Elemento.PASSAGEM);
					alterarElemento(posicaoAntiga, elem);
				}
				else if(elementoAlcancado == Elemento.PASSAGEMVOLTA){
					alterarElemento(posicaoNova, Elemento.PASSAGEMVOLTA);
					alterarElemento(posicaoAntiga, elem);
				}
				
				else if(elementoAlcancado == Elemento.VIDA){
					if(!hud.adicionarVida()){
						alterarElemento(posicaoNova, Elemento.VIDA);
						alterarElemento(posicaoNova.somar(d), Elemento.PERSONAGEMUP);
						alterarElemento(posicaoAntiga, elem);
					}
					else{
						alterarElemento(posicaoAntiga, elem);
						alterarElemento(posicaoNova, Elemento.PERSONAGEMUP);
					}
				}
				
				else{
					alterarElemento(posicaoAntiga, elem);
					alterarElemento(posicaoNova, Elemento.PERSONAGEMUP);
				}
				
			
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
			 
			 else if(acharPosicaoDe(Elemento.PERSONAGEMUPWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUPWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER);
			 }
			 
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);

			//alterarElemento(posicaoAntiga, Elemento.GRAMA);
			Elemento elem = mapa.checarElementoPosicao(posicaoAntiga);
			if(elementoAlcancado == Elemento.PASSAGEM){
				alterarElemento(posicaoNova, Elemento.PASSAGEM);
				alterarElemento(posicaoAntiga, elem);
			}
			else if(elementoAlcancado == Elemento.PASSAGEMVOLTA){
				alterarElemento(posicaoNova, Elemento.PASSAGEMVOLTA);
				alterarElemento(posicaoAntiga, elem);
			}
			
			else if(elementoAlcancado == Elemento.VIDA){
				if(!hud.adicionarVida()){
					alterarElemento(posicaoNova, Elemento.VIDA);
					alterarElemento(posicaoNova.somar(d), Elemento.PERSONAGEMDOWN);
					alterarElemento(posicaoAntiga, elem);
				}
				else{
					alterarElemento(posicaoAntiga, elem);
					alterarElemento(posicaoNova, Elemento.PERSONAGEMDOWN);
				}
			}
			
			else{
				alterarElemento(posicaoAntiga, elem);
				alterarElemento(posicaoNova, Elemento.PERSONAGEMDOWN);
			}
		
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
			 
			 else if(acharPosicaoDe(Elemento.PERSONAGEMUPWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUPWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER);
			 }
			 
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);

			//alterarElemento(posicaoAntiga, Elemento.GRAMA);
			Elemento elem = mapa.checarElementoPosicao(posicaoAntiga);
			if(elementoAlcancado == Elemento.PASSAGEM){
				alterarElemento(posicaoNova, Elemento.PASSAGEM);
				alterarElemento(posicaoAntiga, elem);
			}
			else if(elementoAlcancado == Elemento.PASSAGEMVOLTA){
				alterarElemento(posicaoNova, Elemento.PASSAGEMVOLTA);
				alterarElemento(posicaoAntiga, elem);
			}
			
			else if(elementoAlcancado == Elemento.VIDA){
				if(!hud.adicionarVida()){
					alterarElemento(posicaoNova, Elemento.VIDA);
					alterarElemento(posicaoNova.somar(d), Elemento.PERSONAGEMLEFT);
					alterarElemento(posicaoAntiga, elem);
				}
				else{
					alterarElemento(posicaoAntiga, elem);
					alterarElemento(posicaoNova, Elemento.PERSONAGEMLEFT);
				}
			}
			
			else{
				alterarElemento(posicaoAntiga, elem);
				alterarElemento(posicaoNova, Elemento.PERSONAGEMLEFT);
			}
			
		
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
			 
			 else if(acharPosicaoDe(Elemento.PERSONAGEMUPWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMUPWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER);
			 }
			 else if(acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER)!= null){
				 posicaoAntiga = acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER);
			 }
			 
			posicaoNova = posicaoAntiga.somar(d);
			if (posicaoEhInvalida(posicaoNova)) return;

			elementoAlcancado = elementoEm(posicaoNova);
			
			//alterarElemento(posicaoAntiga, Elemento.GRAMA);
			Elemento elem = mapa.checarElementoPosicao(posicaoAntiga);
			alterarElemento(posicaoAntiga, elem);
			if(elementoAlcancado == Elemento.PASSAGEM){
				alterarElemento(posicaoNova, Elemento.PASSAGEM);
				alterarElemento(posicaoAntiga, elem);
			}
			else if(elementoAlcancado == Elemento.PASSAGEMVOLTA){
				alterarElemento(posicaoNova, Elemento.PASSAGEMVOLTA);
				alterarElemento(posicaoAntiga, elem);
			}
			
			else if(elementoAlcancado == Elemento.VIDA){
				if(!hud.adicionarVida()){
					alterarElemento(posicaoNova, Elemento.VIDA);
					alterarElemento(posicaoNova.somar(d), Elemento.PERSONAGEMRIGHT);
					alterarElemento(posicaoAntiga, elem);
				}
				else{
					alterarElemento(posicaoAntiga, elem);
					alterarElemento(posicaoNova, Elemento.PERSONAGEMRIGHT);
				}
			}
			
			else{
				alterarElemento(posicaoAntiga, elem);
				alterarElemento(posicaoNova, Elemento.PERSONAGEMRIGHT);
			}
		}
		//=======================//
		
		if(hud.getPontuacao() >= mapa.obterQuantidadeTotalDePontosNoMapa()){
			reexibirPortal();
		}
		
		
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
				if(hud.getVida() > 1){
					hud.removerVida();
					//saida.recarregarMapa();
				}
				else{
					hud.removerVida();
					saida.perderJogo();
				}
				
			}
			else if(Direcao.BAIXO == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMDOWNWATER);
				if(hud.getVida() > 1){
					hud.removerVida();
					//saida.recarregarMapa();
				}
				else{
					hud.removerVida();
					saida.perderJogo();
				}
			}
			else if(Direcao.ESQUERDA == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMLEFTWATER);
				if(hud.getVida() > 1){
					hud.removerVida();
					//saida.recarregarMapa();
				}
				else{
					hud.removerVida();
					saida.perderJogo();
				}
			}
			else if(Direcao.DIREITA == d){
				alterarElemento(posicaoNova, Elemento.PERSONAGEMRIGHTWATER);
				if(hud.getVida() > 1){
					hud.removerVida();
					//saida.recarregarMapa();
				}
				else{
					hud.removerVida();
					saida.perderJogo();
				}
			}
			break;

		case MACA:
			hud.incrementarPontuacao();
		
			break;

		case PORTAL:
			saida.passarDeFase();
			break;
			
		case PASSAGEM:
			posicaoNova = posicaoNova.subtrair(d);
			mapa.getMapa()[posicaoNova.getLinha()][posicaoNova.getColuna()] = Elemento.PERSONAGEM;
			mapa.avancarUmMapa();
			if(hud.getPontuacao() >= mapa.obterQuantidadeTotalDePontosNoMapa()){
				reexibirPortal();
			}
			saida.recarregarMapa();
			
			break;
		
		case PASSAGEMVOLTA:
			posicaoNova = posicaoNova.subtrair(d);
			mapa.getMapa()[posicaoNova.getLinha()][posicaoNova.getColuna()] = Elemento.PERSONAGEM;
			mapa.retrocederUmMapa();
			if(hud.getPontuacao() >= mapa.obterQuantidadeTotalDePontosNoMapa()){
				reexibirPortal();
			}
			saida.recarregarMapa();
			break;
			
		case VIDA:
			hud.adicionarVida();

		default:
			break;
		}

	}
	
	public void setHud(Hud hud){
		this.hud = hud;
	}

	private void ocultarPortal() {
		alterarElemento(mapa.obterPosicaoPortalMapa(), Elemento.GRAMA);
	}

	private void reexibirPortal() {
		if(mapa.temPortalNoMapa()==true){
			alterarElemento(mapa.obterPosicaoPortalMapa(), Elemento.PORTAL);
		}
		
	}

	private void alterarElemento(Posicao posicao, Elemento e) {
			mapa.getMapa()[posicao.getLinha()][posicao.getColuna()] = e;
			saida.alterarElemento(posicao, e);
	}

	private int quantidadeMacasRestantes() {
		int ret = 0;

		for (int i = 0; i < mapa.getMapa().length; i++) {
			for (int j = 0; j < mapa.getMapa()[i].length; j++) {
				if (mapa.getMapa()[i][j] == Elemento.MACA) ++ret;
			}
		}

		return ret;
	}

	private Posicao acharPosicaoDe(Elemento elemento) {
		for (int i = 0; i < mapa.getMapa().length; i++) {
			for (int j = 0; j < mapa.getMapa()[i].length; j++) {
				if (mapa.getMapa()[i][j] == elemento) {
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
