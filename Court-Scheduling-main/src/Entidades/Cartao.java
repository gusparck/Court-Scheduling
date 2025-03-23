package Entidades;

import java.io.Serial;
import java.io.Serializable;

public class Cartao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nomeCartao;
    private String numCartao;
    private String cvc;
    private String dataVencimento;
    private double saldo;

    public Cartao(String nomeCartao, String numCartao, String cvc, String dataVencimento, double saldo) {
        this.nomeCartao = nomeCartao;
        this.numCartao = numCartao;
        this.cvc = cvc;
        this.dataVencimento = dataVencimento;
        this.saldo = saldo;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public String getCvc() {
        return cvc;
    }
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void sangriaPagamento(double valor){
        this.saldo -= valor;
    }

}
