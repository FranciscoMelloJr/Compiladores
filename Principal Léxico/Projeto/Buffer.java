package Projeto;

public class Buffer {

	public boolean duplo;
	public int[] vetor;
	public int ponteiro;

	public Buffer(int tamanho) {
		
		vetor = new int [tamanho*2];
		this.duplo = true;
		this.ponteiro = 0;
	}

	public void retrocederPonteiro() {
	
		this.ponteiro--;
	}

	public void incrementaPonteiro() {
	
		this.ponteiro++;
	}

	public boolean isDuplo() {
	
		return duplo;
	}

	public void setTamanho(int num) {
	
		this.vetor = new int[num];
	}

	public void setDuplo(boolean duplo) {
	
		this.duplo = duplo;	
	}

	public int getPonteiro() {
	
		return ponteiro;
	}

	public void setPonteiro(int ponteiro) {
	
		this.ponteiro = ponteiro;
	}
}