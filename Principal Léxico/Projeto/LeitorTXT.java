package Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeitorTXT {

	private final static int BUFFER_SIZE = 20;
	Buffer buffer = new Buffer(BUFFER_SIZE);
	Lexema lexema = new Lexema();
	InputStream inputs;

	public LeitorTXT(String arquivo) {
		
		try {
			inputs = new FileInputStream(new File(arquivo));
			recarregarBuffer0();
		} catch (Exception e) {
			e.printStackTrace();
			}
	}

	public String getLexema() {
	
		return lexema.getNome();
	}
	
	public int lerProximoCaractere() {

		int caractere = lerCaractereDoBuffer();
		System.out.print((char) caractere);
		lexema.setNome(lexema.getNome() + (char) caractere);
		return caractere;
	}

	private int lerCaractereDoBuffer() {

		int caractere = buffer.vetor[buffer.getPonteiro()];
		incrementarPonteiro();
		return caractere;
	}

	private void incrementarPonteiro() {

		buffer.incrementaPonteiro();
		if (buffer.getPonteiro() == BUFFER_SIZE) {
			recarregarBuffer1();
		} else if (buffer.getPonteiro() == BUFFER_SIZE * 2) {
			recarregarBuffer0();
			buffer.setPonteiro(0);
			}
	}

	private void recarregarBuffer0() {

		if (buffer.isDuplo()) {
			buffer.setDuplo(false);

			try {
				for (int i = 0; i < BUFFER_SIZE; i++) {
					buffer.vetor[i] = inputs.read();
					if (buffer.vetor[i] == -1) {
						break;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
				}
		}
	}

	private void recarregarBuffer1() {

		if (!buffer.isDuplo()) {
			buffer.setDuplo(true);

			try {
				for (int i = BUFFER_SIZE; i < BUFFER_SIZE * 2; i++) {
					buffer.vetor[i] = inputs.read();
					if (buffer.vetor[i] == -1) {
						break;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
				}
		}
	}

	public void retroceder() {

		buffer.retrocederPonteiro();
		lexema.setNome(lexema.getNome().substring(0, lexema.getNome().length() - 1));
		if (buffer.getPonteiro() < 0) {
			buffer.setPonteiro(BUFFER_SIZE * 2 - 1);	
		}
	}
}