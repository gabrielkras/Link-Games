package logicajogo;

import apresentacao.Hud;

public class Movimentacao {
	
	private Tabuleiro tabuleiro;
	private SaidaJogador saidaJogador;
	private MapaGlobal estruturaMapas;
	private SaidaJogo saida;
	
	public Movimentacao(Tabuleiro tabuleiro, MapaGlobal estruturaMapas, SaidaJogo saida, SaidaJogador saidaJogador){
		this.estruturaMapas = estruturaMapas;
		this.tabuleiro = tabuleiro;
		this.saida = saida;
		this.saidaJogador = saidaJogador;
	}
	
	public void setEstruturaMapa(MapaGlobal estrutura){
		this.estruturaMapas = estrutura;
	}
	
	public void fazerMovimento(Direcao d, SaidaJogo saida){
		Posicao posicaoAntiga = null;
		Posicao posicaoNova = null;
		Elemento elementoPosicaoNova = null;
		Elemento elementoPosicaoAntiga = null;
		Elemento elementoEmNovaPosicao = null;
		
		posicaoAntiga = encontrarPosicaoPersonagem();
		elementoPosicaoAntiga = tabuleiro.elementoEm(posicaoAntiga);
		posicaoNova = posicaoAntiga.somar(d);
		if(tabuleiro.navegar(posicaoNova,posicaoAntiga,elementoPosicaoAntiga,d)==true) return;
		if (tabuleiro.posicaoEhInvalida(posicaoNova)) return;
		elementoEmNovaPosicao = tabuleiro.elementoEm(posicaoNova);
		elementoPosicaoNova = retornarElementoDaPosicaoNova(posicaoNova, posicaoAntiga, d);
		elementoPosicaoAntiga = retornarElementoDaPosicaoAntiga(posicaoAntiga);
		
		switch (elementoEmNovaPosicao){
			case AGUA:
				saidaJogador.sofrerDano(1);
				if(saidaJogador.obterQuantidadeDeVida() <= 0){
					tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
					tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
					saida.perderJogo();
					break;
				}
				else{
					tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
					tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
					break;
				}
				
			case LAVA:
				saidaJogador.sofrerDano(1);
				if(saidaJogador.obterQuantidadeDeVida() <= 0){
					saida.perderJogo();
					break;
				}
				else{
					break;
				}
				
			case GRAMA:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case TERRA_CAVERNA1:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case TERRA_CAVERNA2:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case PEDRA:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case PLACA:
				saida.mostrarMensagem(estruturaMapas.getMapa().lerMensagem(), "Inforação Placa!",estruturaMapas.getMapa().obterIconeMensagem());
				break;
			case RUBI:
				saidaJogador.coletarRubi();
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case PORTAL:
				saida.passarDeFase();
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case VIDA:
				if(saidaJogador.vidaEstaCheia()){
					break;
				}
				else{
					saidaJogador.coletarVida();
					tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
					tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
					break;
				}
				
			case PASSAGEM:
				tabuleiro.alterarElemento(posicaoNova, Elemento.PASSAGEM);
				estruturaMapas.getMapa().avancarUmMapa();
				tabuleiro.alterarElemento(encontrarPosicaoPersonagem(), tabuleiro.elementoEm(encontrarPosicaoPersonagem()));
				this.saida.recarregarMapa();
				break;
			
			case PASSAGEMVOLTA:
				tabuleiro.alterarElemento(posicaoNova, Elemento.PASSAGEMVOLTA);
				estruturaMapas.getMapa().retrocederUmMapa();
				tabuleiro.alterarElemento(encontrarPosicaoPersonagem(), tabuleiro.elementoEm(encontrarPosicaoPersonagem()));
				this.saida.recarregarMapa();
				break;
			default:
				if(elementoEmNovaPosicao.elementoEhTransponivel() == false){
					break;
				}
				else{
					tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
					tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
					break;
				}
				
			}
		
		
	}
	
	public Posicao encontrarPosicaoPersonagem(){
		Posicao posicao = null;
		
			// Personagem na Grama
			 if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_GRAMA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_GRAMA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_GRAMA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_GRAMA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_GRAMA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_GRAMA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_GRAMA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_GRAMA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_GRAMA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_GRAMA);
			 }
			 
			 // Personagem na Agua
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_AGUA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_AGUA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_AGUA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_AGUA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_AGUA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_AGUA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_AGUA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_AGUA);
			 }
			 
			 // Personagem na Terra
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_TERRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_TERRA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_TERRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_TERRA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_TERRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_TERRA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_TERRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_TERRA);
			 }
			 
			// Personagem na Terra de Caverna
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA);
			 }
			 
			 // Personagem na Pedra
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_PEDRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_CIMA_PEDRA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_PEDRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_BAIXO_PEDRA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_PEDRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_ESQUERDA_PEDRA);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_PEDRA)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM_DIREITA_PEDRA);
			 }
		return posicao;
		
	}
	
	public Elemento retornarElementoDaPosicaoAntiga(Posicao posicaoAntiga){
		Elemento resultante = null;
		
		// CASO GRAMA
		if(tabuleiro.elementoEm(posicaoAntiga).name().endsWith("GRAMA")){
			resultante =  Elemento.GRAMA;
		}
		//CASO AGUA
		else if(tabuleiro.elementoEm(posicaoAntiga).name().endsWith("AGUA")){
			resultante =  Elemento.AGUA;
		}
		//CASO TERRA
		else if(tabuleiro.elementoEm(posicaoAntiga).name().endsWith("TERRA")){
			resultante =  Elemento.TERRA;
		}
		//CASO TERRA DE CAVERNA
		else if(tabuleiro.elementoEm(posicaoAntiga).name().endsWith("TERRA_CAVERNA")){
			resultante =  Elemento.TERRA_CAVERNA1;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga).name().endsWith("TERRA2_CAVERNA")){
			resultante =  Elemento.TERRA_CAVERNA2;
		}
		//CASO PEDRA
		else if(tabuleiro.elementoEm(posicaoAntiga).name().endsWith("PEDRA")){
			resultante =  Elemento.PEDRA;
		}
		
		return resultante;
	}

	private Elemento retornarElementoDaPosicaoNova(Posicao posicaoNova, Posicao posicaoAntiga, Direcao d){
		Elemento elementoPosicaoNova = null;
		Elemento resultante = null;
		tabuleiro.elementoEm(posicaoNova);
		
		
		if((elementoPosicaoNova == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_GRAMA)){
			resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
		}
		else if(Direcao.CIMA == d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_CIMA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(!saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.PERSONAGEM_CIMA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.PERSONAGEM_CIMA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.PERSONAGEM_CIMA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.PERSONAGEM_CIMA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.PERSONAGEM_CIMA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.PERSONAGEM_CIMA_PEDRA;
					}
				}
				else if(saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		else if(Direcao.BAIXO == d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(!saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.PERSONAGEM_BAIXO_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.PERSONAGEM_BAIXO_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.PERSONAGEM_BAIXO_PEDRA;
					}
				}
				else if(saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		else if(Direcao.ESQUERDA == d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(!saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.PERSONAGEM_DIREITA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_PEDRA;
					}
				}
				else if(saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		
		else if(Direcao.DIREITA== d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA_CAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
				resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
				resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
				resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
				resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(!saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.PERSONAGEM_DIREITA_GRAMA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.PERSONAGEM_ESQUERDA_AGUA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.PERSONAGEM_DIREITA_PEDRA;
					}
				}
				else if(saidaJogador.vidaEstaCheia()){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_CIMA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_BAIXO_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_PEDRA)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_GRAMA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_ESQUERDA_AGUA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_TERRA2_CAVERNA)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM_DIREITA_PEDRA)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		
		return resultante;
		
	}
}
