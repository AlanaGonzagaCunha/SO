package memoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import domain.Processo;
import domain.Requisicao;

public class QuickFit extends AlgoritmoDeMemoria {

	static int contadorRequisicoes = 0;
	private int numFilas;
	private ArrayList<Requisicao> listasRequisicoes;
	private ArrayList<Requisicao> listasQuick;

	public QuickFit(int tamanhoTotal, JPanel interfaceG, int numFilas) {
		super(tamanhoTotal, interfaceG);
		listasRequisicoes = new ArrayList<Requisicao>();
		listasQuick = new ArrayList<Requisicao>();
		this.numFilas = numFilas;
	}

	@Override
	public int alocarMemoria(Processo p) {

		Bloco candidato = null;
		// usa a lista quick
		if (this.listasQuick.size() != 0) {

			for (int i = 0; i < listasQuick.size(); i++) {
				if (listasQuick.get(i).getTamanho() >= p.getTamanhoMemoria()) {
					ArrayList<Bloco> blocosQuick = listasQuick.get(i).getBlocos();
					for (int j = 0; j < blocosQuick.size(); j++) {
						if (super.getBlocosLivres().contains(blocosQuick.get(j)) && blocosQuick.get(j).getP() == null) {
							System.out.println("Processo Alocado por meio da Lista Quick");
							candidato = blocosQuick.get(j);
							p.getBlocosOcupados().add(candidato);
							candidato.setP(p);
							super.getBlocosLivres().remove(candidato);
							adicionarRequisicao(candidato,p.getTamanhoMemoria());
							super.atualizarInterface();
							return 1;
						}
					}
				}
			}
		}
		// usa o first fit
		for (int i = 0; i < super.getBlocosLivres().size(); i++) {
			candidato = super.getBlocosLivres().get(i);
			if (candidato.getTamanho() >= p.getTamanhoMemoria()) {
				adicionarRequisicao(candidato, p.getTamanhoMemoria());
				p.getBlocosOcupados().add(candidato);
				System.out.println("Processo Alocado em bloco ja existente");
				candidato.setP(p);
				super.getBlocosLivres().remove(candidato);
				super.atualizarInterface();
				return 1;
			}
		}
		// se nao couber verifica se há memória livre
		if (p.getTamanhoMemoria() < (super.getTamanhoTotal() - super.getTamanhoOcupado())) {
			candidato = new Bloco(p.getTamanhoMemoria(), p);
			super.setTamanhoOcupado(super.getTamanhoOcupado() + candidato.getTamanho());
			p.getBlocosOcupados().add(candidato);
			super.getBlocosOcupados().add(candidato);
			super.interfaceG.add(candidato);
			System.out.println("Processo Alocado em memoria livre");
			adicionarRequisicao(candidato, p.getTamanhoMemoria());
			super.atualizarInterface();
			return 1;
		}
		adicionarRequisicao(null,p.getTamanhoMemoria());
		System.out.println("Out of memory");
		return 0;
	}

	@Override
	public int desalocarMemoria(Processo p) {
		Bloco b = null;
		for (int i = 0; i < p.getBlocosOcupados().size(); i++) {
			b = p.getBlocosOcupados().get(i);
			b.setP(null);
			super.getBlocosOcupados().remove(b);
			super.getBlocosLivres().add(b);
		}
		p.getBlocosOcupados().clear();
		super.atualizarInterface();
		return 0;
	}

	public int getNumFilas() {
		return numFilas;
	}

	public void setNumFilas(int numFilas) {
		this.numFilas = numFilas;
	}

	public void adicionarRequisicao(Bloco bloco,int t) {
		contadorRequisicoes++;
		if (contadorRequisicoes == 100) {
			this.criarListas();
			contadorRequisicoes = 0;
			listasRequisicoes.clear();
		}

		boolean contem = false;
		for (int i = 0; i < listasRequisicoes.size() && bloco!=null; i++) {
			if (listasRequisicoes.get(i).getTamanho() == bloco.getTamanho()) {
				listasRequisicoes.get(i).setQtde(listasRequisicoes.get(i).getQtde() + 1);
				if (!listasRequisicoes.get(i).getBlocos().contains(bloco)) {
					listasRequisicoes.get(i).getBlocos().add(bloco);
				}
				contem = true;
			}
		}
		if (!contem) {
			Requisicao r = new Requisicao(t);
			r.setQtde(1);
			if(bloco!=null)r.getBlocos().add(bloco);
			listasRequisicoes.add(r);
		}

	}

	public void criarListas() {
		this.listasQuick.clear();
		Collections.sort(listasRequisicoes, new Comparator<Requisicao>() {
			public int compare(Requisicao a0, Requisicao a1) {
				return Integer.compare(a0.getQtde(), a1.getQtde()) * -1; 
			}
		});
		for (int i = 0; i < this.listasRequisicoes.size() && i < numFilas; i++) {
			if (listasRequisicoes.get(i) != null) {
				listasQuick.add(listasRequisicoes.get(i));
			}
		}
		System.out.println("Ranking de Requisicoes");
		for (Requisicao requisicao : listasQuick) {
			System.out.println("Chave: " + requisicao.getTamanho() + " qtd: " + requisicao.getQtde());
		}
	}
}