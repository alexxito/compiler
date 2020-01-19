
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Arturo Villalvazo & Alexis Moran
 */
public class Lexer {

    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static ArrayList<Lexico> lex(int numberLine, String input) {
        final ArrayList<Lexico> tokens = new ArrayList<>();
        String word = "";
        final StringTokenizer strings = new StringTokenizer(input, "\"");
        final StringTokenizer st = new StringTokenizer(strings.nextToken());
//        boolean isVariableValue = false;
//        StringBuilder variableValue = new StringBuilder("");
        
        while (st.hasMoreTokens()) {
            word = st.nextToken();
            boolean matched = false;
            

            for (Tokens tokenTipo : Tokens.values()) {
                Pattern patron = Pattern.compile(tokenTipo.regex);
                Matcher matcher = patron.matcher(word);
                if (matcher.matches()) {

                    
                    
                    if(tokenTipo.id == 28){
//                        throw new RuntimeException("Se encontró un token invalido ::: Línea -> " + numberLine + " -> " + word);
                    }
                    
//                    if(isVariableValue){
//                        variableValue.append(word + " ");
//                    }
                    
                    
                    tokens.add(new Lexico(
                            numberLine,
                            tokenTipo.id,
                            tokenTipo.name,
                            word,
                            tokenTipo.dataType,
                            ""
                    ));

                    matched = true;
                    break;
                }
            }
            
            
            

//            if (!matched) {
//                throw new RuntimeException("Se encontró un token invalido ::: Línea -> " + numberLine + " -> " + word);
//            }
        }
        
        if(strings.hasMoreTokens()){
                word = strings.nextToken();
                word = "\"" + word + "\"";
                System.out.println("``` " + word);
                Pattern patron = Pattern.compile(Tokens.STRING_VALUE.regex);
                Matcher matcher = patron.matcher(word);
                if (matcher.matches()) {
                    tokens.add(new Lexico(
                            numberLine,
                            2,
                            "Cadena",
                            word,
                            2,
                            ""
                    ));
                    
//                if(tokenTipo.id == 28){
//                        throw new RuntimeException("Se encontró un token invalido ::: Línea -> " + numberLine + " -> " + word);
//                }
                }else{
                    throw new RuntimeException("Se encontró un token invalido ::: Línea -> " + numberLine + " -> " + word);
//                    tokens.add(new Lexico(
//                            numberLine,
//                            2,
//                            "Cadena",
//                            word,
//                            2,
//                            ""
//                    ));
                }
            }
        //for(Lexico t: tokens){
        //    System.out.println("### -> " + t.getValue());
        //}
        return tokens;
    }

    public static void printToken(Lexico lex) throws IOException {

        try {
            String token = String.format("| %-20s | | %-20s | | %-40s |\n", lex.getNumberLine(), lex.getId() + " " + lex.getType(), lex.getValue());
            bw.write(token);
        } catch (IOException ioe) {
            System.out.println("\n\n\nLexer Class Error -> PrintTokens Method ->   " + ioe.getMessage() + "\n\n\n");
        } finally {
            bw.flush();
        }

    }

    public static void printTokens(ArrayList<Lexico> tokens) throws IOException {
        try {
            for (Lexico token : tokens) {
                printToken(token);
            }
        } catch (IOException ioe) {
            System.out.println("\n\n\nLexer Class Error -> PrintTokens Method ->   " + ioe.getMessage() + "\n\n\n");
        } finally {
            bw.flush();
        }
    }

}
