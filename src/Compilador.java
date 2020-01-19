import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arturo Villalvazo & Alexis Moran
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //int cont = 0;
        //String cLin="";
        Purifier purifier = new Purifier();
        ArrayList<PurifiedLine> lines = purifier.purifyDoc();
        List<String> values = new ArrayList<>();
        List<String> variables = new ArrayList<>();
        List<String> funciones = new ArrayList<>();
        GeneradorASM gasm = new GeneradorASM();

        Semantic s = new Semantic();
        Parser p = new Parser();
        ArrayList<String> countersLoop = gasm.getCountersLoop();
        ArrayList<String> countersIf = gasm.getCountersIf();
        ArrayList<Integer> countersOperators = gasm.getCountersOperators();
        boolean flagLoop = false;
        boolean flagInput = false;
        boolean flagIf = false;
        int contVarIf = 0;
        for (PurifiedLine line : lines) {
            ArrayList<Lexico> tokens = Lexer.lex(line.getNumberLine(), line.getLine());
            for (Lexico lx : tokens) {
                if (lx.getId() == 22 || lx.getId() == 25 || lx.getId() == 26 || lx.getId() == 24 || lx.getId() == 21 || ( lx.getId() > 14 && lx.getId() < 20 )) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    funciones.add(lx.getValue());
                    if (lx.getId() == 25)
                        flagLoop = true;
                    else if (lx.getId() == 24)
                        flagLoop = false;

                    if (lx.getId() == 26)
                        flagInput = true;

                    if(lx.getId() == 21)
                        flagIf = true;

                    if ( lx.getId() > 14 && lx.getId() < 20 ) {
                        countersOperators.add(lx.getId()-14);
                    }
                } else if (lx.getId() == 1 && flagLoop) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    countersLoop.add(lx.getValue());
                    flagLoop = false;
                } else if (lx.getId() == 27 && flagLoop) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    for (Lexico x : s.getVariables()) {
                        if (x.getValue().equalsIgnoreCase(lx.getValue())) {
                            countersLoop.add("ax");
                            break;
                        }
                    }
                    flagLoop = false;
                }  else if(lx.getId() == 1 && flagIf){
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    funciones.add(lx.getValue());
                    System.out.println("................. NUMERIC IF");
                    System.out.println("##### " + lx.getValue());
                    countersIf.add(lx.getValue());
                    contVarIf++;
                    if (contVarIf == 2){
                        contVarIf = 0;
                        flagIf = false;
                    }
                } else if(lx.getId() == 27 && flagIf){
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    funciones.add(lx.getValue());
                    System.out.println("................. ID IF");
                    for (Lexico x : s.getVariables()) {
                        if (x.getValue().equalsIgnoreCase(lx.getValue())) {
                            System.out.println("%%%% " + lx.getValue());
                            if(flagInput){
                                countersIf.add("ax");
                                flagInput = false;
                            }else{
                                countersIf.add(x.getVariableValue());
                            }
                            //countersIf.add("ax");
                            break;
                        }
                    }
                    contVarIf++;
                    if (contVarIf == 2){
                        contVarIf = 0;
                        flagIf = false;
                    }
                }else if (lx.getId() == 27 && flagInput) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    funciones.add(lx.getValue());
                    //flagInput = false;
                }else if (lx.getId() == 27) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    //System.out.println("&&&&&&&&&&&&& " + lx.getValue());
//                    for(Lexico x: s.getVariables()){
//                        if(x.getValue().equalsIgnoreCase(lx.getValue())){
//                            countersLoop.add( Integer.parseInt(x.getVariableValue()) );
//                        }
//                    }
                    //System.out.println("&&&&&&&&&&&&&&&&&&&&& " + s.getVariables().indexOf(lx.getValue()));
                    //countersLoop.add( Integer.parseInt(lx.getVariableValue()) );
                    funciones.add(lx.getValue());
                } else if (lx.getId() == 21) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    funciones.add(lx.getValue());
                    flagIf = true;
                } else if (lx.getId() == 15 || lx.getId() == 16 || lx.getId() == 17 || lx.getId() == 18 || lx.getId() == 19 || lx.getId() == 1) {
                    System.out.println(lx.getId() + "::::> " + lx.getValue());
                    funciones.add(lx.getValue());
                }
                //System.out.println(funciones.toString());
            }

            p.init(tokens, line.getNumberLine(), s);

        }
        for (Lexico var : p.getSemantic().getVariables()) {
            variables.add(var.getValue());
            if (var.getVariableValue().equals("\" \"")) {
                values.add("db 64,?,64 dup('$')");
            } else {
                values.add(var.getVariableValue());
            }
            //System.out.println("..... " + var.getVariableValue());
        }


        int loop = p.counterLoop;
        s.checkGeneralBrackets();
        gasm.setCountersLoop(countersLoop);
        gasm.setCountersIf(countersIf);


        gasm.escribeEncabezado();
        gasm.escrbirVariables(variables, values);
        gasm.escribeCuerpo();
//        for (String w: funciones) {
//            System.out.println(w);
//        }
        gasm.escribeOperaciones(funciones, loop, p.countNextLoop, s);
        gasm.escribeFinal();
    }
}
