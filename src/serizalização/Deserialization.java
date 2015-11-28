package serizalização;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;


public class Deserialization {
    

	public static void main(String[] args) throws Exception {
        ObjectInputStream entrada = null;
        String nomeArquivo = "arquivo.binario";
		
        System.out.println("--------------------------------------------");
        
        entrada = new ObjectInputStream(new FileInputStream(nomeArquivo));
        
        ler(entrada);
        ler(entrada);
        ler(entrada);

        entrada.close();

	}

    private static void ler(ObjectInputStream entrada) throws Exception
    {
        ClasseExemplo aux;
        aux = (ClasseExemplo) entrada.readObject();
        aux.imprimir();
    }

}

class ClasseExemplo implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String nome;
    private int id;
     
    void setar(int id, String nome)
    {
        this.id = id;
        this.nome = nome;
    }
     
    void imprimir()
    {
        System.out.println("ID = " + id);
        System.out.println("Nome = " + nome);
    }
}