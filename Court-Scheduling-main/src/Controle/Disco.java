package Controle;

import java.io.*;
import java.util.ArrayList;
import Entidades.Clientes;
import Entidades.Quadras;

public class Disco {
    private final Controle ctrl;
    private static final String FILE_NAME = "Data/clientes.dat";
    private static final String FILE_QUADRA = "Data/quadras.dat";

    public Disco(Controle ctrl) {
        this.ctrl = ctrl;
    }

    public void salvarClientes() {
        File file = new File(FILE_NAME);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(ctrl.getClientes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void salvarQuadras() {
        File file = new File(FILE_QUADRA);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_QUADRA))) {
            oos.writeObject(ctrl.getQuadras());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarClientes() {
        File file = new File(FILE_NAME);
        if (file.exists()) { // Verifica se o arquivo existe antes de carregar
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                ArrayList<Clientes> c = (ArrayList<Clientes>) ois.readObject();
                ctrl.setClientes(c);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ctrl.setClientes(new ArrayList<>());
        }
    }
    @SuppressWarnings("unchecked")
    public void carregarQuadras() {
        File file = new File(FILE_QUADRA);
        if (file.exists()) { // Verifica se o arquivo existe antes de carregar
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                ArrayList<Quadras> q = (ArrayList<Quadras>) ois.readObject();
                ctrl.setQuadras(q);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ctrl.setQuadras(new ArrayList<>());
        }
    }
}