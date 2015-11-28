package roundRobin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.Core;
import domain.Processo;
import memoria.AlgoritmoDeMemoria;
import memoria.BestFit;
import memoria.MergeFit;
import memoria.QuickFit;

public class RoundRobin extends JFrame {

	private static final long serialVersionUID = 1L;
	public ArrayList<Processo> filaP0 = new ArrayList<Processo>();
	public ArrayList<Processo> filaP1 = new ArrayList<Processo>();
	public ArrayList<Processo> filaP2 = new ArrayList<Processo>();
	public ArrayList<Processo> filaP3 = new ArrayList<Processo>();
	public ArrayList<Processo> filaCompletos = new ArrayList<Processo>();
	public ArrayList<Core> coresDormindo = new ArrayList<Core>();
	public JPanel processosProntosPanel, processosCompletosPanel, coresPanel, btnPanel, memoriaPanel;
	public JButton adcProcesso;
	public int quantun, prioridade = -1, numCores, numProcessos;
	public AlgoritmoDeMemoria memoria; 
	
	public RoundRobin(int quantun, int numCores, int numProcessos, int gM, int tipoGM, int numFilas) {
		this.quantun = quantun;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setLocationRelativeTo(null);
		this.numCores = numCores;
		this.numProcessos = numProcessos;
		processosProntosPanel = new JPanel();
		processosProntosPanel.setLayout(new GridLayout(1, 1000, 5, 5));
		processosCompletosPanel = new JPanel();
		processosCompletosPanel.setLayout(new GridLayout(1, 1000, 5, 5));
		memoriaPanel = new JPanel();
		memoriaPanel.setLayout(new GridLayout(1, 1000, 5, 5));
		this.setTitle("Trabalho de SO");
		if(tipoGM==1){
			System.out.println("Entra Best");
			this.memoria = new BestFit(gM,memoriaPanel);
		}else if(tipoGM==2){
			System.out.println("Entra Quick");
			this.memoria = new QuickFit(gM, memoriaPanel, numFilas);
		}else if(tipoGM==3){
			System.out.println("Entra Merge");
			this.memoria = new MergeFit(gM, memoriaPanel);
		}		
		adcProcesso = new JButton("Adicionar Processo");
		adcProcesso.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(ActionEvent e) {
				Processo p = new Processo();
				adicionarProcesso(p);
				memoria.atualizarInterface();
			}}  );
		btnPanel = new JPanel();
		btnPanel.add(adcProcesso);
		coresPanel = new JPanel();
		coresPanel.setLayout(new GridLayout(1, 1000, 5, 5));
		JScrollPane aptosSPane = new JScrollPane(processosProntosPanel);
		JScrollPane coresSPane = new JScrollPane(coresPanel);
		JScrollPane memoriaSPane = new JScrollPane(memoriaPanel);
		JScrollPane completosSPane = new JScrollPane(processosCompletosPanel);
		this.add(coresSPane);
		this.add(aptosSPane);
		this.add(completosSPane);
		this.add(memoriaSPane);
		this.add(btnPanel);
		this.inicializarProcessos();
		this.inicializarCores();
		this.setVisible(true);
	}

	public void inicializarCores() {
		for (int i = 0; i < numCores; i++) {
			Core c = new Core(this);
			this.coresPanel.add(c);
			if (!c.atualizarCore()) {
				coresDormindo.add(c);
			}
		}
	}

	public void inicializarProcessos() {
		for (int i = 0; i < numProcessos; i++) {
			Processo p = new Processo();
			adicionarProcesso(p);
		}
	}

	public void adicionarProcesso(Processo p) {
		int t = -1;
		if(p!=null && p.getBlocosOcupados().size()==0){
			t = memoria.alocarMemoria(p);
		}else{
			t = 1;
		}
		
		if (p != null && p.getTempoRestante() > 0 && t == 1) {
				if (p.getPrioridade() == 0)
					this.filaP0.add(p);
				if (p.getPrioridade() == 1)
					this.filaP1.add(p);
				if (p.getPrioridade() == 2)
					this.filaP2.add(p);
				if (p.getPrioridade() == 3)
					this.filaP3.add(p);
				processosProntosPanel.add(p);

				if (coresDormindo.size() > 0) {
					coresDormindo.remove(0).atualizarCore();
				}
				processosProntosPanel.revalidate();
				processosProntosPanel.repaint();

			} else {
				memoria.desalocarMemoria(p);
				adicionarTerminados(p, t);
			}
		
		
	}

	public void adicionarTerminados(Processo p, int t) {
		if (p != null) {
			if(t == 1){
				p.setEstadoProcesso("Terminado");
			}else{
				p.setEstadoProcesso("Abortado");
			}
			this.processosCompletosPanel.add(p);
			this.processosCompletosPanel.revalidate();
			this.processosCompletosPanel.repaint();
		}
	}

	public synchronized Processo escalonarProcesso(Core c) {
		this.roundIncrement();
		if (isProcessos()) {
			if (prioridade == 0 && this.filaP0.size() > 0) {
				Processo p = filaP0.remove(0);
				processosProntosPanel.remove(p);
				processosProntosPanel.revalidate();
				processosProntosPanel.repaint();
				p.setEstadoProcesso("Rodando");
				return p;
			}
			if (prioridade == 1 && this.filaP1.size() > 0) {
				Processo p = filaP1.remove(0);
				processosProntosPanel.remove(p);
				processosProntosPanel.revalidate();
				processosProntosPanel.repaint();
				p.setEstadoProcesso("Rodando");
				return p;
			}
			if (prioridade == 2 && this.filaP2.size() > 0) {
				Processo p = filaP2.remove(0);
				processosProntosPanel.remove(p);
				processosProntosPanel.revalidate();
				processosProntosPanel.repaint();
				p.setEstadoProcesso("Rodando");
				return p;
			}
			if (prioridade == 3 && this.filaP3.size() > 0) {
				Processo p = filaP3.remove(0);
				processosProntosPanel.remove(p);
				processosProntosPanel.revalidate();
				processosProntosPanel.repaint();
				p.setEstadoProcesso("Rodando");
				return p;
			}
			return escalonarProcesso(c);
		} else {
			coresDormindo.add(c);
		}
		return null;
	}

	public boolean isProcessos() {
		if (filaP0.size() != 0)
			return true;
		if (filaP1.size() != 0)
			return true;
		if (filaP2.size() != 0)
			return true;
		if (filaP3.size() != 0)
			return true;
		return false;
	}

	public void roundIncrement() {
		if (prioridade == 3) {
			prioridade = 0;
		} else {
			prioridade++;
		}
	}
}