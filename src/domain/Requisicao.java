package domain;

import java.util.ArrayList;

import memoria.Bloco;

public class Requisicao {
	
	private int tamanho;
	private int qtde;
	private ArrayList<Bloco> blocos;
	
	public Requisicao(int tamanho){
		this.tamanho = tamanho;
		blocos = new ArrayList<>();
	}
	
	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public ArrayList<Bloco> getBlocos() {
		return blocos;
	}

	public void setBlocos(ArrayList<Bloco> blocos) {
		this.blocos = blocos;
	}

	
	
}
