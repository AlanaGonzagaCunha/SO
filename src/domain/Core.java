package domain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JPanel;

import roundRobin.RoundRobin;

public class Core extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private Processo p;
	private Thread t;
	private RoundRobin rr;
	private int quantum;
	private Random random;

	public Core(RoundRobin rr) {
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(150,150));
		this.rr = rr;
		this.quantum = rr.quantun;
		this.random = new Random();
		p = null;
		t = new Thread(this);
		t.start();
	}

	private void adicionarProcesso(Processo p) {
		this.p = p;
		this.add(p);
		this.revalidate();
		this.repaint();
		quantum = rr.quantun - (p.getPrioridade() - 1);
		synchronized (this) {
			this.notify();
		}
	}

	public boolean atualizarCore() {
		if (p != null) {
			this.remove(p);
			this.revalidate();
			this.repaint();
			p.setEstadoProcesso("pronto");
			rr.adicionarProcesso(p);
			p = null;
		}
		p = rr.escalonarProcesso(this);
		if (p != null) {
			adicionarProcesso(p);
			return true;
		}
		return false;
	}

	public void run() {
		synchronized (this) {
			try {
				while (true) {
					if (p != null) {
						if (p.getTempoRestante() != 0 && quantum > 0) {
							p.setTempoRestante(p.getTempoRestante() - 1);
							quantum--;
							this.wait(1000);
						} else {
							atualizarCore();
						}
					} else {
						wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}