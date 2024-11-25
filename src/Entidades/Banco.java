package Entidades;

public class Banco {

    int id_banco;
    String nome;

    public Banco(int id_banco, String nome) {
        this.id_banco = id_banco;
        this.nome = nome;
    }

    @Override
    public String toString(){
        return id_banco + ". " + nome + "\n";
    }

    public int getId_banco() {
        return id_banco;
    }

    public String getNome() {
        return nome;
    }
}
