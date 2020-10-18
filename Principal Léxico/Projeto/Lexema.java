package Projeto;

public class Lexema {

	String nome;
	int inicio;

	public Lexema() {
		this.inicio = 0;
		this.nome = "";

	}
	
	public void confirmar(Buffer buffer) {
		inicio = buffer.getPonteiro();
		
	}
	
	public void  zerar(Buffer buffer) {
		buffer.setPonteiro(this.inicio);
		nome = "";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

}
