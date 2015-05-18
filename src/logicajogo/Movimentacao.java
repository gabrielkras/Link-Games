package logicajogo;

/**
 * Classe responsável por efetuar o gerenciamento de movimentação dos mapas
 * considerando os elementos dispostos no mapa*/
public class Movimentacao {
	
	private Tabuleiro tabuleiro;
	private Mapa mapa;
	private Hud hud;
	private SaidaJogo saida;
	
	public Movimentacao(Tabuleiro tabuleiro, Mapa mapa, Hud hud,SaidaJogo saida){
		this.mapa = mapa;
		this.tabuleiro = tabuleiro;
		this.hud = hud;
	}
	/**
	 * Responsável por realizar a movimentação do personagem no mapa, não importando
	 * o tipo de terreno que o mesmo se encontra
	 * @param Direcao d*/	
	public void fazerMovimento(Direcao d){
		Posicao posicaoAntiga = null;
		Posicao posicaoNova = null;
		Elemento elementoPosicaoNova = null;
		Elemento elementoPosicaoAntiga = null;
		
		posicaoAntiga = encontrarPosicaoPersonagem();
		elementoPosicaoAntiga = retornarElementoDaPosicaoAntiga(posicaoAntiga);
		posicaoNova = posicaoAntiga.somar(d);
		
		if (posicaoEhInvalida(posicaoNova)) return;
		
		elementoPosicaoNova = retornarElementoDaPosicaoNova(posicaoNova, posicaoAntiga, d);
		
		Elemento elementoAlcancado = tabuleiro.elementoEm(posicaoNova);
		
		tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
		tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
		
		switch (elementoPosicaoNova){
			case AGUA:
				hud.removerVida();
				break;
			case GRAMA:
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
					tabuleiro.reexibirPortal();
				}
				//saida.recarregarMapa();
				
				break;
			
			case PASSAGEMVOLTA:
				posicaoNova = posicaoNova.subtrair(d);
				mapa.getMapa()[posicaoNova.getLinha()][posicaoNova.getColuna()] = Elemento.PERSONAGEM;
				mapa.retrocederUmMapa();
				if(hud.getPontuacao() >= mapa.obterQuantidadeTotalDePontosNoMapa()){
					tabuleiro.reexibirPortal();
				}
				saida.recarregarMapa();
				break;
			default:
				break;
			}
		
		
	}
	
	/**
	 * Método responsável por encontrar a posição do personagem no mapa
	 * @param Direcao d
	 * @return posicaoAntiga*/
	private Posicao encontrarPosicaoPersonagem(){
		Posicao posicao = null;
		
			// Personagem na Grama
			 if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUP)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUP);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWN)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWN);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFT)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFT);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHT)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHT);
			 }
			 
			 // Personagem na Agua
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPWATER)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPWATER);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNWATER);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTWATER);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTWATER);
			 }
			 
			 // Personagem na Terra
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPDIRTY)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPDIRTY);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNDIRTY)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNDIRTY);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTDIRTY)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTDIRTY);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTDIRTY)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTDIRTY);
			 }
		return posicao;
		
	}
	
	/**
	 * Método responsável por retornar o elemento correto da posicao antiga,
	 * depois do movimento
	 * @param Posicao posicaoAntiga
	 * @return Elemento*/
	private Elemento retornarElementoDaPosicaoAntiga(Posicao posicaoAntiga){
		Elemento resultante = null;
		// CASO GRAMA
		if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM){
			resultante =  Elemento.GRAMA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP){
			resultante =  Elemento.GRAMA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN){
			resultante =  Elemento.GRAMA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT){
			resultante =  Elemento.GRAMA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT){
			resultante =  Elemento.GRAMA;
		}
		//CASO AGUA
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER){
			resultante =  Elemento.AGUA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER){
			resultante =  Elemento.AGUA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER){
			resultante =  Elemento.AGUA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER){
			resultante =  Elemento.AGUA;
		}
		//CASO TERRA
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY){
			resultante =  Elemento.TERRA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY){
			resultante =  Elemento.TERRA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY){
			resultante =  Elemento.TERRA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY){
			resultante =  Elemento.TERRA;
		}
		
		return resultante;
	}

	/**
	 * Método responsável por calcular as possibilidades de locomoção no mapa,
	 * ele efetua o possivel elemento a ser retornado de acordo com a direcao
	 * selecionada
	 * @param Posicao posicaoNova, Posicao posicaoAntiga, Direcao d
	 * @return Elemento*/
	private Elemento retornarElementoDaPosicaoNova(Posicao posicaoNova, Posicao posicaoAntiga, Direcao d){
		Elemento elementoPosicaoNova = null;
		Elemento resultante = null;
		tabuleiro.elementoEm(posicaoNova);
		
		
		if((elementoPosicaoNova == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEM)){
			resultante = Elemento.PERSONAGEMDOWNWATER;
		}
		else if(Direcao.CIMA == d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUP;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUP;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUP;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUP;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			//PAREDE
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PAREDE;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.adicionarVida() == true){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.PERSONAGEMUP;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.PERSONAGEMUPWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.PERSONAGEMUPDIRTY;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.PERSONAGEMUP;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.PERSONAGEMUPWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.PERSONAGEMUPDIRTY;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.PERSONAGEMUP;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.PERSONAGEMUPWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.PERSONAGEMUPDIRTY;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.PERSONAGEMUP;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.PERSONAGEMUPWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.PERSONAGEMUPDIRTY;
					}
				}
				else if(hud.adicionarVida() == false){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.VIDA;
					}
				}
			}
			//PASSAGEM
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PASSAGEM;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PASSAGEM;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PASSAGEM;
			}
				//direita
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PASSAGEM;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PASSAGEM) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PASSAGEM;
			}
		}
		
		
		else if(Direcao.BAIXO == d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			//PAREDE
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PAREDE;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.adicionarVida() == true){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.PERSONAGEMDOWN;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.PERSONAGEMDOWNWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.PERSONAGEMDOWNDIRTY;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.PERSONAGEMDOWN;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.PERSONAGEMDOWNWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.PERSONAGEMDOWNDIRTY;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.PERSONAGEMDOWN;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.PERSONAGEMDOWNWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.PERSONAGEMDOWNDIRTY;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.PERSONAGEMDOWN;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.PERSONAGEMDOWNWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.PERSONAGEMDOWNDIRTY;
					}
				}
				else if(hud.adicionarVida() == false){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		else if(Direcao.ESQUERDA == d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			//PAREDE
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PAREDE;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.adicionarVida() == true){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.PERSONAGEMLEFT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.PERSONAGEMLEFTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.PERSONAGEMLEFTDIRTY;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.PERSONAGEMLEFT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.PERSONAGEMLEFTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.PERSONAGEMLEFTDIRTY;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.PERSONAGEMLEFT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.PERSONAGEMLEFTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.PERSONAGEMLEFTDIRTY;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.PERSONAGEMLEFT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.PERSONAGEMLEFTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.PERSONAGEMLEFTDIRTY;
					}
				}
				else if(hud.adicionarVida() == false){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		
		else if(Direcao.DIREITA== d){
			// AGUA
				//cima
			if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			    //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			     //esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			      //direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			//GRAMA
				 //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			     //baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			//TERRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			//PAREDE
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PAREDE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PAREDE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PAREDE) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PAREDE;
			}
			//PONTOS
			    //cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.MACA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.adicionarVida() == true){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.PERSONAGEMRIGHT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.PERSONAGEMRIGHTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTY;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.PERSONAGEMRIGHT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.PERSONAGEMRIGHTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTY;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.PERSONAGEMRIGHT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.PERSONAGEMRIGHTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTY;
					}
					//direita
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.PERSONAGEMRIGHT;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.PERSONAGEMRIGHTWATER;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTY;
					}
				}
				else if(hud.adicionarVida() == false){
					//cima
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.VIDA;
					}
					//baixo
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
						resultante = Elemento.VIDA;
					}
					//esquerda
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
						resultante = Elemento.VIDA;
					}
					//direita
					if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
						resultante = Elemento.VIDA;
					}
				}
			}
		}
		
		
		return resultante;
		
	}
	
	/**
	 * Método que verifica se a posição é invalida
	 * @param Posicao p*/
	private boolean posicaoEhInvalida(Posicao p) {
		return p.getLinha() < 0 || p.getLinha() >= tabuleiro.getNumeroLinhas()
				|| p.getColuna() < 0 || p.getColuna() >= tabuleiro.getNumeroColunas();
	}
}
