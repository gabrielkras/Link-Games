package apresentacao;

import static logicajogo.Elemento.*;

import logicajogo.Elemento;
import logicajogo.Tabuleiro;

public class Main {

	public static void main(String[] args) {
		final Elemento[][] disposicaoInicial1 = {
				{GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA},
				{GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA, AGUA},
				{GRAMA, GRAMA, GRAMA, MACA, GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA, GRAMA, GRAMA, GRAMA},
				{PORTAL, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA, GRAMA, GRAMA, GRAMA},
				{GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, GRAMA, GRAMA, GRAMA},
				{GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, MACA},
				{GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, GRAMA},
				{GRAMA, GRAMA, GRAMA, GRAMA, AGUA, AGUA, GRAMA, GRAMA, AGUA, AGUA, AGUA, AGUA, AGUA, AGUA, GRAMA},
				{GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, MACA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA},
				{GRAMA, PERSONAGEM, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA, GRAMA},
		};

		Tabuleiro tabuleiro = new Tabuleiro(disposicaoInicial1);
		tabuleiro.setSaida(new TelaJogo(tabuleiro, new FabricaIcones()));
		tabuleiro.iniciarJogo();
		
		//TelaJogoHUD hud = new TelaJogoHUD(tabuleiro, new FabricaIcones());
		//hud.iniciarJogo();
		
		//Tabuleiro tabuleiro = new Tabuleiro(disposicaoInicial1);
		//tabuleiro.setSaida(new TelaJogoHUD(tabuleiro, new FabricaIcones()));
		//tabuleiro.iniciarJogo();
	}

}