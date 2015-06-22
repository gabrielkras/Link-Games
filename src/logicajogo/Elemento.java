package logicajogo;

public enum Elemento {
	//ELEMENTOS DO MAPA(AMBIENTE)
	// caverna
	PAREDE_CAVERNA_CANTO_SUPERIOR_DIREITO("/canto_caverna.jpg",false),
	PAREDE_CAVERNA_CANTO_INFERIOR_DIREITO("/canto_caverna2.jpg",false),
	PAREDE_CAVERNA_CANTO_INFERIOR_ESQUERDO("/canto_caverna3.jpg",false),
	PAREDE_CAVERNA_CANTO_SUPERIOR_ESQUERDO("/canto_caverna4.jpg",false),
	PAREDE_CAVERNA_MEIO_ESQUERDO("/meio_lateral2_caverna.jpg",false),
	PAREDE_CAVERNA_MEIO_DIREITO("/meio_lateral1_caverna.jpg",false),
	PAREDE_CAVERNA_MEIO_SUPERIOR("/meio_superior_caverna.jpg",false),
	PAREDE_CAVERNA_MEIO_INFERIOR("/meio_inferior_caverna.jpg",false),
	TERRA_CAVERNA1("/chao_caverna.jpg",true),
	TERRA_CAVERNA2("/chao_caverna2.jpg",true),
	
	// normal
	PAREDE_GRAMA_CANTO_SUPERIOR_DIREITO("/grama_canto_superior_direito.jpg",false),
	PAREDE_GRAMA_CANTO_INFERIOR_DIREITO("/grama_canto_inferior_direito.jpg",false),
	PAREDE_GRAMA_CANTO_INFERIOR_ESQUERDO("/grama_canto_inferior_esquerdo.jpg",false),
	PAREDE_GRAMA_CANTO_SUPERIOR_ESQUERDO("/grama_canto_superior_esquerdo.jpg",false),
	PAREDE_GRAMA_MEIO_ESQUERDO("/",false),
	PAREDE_GRAMA_MEIO_DIREITO("/",false),
	PAREDE_GRAMA_MEIO_SUPERIOR("/grama_meio_superior.jpg",false),
	PAREDE_GRAMA_MEIO_INFERIOR("/grama_meio_inferior.jpg",false),
	AGUA("/agua2.png",true),
	GRAMA("/grama2.png",true),
	LAVA("/lava.png",false),
	TERRA("/terra2.jpg",true),
	PEDRA("/pedra.jpg",true),
	PEDRAENFEITE("/pedra_enfeite.jpg",false),
	PLACA("/placa.jpg",false),
	
	//ELEMENTOS DE PONTUAÇÃO
	RUBI("/rubi.png",true),
	
	//ELEMENTOS / ITENS
	VIDA("/coracao.png",true),
	SEMVIDA("/coracao2.png",true),
	
	//SPRITES PERSONAGEM
	// grama
	PERSONAGEM_GRAMA("/personagemdown.png",false),
	PERSONAGEM_CIMA_GRAMA("/personagemup.png",false),
	PERSONAGEM_BAIXO_GRAMA("/personagemdown.png",false),
	PERSONAGEM_ESQUERDA_GRAMA("/personagemleft.png",false),
	PERSONAGEM_DIREITA_GRAMA("/personagemright.png",false),
	// agua
	PERSONAGEM_CIMA_AGUA("/personagemupwater.png",false),
	PERSONAGEM_BAIXO_AGUA("/personagemdownwater.png",false),
	PERSONAGEM_ESQUERDA_AGUA("/personagemrightwater.png",false),
	PERSONAGEM_DIREITA_AGUA("/personagemleftwater.png",false),
	// terra
	PERSONAGEM_CIMA_TERRA("/personagemupdirty.jpg",false),
	PERSONAGEM_BAIXO_TERRA("/personagemdowndirty.jpg",false),
	PERSONAGEM_ESQUERDA_TERRA("/personagemleftdirty.jpg",false),
	PERSONAGEM_DIREITA_TERRA("/personagemrightdirty.jpg",false),
	// terra de caverna1
	PERSONAGEM_CIMA_TERRA_CAVERNA("/personagemupcave.jpg",false),
	PERSONAGEM_BAIXO_TERRA_CAVERNA("/personagemdowncave.jpg",false),
	PERSONAGEM_ESQUERDA_TERRA_CAVERNA("/personagemleftcave.jpg",false),
	PERSONAGEM_DIREITA_TERRA_CAVERNA("/personagemrightcave.jpg",false),
	// terra de caverna2
	PERSONAGEM_CIMA_TERRA2_CAVERNA("/maca.png",false),
	PERSONAGEM_BAIXO_TERRA2_CAVERNA("/maca.png",false),
	PERSONAGEM_ESQUERDA_TERRA2_CAVERNA("/maca.png",false),
	PERSONAGEM_DIREITA_TERRA2_CAVERNA("/maca.png",false),
	// pedra
	PERSONAGEM_CIMA_PEDRA("/personagemupstone.jpg",false),
	PERSONAGEM_BAIXO_PEDRA("/personagemdownstone.jpg",false),
	PERSONAGEM_ESQUERDA_PEDRA("/personagemleftstone.jpg",false),
	PERSONAGEM_DIREITA_PEDRA("/personagemrightstone.jpg",false),
	
	//DEMAIS ELEMENTOS
	PORTAL("/portal2.png",true),
	PASSAGEM("/entrada caverna.jpg",false),
	PASSAGEMVOLTA("/passagem_volta_caverna.jpg",false),
	PAREDE("/parede1.png",false),
	NADA("/nada.png",false);
	
	private final String caminhoImagem;
	private boolean transponivel;

	Elemento(String caminhoImagem, boolean transponivel) {
		this.caminhoImagem = caminhoImagem;
		this.transponivel = transponivel;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}
	
	public void setElementoEhTransponivel(boolean transponivel){
		this.transponivel = transponivel;
	}
	
	public boolean elementoEhTransponivel(){
		return transponivel;
	}
	
}
