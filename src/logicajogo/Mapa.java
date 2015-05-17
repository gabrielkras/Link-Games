package logicajogo;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.oa.toa.TOA;

/**
 * Classe Responsável por gerenciar os mapas
 *  
 * */
public class Mapa {
	
	private Elemento[][] mapa;
	private List<Elemento[][]> listaMapa = new ArrayList<Elemento[][]>();
	private List<Posicao> posicaoPortais = new ArrayList<Posicao>();
	private int indiceMapaAtual = 0;
	private int linha;
	private int coluna;
	private int totalDePontosNoMapa = 0;
	private static int proxMapa;
	
	public Mapa(Elemento[][] mapa){
		
		this.mapa = mapa;
		listaMapa.add(mapa);
		linha = mapa.length;
		coluna = mapa[0].length;
		proxMapa = listaMapa.size();
		calcularQuantidadeTotalDePontosNoMapa();
		armazenarPosicaoPortalMapa();
		System.out.println(totalDePontosNoMapa);
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
	public void avancarUmMapa(){
		indiceMapaAtual++;
		this.mapa = listaMapa.get(indiceMapaAtual);
	}
	
	public void retrocederUmMapa(){
		indiceMapaAtual--;
		this.mapa = listaMapa.get(indiceMapaAtual);
	}
	
	public int getproxMapa(){
		return proxMapa;
	}
	
	public int setproxMapa(int index){
		return proxMapa = index;
	}
	
	public int obterQuantidadeTotalDePontosNoMapa(){
		return totalDePontosNoMapa-1;
	}
	
	public boolean temPortalNoMapa(){
		try{
			posicaoPortais.get(indiceMapaAtual);
			return true;
		}
		catch(Exception e){
			return false;
		}
		
	}
	
	public Posicao obterPosicaoPortalMapa(){
			return posicaoPortais.get(indiceMapaAtual);
		
	}
	
	private void armazenarPosicaoPortalMapa(){
		int tamanhoLista = listaMapa.size();
		for(int k = 0; k < tamanhoLista; k++){
			Elemento[][] map = listaMapa.get(k);
			for(int g = 0; g < linha; g++){
				for(int s = 0; s < coluna; s++){
					if(map[g][s]==Elemento.PORTAL){
						posicaoPortais.add(new Posicao(g,s));
					}
				}
			}
		}
		if(posicaoPortais.isEmpty()){
			for(int k = 0; k < linha; k++){
				for(int l = 0; l < coluna; l++){
					if(mapa[k][l]==Elemento.PORTAL){
						posicaoPortais.add(new Posicao(k,l));
					}
				}
			}
		}
	}
	
	private void calcularQuantidadeTotalDePontosNoMapa(){
		for(int k = 0; k < linha; k++){
			for(int l = 0; l < coluna; l++){
				if(mapa[k][l]==Elemento.MACA){
					totalDePontosNoMapa++;
				}
			}
		}
		int tamanhoLista = listaMapa.size();
		for(int k = 0; k < tamanhoLista; k++){
			Elemento[][] map = listaMapa.get(k);
			for(int g = 0; g < linha; g++){
				for(int s = 0; s < coluna; s++){
					if(map[g][s]==Elemento.MACA){
						totalDePontosNoMapa++;
					}
				}
			}
		}
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
		
		else if(tab.elementoEm(pos) == Elemento.VIDA){
			return Elemento.VIDA;
		}
		
		//else if(tab.elementoEm(pos) == Elemento.PORTAL){
			//return Elemento.GRAMA;
		//}
		
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMUPWATER){
			return Elemento.AGUA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMDOWNWATER){
			return Elemento.AGUA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMLEFTWATER){
			return Elemento.AGUA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMRIGHTWATER){
			return Elemento.AGUA;
		}
		else{
			return tab.elementoEm(pos);
		}
	}

}
