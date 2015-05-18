package logicajogo;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.oa.toa.TOA;

/**
 * A classe mapa é responsável por:
 * - Gerenciar Mapa com Multiplas Salas
 * - Gerenciar a troca de Icones no Mapa
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
	
	public Mapa(Elemento[][] mapa){
		
		this.mapa = mapa;
		listaMapa.add(mapa);
		linha = mapa.length;
		coluna = mapa[0].length;
		calcularQuantidadeTotalDePontosNoMapa();
		armazenarPosicaoPortalMapa();
		System.out.println(totalDePontosNoMapa);
	}
	
	/**
	 * Método que retorna o mapa atual que esta sendo utilizado*/
	public Elemento[][] getMapa(){
		return this.mapa;
	}
	
	public int getLinha(){
		return linha;
	}
	
	public int getColuna(){
		return coluna;
	}
	/**
	 * Adiciona Mapas que compõem as salas extras do mapa principal*/
	public void adicionarMapas(Elemento[][] mapa){
		listaMapa.add(mapa);
	}
	/**
	 * Realiza a troca do mapa, avançando para o proximo da lista*/
	public void avancarUmMapa(){
		indiceMapaAtual++;
		this.mapa = listaMapa.get(indiceMapaAtual);
	}
	/**
	 * Realiza a Troca do Mapa, retrocedendo para o mapa anterior*/
	public void retrocederUmMapa(){
		indiceMapaAtual--;
		this.mapa = listaMapa.get(indiceMapaAtual);
	}
	
	/**
	 * Retorna a quantidade total de "Macas" do mapa*/
	public int obterQuantidadeTotalDePontosNoMapa(){
		return totalDePontosNoMapa-1;
	}
	/**
	 * Verifica se o mapa atual, possui ou não um portal*
	 * @return boolean
	 */
	public boolean temPortalNoMapa(){
		try{
			posicaoPortais.get(indiceMapaAtual);
			return true;
		}
		catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * Função responsável por retornar a posicao do portal
	 * no mapa atual
	 * @return Posicao*/
	public Posicao obterPosicaoPortalMapa(){
			return posicaoPortais.get(indiceMapaAtual);
		
	}
	/**
	 * Efetua a varredura de todos os mapas e armazena as posições dos portais*/
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
	
	/**
	 * Realiza o calculo da quantidade total de macas que o mapa possui*/
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
	
	/**
	 * Método responsável por encontrar a posição do personagem no mapa
	 * @param Direcao d
	 * @return posicaoAntiga*/
	
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
		
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMUPDIRTY){
			return Elemento.TERRA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMDOWNDIRTY){
			return Elemento.TERRA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMLEFTDIRTY){
			return Elemento.TERRA;
		}
		else if(tab.elementoEm(pos) == Elemento.PERSONAGEMRIGHTDIRTY){
			return Elemento.TERRA;
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
	
	// Permite devolver um possivel personagem a sua posição antiga, no caso de ser parede
	
	public Elemento checarElementoPosicao(Posicao pos, Posicao pos2, Direcao d){
		Tabuleiro tab = new Tabuleiro(new Mapa(mapa));
		
		if(d == Direcao.CIMA){
			if((tab.elementoEm(pos) == Elemento.AGUA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMUPWATER;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMUP;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.TERRA)){
				return Elemento.PERSONAGEMUPDIRTY;
			}
		}
		else if(d == Direcao.BAIXO){
			if((tab.elementoEm(pos) == Elemento.AGUA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMDOWNWATER;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMDOWN;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.TERRA)){
				return Elemento.PERSONAGEMDOWNDIRTY;
			}
		}
		
		else if(d == Direcao.DIREITA){
			if((tab.elementoEm(pos) == Elemento.AGUA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMRIGHTWATER;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMRIGHT;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.TERRA)){
				return Elemento.PERSONAGEMRIGHTDIRTY;
			}
		}
		
		else if(d == Direcao.ESQUERDA){
			if((tab.elementoEm(pos) == Elemento.AGUA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMLEFTWATER;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.PAREDE)){
				return Elemento.PERSONAGEMLEFT;
			}
			else if((tab.elementoEm(pos) == Elemento.GRAMA) && (tab.elementoEm(pos2)== Elemento.TERRA)){
				return Elemento.PERSONAGEMLEFTDIRTY;
			}
		}
		return tab.elementoEm(pos); 
	}

}
