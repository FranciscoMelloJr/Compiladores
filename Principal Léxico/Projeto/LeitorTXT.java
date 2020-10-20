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
			chargeBuffer0();
		} catch (Exception e) {
			e.printStackTrace();
			}
	}

	public int nextCaracter() {

		int caractere = readCaracter();
		lexema.setNome(lexema.getNome() + (char) caractere);
		return caractere;
	}

	private int readCaracter() {

		int caractere = buffer.vetor[buffer.getPonteiro()];
		PonteiroPlus();
		return caractere;
	}

	private void PonteiroPlus() {

		buffer.incrementaPonteiro();
		if (buffer.getPonteiro() == BUFFER_SIZE) {
			chargeBuffer1();
		} else if (buffer.getPonteiro() == buffer.getTamanhoTotal()) {
			chargeBuffer0();
			buffer.setPonteiro(0);
			}
	}

	private void chargeBuffer0() {

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

	private void chargeBuffer1() {

		if (!buffer.isDuplo()) {
			buffer.setDuplo(true);

			try {
				for (int i = BUFFER_SIZE; i < buffer.getTamanhoTotal(); i++) {
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

	public void back() {

		buffer.retrocederPonteiro();
		lexema.setNome(lexema.getNome().substring(0, lexema.getNome().length() - 1));
		if (buffer.getPonteiro() < 0) {
			buffer.setPonteiro(buffer.getTamanhoTotal() - 1);
		}
	}
	
	public String getLexema() {
		
		return lexema.getNome();
	}
	
	public void confirmar() {
		
		lexema.doConfirmar(buffer);
	}
	
	public void zerar() {
		
		lexema.doZerar(buffer);
	}
	
}