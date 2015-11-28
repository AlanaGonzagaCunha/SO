package domain;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import memoria.Bloco;

public class Processo extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static int id = 0;
	private int idProcesso, tempoTotal, tempoRestante, prioridade, tamanhoMemoria;
	private JLabel idLabel, tempoTotalLabel, tempoRestanteLabel, prioridadeLabel, estadoLabel, tamanhoProceso;
	private String estadoProcesso;
	private static Random random = new Random();
	private ArrayList <Bloco> blocosOcupados;
	private Requisicao requisicao;
	
	public Processo(){
		Random rdm = new Random();
		this.idProcesso = Processo.id;
		Processo.id++;
		this.setLayout(new GridLayout(4, 1,5,5));
		blocosOcupados = new ArrayList<Bloco>();
		tamanhoMemoria = Math.min(1024,random.nextInt(1024)+32);
		tempoTotal = rdm.nextInt(17)+4;
		tempoRestante = tempoTotal;
		prioridade = rdm.nextInt(4);
		estadoProcesso = "Pronto";
		//TODO: ajeitar isso aqui  tratar caso de outros algoritimos 
		requisicao = new Requisicao(tamanhoMemoria);		
		idLabel = new JLabel("id: "+this.getIdProcesso());
		tempoTotalLabel = new JLabel("TT: "+tempoTotal);
		tempoRestanteLabel = new JLabel("TR: "+tempoRestante);
		prioridadeLabel = new JLabel("Pri: "+prioridade);
		estadoLabel = new JLabel("Estado: "+ estadoProcesso);
		tamanhoProceso= new JLabel("Tamanho: "+tamanhoMemoria);
		this.add(tamanhoProceso);
		this.add(idLabel);
		this.add(tempoTotalLabel);
		this.add(tempoRestanteLabel);
		this.add(estadoLabel);
		this.add(prioridadeLabel);
		if(prioridade==0)this.setBackground(new Color(255,174,185));
		if(prioridade==1)this.setBackground(new Color(225,102,255));
		if(prioridade==2)this.setBackground(new Color(205,205,180));
		if(prioridade==3)this.setBackground(new Color(255,193,37));
	}
	
	public int getTempoTotal() {
		return tempoTotal;
	}
	public void setTempoTotal(int tempoTotal) {
		this.tempoTotal = tempoTotal;
	}
	public int getTempoRestante() {
		return tempoRestante;
	}
	public void setTempoRestante(int tempoRestante) {
		this.tempoRestante = tempoRestante;
		tempoRestanteLabel.setText("TR: "+tempoRestante);
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public String getEstadoProcesso() {
		return estadoProcesso;
	}
	public void setEstadoProcesso(String estadoProcesso) {
		this.estadoProcesso = estadoProcesso;
		this.estadoLabel.setText(estadoProcesso);
	}
	public int getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
		this.idLabel.setText("id: "+idProcesso);
	}
	public int getTamanhoMemoria() {
		return tamanhoMemoria;
	}
	public void setTamanhoMemoria(int tamanhoMemoria) {
		this.tamanhoMemoria = tamanhoMemoria;
	}
	public ArrayList<Bloco> getBlocosOcupados() {
		return blocosOcupados;
	}
	public void setBlocosOcupados(ArrayList<Bloco> blocosOcupados) {
		this.blocosOcupados = blocosOcupados;
	}

	public Requisicao getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}
	
	
}
