package Projeto;

public class LA_Lex {
	
	LeitorTXT reader;

		public LA_Lex(String arquivo) {
			reader = new LeitorTXT(arquivo);
			}
		
		public Token proximoToken() {

			int caractereLido = -1;
			while ((caractereLido = reader.lerProximoCaractere()) != -1) {
				char c = (char) caractereLido;

				if (c != ' ' && c != '\n') {
					switch (c) {
					case ':': return new Token(TipoToken.Delimitador, ":");
					case '*': return new Token(TipoToken.OAMultiplica, "*");
					case '/': return new Token(TipoToken.OADivide, "/");
					case '+': return new Token(TipoToken.OASoma, "+");
					case '-': return new Token(TipoToken.OASubtrai, "-");
					case '(': return new Token(TipoToken.AbreParent, "(");
					case ')': return new Token(TipoToken.FechaParent, ")");
					case '>':
						c = (char) reader.lerProximoCaractere();
						if (c == '=') return new Token(TipoToken.ORMaiorIgual, ">=");					
						else {
								reader.retroceder();
								return new Token(TipoToken.ORMaior, ">");
						}
					case '<':
						c = (char) reader.lerProximoCaractere();
						if (c == '=') {
							return new Token(TipoToken.ORMenorIgual, "<=");
						} else if (c == '>') {						
							return new Token(TipoToken.ORDiferente, "<>");
						} else { 
							reader.retroceder();
							return new Token(TipoToken.ORMenor, "<");
						}
					}
				}	
			}
			return null;
		}
}
