package model;

public class Imagem {
    private String nome;
    private String caminho;
    private boolean isPrincipal; 

    public Imagem(String nome, String caminho, boolean isPrincipal) {
        this.nome = nome;
        this.caminho = caminho;
        this.isPrincipal = isPrincipal; 
    }

    public Imagem() {

        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(boolean principal) {
        isPrincipal = principal;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Caminho: " + caminho + ", Principal: " + isPrincipal;
    }
}
