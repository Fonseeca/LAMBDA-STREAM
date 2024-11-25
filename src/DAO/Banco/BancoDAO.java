package DAOs.Banco;

import DAOs.Conta.ContaExistenteException;
import Entidades.Banco;
import conexao.Conexao;
import conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;

public class BancoDAO {

    private static final int id_bancos = 1;
    private static final int nome = 2;
    static final int DUPLICATE_KEY_ERROR_CODE = 1062;
    /*
     * criaTabelaCliente ok
     * obterCliente ok
     * InsereCliente(Cliente cliente) ok
     * removeCliente(String cpf_cnpj, int tipo_cliente) ok
     * */

    public static void criaTabelaBanco() throws FalhaConexaoException{

        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS `internetBanking`.`bancos` (\n" +
                    "  `id_banco` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `nome` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id_banco`))\n" +
                    "ENGINE = InnoDB;");
        } catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }

    public static ArrayList<Banco> obterBancos() throws FalhaConexaoException, BancoInexistenteException{

        try {
            Connection conexao = Conexao.obtemConexao();
            ArrayList<Banco> Bancos = new ArrayList<>();

            Statement stmt = conexao.createStatement();

            ResultSet resultado = stmt.executeQuery("SELECT * FROM bancos");

            while (resultado.next()) {
                Banco banco = new Banco(resultado.getInt(id_bancos), resultado.getString(nome));
                Bancos.add(banco);
            }
            resultado.close();

            if (Bancos.isEmpty()) {
                throw new BancoInexistenteException("Se chegou aqui é porque não existem bancos cadastrados");
            }

            return Bancos;

        }catch (SQLException e){
            throw new Error(e.getMessage());
        }

    }

    public static void insereBanco(Banco banco) throws FalhaConexaoException, BancoExistenteException{

        try {
            Connection conexao = Conexao.obtemConexao();

            PreparedStatement stmt = conexao.prepareStatement(
                    "INSERT INTO `internetBanking`.`bancos` " +
                            "(`id_banco`, `nome`) " +
                            "VALUES (?, ?)"
            );

            stmt.setInt(1, banco.getId_banco());
            stmt.setString(2, banco.getNome());

            stmt.execute();

        }catch (SQLException e){
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new BancoExistenteException("Ja existe esse Banco");
            throw new Error(e.getMessage());
        }

    }

    public static void removeBanco(String nome) throws FalhaConexaoException, BancoInexistenteException{
        try {
            Connection conexao = Conexao.obtemConexao();

            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM bancos WHERE nome = ?;");

            stmt.setString(1, nome);

            stmt.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
