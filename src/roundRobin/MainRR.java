package roundRobin;

public class MainRR {

	public static void main(String[] args) {
	
		String qtn = "3";//JOptionPane.showInputDialog("Digite o tamanho do Quantum.");
		String numCores = "4";//JOptionPane.showInputDialog("Digite o n�mero de cores.");
		String numProcessos = "100";//JOptionPane.showInputDialog("Digite o n�mero de processos.");
		String tamanhoMemoria = "10000";//JOptionPane.showInputDialog("Digite o tamanho da mem�ria");
		String numFilas = "10";
		String tipoMemoria = "2";
		
		RoundRobin rr = new RoundRobin(Integer.parseInt(qtn), Integer.parseInt(numCores), 
				Integer.parseInt(numProcessos),Integer.parseInt(tamanhoMemoria) ,
				Integer.parseInt(tipoMemoria),Integer.parseInt(numFilas));
		rr.getDefaultCloseOperation();
	}

}
