package memoria;

import java.util.ArrayList;

import javax.swing.JPanel;

import domain.Processo;

public abstract class AlgoritmoDeMemoria {
	
	private ArrayList <Bloco> blocosOcupados, blocosLivres, blocos;
	private int tamanhoTotal, tamanhoOcupado;
	protected JPanel interfaceG;
	
	public AlgoritmoDeMemoria(int tamanhoTotal, JPanel interfaceG){
		this.tamanhoTotal = tamanhoTotal;
		this.interfaceG = interfaceG;
		this.blocosOcupados= new ArrayList<Bloco>();
		this.blocosLivres= new ArrayList<Bloco>();
		this.blocos= new ArrayList<Bloco>();
	}
	
	public abstract int alocarMemoria(Processo p);
	
	public abstract int desalocarMemoria(Processo p);
	
	public void atualizarInterface(){
		this.interfaceG.revalidate();
		this.interfaceG.repaint();
	}

	public ArrayList<Bloco> getBlocosOcupados() {
		return blocosOcupados;
	}

	public void setBlocosOcupados(ArrayList<Bloco> blocosOcupados) {
		this.blocosOcupados = blocosOcupados;
	}

	public ArrayList<Bloco> getBlocosLivres() {
		return blocosLivres;
	}

	public void setBlocosLivres(ArrayList<Bloco> blocosLivres) {
		this.blocosLivres = blocosLivres;
	}

	public ArrayList<Bloco> getBlocos() {
		return blocos;
	}

	public void setBlocos(ArrayList<Bloco> blocos) {
		this.blocos = blocos;
	}

	public int getTamanhoTotal() {
		return tamanhoTotal;
	}

	public void setTamanhoTotal(int tamanhoTotal) {
		this.tamanhoTotal = tamanhoTotal;
	}

	public int getTamanhoOcupado() {
		return tamanhoOcupado;
	}

	public void setTamanhoOcupado(int tamanhoOcupado) {
		this.tamanhoOcupado = tamanhoOcupado;
	}

	public JPanel getInterfaceG() {
		return interfaceG;
	}

	public void setInterfaceG(JPanel interfaceG) {
		this.interfaceG = interfaceG;
	}		
	
}
