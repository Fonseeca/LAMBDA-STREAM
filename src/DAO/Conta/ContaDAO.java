package DAOs.Conta;

import DAOs.Cliente.ClienteExistenteException;
import DAOs.Cliente.ClienteInexistenteException;
import Entidades.Conta;
import Entidades.PessoaFisica;
import conexao.Conexao;
import conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;

public class ContaDAO {
    private static final int id_conta = 1;
    private static final int numero = 2;
    private static final int saldo = 3;
    private static final int limite = 4;
    private static final int tipo_conta = 5;
    private static final int id_agencia = 6;
    static final int DUPLICATE_KEY_ERROR_CODE = 1062;

    /*
     * criaTabelaCliente ok
     * obterCliente ok
     * obtemClientePorIdentificador ok
     * InsereCliente(Cliente cliente) ok
     * removeCliente(String cpf_cnpj, int tipo_cliente) ok
     * */

    public static void criaTabelaConta() throws FalhaConexaoException {

        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS `internetBanking`.`contas` (\n" +
                    "  `id_conta` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `numero` VARCHAR(45) NOT NULL,\n" +
                    "  `saldo` DECIMAL(9,2) NOT NULL,\n" +
                    "  `limite` DECIMAL(9,2) NOT NULL,\n" +
                    "  `tipo_conta` INT NOT NULL,\n" +
                    "  `id_agencia` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_conta`),\n" +
                    "  INDEX `fk_contas_agencias1_idx` (`id_agencia` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_contas_agencias1`\n" +
                    "    FOREIGN KEY (`id_agencia`)\n" +
                    "    REFERENCES `internetBanking`.`agencias` (`id_agencia`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");
        } catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }

    public static ArrayList<Conta> obterContas() throws FalhaConexaoException, ContaInexistenteException {

        try {
            Connection conexao = Conexao.obtemConexao();
            ArrayList<Conta> contas = new ArrayList<>();

            Statement stmt = conexao.createStatement();

            ResultSet resultado = stmt.executeQuery("SELECT * FROM contas");

            while (resultado.next()) {
                Conta conta = new Conta(resultado.getInt(id_conta), resultado.getString(numero), resultado.getDouble(saldo), resultado.getDouble(limite), resultado.getInt(tipo_conta), resultado.getInt(id_agencia));
                contas.add(conta);
            }
            resultado.close();

            if (contas.isEmpty()) {
                throw new ContaInexistenteException("Se chegou aqui é porque não existem contas cadastradas");
            }

            return contas;


        }catch (SQLException e){
            throw new Error(e.getMessage());
        }

    }

    public static Conta obtemContaPorIdentificador(String numero) throws ContaInexistenteException, FalhaConexaoException{

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();

            ResultSet resultado = stmt.executeQuery("SELECT * FROM `contas` WHERE numero= " + numero + ";");

            return new Conta(resultado.getInt("id_conta"), resultado.getString("numero"), resultado.getDouble("saldo"), resultado.getDouble("limite"), resultado.getInt("tipo_conta"), resultado.getInt("id_agencia"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        throw new ContaInexistenteException("Não há conta com esse identificador");
    }

    public static void insereConta(Conta conta) throws FalhaConexaoException, ContaExistenteException{

        try{

            Connection conexao = Conexao.obtemConexao();

            PreparedStatement stmt = conexao.prepareStatement(
                    "INSERT INTO `internetBanking`.`contas` " +
                            "(`id_conta`, `numero`, `saldo`, `limite`, `tipo_conta`, `id_agencia`) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );

            stmt.setInt(1, conta.getId_conta());
            stmt.setString(2, conta.getNumero());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setDouble(4, conta.getLimite());
            stmt.setInt(5, conta.getTipo_conta());
            stmt.setInt(6, conta.getId_agencia());

            stmt.execute();
        }catch (SQLException e){
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new ContaExistenteException("Ja existe essa conta no banco");
            throw new Error(e.getMessage());
        }
    }

    public static void removeConta(String numero) throws ContaInexistenteException, FalhaConexaoException{

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM contas WHERE numero = ?;");
            stmt.setString(1, numero);
            stmt.execute();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
