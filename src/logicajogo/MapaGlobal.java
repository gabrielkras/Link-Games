package logicajogo;
/**
 * Classe responsável por permitir o mapeamento das estrutura de mapa,
 * afim de facilitar na locomoção entre os mapas solicitados
 * @author Francisco Gonçalves da Mota Longhini
 * @author Gabriel Sousa Kraszczuk*/
public class MapaGlobal {
	
	private MapaGlobal mapaCima = null;
	private MapaGlobal mapaBaixo = null;
	private MapaGlobal mapaEsquerda = null;
	private MapaGlobal mapaDireita = null;
	private Mapa mapa = null;
	
	public MapaGlobal(Mapa mapaInicial){
		this.mapa = mapaInicial;
	}
	/**
	 * Adiciona um mapa Acima do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaCima(Mapa mapa){
		this.mapaCima = new MapaGlobal(mapa);
		this.mapaCima.adicionarMapaCimaVolta(this);
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * Acima, neste caso, abaixo.
	 * @param MapaGlobal estruturaMapas*/
	public void adicionarMapaCimaVolta(MapaGlobal estruturaMapas){
		this.mapaBaixo = estruturaMapas;
	}
	/**
	 * Adiciona um mapa Abaixo do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaAbaixo(Mapa mapa){
		this.mapaBaixo = new MapaGlobal(mapa);
		this.mapaBaixo.adicionarMapaBaixoVolta(this);
		
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * Abaixo, neste caso, acima.
	 * @param MapaGlobal estruturaMapas*/
	public void adicionarMapaBaixoVolta(MapaGlobal estruturaMapas){
		this.mapaCima = estruturaMapas;
	}
	/**
	 * Adiciona um mapa a esquerda do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaEsquerda(Mapa mapa){
		this.mapaEsquerda = new MapaGlobal(mapa);
		this.mapaEsquerda.adicionarMapaEsquerdaVolta(this);
		
	}
	public void adicionarMapaEsquerda(MapaGlobal estruturaMapas){
		this.mapaEsquerda = estruturaMapas;
		
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * a esquerda, neste caso, a direita.
	 * @param MapaGlobal estruturaMapas*/
	public void adicionarMapaEsquerdaVolta(MapaGlobal estruturaMapas){
		this.mapaDireita = estruturaMapas;
	}
	/**
	 * Adiciona um mapa a direita do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaDireita(Mapa mapa){
		this.mapaDireita = new MapaGlobal(mapa);
		this.mapaDireita.adicionarMapaDireitaVolta(this);
		
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * a direita, neste caso, a esquerda.
	 * @param MapaGlobal estruturaMapas*/
	public void adicionarMapaDireitaVolta(MapaGlobal estruturaMapas){
		this.mapaEsquerda = estruturaMapas;
		//adicionarMapaEsquerda(estruturaMapas);
	}
	/**
	 * Permite navegar para o mapa CIMA do atual, retornando 
	 * sua estrutura
	 * @return EstruturaMapas
	 * @return null*/
	public MapaGlobal navegarParaCima(){
		if(this.mapaCima != null){
			return this.mapaCima;
		}
		else{
			return null;
		}
	}
	/**
	 * Permite navegar para o mapa BAIXO do atual, retornando 
	 * sua estrutura
	 * @return EstruturaMapas
	 * @return null*/
	public MapaGlobal navegarParaBaixo(){
		if(this.mapaBaixo != null){
			return this.mapaBaixo;
		}
		else{
			return null;
		}
	}
	/**
	 * Permite navegar para o mapa ESQUERDA do atual, retornando 
	 * sua estrutura
	 * @return EstruturaMapas
	 * @return null*/
	public MapaGlobal navegarParaEsquerda(){
		if(this.mapaEsquerda != null){
			return this.mapaEsquerda;
		}
		else{
			return null;
		}
	}
	/**
	 * Permite navegar para o mapa DIREITA do atual, retornando 
	 * sua estrutura
	 * @return EstruturaMapas
	 * @return null*/
	public MapaGlobal navegarParaDireita(){
		if(this.mapaDireita != null){
			return this.mapaDireita;
		}
		else{
			return null;
		}
	}
	public MapaGlobal getMapaCima(){
		return this.mapaCima;
	}
	
	public MapaGlobal getMapaBaixo(){
		return this.mapaBaixo;
	}
	
	public MapaGlobal getMapaEsquerda(){
		return this.mapaEsquerda;
	}
	
	public MapaGlobal getMapaDireita(){
		return this.mapaDireita;
	}
	
	public Mapa getMapa(){
		return this.mapa;
	}
}
