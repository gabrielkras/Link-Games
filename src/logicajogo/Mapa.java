package logicajogo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Responsável por gerenciar os mapas
 *  
 * */
public class Mapa {
	
	private Elemento[][] mapa;
	private List<Elemento[][]> listaMapa = new ArrayList<Elemento[][]>();
	private int linha;
	private int coluna;
	private static int proxMapa;
	
	public Mapa(Elemento[][] mapa){
		
		this.mapa = mapa;
		listaMapa.add(mapa);
		linha = mapa.length;
		coluna = mapa[0].length;
		proxMapa = listaMapa.size();
	}
	
	public Elemento[][] getMapa(){
		return this.mapa;
	}
	
	public int getLinha(){
		return linha;
	}
	
	public int getColuna(){
		return coluna;
	}
	
	public void adicionarMapas(Elemento[][] mapa){
		listaMapa.add(mapa);
	}
	/**
	 * Permite realizar a troca de mapas, trocando o mapa atual "mapa", pelo mapa 
	 * armazenado na lista de mapas 
	 * @param int index*/
	public void trocarMapa(int index){
		this.mapa = listaMapa.get(index);
	}
	
	public void trocarMapa(Elemento[][] mapa){
		this.mapa = mapa;
	}
	
	public int getproxMapa(){
		return proxMapa;
	}
	
	public int setproxMapa(int index){
		return proxMapa = index;
	}
	
	public Elemento checarElementoPosicao(Posicao pos){
		Tabuleiro tab = new Tabuleiro(new Mapa(mapa));
		if(tab.elementoEm(pos) == Elemento.PERSONAGEM){
			return Elemento.GRAMA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMUP){
			return Elemento.GRAMA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMDOWN){
			return Elemento.GRAMA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMLEFT){
			return Elemento.GRAMA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMRIGHT){
			return Elemento.GRAMA;
		}
		else if(tab.elementoEm(pos) == Elemento.PASSAGEM){
			return Elemento.PASSAGEM;
		}
		else{
			return tab.elementoEm(pos);
		}
	}

}
