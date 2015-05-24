package logicajogo;

/**
 * Classe responsável por efetuar o gerenciamento de movimentação dos mapas
 * considerando os elementos dispostos no mapa*/
public class Movimentacao {
	
	private Tabuleiro tabuleiro;
	private EstruturaMapas estruturaMapas;
	private Hud hud;
	private SaidaJogo saida;
	
	public Movimentacao(Tabuleiro tabuleiro, EstruturaMapas estruturaMapas, Hud hud, SaidaJogo saida){
		this.estruturaMapas = estruturaMapas;
		this.tabuleiro = tabuleiro;
		this.saida = saida;
		this.hud = hud;
	}
	/**
	 * Responsável por realizar a movimentação do personagem no mapa, não importando
	 * o tipo de terreno que o mesmo se encontra
	 * @param Direcao d*/	
	public void fazerMovimento(Direcao d, SaidaJogo saida){
		Posicao posicaoAntiga = null;
		Posicao posicaoNova = null;
		Elemento elementoPosicaoNova = null;
		Elemento elementoPosicaoAntiga = null;
		Elemento elementoEmNovaPosicao = null;
		
		posicaoAntiga = encontrarPosicaoPersonagem();
		elementoPosicaoAntiga = tabuleiro.elementoEm(posicaoAntiga);
		
		posicaoNova = posicaoAntiga.somar(d);
		
//		if(tabuleiro.navegar(posicaoNova) == true){
//			posicaoNova = new Posicao(5,0);
//			tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
//			return;
//		}
		
		if(tabuleiro.navegar(posicaoNova,posicaoAntiga,elementoPosicaoAntiga,d)==true) return;
		if (tabuleiro.posicaoEhInvalida(posicaoNova)) return;
		
		elementoEmNovaPosicao = tabuleiro.elementoEm(posicaoNova);
		
		elementoPosicaoNova = retornarElementoDaPosicaoNova(posicaoNova, posicaoAntiga, d);
		elementoPosicaoAntiga = retornarElementoDaPosicaoAntiga(posicaoAntiga);
		
		switch (elementoEmNovaPosicao){
			case AGUA:
				hud.removerVida();
				if(hud.getVida() <= 0){
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
				
			case GRAMA:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case RUBI:
				hud.incrementarPontuacao();
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case PORTAL:
				saida.passarDeFase();
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case VIDA:
				if(hud.vidaEstaCheia() == true){
					break;
				}
				else{
					hud.adicionarVida();
					tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
					tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
					break;
				}
				
			case PASSAGEM:
				tabuleiro.alterarElemento(posicaoNova, Elemento.PASSAGEM);
				estruturaMapas.getMapaAtual().avancarUmMapa();
				tabuleiro.alterarElemento(encontrarPosicaoPersonagem(), tabuleiro.elementoEm(encontrarPosicaoPersonagem()));
				this.saida.recarregarMapa();
				break;
			
			case PASSAGEMVOLTA:
				tabuleiro.alterarElemento(posicaoNova, Elemento.PASSAGEMVOLTA);
				estruturaMapas.getMapaAtual().retrocederUmMapa();
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
	
	
	/**
	 * Método responsável por encontrar a posição do personagem no mapa
	 * @param Direcao d
	 * @return posicaoAntiga*/
	public Posicao encontrarPosicaoPersonagem(){
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
	public Elemento retornarElementoDaPosicaoAntiga(Posicao posicaoAntiga){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.vidaEstaCheia() == false){
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
				else if(!hud.vidaEstaCheia() == true){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.vidaEstaCheia() == false){
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
				else if(hud.vidaEstaCheia() == true){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.vidaEstaCheia() == false){
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
				else if(hud.vidaEstaCheia() == true){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			//VIDA
			else if(tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA){
				if(hud.vidaEstaCheia() == false){
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
				else if(hud.vidaEstaCheia() == true){
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
