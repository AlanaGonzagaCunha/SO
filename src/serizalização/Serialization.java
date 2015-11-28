package serizalização;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
 
public class Serialization
{

    public static void main(String args[]) throws Exception
    {
        ObjectOutputStream saida = null;
        String nomeArquivo = "arquivo.binario"; 
         
        saida = new ObjectOutputStream(new FileOutputStream(nomeArquivo,true));
        ClasseExemplo aux = new ClasseExemplo();

        aux.setar(100,"Eriko");
        gravar(saida, aux);

        aux.setar(250,"Othon");
        gravar(saida, aux);

        aux.setar(300,"Alana");
        gravar(saida, aux);
        
        saida.close();
        
         
    }

    private static void gravar(ObjectOutputStream out, ClasseExemplo dados) throws Exception
    {
        dados.imprimir();
        out.writeObject(dados);
        out.reset();

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