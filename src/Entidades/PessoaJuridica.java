package Entidades;

import java.util.Date;

public class PessoaJuridica extends Cliente{

    String id_pj;
    String cnpj;
    int num_funcionarios;
    String setor;

    public PessoaJuridica(int id_clientes, String nome, String endereco, Date data, String id_pj, String cnpj, int num_funcionarios, String setor) {
        super(id_clientes, nome, endereco, data);
        this.id_pj = id_pj;
        this.cnpj = cnpj;
        this.num_funcionarios = num_funcionarios;
        this.setor = setor;
    }

    @Override
    public String toString() {
        return "PessoaJuridica {\n" +
                "    id_clientes=" + id_clientes + ",\n" +
                "    nome='" + nome + "',\n" +
                "    endereco='" + endereco + "',\n" +
                "    data=" + data + ",\n" +
                "    id_pj=" + id_pj + ",\n" +
                "    cnpj='" + cnpj + "',\n" +
                "    num_funcionarios=" + num_funcionarios + ",\n" +
                "    setor='" + setor + "'\n" +
                '}';
    }

    public String getId_pj() {
        return id_pj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public int getNum_funcionarios() {
        return num_funcionarios;
    }

    public String getSetor() {
        return setor;
    }
}
