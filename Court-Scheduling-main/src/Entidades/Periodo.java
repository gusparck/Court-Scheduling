package Entidades;

import java.time.LocalDateTime;

public class Periodo {
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Periodo(LocalDateTime inicio, LocalDateTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalDateTime getInicio() { return inicio;}

    public LocalDateTime getFim() { return fim;}
}
