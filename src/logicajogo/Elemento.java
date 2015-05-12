package logicajogo;

public enum Elemento {

	AGUA("/agua.png"),
	MACA("/maca.png"),
	PERSONAGEM("/personagem.png"),
	GRAMA("/grama.png"),
	PORTAL("/passagem.png"),
	NADA("/fundo.png"),
	;

	private final String caminhoImagem;

	Elemento(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

}
