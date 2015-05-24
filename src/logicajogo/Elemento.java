package logicajogo;

public enum Elemento {

	
	//ELEMENTOS DO MAPA(AMBIENTE)
	// caverna
	PAREDECAVERNACANTO1("/canto_caverna.jpg",false),
	PAREDECAVERNACANTO2("/canto_caverna2.jpg",false),
	PAREDECAVERNACANTO3("/canto_caverna3.jpg",false),
	PAREDECAVERNACANTO4("/canto_caverna4.jpg",false),
	PAREDECAVERNACANTOESQUERDO("/meio_lateral2_caverna.jpg",false),
	PAREDECAVERNACANTODIREITO("/meio_lateral1_caverna.jpg",false),
	PAREDECAVERNACANTOSUPERIOR("/meio_superior_caverna.jpg",false),
	PAREDECAVERNACANTOINFERIOR("/meio_inferior_caverna.jpg",false),
	TERRACAVERNA("/chao_caverna",true),
	
	// normal
	AGUA("/agua2.png",true),
	GRAMA("/grama2.png",true),
	TERRA("/terra.png",true),
	
	//ELEMENTOS DE PONTUAÇÃO
	RUBI("/rubi.png",true),
	
	//ELEMENTOS / ITENS
	VIDA("/coracao.png",true),
	SEMVIDA("/coracao2.png",true),
	
	//SPTITES PERSONAGEM
	// grama
	PERSONAGEM("/personagemdown.png",false),
	PERSONAGEMUP("/personagemup.png",false),
	PERSONAGEMDOWN("/personagemdown.png",false),
	PERSONAGEMLEFT("/personagemleft.png",false),
	PERSONAGEMRIGHT("/personagemright.png",false),
	// agua
	PERSONAGEMUPWATER("/personagemupwater.png",false),
	PERSONAGEMDOWNWATER("/personagemdownwater.png",false),
	PERSONAGEMRIGHTWATER("/personagemrightwater.png",false),
	PERSONAGEMLEFTWATER("/personagemleftwater.png",false),
	// terra
	PERSONAGEMUPDIRTY("/maca.png",false),
	PERSONAGEMDOWNDIRTY("/maca.png",false),
	PERSONAGEMLEFTDIRTY("/maca.png",false),
	PERSONAGEMRIGHTDIRTY("/maca.png",false),
	
	
	//DEMAIS ELEMENTOS
	PORTAL("/portal2.png",true),
	PASSAGEM("/entrada caverna.jpg",false),
	PASSAGEMVOLTA("/fundo.png",false),
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
