import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneradorASM {
    private static StringBuilder datos;
    private static StringBuilder func;
    private static StringBuilder macro;
    private static String encabezado = "";
    private static String cuerpo = "";
    private static String fin = "";
    private static ArrayList<String> countersLoop = new ArrayList<String>();
    private static ArrayList<String> countersIf = new ArrayList<String>();
    private static ArrayList<Integer> countersOperators = new ArrayList<Integer>();
    private static int contLabel = 0;
    private static int contLabel2 = 0;
    private static int contLabel3 = 0;
    private static int contLabel4 = 0;

    public static ArrayList<String> getCountersLoop() {
        return countersLoop;
    }

    public static void setCountersLoop(ArrayList<String> countersLoop) {
        GeneradorASM.countersLoop = countersLoop;
    }

    public static ArrayList<String> getCountersIf() {
        return countersIf;
    }

    public static void setCountersIf(ArrayList<String> countersIf) {
        GeneradorASM.countersIf = countersIf;
    }

    public static ArrayList<Integer> getCountersOperators() {
        return countersOperators;
    }

    public static void setCountersOperators(ArrayList<Integer> countersOperators) {
        GeneradorASM.countersOperators = countersOperators;
    }

    public void escribeEncabezado() {
        encabezado = "include macros2.inc\n" + "pila segment para stack 'stack'\n" +
                "\tdb 500 dup (?)\n" +
                "pila ends\n" +
                "\n" +
                "datos segment para public 'data'\n";
    }

    public void escrbirVariables(List variables, List values) {
        datos = new StringBuilder();
        for (int i = 0; i < variables.size(); i++) {
            if (values.get(i).toString().matches("^\\d+$")) {
                String valor = "db ";
                char[] numero = values.get(i).toString().toCharArray();
                if (numero[0] == 0) {

                }
                for (char num : numero) {
                    valor += ((Integer.toHexString((int) num))) + "h,";
                }
                datos.append("\t" + variables.get(i) + " " + valor + "10,'$'\n");
            } else if (values.get(i).toString().startsWith("db")) {
                //System.out.println("Aqui llegó");
                datos.append("\t" + variables.get(i) + " " + values.get(i).toString() + "\n");
            } else {
                String remplazo = " " + values.get(i).toString().substring(1, values.get(i).toString().length() - 1);
                datos.append("\t" + variables.get(i) + " db \"" + remplazo + "\",10,63 dup('$')\n");
            }
        }
        datos.append("datos ends\n" +
                "\n" +
                "extra segment para public 'data'\n" +
                "\n" +
                "extra ends\n");
    }

    public void escribeOperaciones(List functions, int loop, int count, Semantic s) {
        //System.out.println(",,,,,,,,,,,,, " + functions.size());
        func = new StringBuilder();
        boolean flagInput = false;
        boolean flagLoop = false;
        boolean flagIf = false;

        for (int i = 1; i < functions.size(); i++) {

            //System.out.println(",,,,,,,,,,,,,,,,,,, " + functions.get(i));
            if (functions.get(i).equals("print")) {
                func.append("\t\t\timprime " + functions.get(i + 1) + "\n");
            } else if (functions.get(i).equals("input")) {
                //func.append("\t\t\tleer " + functions.get(i + 1) + "\n");
                contLabel2++;
                //func.append(leerNumero(contLabel2, ""));
                flagInput = true;
            } else if (functions.get(i).equals("do")) {
                contLabel++;
                func.append("\t\tmov cx," + countersLoop.get(contLabel - 1) + "\n \t\tC" + contLabel + ":\n");
                flagLoop = true;
                //System.out.println("¬¬¬¬¬¬¬¬¬¬¬¬ " + functions.size() + ":" + count);
//                if ((functions.size() / 2)-1 > count/2) {
//                    for (int j = i; j < functions.size() - count; j++) {
//                        if (functions.get(j).equals("print")) {
//                            func.append("\t\t\timprime " + functions.get(j + 1) + "\n");
//                            functions.remove(j);
//                        } else if (functions.get(j).equals("input")) {
//                            func.append("\t\t\tleer " + functions.get(j + 1) + "\n");
//
//                        }
//                    }
//
//                    System.out.println(functions.toString());
//               } else {
//                    for (int j = i; j < functions.size(); j++) {
//                        if (functions.get(j).equals("print")) {
//                            func.append("\t\t\timprime " + functions.get(j + 1) + "\n");
//                            functions.remove(j);
//                        } else if (functions.get(j).equals("input")) {
//                            func.append("\t\t\tleer " + functions.get(j + 1) + "\n");
//                            functions.remove(j);
//                        }
//                    }
//                }
                //func.append("\t\tloop C" + contLabel + "\n");
            }else if(functions.get(i).equals("}") && flagIf){
                func.append("\t\tFIN" + contLabel4 + ":\n");
                flagIf = false;
            } else if (functions.get(i).equals("}") && flagLoop) {
                func.append("\t\tloop C" + contLabel + "\n");
                flagLoop = false;
            } else if (flagInput) {
                Pattern patron = Pattern.compile(Tokens.IDENTIFIER.regex);
                Matcher matcher = patron.matcher(functions.get(i).toString());
                if (matcher.matches()) {
                    for (Lexico b : s.getVariables()) {
                        if (b.getValue().equalsIgnoreCase(functions.get(i).toString())) {
                            if (b.getDataType() == 1) {
                                func.append(leerNumero(contLabel2, functions.get(i).toString()));
                            } else if (b.getDataType() == 2) {
                                func.append("\t\t\tleer " + functions.get(i) + "\n");
                            }
                        }
                    }
                    //func.append(leerNumero(contLabel2, functions.get(i).toString()));
                    flagInput = false;
                }

            } else if (functions.get(i).equals("if")) {
                contLabel3++;
                contLabel4++;
                boolean flagSecond = false;
                flagIf = true;



                for (int j = i; j < functions.size(); j++) {
                    //System.out.println("++++++++++++++++ " + functions.get(j));
                    if (functions.get(j).equals("}")){

                        break;
                    }
                    else {
                        Pattern patron = Pattern.compile(Tokens.IDENTIFIER.regex);
                        Matcher matcher = patron.matcher(functions.get(j).toString());
                        if (matcher.matches()){
                            for (Lexico b: s.getVariables()){
                                if (b.getValue().equalsIgnoreCase(functions.get(j).toString())) {
                                    if (b.getDataType() == 1 && !flagSecond) {
                                        func.append("\t\tMOV BX, " + countersIf.get(contLabel3-1) + "\n");
                                        flagSecond = true;
                                        contLabel3++;
                                    }else if(b.getDataType() == 1 && flagSecond){
                                        func.append("\t\tCMP BX, " + countersIf.get(contLabel3-1) + "\n");
                                        switch(countersOperators.get(contLabel4-1)){
                                            case 1:
                                                func.append("\t\tJL ALGO" + contLabel4 + "\n");
                                                break;

                                            case 2:
                                                func.append("\t\tJG ALGO" + contLabel4 + "\n");
                                                break;

                                            case 3:
                                                func.append("\t\tJLE ALGO" + contLabel4 + "\n");
                                                break;

                                            case 4:
                                                func.append("\t\tJGE ALGO" + contLabel4 + "\n");
                                                break;

                                            case 5:
                                                func.append("\t\tJE ALGO" + contLabel4 + "\n");
                                                break;
                                        }
                                        func.append("\t\tJMP FIN" + contLabel4 + "\n");
                                        func.append("\t\tALGO" + contLabel4 + ":\n");
                                        flagSecond = false;
                                    }
                                    System.out.println("======= ERROR NO ES UNA VARIABLE NUMERICA");
                                }
                            }
                        }
                        patron = Pattern.compile(Tokens.NUMBER_VALUE.regex);
                        matcher = patron.matcher(functions.get(j).toString());
                        System.out.println(",,,,, EVALUAR NUM IF " + functions.get(j).toString());
                        if (matcher.matches()){
                            System.out.println(",,,,, EXITO EVALUAR NUM IF");
                            if (!flagSecond) {
                                func.append("\t\tMOV BX, " + countersIf.get(contLabel3-1) + "\n");
                                flagSecond = true;
                                contLabel3++;
                            }else if(flagSecond){
                                func.append("\t\tCMP BX, " + countersIf.get(contLabel3-1) + "\n");
                                switch(countersOperators.get(contLabel4-1)){
                                    case 1:
                                        func.append("\t\tJL ALGO" + contLabel4 + "\n");
                                        break;

                                    case 2:
                                        func.append("\t\tJG ALGO" + contLabel4 + "\n");
                                        break;

                                    case 3:
                                        func.append("\t\tJLE ALGO" + contLabel4 + "\n");
                                        break;

                                    case 4:
                                        func.append("\t\tJGE ALGO" + contLabel4 + "\n");
                                        break;

                                    case 5:
                                        func.append("\t\tJE ALGO" + contLabel4 + "\n");
                                        break;
                                }
                                func.append("\t\tJMP FIN" + contLabel4 + "\n");
                                func.append("\t\tALGO" + contLabel4 + ":\n");
                                flagSecond = false;
                            }
                        }

                    }
                }
            }
        }

    }

    public void escribeCuerpo() {
        cuerpo = "codigo segment para public 'code'\n" +
                "\tpublic principal\n" +
                "\tassume cs:codigo, ds:datos, es:extra, ss:pila\n" +
                "\t\n" +
                "\tprincipal proc far\n" +
                "\t\tpush ds\n" +
                "\t\tmov ax,0\n" +
                "\t\tpush ax\n" +
                "\t\tmov ax,datos\n" +
                "\t\tmov ds,ax\n" +
                "\t\tmov ax,extra\n" +
                "\t\tmov es,ax\n";
    }

    public void escribeFinal() throws IOException {
        fin = "\t\tret\n" +
                "\tprincipal endp\n" +
                "codigo ends\n" +
                "\tend principal";

        FileWriter fw = new FileWriter("C:\\Users\\PC\\Documents\\6tosemestre\\Lenguajesdeinterfaz\\masm\\Codigo.asm");
        fw.write(encabezado);
        fw.flush();
        fw.write(datos.toString());
        fw.flush();
        fw.write(cuerpo);
        fw.flush();
        fw.write(func.toString());
        fw.flush();
        fw.write(fin);
        fw.close();
    }

    public static String leerNumero(int contLabel, String variable) {
        macro = new StringBuilder();
        //macro.append("\tPUSH DX \n");
        //macro.append("\tPUSH AX \n");
        // macro.append("\tPUSH BX \n");
        //macro.append("\tPUSH CX \n");
        macro.append("\tMOV DL,0AH\n" +
                "\tMOV AH,02\n" +
                "\tINT 21H\n" +
                "\tLEA DX," + variable + "\n" +
                "\tMOV AH,0AH\t\n" +
                "\tINT 21H\n");
        macro.append("\tLEA SI, " + variable + " + 1\n" +
                "\tPUSH SI \n" +
                "\tMOV CL,[SI]\n" +
                "\tMOV CH,0 \n" +
                "\tPUSH CX \n" +
                "\tRESTA30H" + contLabel + ":\n" +
                "\tINC SI \n" +
                "\tSUB [SI],30H \n" +
                "\tLOOP RESTA30H" + contLabel);
        macro.append("\n\tMOV BP,0AH\n" +
                "\tPOP CX \n" +
                "\tDEC CX \n" +
                "\t\t\t \n" +
                "\tPOP BX \n" +
                "\tINC BX \n" +
                "\tMOV AL,[BX] \n" +
                "\tMOV AH,0 \n" +
                "\n" +
                "\tCMP CX,0 \n" +
                "\tJE BRINCOCICLO" + contLabel + "\n" +
                "\t\t\t \n" +
                "\tMULTIYSUMA" + contLabel + ":\n" +
                "\tMUL BP \n" +
                "\tINC BX \n" +
                "\tMOV DL,[BX] \n" +
                "\tMOV DH,0 \n" +
                "\tADD AX,DX \n" +
                "\tLOOP MULTIYSUMA" + contLabel + "\n" +
                "\tBRINCOCICLO" + contLabel + ":");
        macro.append("\n");
        //macro.append("\tPOP CX \n");
        // macro.append("\tPOP BX \n");
        // macro.append("\tPOP AX \n");
        // macro.append("\tPOP DX \n");
        return macro.toString();
    }

    public static void menorQue() {

    }

}
