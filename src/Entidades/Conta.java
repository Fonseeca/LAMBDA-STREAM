package Entidades;

public class Conta {

    private int id_conta;
    private String numero;
    private double saldo;
    private double limite;
    private int tipo_conta;
    private int id_agencia;

    public Conta(int id_conta, String numero, double saldo, double limite, int tipo_conta, int id_agencia) {
        this.id_conta = id_conta;
        this.numero = numero;
        this.saldo = saldo;
        this.limite = limite;
        this.tipo_conta = tipo_conta;
        this.id_agencia = id_agencia;
    }


    @Override
    public String toString() {
        return "Conta {\n" +
                "    id_conta=" + id_conta + ",\n" +
                "    numero=" + numero + ",\n" +
                "    saldo=" + saldo + ",\n" +
                "    limite=" + limite + ",\n" +
                "    tipo_conta=" + tipo_conta + ",\n" +
                "    id_agencia=" + id_agencia + "\n" +
                '}';
    }

    public int getId_conta() {
        return id_conta;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public int getTipo_conta() {
        return tipo_conta;
    }

    public int getId_agencia() {
        return id_agencia;
    }
}
