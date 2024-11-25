package DAOs.Cliente;

import Entidades.Cliente;
import Entidades.PessoaFisica;
import Entidades.PessoaJuridica;
import conexao.Conexao;
import conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {
    private static final int id_clientes = 1;
    private static final int nome = 2;
    private static final int endereco = 3;
    private static final int data = 4;
    private static final int id_pf = 5;
    private static final int id_pj = 5;
    private static final int cpf = 6;
    private static final int cnpj = 6;
    private static final int genero = 7;
    private static final int idade = 8;
    private static final int num_funcionarios = 7;
    private static final int setor = 8;
    static final int DUPLICATE_KEY_ERROR_CODE = 1062;


    /*
    * criaTabelaCliente
    * obterCliente
    * obtemClientePorIdentificador
    * InsereCliente(Cliente cliente)
    * removeCliente(String cpf_cnpj, int tipo_cliente)
    * */

    public static void criaTabelaCliente() throws FalhaConexaoException {

        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS `internetBanking`.`clientes` (\n" +
                    "  `id_cliente` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `nome` VARCHAR(45) NOT NULL,\n" +
                    "  `endereco` VARCHAR(45) NOT NULL,\n" +
                    "  `data` DATE NOT NULL,\n" +
                    "  `cpf` VARCHAR(45) NULL,\n" +
                    "  `genero` VARCHAR(45) NULL,\n" +
                    "  `idade` INT NULL,\n" +
                    "  `id_pf` VARCHAR(45) NULL,\n" +
                    "  `cnpj` VARCHAR(45) NULL,\n" +
                    "  `num_funcionarios` VARCHAR(45) NULL,\n" +
                    "  `setor` VARCHAR(45) NULL,\n" +
                    "  `id_pj` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id_cliente`)\n" +
                    ") ENGINE = InnoDB;");

            stmt.execute("CREATE TABLE IF NOT EXISTS `internetBanking`.`clientes_contas` (\n" +
                    "  `id_contas` INT NOT NULL,\n" +
                    "  `id_clientes` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_clientes`, `id_contas`),\n" +
                    "  INDEX `fk_contas_has_clientes_clientes1_idx` (`id_clientes` ASC) VISIBLE,\n" +
                    "  INDEX `fk_contas_has_clientes_contas1_idx` (`id_contas` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_contas_has_clientes_contas1`\n" +
                    "    FOREIGN KEY (`id_contas`)\n" +
                    "    REFERENCES `internetBanking`.`contas` (`id_conta`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `fk_contas_has_clientes_clientes1`\n" +
                    "    FOREIGN KEY (`id_clientes`)\n" +
                    "    REFERENCES `internetBanking`.`clientes` (`id_cliente`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION\n" +
                    ") ENGINE = InnoDB;");

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static ArrayList<Cliente> obterCliente() throws FalhaConexaoException, ClienteInexistenteException {

        try {
            Connection conexao = Conexao.obtemConexao();
            ArrayList<Cliente> clientes = new ArrayList<>();

            Statement stmt = conexao.createStatement();

            ResultSet resultado = stmt.executeQuery("SELECT id_cliente, nome, endereco, data, id_pf, cpf, genero, idade FROM `clientes` WHERE cpf IS NOT NULL;");

            while (resultado.next()) {
                Cliente cliente = new PessoaFisica(resultado.getInt(id_clientes), resultado.getString(nome), resultado.getString(endereco), resultado.getDate(data), resultado.getString(id_pf), resultado.getString(cpf), resultado.getString(genero), resultado.getInt(idade));
                clientes.add(cliente);
            }
            resultado.close();

            resultado = stmt.executeQuery("SELECT id_cliente, nome, endereco, data, id_pj, cnpj, num_funcionarios, setor FROM `clientes` WHERE cnpj IS NOT NULL;");

            while (resultado.next()) {
                Cliente cliente = new PessoaJuridica(resultado.getInt(id_clientes), resultado.getString(nome), resultado.getString(endereco), resultado.getDate(data), resultado.getString(id_pj), resultado.getString(cnpj), resultado.getInt(num_funcionarios), resultado.getString(setor));
                clientes.add(cliente);
            }

            resultado.close();

            if (clientes.isEmpty()) {
                throw new ClienteInexistenteException("Se chegou aqui é porque não existem clientes cadastrados");
            }

            return clientes;


        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }

    }

    //tipo 1 é fisico, tipo 2 juridico
    public static Cliente obtemClientePorIdentificador(String cpf_cnpj, int tipo_cliente) throws ClienteInexistenteException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            if (tipo_cliente == 1) {
                ResultSet resultado = stmt.executeQuery("SELECT id_cliente, nome, endereco, data, id_pf, cpf, genero, idade FROM `clientes` WHERE cpf= "+ cpf_cnpj + ";");

                if (resultado.next()) {
                    return new PessoaFisica(resultado.getInt(id_clientes), resultado.getString(nome), resultado.getString(endereco), resultado.getDate(data), resultado.getString(id_pf), resultado.getString(cpf), resultado.getString(genero), resultado.getInt(idade));
                }
            }
            if (tipo_cliente == 2) {
                ResultSet resultado = stmt.executeQuery("SELECT id_cliente, nome, endereco, data, id_pj, cnpj, num_funcionarios, setor FROM `clientes` WHERE cnpj = " + cpf_cnpj +";");

                if (resultado.next()) {
                    return new PessoaJuridica(resultado.getInt(id_clientes), resultado.getString(nome), resultado.getString(endereco), resultado.getDate(data), resultado.getString(id_pj), resultado.getString(cnpj), resultado.getInt(num_funcionarios), resultado.getString(setor));
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        throw new ClienteInexistenteException("Não há cliente com esse identificador");
    }

    public static void InsereCliente(Cliente cliente) throws FalhaConexaoException, ClienteExistenteException{
        try {
            Connection conexao = Conexao.obtemConexao();

            if (cliente instanceof PessoaFisica) {
                PreparedStatement stmt = conexao.prepareStatement(
                        "INSERT INTO `internetBanking`.`clientes` " +
                                "(`nome`, `endereco`, `data`, `cpf`, `genero`, `idade`, `id_pf`) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)"
                );

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getEndereco());
                stmt.setDate(3, new java.sql.Date(cliente.getData().getTime()));
                stmt.setString(4, ((PessoaFisica) cliente).getCpf()); // Assumindo que PessoaFisica tem um método getCpf()
                stmt.setString(5, ((PessoaFisica) cliente).getGenero());
                stmt.setInt(6, ((PessoaFisica) cliente).getIdade());
                stmt.setString(7, ((PessoaFisica) cliente).getId_pf());

                stmt.execute();
            }
            if (cliente instanceof PessoaJuridica){
                PreparedStatement stmt = conexao.prepareStatement(
                        "INSERT INTO `internetBanking`.`clientes` " +
                                "(`nome`, `endereco`, `data`, `cnpj`, `num_funcionarios`, `setor`, `id_pj`) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)"
                );

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getEndereco());
                stmt.setDate(3, new java.sql.Date(cliente.getData().getTime()));
                stmt.setString(4, ((PessoaJuridica) cliente).getCnpj());
                stmt.setInt(5, ((PessoaJuridica) cliente).getNum_funcionarios());
                stmt.setString(6, ((PessoaJuridica) cliente).getSetor());
                stmt.setString(7, ((PessoaJuridica) cliente).getId_pj());

                stmt.execute();

            }

        }catch (SQLException e){
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new ClienteExistenteException("Ja existe esse cliente no banco");
            throw new Error(e.getMessage());
        }
    }

    public static void removeCliente(String cpf_cnpj, int tipo_cliente) throws FalhaConexaoException {
        try {


            Connection conexao = Conexao.obtemConexao();

            if (tipo_cliente == 1) {
                PreparedStatement stmt = conexao.prepareStatement("DELETE FROM clientes WHERE cpf = ?;");
                stmt.setString(1, cpf_cnpj);
                stmt.execute();
            }
            if (tipo_cliente == 2) {
                PreparedStatement stmt = conexao.prepareStatement("DELETE FROM clientes WHERE cnpj = ?;");
                stmt.setString(1, cpf_cnpj);
                stmt.execute();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}