package main;

public class LA_Lex {

	LeitorDeArquivosTexto ldat;

	public LA_Lex(String arquivo) {
		ldat = new LeitorDeArquivosTexto(arquivo);
	}
	
//	public Token proximoToken() {
//		Token proximo = null;
//	}
	
	private Token fim() {
		int caractereLido = ldat.lerProximoCaractere();
		if (caractereLido == -1) {
			return new Token(TipoToken.Fim,"Fim");
		}
		return null;
	}
	
	private Token palavrasChave() {
		
		while(true) {
			char c = (char) ldat.lerProximoCaractere();
			if(!Character.isLetter(c)) {
				ldat.retroceder();
				String lexema=ldat.getLexema();
				if (lexema.equals("DECLARACOES")) {
					return new Token(TipoToken.PCDeclaracoes,lexema);
				} else if (lexema.equals("ALGORITMO")) {
					return new Token(TipoToken.PCAlgoritmo,lexema);
				} else if (lexema.equals("INT")) {
					return new Token(TipoToken.PCInteiro,lexema);
				} else if (lexema.equals("REAL")) {
					return new Token(TipoToken.PCReal,lexema);
				} else if (lexema.equals("ATRIBUIR")) {
					return new Token(TipoToken.PCAtribuir,lexema);
				} else if (lexema.equals("A")) {
					return new Token(TipoToken.PCA,lexema);
				} else if (lexema.equals("LER")) {
					return new Token(TipoToken.PCLer,lexema);
				} else if (lexema.equals("IMPRIMIR")) {
					return new Token(TipoToken.PCImprimir,lexema);
				} else if (lexema.equals("SE")) {
					return new Token(TipoToken.PCSe,lexema);
				} else if (lexema.equals("ENTAO")) {
					return new Token(TipoToken.PCEntao,lexema);
				} else if (lexema.equals("ENQUANTO")) {
					return new Token(TipoToken.PCEnquanto,lexema);
				} else if (lexema.equals("INICIO")) {
					return new Token(TipoToken.PCInicio,lexema);
				} else if (lexema.equals("FIM")) {
					return new Token(TipoToken.PCFim,lexema);
				} else if (lexema.equals("E")) {
					return new Token(TipoToken.OpBoolE,lexema);
				} else if (lexema.equals("OU")) {
					return new Token(TipoToken.OpBoolOU,lexema);
				} else {
					return null;
				}
			}
		}
	}
	
	private void espacosEComentarios() {
		
		int estado = 1 ;
		while (true) {
			char c = (char) ldat.lerProximoCaractere();
			if (estado == 1 ) {
				if (Character.isWhitespace(c) || c == ' ') {
					estado = 2;
				} else if (c == '%') {
					estado = 3;
				} else {
					ldat.retroceder();
					return;
				}
			} else if (estado == 2) {
				if ( c == '%') {
					estado = 3;
				} else if (!Character.isWhitespace(c) || c == ' ') {
					ldat.retroceder();
					return;
				}
			} else if (estado == 3) {
				if (c == '\n') {
					return;
				}
			}
		}
	}
	
	private Token cadeia() {
		int estado = 1 ;
		while (true ) {
			char c = (char) ldat.lerProximoCaractere();
			if (estado == 1) {
				if (c== '\'') {
					estado = 2;
			} else {
				return null;
			}
		} else if (estado == 2) {
			if (c == '\n') {
				return null;
			}
			if (c=='\'') {
				return new Token(TipoToken.Cadeia,ldat.getLexema());
			} else if (c == '\\') {
				estado = 3;
			}
		} else if (estado == 3) {
			if (c == '\n') {
				return null;
			} else {
				estado = 2 ;
			}
		}
	}
}
	
	private Token variavel() {
		int estado = 1;
		while (true) {
			char c = (char) ldat.lerProximoCaractere();
			if (estado == 1) {
				if (Character.isLetter(c)) {
					estado = 2;
				} else {
					return null;
				}
			} else if (estado == 2 ) {
				if(!Character.isLetterOrDigit(c)) {
					ldat.retroceder();
					return new Token(TipoToken.Var,ldat.getLexema());
				}
			}
		}
	}
	
	private Token numeros() {
		int estado = 1;
		while (true) {
			char c = (char) ldat.lerProximoCaractere();
			if (estado == 1) {
				if (Character.isDigit(c)) {
					estado = 2;
				} else {
					return null;
				}
			} else if (estado == 2) {
				if (c == '.') {
					c = (char) ldat.lerProximoCaractere();
					if (Character.isDigit(c)) {
						estado = 3;
					} else {
						return null;
					}
				} else if (!Character.isDigit(c)) {
					ldat.retroceder();
					return new Token(TipoToken.NumInt,ldat.getLexema());
				}
			}else if ( estado == 3 ) {
				if (!Character.isDigit(c)) {
					ldat.retroceder();
					return new Token(TipoToken.NumReal, ldat.getLexema());
				}
			}
		}
	}
	
	private Token operadorRelacional() {
		int caractereLido = ldat.lerProximoCaractere();
		char c = (char) caractereLido;
		if( c == '<') {
			c = (char) ldat.lerProximoCaractere();
			if (c == '>') {
				return new Token (TipoToken.OpRelDif,ldat.getLexema());
			} else if (c == '=') {
				return new Token(TipoToken.OpRelMenorIgual,ldat.getLexema());
			} else { 
				ldat.retroceder();
				return new Token(TipoToken.OpRelMaior,ldat.getLexema());
			}
			} else if (c == '=') {
				return new Token(TipoToken.OpRelIgual,ldat.getLexema());
			} else if (c == '>') { 
				c = (char) ldat.lerProximoCaractere();
				if (c == '=') {
					return new Token(TipoToken.OpRelMaiorIgual,ldat.getLexema());
				} else { 
					ldat.retroceder();
					return new Token(TipoToken.OpRelMaior,ldat.getLexema());
				}
			}
	return null;	
	}
	
	
	private Token parenteses() {
		int caractereLido = ldat.lerProximoCaractere();
		char c = (char) caractereLido;
		
		if (c== '(') {
			return new Token (TipoToken.AbrePar, ldat.getLexema());
		} else if (c == ')') {
			return new Token(TipoToken.FechaPar,ldat.getLexema());
		} else {
			return null;
		}
	}
	
	private Token delimitador() {
		int caractereLido = ldat.lerProximoCaractere();
		char c = (char) caractereLido;
		if (c == ':') {
			return new Token(TipoToken.Delim, ldat.getLexema());
		} else {
			return null;
		}
	}
	
	private Token operadorAritmetico() {
		int caractereLido = ldat.lerProximoCaractere();
		char c = (char) caractereLido;
		if (c == '*') {
			return new Token(TipoToken.OpAritMult, ldat.getLexema());
		} else if (c == '/') {
			return new Token(TipoToken.OpAritDiv, ldat.getLexema());
		} else if (c == '+') {
			return new Token(TipoToken.OpAritSoma, ldat.getLexema());
		} else if (c == '-') { 
			return new Token(TipoToken.OpAritSub, ldat.getLexema());
		} else { 
			return null;
		}
	}
	
	public Token proximoToken() {

		int caractereLido = -1;
		while ((caractereLido = ldat.lerProximoCaractere()) != -1) {
			char c = (char) caractereLido;

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
					c = (char) ldat.lerProximoCaractere();
					if (c == '=') return new Token(TipoToken.OpRelMaiorIgual, ">=");					
					else {
							ldat.retroceder();
							return new Token(TipoToken.OpRelMaior, ">");
					}
				case '<':
					c = (char) ldat.lerProximoCaractere();
					if (c == '=') {
						return new Token(TipoToken.OpRelMenorIgual, "<=");
					} else if (c == '>') {						
						return new Token(TipoToken.OpRelDif, "<>");
					} else { 
						ldat.retroceder();
						return new Token(TipoToken.OpRelMenor, "<");
					}
				}
			}	
		}
		return null;
	}		
	
	public Token proximoTokenV2() {
		
		Token proximo = null;
		espacosEComentarios();
		ldat.confirmar();
		//
		// nao consegue ler 2 linhas de comentarios seguidos. VERIFICAR!!
		//
		proximo = fim();
		
	}
}