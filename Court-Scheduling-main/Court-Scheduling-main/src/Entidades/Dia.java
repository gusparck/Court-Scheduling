package Entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Dia {
    String quadraPertencente;
    private final LocalDateTime dia;
    private final ArrayList<Hora> horasDisponiveis;

    public static class Hora{
        private final LocalDateTime horaInicio;
        private final LocalDateTime horaFim;

        public Hora(LocalDateTime horaInicio, LocalDateTime horaFim) {
            this.horaInicio = horaInicio;
            this.horaFim = horaFim;
        }

        public LocalDateTime getHoraInicio() {return horaInicio;}
        public LocalDateTime getHoraFim() {return horaFim;}
    }

    public Dia(String quadraPertencente, LocalDateTime dia) {
        this.quadraPertencente = quadraPertencente;
        this.dia = dia;
        this.horasDisponiveis = new ArrayList<Hora>();
    }

    public String getQuadraPertencente() {return quadraPertencente;}
    public LocalDateTime getDia() {return dia;}
    public List<Hora> getHorasDisponiveis() {return horasDisponiveis;}
}
