package memoria;

import javax.swing.JPanel;

import domain.Processo;

public class BestFit extends AlgoritmoDeMemoria {

	public BestFit(int tamanhoTotal, JPanel interfaceG) {
		super(tamanhoTotal, interfaceG);
	}

	@Override
	public int alocarMemoria(Processo p) {
		//testa se tem bloco livre
		Bloco b = null;
		Bloco candidato = null;
		if (super.getBlocosLivres().size() > 0) {
			for (int i = 0; i < super.getBlocosLivres().size(); i++) {
				b = super.getBlocosLivres().get(i);
				if (b.getTamanho() >= p.getTamanhoMemoria()) {
					if (candidato == null) {
						candidato = b;
					} else if (b.getTamanho() < candidato.getTamanho()) {
						candidato = b;
					}
				}
			}
			if (candidato!=null){
				super.setTamanhoOcupado(super.getTamanhoOcupado()+candidato.getTamanho());
				p.getBlocosOcupados().add(candidato);
				candidato.setP(p);	
				super.getBlocosLivres().remove(candidato);
				super.atualizarInterface();
				return 1;
			}
		}
		// testa se tem espaço livre na memoria(que ainda não virou bloco)
		if (p.getTamanhoMemoria() < (super.getTamanhoTotal() - super.getTamanhoOcupado())) {
			b = new Bloco(p.getTamanhoMemoria(), p);
			super.setTamanhoOcupado(super.getTamanhoOcupado()+b.getTamanho());
			p.getBlocosOcupados().add(b);
			super.getBlocosOcupados().add(b);
			super.interfaceG.add(b);
			super.atualizarInterface();
			return 1;
		}
		return 0;
	}

	@Override
	public int desalocarMemoria(Processo p) {
		Bloco b = null;
		for(int i=0; i<p.getBlocosOcupados().size(); i++){
			b = p.getBlocosOcupados().get(i);
			b.setP(null);
			super.getBlocosOcupados().remove(b);
			super.getBlocosLivres().add(b);
		}
		p.getBlocosOcupados().clear();
		super.atualizarInterface();
		return 0;
	}

}
