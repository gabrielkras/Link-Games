package logicajogo;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe HashMap, Estrutura de dados que permite armazenar informações
 * de qualquer tipo (Object). Permite recuperar as informações através
 * de uma chave unica gerada automaticamente para cada elemento
 * @author Francisco Gonçalves da Mota Longhini 
 * @author Gabriel Sousa Kraszczuk*/
public class HashMap {
	
	private Object tabelaHash[][];
	private int tamanhoTabelaHash;
	/**
	 * Construtor, Recebe um tamanho definido para a estrutura da
	 * tabela Hash
	 * @param int tamanhoTotal*/
	public HashMap(int tamanhoTotal){
		this.tabelaHash = new Object[tamanhoTotal][];
		this.tamanhoTabelaHash = tamanhoTotal;
	}
	/**
	 * Armazena Objetos na tabela e retorna a sua respectiva chave.
	 * @param Object objeto
	 * @return Float*/
	public float armazenarObjeto(Object objeto){
		String objetoA = objeto.toString();
		int chave = gerarChave(objetoA);
		if(contemObjeto(objetoA, chave) == true){
			List<Object> listaTemp = new ArrayList<Object>();
			int quantidadeElementos = 0;
			for(quantidadeElementos=0; ;quantidadeElementos++){
				try{
					listaTemp.add(tabelaHash[chave][quantidadeElementos]);
				}
				catch(ArrayIndexOutOfBoundsException e){
					break;
				}
			}
			listaTemp.add(objeto);
			tabelaHash[chave] = new Object[quantidadeElementos+1];
			for(int k=0; k<=quantidadeElementos; k++){
				tabelaHash[chave][k] = listaTemp.get(k);
			}
			listaTemp = null;
			float chaveFinal = (float) ((chave) + ((quantidadeElementos)*0.1));
			return chaveFinal;
		}
		else{
			tabelaHash[chave] = new Object[1];
			tabelaHash[chave][0] = objeto;
			return chave;
		}
	}
	/**
	 * Remove um objeto da tabela.
	 * @param float chave*/
	public void removerObjeto(float chave){
		try{
			int chaveInteira = (int)chave;
			int chaveSecundaria =  (int) ((chave - chaveInteira)*10);
			tabelaHash[chaveInteira][chaveSecundaria]= null;
		}
		catch(Exception e){
		}
	}
	/**
	 * Efetua a Geração da Chave
	 * @param String objeto
	 * @return float*/
	private int gerarChave(String objeto){
		int tamanhoObjeto = objeto.length();
		int codigo = 0;
		for(int k=0; k<tamanhoObjeto; k++){
			codigo += objeto.charAt(k)+objeto.charAt(tamanhoObjeto-1)+((k+12));
		}
		return codigo%tamanhoTabelaHash;
	}
	/**
	 * Verifica se o objeto, com a respectiva chave já foi inserida
	 * na tabela.
	 * @param String objeto
	 * @param int chave
	 * @return boolean*/
	private boolean contemObjeto(String objeto, int chave){
		try{
			if(objeto.equals((String)tabelaHash[chave][0])== true){
				return true;
			}
			else{
				return false;
			}
		}
		catch(NullPointerException e){
			return false;
		}
	}
	/**
	 * Retorna o objeto com base na chave inserida
	 * @param float chave
	 * @return Object*/
	public Object retornarObjeto(float chave){
		try{
			int chaveInteira = (int)chave;
			int chaveSecundaria =  (int) ((chave - chaveInteira)*10);
			return tabelaHash[chaveInteira][chaveSecundaria];
		}
		catch(Exception e){
			return null;
		}
	}
}
