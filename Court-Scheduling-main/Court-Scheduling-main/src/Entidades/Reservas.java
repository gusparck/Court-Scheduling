package Entidades;

import java.time.LocalDateTime;

public class Reservas {
    private int id;
    private Usuarios usuario;
    private Quadras quadra;
    private LocalDateTime horario;

    public Reservas(int id, Usuarios usuario, Quadras quadra, LocalDateTime horario) {
        this.id = id;
        this.usuario = usuario;
        this.quadra = quadra;
        this.horario = horario;
    }

    public int getId() {return id;}

    public Usuarios getUsuario() {return usuario;}

    public Quadras getQuadra() { return quadra;}

    public LocalDateTime getHorario() {return horario;}

}
