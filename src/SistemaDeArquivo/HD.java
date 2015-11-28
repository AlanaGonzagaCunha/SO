package SistemaDeArquivo;

import java.util.ArrayList;
import memoria.Bloco;

public class HD {
	private ArrayList<Bloco> hd;

	public HD() {
		hd = new ArrayList<Bloco>();
		
	}
	public ArrayList<Bloco> getHd() {
		return hd;
	}

	public void setHd(ArrayList<Bloco> hd) {
		this.hd = hd;
	}


}
