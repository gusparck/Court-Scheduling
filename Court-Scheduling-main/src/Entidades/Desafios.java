package Entidades;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Desafios implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Clientes desafiante;
    private final Clientes desafiado;
    private final Quadras quadraDesafio;
    private transient final LocalDateTime horarioDesafio;
    private Boolean statusDesafio; //true para aceito e false para recusado, null nao respodido

    public Desafios(Clientes desafiante, Clientes desafiado,
                    Quadras quadraDesafio, LocalDateTime horarioDesafio) {
        this.desafiante = desafiante;
        this.desafiado = desafiado;
        this.quadraDesafio = quadraDesafio;
        this.horarioDesafio = horarioDesafio;
        this.statusDesafio = null;
    }

    public Clientes getDesafiante() { return desafiante; }

    public Clientes getDesafiado() { return desafiado; }

    public Quadras getQuadraDesafio() { return quadraDesafio; }

    public LocalDateTime getHorarioDesafio() { return horarioDesafio; }

    public Boolean getStatusDesafio() { return statusDesafio; }
    public void setStatusDesafio(boolean statusDesafio) { this.statusDesafio = statusDesafio; }
}