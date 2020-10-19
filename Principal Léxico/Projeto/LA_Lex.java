package Projeto;

public class LA_Lex {
	
	LeitorTXT reader;

		public LA_Lex(String arquivo) {
			reader = new LeitorTXT(arquivo);
			
		}
		
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
		
		private Token numero() {
					
			int number = 1;
			while (true) {
				char caractere = (char) reader.lerProximoCaractere();
				switch (number) {
				case 1 : if (Character.isDigit(caractere)) {number = 2;
						}else return null;
				case 2 : if (caractere == '.') {caractere = (char) reader.lerProximoCaractere();
						if (Character.isDigit(caractere)) {number = 3;
						}else return null;
						} else if (!Character.isDigit(caractere)) {reader.retroceder();
						return new Token(TipoToken.NInteiro,reader.getLexema());}
				case 3 : if (!Character.isDigit(caractere)) {reader.retroceder();
						return new Token(TipoToken.NReal, reader.getLexema());}
				}
			}
		}
		
		private Token operadorRelacional() {

			char c = (char) reader.lerProximoCaractere();
			switch (c) {
			case '=' : return new Token(TipoToken.ORIgual,reader.getLexema());
			case '>' : c = (char) reader.lerProximoCaractere();
			if (c == '=') {return new Token(TipoToken.ORMaiorIgual,reader.getLexema());
			}else reader.retroceder(); return new Token(TipoToken.ORMaior,reader.getLexema());
			case '<' : c = (char) reader.lerProximoCaractere();
			if (c == '>') {return new Token(TipoToken.ORDiferente,reader.getLexema());
			}else if (c == '=') {return new Token(TipoToken.ORMenorIgual,reader.getLexema());
			}else reader.retroceder(); return new Token(TipoToken.ORMenor,reader.getLexema());
			default  : return null;
			}
		}
		
		private Token parenteses() {
			
			char c = (char) reader.lerProximoCaractere();
			switch (c) {
			case '(' : return new Token(TipoToken.AbreParent,reader.getLexema());
			case ')' : return new Token(TipoToken.FechaParent,reader.getLexema());
			default  : return null;
			}
		}
		
		private Token delimitador() {

			char c = (char) reader.lerProximoCaractere();
			switch (c) {
			case ':' : return new Token(TipoToken.Delimitador,reader.getLexema());
			default  : return null;
			}
		}
		
		private Token opAritmetico() {
			
			char c = (char) reader.lerProximoCaractere();
			switch (c) {
			case '*' : return new Token(TipoToken.OAMultiplica,reader.getLexema());
			case '/' : return new Token(TipoToken.OADivide,reader.getLexema());
			case '+' : return new Token(TipoToken.OASoma,reader.getLexema());
			case '-' : return new Token(TipoToken.OASubtrai,reader.getLexema());
			default  : return null;
			}
		}
}