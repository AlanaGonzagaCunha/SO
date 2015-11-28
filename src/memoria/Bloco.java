package memoria;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Processo;

public class Bloco extends JPanel {

	private static final long serialVersionUID = -1967956319582712277L;
	private static int idStatic = 0;
	private int id, tamanho, tamanhoUtilizado;
	private JLabel idLabel, idProcessoLabel, tamanhoUtiLab, tamanhoTotal;
	private Processo p;
	
	public Bloco(int tamanho, Processo p) {
		id = idStatic++;
		this.tamanho = tamanho;
		tamanhoUtilizado = 0;
		idLabel = new JLabel("Id: " + id);
		this.add(idLabel);
		idProcessoLabel = new JLabel("");
		this.add(idProcessoLabel);
		tamanhoUtiLab = new JLabel("");
		tamanhoTotal = new JLabel("Tamanho Total: " + tamanho);
		this.add(tamanhoTotal);
		this.add(tamanhoUtiLab);
		this.setP(p);
		this.setLayout(new GridLayout(2, 2, 5, 5));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		tamanhoTotal.setText("Tamanho Total: " + tamanho);
		this.tamanho = tamanho;
	}

	public int getTamanhoUtilizado() {
		return tamanhoUtilizado;
	}

	public void setTamanhoUtilizado(int tamanhoUtilizado) {
		this.tamanhoUtiLab.setText("Tamanho Utilizado: " + tamanhoUtilizado);
		this.tamanhoUtilizado = tamanhoUtilizado;
	}

	public JLabel getIdLabel() {
		return idLabel;
	}

	public void setIdLabel(JLabel idLabel) {
		this.idLabel = idLabel;
	}

	public JLabel getIdProcessoLabel() {
		return idProcessoLabel;
	}

	public void setIdProcessoLabel(JLabel idProcessoLabel) {
		this.idProcessoLabel = idProcessoLabel;
	}

	public Processo getP() {
		return p;
	}

	public void setP(Processo p) {
		if (p == null) { // Livre
			this.setBackground(Color.WHITE);
			this.p = null;
			this.idProcessoLabel.setText("Livre");
			this.tamanhoUtilizado = 0;
			this.tamanhoUtiLab
					.setText("Tamanho Utilizado: " + tamanhoUtilizado);
		} else {
			int prioridade = p.getPrioridade();
			if (prioridade == 0)
				this.setBackground(new Color(255, 174, 185));
			if (prioridade == 1)
				this.setBackground(new Color(225, 102, 255));
			if (prioridade == 2)
				this.setBackground(new Color(205, 205, 180));
			if (prioridade == 3)
				this.setBackground(new Color(255, 193, 37));
			p.getBlocosOcupados().add(this);
			this.p = p;
			this.idProcessoLabel.setText("Processo: " + p.getIdProcesso());
			this.tamanhoUtilizado = p.getTamanhoMemoria();
			this.tamanhoUtiLab
					.setText("Tamanho Utilizado: " + tamanhoUtilizado);

		}

	}

}
