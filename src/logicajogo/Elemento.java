package logicajogo;

public enum Elemento {

	AGUA("/agua2.png"),
	MACA("/rubi.png"),
	PERSONAGEM("/personagemdown.png"),
	PERSONAGEMUP("/personagemup.png"),
	PERSONAGEMDOWN("/personagemdown.png"),
	PERSONAGEMLEFT("/personagemleft.png"),
	PERSONAGEMRIGHT("/personagemright.png"),
	
	PERSONAGEMUPWATER("/personagemupwater.png"),
	PERSONAGEMDOWNWATER("/personagemdownwater.png"),
	PERSONAGEMRIGHTWATER("/personagemrightwater.png"),
	PERSONAGEMLEFTWATER("/personagemleftwater.png"),
	
	GRAMA("/grama2.png"),
	PORTAL("/portal2.png"),
	PASSAGEM("/fundo.png"),
	PASSAGEMVOLTA("/fundo.png"),
	PAREDE("/parede1.png"),
	NADA("/nada.png"),
	
	// PARA O HUD,
	VIDA("/coracao.png"),
	SEMVIDA("/coracao2.png")
	
	;

	private final String caminhoImagem;

	Elemento(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}
	
}
