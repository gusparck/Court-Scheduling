package Entidades;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;

public class Reservas implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Clientes cliente;
    private final Quadras quadra;
    private transient final LocalDateTime horario;

    public Reservas(Clientes cliente, Quadras quadra, LocalDateTime horario) {
        this.cliente = cliente;
        this.quadra = quadra;
        this.horario = horario;
    }

    public Clientes getCliente() {return cliente;}

    public Quadras getQuadra() { return quadra;}

    public LocalDateTime getHorario() {return horario;}

}
