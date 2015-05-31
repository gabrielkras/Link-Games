package logicajogo;

/**
 * Classe responsável por efetuar o gerenciamento de movimentação dos mapas
 * considerando os elementos dispostos no mapa
 * @author Francisco Gonçalves da Mota Longhini
 * @author Gabriel Sousa Kraszczuk*/
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
	 * Método utilizado para atualizar a estrutura de mapa atual sendo executada pelo
	 * tabuleiro
	 * @param*/
	public void setEstruturaMapa(EstruturaMapas estrutura){
		this.estruturaMapas = estrutura;
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
				
			case LAVA:
				hud.removerVida();
				if(hud.getVida() <= 0){
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
			case TERRACAVERNA1:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case TERRACAVERNA2:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case PEDRA:
				tabuleiro.alterarElemento(posicaoNova, elementoPosicaoNova);
				tabuleiro.alterarElemento(posicaoAntiga, elementoPosicaoAntiga);
				break;
			case PLACA:
				saida.mostrarMensagem(estruturaMapas.getMapaAtual().lerMensagem(), "Inforação Placa!",estruturaMapas.getMapaAtual().obterIconeMensagem());
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
			 
			// Personagem na Terra de Caverna
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPDIRTYCAVE1)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPDIRTYCAVE1);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNDIRTYCAVE1)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNDIRTYCAVE1);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTDIRTYCAVE1)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTDIRTYCAVE1);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTDIRTYCAVE1)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTDIRTYCAVE1);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPDIRTYCAVE2)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPDIRTYCAVE2);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNDIRTYCAVE2)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNDIRTYCAVE2);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTDIRTYCAVE2)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTDIRTYCAVE2);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTDIRTYCAVE2)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTDIRTYCAVE2);
			 }
			 
			 // Personagem na Pedra
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPSTONE)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMUPSTONE);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNSTONE)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMDOWNSTONE);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTSTONE)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMLEFTSTONE);
			 }
			 else if(tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTSTONE)!= null){
				 posicao = tabuleiro.acharPosicaoDe(Elemento.PERSONAGEMRIGHTSTONE);
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
		//CASO TERRA DE CAVERNA
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1){
			resultante =  Elemento.TERRACAVERNA1;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1){
			resultante =  Elemento.TERRACAVERNA1;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1){
			resultante =  Elemento.TERRACAVERNA1;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1){
			resultante =  Elemento.TERRACAVERNA1;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2){
			resultante =  Elemento.TERRACAVERNA2;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2){
			resultante =  Elemento.TERRACAVERNA2;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2){
			resultante =  Elemento.TERRACAVERNA2;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2){
			resultante =  Elemento.TERRACAVERNA2;
		}
		//CASO PEDRA
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE){
			resultante =  Elemento.PEDRA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE){
			resultante =  Elemento.PEDRA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE){
			resultante =  Elemento.PEDRA;
		}
		else if(tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE){
			resultante =  Elemento.PEDRA;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUP;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTY;
			}
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMUPSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
						resultante = Elemento.PERSONAGEMUPSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
						resultante = Elemento.PERSONAGEMUPSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
						resultante = Elemento.PERSONAGEMUPSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMUPDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
						resultante = Elemento.PERSONAGEMUPSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
						resultante = Elemento.VIDA;
					}
				}
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWN;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTY;
			}
			
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMDOWNSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
						resultante = Elemento.PERSONAGEMDOWNSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
						resultante = Elemento.PERSONAGEMDOWNSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
						resultante = Elemento.PERSONAGEMDOWNSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMDOWNDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
						resultante = Elemento.PERSONAGEMDOWNSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTY;
			}
			
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMLEFTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
						resultante = Elemento.PERSONAGEMLEFTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
						resultante = Elemento.PERSONAGEMLEFTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
						resultante = Elemento.PERSONAGEMLEFTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMLEFTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
						resultante = Elemento.PERSONAGEMLEFTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.AGUA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHT;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.GRAMA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTY;
			}
			
			//TERRA DE CAVERNA 1
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA1) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			
			//TERRA DE CAVERNA 2
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.TERRACAVERNA2) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			
			//PEDRA
				//cima
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUP)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPWATER)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
				//baixo
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWN)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNWATER)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
				//esquerda
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFT)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
				//direita
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHT)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTWATER)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTY)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
			}
			else if((tabuleiro.elementoEm(posicaoNova)== Elemento.PEDRA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
				resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
			}
			else if((tabuleiro.elementoEm(posicaoNova) == Elemento.RUBI) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
				resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
						resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
						resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
						resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE1;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.PERSONAGEMRIGHTDIRTYCAVE2;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
						resultante = Elemento.PERSONAGEMRIGHTSTONE;
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMUPSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMDOWNSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMLEFTSTONE)){
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
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE1)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTDIRTYCAVE2)){
						resultante = Elemento.VIDA;
					}
					else if((tabuleiro.elementoEm(posicaoNova) == Elemento.VIDA) && (tabuleiro.elementoEm(posicaoAntiga) == Elemento.PERSONAGEMRIGHTSTONE)){
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
