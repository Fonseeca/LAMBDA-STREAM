package Entidades;

import java.util.Date;

public class PessoaFisica extends Cliente{

    String id_pf;
    String cpf;
    String genero;
    int idade;

    public PessoaFisica(int id_clientes, String nome, String endereco, Date data, String id_pf, String cpf, String genero, int idade) {
        super(id_clientes, nome, endereco, data);
        this.id_pf = id_pf;
        this.cpf = cpf;
        this.genero = genero;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "PessoaFisica {\n" +
                "    id_clientes=" + id_clientes + ",\n" +
                "    nome='" + nome + "',\n" +
                "    endereco='" + endereco + "',\n" +
                "    data=" + data + ",\n" +
                "    id_pf=" + id_pf + ",\n" +
                "    cpf='" + cpf + "',\n" +
                "    genero='" + genero + "',\n" +
                "    idade=" + idade + "\n" +
                '}';
    }

    public String getId_pf() {
        return id_pf;
    }

    public String getCpf() {
        return cpf;
    }

    public String getGenero() {
        return genero;
    }

    public int getIdade() {
        return idade;
    }
}
