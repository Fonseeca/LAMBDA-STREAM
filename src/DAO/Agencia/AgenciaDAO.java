package DAOs.Agencia;

import DAOs.Banco.BancoExistenteException;
import DAOs.Banco.BancoInexistenteException;
import Entidades.Agencia;
import Entidades.Banco;
import conexao.Conexao;
import conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;

public class AgenciaDAO{

    private static final int id_agencias = 1;
    private static final int numero = 2;
    private static final int id_banco = 3;
    static final int DUPLICATE_KEY_ERROR_CODE = 1062;

    /*
     * criaTabelaCliente ok
     * obterCliente ok
     * InsereCliente(Cliente cliente) ok
     * removeCliente(String cpf_cnpj, int tipo_cliente) ok
     * */

    public static void criaTabelaAgencia() throws FalhaConexaoException{

        try {
            Connection conexao = Conexao.obtemConexao();

            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS `internetBanking`.`agencias` (\n" +
                    "  `id_agencia` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `numero` INT NOT NULL,\n" +
                    "  `id_banco` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_agencia`),\n" +
                    "  INDEX `fk_agencias_bancos1_idx` (`id_banco` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_agencias_bancos1`\n" +
                    "    FOREIGN KEY (`id_banco`)\n" +
                    "    REFERENCES `internetBanking`.`bancos` (`id_banco`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");
        } catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }

    public static ArrayList<Agencia> obterAgencias() throws FalhaConexaoException, AgenciaInexistenteException {

        try {
            Connection conexao = Conexao.obtemConexao();
            ArrayList<Agencia> Agencias = new ArrayList<>();

            Statement stmt = conexao.createStatement();

            ResultSet resultado = stmt.executeQuery("SELECT * FROM agencias");

            while (resultado.next()) {
                Agencia agencia = new Agencia(resultado.getInt(id_agencias), resultado.getInt(numero), resultado.getInt(id_banco));
                Agencias.add(agencia);
            }

            resultado.close();

            if (Agencias.isEmpty()) {
                throw new AgenciaInexistenteException("Se chegou aqui é porque não existem agencias cadastradas");
            }

            return Agencias;


        }catch (SQLException e){
            throw new Error(e.getMessage());
        }

    }

    public static void insereAgencia(Agencia agencia) throws FalhaConexaoException, AgenciaExistenteException{

        try {
            Connection conexao = Conexao.obtemConexao();

            PreparedStatement stmt = conexao.prepareStatement(
                    "INSERT INTO `internetBanking`.`agencias` " +
                            "(`id_agencia`, `numero`, `id_banco`) " +
                            "VALUES (?, ?, ?)"
            );

            stmt.setInt(1, agencia.getId_agencias());
            stmt.setInt(2, agencia.getNumero());
            stmt.setInt(3, agencia.getId_banco());

            stmt.execute();

        }catch (SQLException e){
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new AgenciaExistenteException("Ja existe essa agencia");
            throw new Error(e.getMessage());
        }

    }

    public static void removeAgencia(int numero) throws FalhaConexaoException, AgenciaInexistenteException{
        try {
            Connection conexao = Conexao.obtemConexao();

            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM agencias WHERE numero = ?;");

            stmt.setInt(1, numero);

            stmt.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}