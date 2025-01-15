package Entidades;

public class Quadras {
    private int id;
    private String nome;
    private String endereco;
    
    //private ArrayList<Horarios> disponibilidade; //não sei como implementar

    public Quadras(int id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getId(){return id;}
    public void setId(int id ){this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

}
