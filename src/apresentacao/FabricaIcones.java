package apresentacao;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import logicajogo.Elemento;

public class FabricaIcones {

	public Icon obterIcone(Elemento elemento) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResourceAsStream(elemento.getCaminhoImagem())));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
