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
	private String mensagemPlaca;
	
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
		this.listaMapa.add(mapa);
		this.indiceMapaAtual = 0;
	}
	/**
	 * Adiciona uma mensagem a placa do mapa*/
	public void adicionarMensagemMapa(String mensagem){
		this.mensagemPlaca = mensagem;
	}
	/**
	 * Recupera a mensagem do mapa*/
	public String lerMensagem(){
		return this.mensagemPlaca;
	}
	/**
	 * Realiza a troca do mapa, avançando para o proximo da lista*/
	public void avancarUmMapa(){
		indiceMapaAtual++;
		this.mapa = this.listaMapa.get(indiceMapaAtual);
	}
	/**
	 * Realiza a Troca do Mapa, retrocedendo para o mapa anterior*/
	public void retrocederUmMapa(){
		indiceMapaAtual--;
		this.mapa = this.listaMapa.get(indiceMapaAtual);
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
	
	public int getIndiceDoMapaAtual(){
		return indiceMapaAtual;
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
				if(mapa[k][l]==Elemento.RUBI){
					totalDePontosNoMapa++;
				}
			}
		}
		int tamanhoLista = listaMapa.size();
		for(int k = 0; k < tamanhoLista; k++){
			Elemento[][] map = listaMapa.get(k);
			for(int g = 0; g < linha; g++){
				for(int s = 0; s < coluna; s++){
					if(map[g][s]==Elemento.RUBI){
						totalDePontosNoMapa++;
					}
				}
			}
		}
	}
	
}
