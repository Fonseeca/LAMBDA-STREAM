package Entidades;

import java.util.Date;

public abstract class Cliente {

    int id_clientes;
    String nome;
    String endereco;
    Date data;

    public Cliente(int id_clientes, String nome, String endereco, Date data) {
        this.id_clientes = id_clientes;
        this.nome = nome;
        this.endereco = endereco;
        this.data = data;
    }

    public int getId_clientes() {
        return id_clientes;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Date getData() {
        return data;
    }
}
