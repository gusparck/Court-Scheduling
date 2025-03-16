package Entidades;

import java.time.LocalDateTime;

public class Reservas {
    private final Clientes usuario;
    private final Quadras quadra;
    private final LocalDateTime horario;

    public Reservas(Clientes usuario, Quadras quadra, LocalDateTime horario) {
        this.usuario = usuario;
        this.quadra = quadra;
        this.horario = horario;
    }

    public Clientes getUsuario() {return usuario;}

    public Quadras getQuadra() { return quadra;}

    public LocalDateTime getHorario() {return horario;}

}
