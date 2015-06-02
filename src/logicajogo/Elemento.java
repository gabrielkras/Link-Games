package logicajogo;

/**
 * Classe que cria os elementos do jogo, que poderão ser utilizados nos mapas
 * @author Francisco Gonçalves da Mota Longhini
 * @author Gabriel Sousa Kraszczuk*/
public enum Elemento {
	//ELEMENTOS DO MAPA(AMBIENTE)
	// caverna
	PAREDECAVERNACANTOSUPERIORDIREITO("/canto_caverna.jpg",false),
	PAREDECAVERNACANTOINFERIORDIREITO("/canto_caverna2.jpg",false),
	PAREDECAVERNACANTOINFERIORESQUERDO("/canto_caverna3.jpg",false),
	PAREDECAVERNACANTOSUPERIORESQUERDO("/canto_caverna4.jpg",false),
	PAREDECAVERNAMEIOESQUERDO("/meio_lateral2_caverna.jpg",false),
	PAREDECAVERNAMEIODIREITO("/meio_lateral1_caverna.jpg",false),
	PAREDECAVERNAMEIOSUPERIOR("/meio_superior_caverna.jpg",false),
	PAREDECAVERNAMEIOINFERIOR("/meio_inferior_caverna.jpg",false),
	TERRACAVERNA1("/chao_caverna.jpg",true),
	TERRACAVERNA2("/chao_caverna2.jpg",true),
	
	// normal
	PAREDEGRAMACANTOSUPERIORDIREITO("/grama_canto_superior_direito.jpg",false),
	PAREDEGRAMACANTOINFERIORDIREITO("/grama_canto_inferior_direito.jpg",false),
	PAREDEGRAMACANTOINFERIORESQUERDO("/grama_canto_inferior_esquerdo.jpg",false),
	PAREDEGRAMACANTOSUPERIORESQUERDO("/grama_canto_superior_esquerdo.jpg",false),
	PAREDEGRAMAMEIOESQUERDO("/",false),
	PAREDEGRAMAMEIODIREITO("/",false),
	PAREDEGRAMAMEIOSUPERIOR("/grama_meio_superior.jpg",false),
	PAREDEGRANAMEIOINFERIOR("/grama_meio_inferior.jpg",false),
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
	PERSONAGEMUPDIRTY("/personagemupdirty.jpg",false),
	PERSONAGEMDOWNDIRTY("/personagemdowndirty.jpg",false),
	PERSONAGEMLEFTDIRTY("/personagemleftdirty.jpg",false),
	PERSONAGEMRIGHTDIRTY("/personagemrightdirty.jpg",false),
	// terra de caverna1
	PERSONAGEMUPDIRTYCAVE1("/personagemupcave.jpg",false),
	PERSONAGEMDOWNDIRTYCAVE1("/personagemdowncave.jpg",false),
	PERSONAGEMLEFTDIRTYCAVE1("/personagemleftcave.jpg",false),
	PERSONAGEMRIGHTDIRTYCAVE1("/personagemrightcave.jpg",false),
	// terra de caverna2
	PERSONAGEMUPDIRTYCAVE2("/maca.png",false),
	PERSONAGEMDOWNDIRTYCAVE2("/maca.png",false),
	PERSONAGEMLEFTDIRTYCAVE2("/maca.png",false),
	PERSONAGEMRIGHTDIRTYCAVE2("/maca.png",false),
	// pedra
	PERSONAGEMUPSTONE("/personagemupstone.jpg",false),
	PERSONAGEMDOWNSTONE("/personagemdownstone.jpg",false),
	PERSONAGEMLEFTSTONE("/personagemleftstone.jpg",false),
	PERSONAGEMRIGHTSTONE("/personagemrightstone.jpg",false),
	
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
