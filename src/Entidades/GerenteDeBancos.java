import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class GerenteDeBancos {

  public static String procurarBancoPorId(int idBanco, List<Banco> listaBancos) {
    return listaBancos.stream()
      .filter(banco -> banco.getId_banco() == idBanco)
      .map(Banco::getNome)
      .findFirst()
      .orElse("Banco não encontrado");
  }

  public static List<Banco> buscarBancosPorNomeIniciadoCom(char letraInicial, List<Banco> listaBancos) {
    return listaBancos.stream()
      .filter(banco -> banco.getNome().toLowerCase().charAt(0) == Character.toLowerCase(letraInicial))
      .collect(Collectors.toList());
  }

  public static long contarTotalDeBancos(List<Banco> listaBancos) {
    return listaBancos.stream()
      .count();
  }

  public static List<Banco> listarBancosOrdenadosPorId(List<Banco> listaBancos) {
    return listaBancos.stream()
      .sorted(Comparator.comparingInt(Banco::getId_banco))
      .collect(Collectors.toList());
  }

  public static List<Banco> listarBancosComNomeMenorQueX(int limiteDeCaracteres, List<Banco> listaBancos) {
    return listaBancos.stream()
      .filter(banco -> banco.getNome().length() < limiteDeCaracteres)
      .collect(Collectors.toList());
  }
}
