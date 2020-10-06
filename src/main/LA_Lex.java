package main;

public class LA_Lex {

	LeitorDeArquivosTexto ldat;

	char[] buffer_A;
	char[] buffer_B;
	char[] buffer;
	int ponteiro = 0;
	int caracterLido = -1;
	int bufferSize = 50;
	boolean endOfReading = false;
	boolean endOfProgram = false;

	public LA_Lex(String arquivo) {
		ldat = new LeitorDeArquivosTexto(arquivo);
		carregaBuffer(buffer_A);
		buffer = buffer_A;
	}

	public void carregaBuffer(char[] bufferAux) {
		if (bufferAux == buffer_A) {
			bufferAux = buffer_A = new char[bufferSize];
		} else {
			bufferAux = buffer_B = new char[bufferSize];
		}
		if (!endOfReading) {
			for (int i = 0; i < bufferAux.length; i++) {
				if ((caracterLido = ldat.lerProximoCaractere()) != -1) {
					bufferAux[i] = (char) caracterLido;
					// System.out.println(i+"-"+(char)caracterLido);
				} else {
					endOfReading = true;
					break;
				}
			}
		}

	}

	public char getChar(int ponteiro) { // Retorna o char da posição atual do ponteiro
		return buffer[ponteiro];
	}

	public char getNextChar(int ponteiro) {// Retorna o char da posição seguinte ao ponteiro
		if (ponteiro != (buffer.length - 1)) {
			return buffer[ponteiro + 1];
		} else if (swapBuffer() == null) {
			return '#';
		}
		return swapBuffer()[0];
	}

	public void switchBuffer() {// Troca o buffer em uso
		buffer = swapBuffer();
		if (endOfReading) {
			endOfProgram = true;
		}
	}

	public char[] swapBuffer() {// Retorna o buffer em espera
		if (buffer == buffer_A) {
			return buffer_B;
		}
		return buffer_A;
	}

	public String usedBuffer() {
		if (buffer == buffer_A) {
			return "buffer_A";
		}
		return "buffer_B";
	}

	public Token proximoToken() {

		for (int i = ponteiro; i < buffer.length; i++) {

			System.out.println(usedBuffer() + "[" + i + "] = \t" + buffer[i] + "\t");

			ponteiro++;
			if (i == (buffer.length - 1)) {
				carregaBuffer(swapBuffer());
			}

			char c = buffer[i];

			if (c != ' ' && c != '\n') {

				switch (c) {
				case ':': return new Token(TipoToken.Delim, ":");
				case '*': return new Token(TipoToken.OpAritMult, "*");
				case '/': return new Token(TipoToken.OpAritDiv, "/");
				case '+': return new Token(TipoToken.OpAritSoma, "+");
				case '-': return new Token(TipoToken.OpAritSub, "-");
				case '(': return new Token(TipoToken.AbrePar, "(");
				case ')': return new Token(TipoToken.FechaPar, ")");

				case '>':
					c = getNextChar(i);
					if (c == '=') return new Token(TipoToken.OpRelMaiorIgual, ">=");					
					return new Token(TipoToken.OpRelMaior, ">");
				case '<':
					c = getNextChar(i);
					if (c == '=') {
						return new Token(TipoToken.OpRelMenorIgual, "<=");
					} else if (c == '>') {						
						return new Token(TipoToken.OpRelDif, "<>");
					}
					return new Token(TipoToken.OpRelMenor, "<");
				}

			}

			if (i == (buffer.length - 1) && !endOfProgram) {
				switchBuffer();
				i = -1;
				ponteiro = 0;
			}
						
		}

		return null;
	}

}