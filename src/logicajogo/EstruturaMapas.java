package logicajogo;

public class EstruturaMapas {
	
	private EstruturaMapas cima = null;
	private EstruturaMapas baixo = null;
	private EstruturaMapas esquerda = null;
	private EstruturaMapas direita = null;
	private Mapa mapaAtual = null;
	
	public EstruturaMapas(Mapa mapaInicial){
		this.mapaAtual = mapaInicial;
	}
	/**
	 * Adiciona um mapa Acima do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaCima(Mapa mapa){
		this.cima = new EstruturaMapas(mapa);
		this.cima.adicionarMapaCimaVolta(this);
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * Acima, neste caso, abaixo.
	 * @param EstruturaMapas estruturaMapas*/
	public void adicionarMapaCimaVolta(EstruturaMapas estruturaMapas){
		this.baixo = estruturaMapas;
	}
	/**
	 * Adiciona um mapa Abaixo do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaAbaixo(Mapa mapa){
		this.baixo = new EstruturaMapas(mapa);
		this.baixo.adicionarMapaBaixoVolta(this);
		
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * Abaixo, neste caso, acima.
	 * @param EstruturaMapas estruturaMapas*/
	public void adicionarMapaBaixoVolta(EstruturaMapas estruturaMapas){
		this.cima = estruturaMapas;
	}
	/**
	 * Adiciona um mapa a esquerda do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaEsquerda(Mapa mapa){
		this.esquerda = new EstruturaMapas(mapa);
		this.esquerda.adicionarMapaEsquerdaVolta(this);
		
	}
	public void adicionarMapaEsquerda(EstruturaMapas estruturaMapas){
		this.esquerda = estruturaMapas;
		
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * a esquerda, neste caso, a direita.
	 * @param EstruturaMapas estruturaMapas*/
	public void adicionarMapaEsquerdaVolta(EstruturaMapas estruturaMapas){
		this.direita = estruturaMapas;
	}
	/**
	 * Adiciona um mapa a direita do Mapa Atual
	 * @param Mapa mapa*/
	public void adicionarMapaDireita(Mapa mapa){
		this.direita = new EstruturaMapas(mapa);
		this.direita.adicionarMapaDireitaVolta(this);
		
	}
	/**
	 * Adiciona a Estrutura de Retorno do mapa Adicionado
	 * a direita, neste caso, a esquerda.
	 * @param EstruturaMapas estruturaMapas*/
	public void adicionarMapaDireitaVolta(EstruturaMapas estruturaMapas){
		this.esquerda = estruturaMapas;
		//adicionarMapaEsquerda(estruturaMapas);
	}
	/**
	 * Permite navegar para o mapa CIMA do atual, retornando 
	 * sua estrutura
	 * @return EstruturaMapas
	 * @return null*/
	public EstruturaMapas navegarParaCima(){
		if(this.cima != null){
			return this.cima;
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
	public EstruturaMapas navegarParaBaixo(){
		if(this.baixo != null){
			return this.baixo;
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
	public EstruturaMapas navegarParaEsquerda(){
		if(this.esquerda != null){
			return this.esquerda;
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
	public EstruturaMapas navegarParaDireita(){
		if(this.direita != null){
			return this.direita;
		}
		else{
			return null;
		}
	}
	/**
	 * Retorna somente o objeto CIMA, do atual.
	 * @return EstruturaMapas*/
	public EstruturaMapas getCima(){
		return this.cima;
	}
	
	/**
	 * Retorna somente o objeto BAIXO, do atual.
	 * @return EstruturaMapas*/
	public EstruturaMapas getBaixo(){
		return this.baixo;
	}
	
	/**
	 * Retorna somente o objeto ESQUERDA, do atual.
	 * @return EstruturaMapas*/
	public EstruturaMapas getEsquerda(){
		return this.esquerda;
	}
	
	/**
	 * Retorna somente o objeto DIREITA, do atual.
	 * @return EstruturaMapas*/
	public EstruturaMapas getDireita(){
		return this.direita;
	}
	
	/**
	 * Retorna o objeto Mapa da Estrutura Atual.
	 * @return EstruturaMapas*/
	public Mapa getMapaAtual(){
		return this.mapaAtual;
	}
}
