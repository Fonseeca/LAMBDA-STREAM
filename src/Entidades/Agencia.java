package Entidades;

public class Agencia {

    int id_agencias;
    int numero;
    int id_banco;

    public Agencia(int id_agencias, int numero, int id_banco) {
        this.id_agencias = id_agencias;
        this.numero = numero;
        this.id_banco = id_banco;
    }

    @Override
    public String toString(){
        return id_agencias + ". " + numero + " pertence ao banco: " + id_banco +"\n";
    }

    public int getId_agencias() {
        return id_agencias;
    }

    public int getNumero() {
        return numero;
    }

    public int getId_banco() {
        return id_banco;
    }
}
