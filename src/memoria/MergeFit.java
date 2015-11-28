package memoria;

import javax.swing.JPanel;

import domain.Processo;

public class MergeFit extends AlgoritmoDeMemoria {

	// A memoria inicialmente vai ser um bloco vazio gigante
	public MergeFit(int tamanhoTotal, JPanel interfaceG) {
		super(tamanhoTotal, interfaceG);
		Bloco b = new Bloco(tamanhoTotal, null);
		super.getBlocos().add(b);
		super.getBlocosLivres().add(b);
		super.interfaceG.add(b);
	}

	//O alocar é bem parecido com o "best", mas agente uso o firstFit 
	@Override
	public int alocarMemoria(Processo p) {

		Bloco b = null;
		Bloco candidato = null;
		if (super.getBlocosLivres().size() > 0) {
			for (int i = 0; i < super.getBlocosLivres().size(); i++) {
				b = super.getBlocosLivres().get(i);
				if (b.getTamanho() >= p.getTamanhoMemoria()) {
					candidato = b;
				}
			}
			if (candidato != null) {
				super.setTamanhoOcupado(super.getTamanhoOcupado()
						+ candidato.getTamanho());
				p.getBlocosOcupados().add(candidato);
				candidato.setP(p);
				super.getBlocosLivres().remove(candidato);
				//vou alocando e dividindo
				this.split(candidato);
				super.atualizarInterface();
				return 1;
			}
		}

		return 0;
	}

	@Override
	public int desalocarMemoria(Processo p) {
		Bloco bloco = null;

		for (int i = 0; i < p.getBlocosOcupados().size(); i++) {
			bloco = p.getBlocosOcupados().get(i);
			bloco.setP(null);
			super.getBlocosOcupados().remove(bloco);
			super.getBlocosLivres().add(bloco);
			//desaloco e junto...
			mergeBlocos(bloco);
			super.atualizarInterface();

		}

		p.getBlocosOcupados().clear();
		super.atualizarInterface();
		return 0;
	}

	//O usamos a expressão lambda, pegamos o local onde se encontra o bloco 'b' subtraimos ou somamos caso queria ir para esquerda ou direita
	//verifico ser é maior q zero no caso da esquerda, ser for retorno o bloco da esquera se nao null, assim faço o join e vou recursivaente  
	public synchronized Bloco mergeBlocos(Bloco b) {
		Bloco esquerda = null;
		Bloco direita = null;

		if (b != null) {
			esquerda = getBlocos().indexOf(b) - 1 >= 0 ? getBlocos().get(
					getBlocos().indexOf(b) - 1) : null;
			while (esquerda != null && getBlocosLivres().contains(esquerda)) {
				join(b, esquerda);
				esquerda = getBlocos().indexOf(b) - 1 >= 0 ? getBlocos().get(
						getBlocos().indexOf(b) - 1) : null;
			}
			direita = getBlocos().indexOf(b) + 1 < getBlocos().size() ? getBlocos()
					.get(getBlocos().indexOf(b) + 1) : null;

			if (direita != null && getBlocosLivres().contains(direita)) {
				join(b, direita);
				direita = getBlocos().indexOf(b) + 1 < getBlocos().size() ? getBlocos()
						.get(getBlocos().indexOf(b) + 1) : null;
			}
			return b;
		}

		return null;
	}
	//junto os blocos e removo de todas as formas das lista, para não haver referencia ao bloco
	public void join(Bloco b1, Bloco b2) {
		b1.setTamanho(b1.getTamanho() + b2.getTamanho());
		super.getBlocos().remove(b2);
		super.interfaceG.remove(b2);
		super.getBlocosLivres().remove(b2);
		super.getBlocosOcupados().remove(b2);
	}
	
	//o uso do SetComponenet serve qdo dividimos o bloco ai precisamos pega o bloco vazio q divido e
	//inserir-lo logo atras, assim empuramos todos os outros 
	public void split(Bloco b) {
		int novoTamanho;
		if (b.getTamanho() > b.getTamanhoUtilizado()) {
			novoTamanho = b.getTamanho() - b.getTamanhoUtilizado();
			b.setTamanho(b.getTamanhoUtilizado());
			Bloco novoBloco = new Bloco(novoTamanho, null);
			super.interfaceG.add(novoBloco);
			super.interfaceG.setComponentZOrder(novoBloco,
					interfaceG.getComponentZOrder(b) + 1);
			super.getBlocosLivres().add(novoBloco);
			super.getBlocos().add(novoBloco);
		}
	}
}
