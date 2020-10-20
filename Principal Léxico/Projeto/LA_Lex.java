package Projeto;

public class LA_Lex {
	
	LeitorTXT reader;

		public LA_Lex(String arquivo) {
			reader = new LeitorTXT(arquivo);
			
		}
		
		public Token proximoToken() {
			
			Token token = null;
			espacoEComentario();
			reader.confirmar();
			
			token = fim();
			if (!(zerarOUconfirmar(token) == null))
				return token;		
			token = palavraChave();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = variavel();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = numero();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = opAritmetico();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = opRelacional();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = delimitador();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = parenteses();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			token = cadeia();
			if (!(zerarOUconfirmar(token) == null))
				return token;
			System.err.println("Erro Léxico!");
			System.err.println(reader.toString());
			return null;
		}
		
		public Token zerarOUconfirmar(Token token) {
			
			if(token == null) {
				reader.zerar();
			} else {
				reader.confirmar();
				return token;
			}
			return null;
		}
		
		private Token fim() {
			int caractere = reader.lerProximoCaractere();
			if (caractere == -1) {
				return new Token(TipoToken.Fim,"Fim");
			}
			return null;
		}
		
		private Token palavraChave() {
			
			while(true) {
				char caractere = (char) reader.lerProximoCaractere();
				if(!Character.isLetter(caractere)) {
					reader.retroceder();
					String lexema = reader.getLexema();
					switch (lexema) {
					case "DECLARACOES" : return new Token(TipoToken.PCDeclaracoes,lexema);
					case "ALGORITMO" : return new Token(TipoToken.PCAlgoritmo,lexema);
					case "INT" : return new Token(TipoToken.PCInteiro,lexema);
					case "REAL" : return new Token(TipoToken.PCReal,lexema);
					case "ATRIBUIR" : return new Token(TipoToken.PCAtribuir,lexema);
					case "A" : return new Token(TipoToken.PCA,lexema);
					case "LER" : return new Token(TipoToken.PCLer,lexema);
					case "IMPRIMIR" : return new Token(TipoToken.PCImprimir,lexema);
					case "SE" : return new Token(TipoToken.PCSe,lexema);
					case "ENTAO" : return new Token(TipoToken.PCEntao,lexema);
					case "ENQUANTO" : return new Token(TipoToken.PCEnquanto,lexema);
					case "INICIO" : return new Token(TipoToken.PCInicio,lexema);
					case "FIM" : return new Token(TipoToken.PCFim,lexema);
					case "E" : return new Token(TipoToken.OBE,lexema);
					case "OU" : return new Token(TipoToken.OBOU,lexema);
					default : return null;
					}
				}
			}
		}
		
		private void espacoEComentario() {

			System.out.println("");
			int number = 1 ;
			while (true) {
				char caractere = (char) reader.lerProximoCaractere();
				switch (number) {
				case 1 : if (Character.isWhitespace(caractere) || caractere == ' ') {number = 2;
						}else if (caractere == '%') {number = 3;
						}else {reader.retroceder();return;}break;
				case 2 : if (caractere == '%') {number = 3;
						}else if (!Character.isWhitespace(caractere) || caractere == ' ') {
						reader.retroceder();return;}break;
				case 3 : if (caractere == '\n'){
							caractere = (char) reader.lerProximoCaractere();
							if(!(caractere == '%')) {
								reader.retroceder();
								return;
							}
						}
				}
			}
		}
		
		private Token cadeia() {
			
			int number = 1 ;
			while (true) {
				char caractere = (char) reader.lerProximoCaractere();
				switch (number) {
				case 1 : if (caractere == '\'') {number = 2;
						} else return null;
				case 2 : if (caractere == '\n') return null;
						 if (caractere =='\'') {return new Token(TipoToken.Cadeia,reader.getLexema());
						}else if (caractere == '\\') number = 3;
				case 3 : if (caractere == '\n') {return null;
						} else number = 2 ;
				}
			}
		}
	
		private Token variavel() {
			
			int number = 1;
			while (true) {
				char caractere = (char) reader.lerProximoCaractere();
				switch (number) {
				case 1 : if (Character.isLetter(caractere)) {number= 2;
						}else return null;
				case 2 : if(!Character.isLetterOrDigit(caractere)) {reader.retroceder();
						 return new Token(TipoToken.Variavel,reader.getLexema());}
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
						}else if (!Character.isDigit(caractere)) {reader.retroceder();
						 return new Token(TipoToken.NInteiro,reader.getLexema());}
				case 3 : if (!Character.isDigit(caractere)) {reader.retroceder();
						 return new Token(TipoToken.NReal, reader.getLexema());}
				}
			}
		}
		
		private Token opRelacional() {

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