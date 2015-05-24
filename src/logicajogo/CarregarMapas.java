package logicajogo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

public class CarregarMapas {
	
	
	@SuppressWarnings("null")
	public EstruturaMapas carregar() throws FileNotFoundException{
		
		InputStream arquivo = getClass().getResourceAsStream("/fase1.txt");
		Scanner leitor = new Scanner(arquivo);
		
		
		leitor.useDelimiter("\\{|\\-|\\,|\\;|\n");
		String ler;
		
		while(leitor.hasNext()){
			ler = leitor.next();
			switch(ler){
			case "I":
				Elemento[][] mapaInicial = new Elemento[][]{{null}};
				int linha = 0;
				int coluna = 0;
				String leitora;
				while(leitor.hasNext()){
					leitora = leitor.next();
					switch(leitora){
					case "GRAMA":
						mapaInicial[linha][coluna] = Elemento.GRAMA;
						coluna++;
						break;
					case "AGUA":
						mapaInicial[linha][coluna] = Elemento.AGUA;
						coluna++;
						break;
					case "TERRA":
						mapaInicial[linha][coluna] = Elemento.TERRA;
						coluna++;
						break;
					case "RUBI":
						mapaInicial[linha][coluna] = Elemento.RUBI;
						coluna++;
						break;
					case "VIDA":
						mapaInicial[linha][coluna] = Elemento.VIDA;
						coluna++;
						break;
					case "PERSONAGEMUP":
						mapaInicial[linha][coluna] = Elemento.PERSONAGEMUP;
						coluna++;
						break;
					case "PERSONAGEMDOWN":
						mapaInicial[linha][coluna] = Elemento.PERSONAGEMDOWN;
						coluna++;
						break;
					case "PERSONAGEMLEFT":
						mapaInicial[linha][coluna] = Elemento.PERSONAGEMLEFT;
						coluna++;
						break;
					case "PERSONAGEMRIGHT":
						mapaInicial[linha][coluna] = Elemento.PERSONAGEMRIGHT;
						coluna++;
						break;
					case "PAREDE":
						mapaInicial[linha][coluna] = Elemento.PAREDE;
						coluna++;
						break;
					case "\n":
						linha++;
						break;
					default:
						System.out.println(leitor.next());
					}
					
				}
				return new EstruturaMapas(new Mapa(mapaInicial));
			}
		}
		return null;
	}

}
