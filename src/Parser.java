import java.util.ArrayList;

/**
 * @author Arturo Villalvazo & Alexis Moran
 */
public class Parser {

    private int counter;
    public int counterLoop;
    public int countNextLoop;
    private ArrayList<Lexico> tokens;
    private boolean isAccepted;
    private int numberLine;
    private Semantic s;
    private String variableValue;
    private int variableId = 1;

    public Parser() {
        this.counter = -1;
        this.tokens = null;
        this.isAccepted = false;
        this.numberLine = -1;
        this.s = null;
        this.variableValue = "";
        this.counterLoop = 0;
        this.countNextLoop = 0;
    }

//    public Parser() {
//        this.counter = 0;
//        this.tokens = tokens;
//        this.isAccepted = false;
//        this.numberLine = numberLine;
//        this.s = s;
//        this.variableValue = "";
//    }

    public void init(ArrayList<Lexico> tokens, int numberLine, Semantic s) {
        this.counter = 0;
        this.tokens = tokens;
        this.isAccepted = false;
        this.numberLine = numberLine;
        this.s = s;
        this.variableValue = "";


        q1();

        if (!isIsAccepted()) {
            syntaxError();
        }
    }

    private void syntaxError() {
        throw new RuntimeException("Sintaxis no válida ::: Línea -> " + getNumberLine());
    }

    private void q1() {
//        System.out.print(s.getVariables().size() + " ---------------> State 1");
        if (getCounter() < getTokens().size()) {
            // System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id > 3 && id < 7) {
                s.setVariableDataType(tokens.get(counter).getDataType());
                setCounter(getCounter() + 1);
                q2();
            } else if (id == 27) {
                s.checkVariableInMemory(tokens.get(counter), numberLine);
                variableId = 0;
                s.getDataTypes().push(s.getVariables().get(s.getVariables().indexOf(tokens.get(counter))).getDataType());
                setCounter(getCounter() + 1);
                q3();
            } else if (id == 22) {
                setCounter(getCounter() + 1);
                s.getDataTypes().push(tokens.get(counter).getDataType());
                q4();
            } else if (id == 21) {
                setCounter(getCounter() + 1);
                q7();
            } else if (id == 24) {
                s.setIsBracketClosed(true);
                s.isBracketOpen();
                s.setIsBracktOpen(false);
                s.setClosedBrackets(s.getClosedBrackets() + 1);
                setCounter(getCounter() + 1);
                q10();
            } else if (id == 25) {
                counterLoop = 0;
                setCounter(getCounter() + 1);
                q12();
            } else if (id == 26) {
                setCounter(getCounter() + 1);
                q14();
            }
        }
    }

    private void q2() {
        setIsAccepted(false);
        //System.out.print("---------------> State 2");
        s.checkDeclaredVariable(tokens.get(counter), numberLine);
        if (getCounter() < getTokens().size()) {
            // System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 27) {
                tokens.get(counter).setDataType(s.getVariableDataType());
                s.getDataTypes().push(tokens.get(counter).getDataType());

                tokens.get(counter).setVariableValue("");
//                System.out.println("====> " + tokens.get(counter).getValue() + " - " + tokens.get(counter).getDataType());
                setCounter(getCounter() + 1);
                q3();
            }
        }
    }

    private void q3() {
        setIsAccepted(false);
        // System.out.print("---------------> State 3");
        if (getCounter() < getTokens().size()) {
            //  System.out.println("~~~~~~~~ " + tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
            tokens.get(variableId).setVariableValue(tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
//            System.out.println(", Token " + tokens.get(counter).getValue());
//            System.out.println("~~~~ " + tokens.get(variableId).getVariableValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 11) {
                setCounter(getCounter() + 1);
                q4();
            }
        }
    }

    private void q4() {
        setIsAccepted(false);
        // System.out.print("---------------> State 4");
        if (getCounter() < getTokens().size()) {
            //System.out.println("~~~~~~~~ " + tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
            tokens.get(variableId).setVariableValue(tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
//            System.out.println(", Token " + tokens.get(counter).getValue());
//            System.out.println("~~~~ " + tokens.get(variableId).getVariableValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 14) {
                setCounter(getCounter() + 1);
                q6();
            } else if ((id > 0 && id < 4) || id == 27) {
                if (id == 27) {
                    s.checkVariableInMemory(tokens.get(counter), numberLine);
                    tokens.get(counter).setDataType(s.getVariables().get(s.getVariables().indexOf(tokens.get(counter))).getDataType());
                }
                s.checkDataTypeWithStack(tokens.get(counter), numberLine);
                setCounter(getCounter() + 1);
                q5();
            }
        }
    }

    private void q5() {
        setIsAccepted(true);
        //System.out.print("---------------> State 5");
        if (getCounter() < getTokens().size()) {
            //System.out.println("~~~~~~~~ " + tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
            tokens.get(variableId).setVariableValue(tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
//            System.out.println(", Token " + tokens.get(counter).getValue());
//            System.out.println("~~~~ " + tokens.get(variableId).getVariableValue());
            int id = getTokens().get(getCounter()).getId();
            if ((id > 6 && id < 11) || (id > 11 && id < 14) || (id > 14 && id < 21)) {
                s.checkDataTypeWithStack(tokens.get(counter), numberLine);
                setCounter(getCounter() + 1);
                q4();
            } else {
                setIsAccepted(false);
            }
        }
    }

    private void q6() {
        //System.out.print("---------------> State 6");
        if (getCounter() < getTokens().size()) {
            //System.out.println("~~~~~~~~ " + tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
            tokens.get(variableId).setVariableValue(tokens.get(counter).getVariableValue() + getTokens().get(getCounter()).getValue());
//            System.out.println(", Token " + tokens.get(counter).getValue());
//            System.out.println("~~~~ " + tokens.get(variableId).getVariableValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 27) {
                setCounter(getCounter() + 1);
                q5();
            }
        }
    }

    private void q7() {
        setIsAccepted(false);
        // System.out.print("---------------> State 7");
        if (getCounter() < getTokens().size()) {
            //System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
//            if (id == 14) {
//                setCounter(getCounter() + 1);
//                q9();
//            } else
                if (id == 1 || id == 27) {
                setCounter(getCounter() + 1);
                q8();
            }
        }
    }

    private void q8() {
        //System.out.print("---------------> State 8");
        if (getCounter() < getTokens().size()) {
            //System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
//            if (id == 23) {
//                s.setIsBracktOpen(true);
//                s.setOpenedBrackets(s.getOpenedBrackets() + 1);
//                setCounter(getCounter() + 1);
//                q10();
//            } else
//                if ((id > 6 && id < 11) || (id > 11 && id < 14) || (id > 14 && id < 21)) {
//                setCounter(getCounter() + 1);
//                q7();
//            }
            if(id > 14 && id < 20){
                setCounter(getCounter() + 1);
                q15();
            }
        }
    }

    private void q9() {
        //System.out.print("---------------> State 9");
        if (getCounter() < getTokens().size()) {
            // System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 27) {
                setCounter(getCounter() + 1);
                q8();
            }
        }
    }

    private void q10() {
        // System.out.print("---------------> State 10");
        setIsAccepted(true);
    }

    private void q11() {
        //System.out.print("---------------> State 11");
        if (getCounter() < getTokens().size()) {
            // System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 2) {
                setCounter(getCounter() + 1);
                q10();
            }
        }
    }

    private void q12() {
        //System.out.print("---------------> State 12");
        if (getCounter() < getTokens().size()) {
            //System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 1 || (id == 27)) {
                setCounter(getCounter() + 1);
                //System.out.println(getTokens().get(getCounter()).getVariableValue() + "........................ " + counterLoop);
                //System.out.println("................ " + s.getVariables().contains(getTokens().get(getCounter() - 1).getValue()));
                if (id == 1) {
                    counterLoop = Integer.parseInt(getTokens().get(getCounter() - 1).getValue());
                } else {
                    boolean flag = false;
                    for (Lexico l : s.getVariables()) {
                        flag = l.getValue().equalsIgnoreCase(getTokens().get(getCounter() - 1).getValue());
                        if (flag) {
                            counterLoop = Integer.parseInt(l.getVariableValue());
                            break;
                        }
                    }
                    // counterLoop = Integer.parseInt(getTokens().get(getCounter() - 1).getVariableValue());
                }
//                System.out.println( "........................ " + counterLoop);
                q13();
            }
        }
    }

    private void q13() {
        //System.out.print("---------------> State 13");
        if (getCounter() < getTokens().size()) {
            // System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 23) {
                if (id == 22 || id == 26) {
                    countNextLoop++;
                } else {
//                if(s.isIsBracktOpen()){
//                    
//                }
                    s.setOpenedBrackets(s.getOpenedBrackets() + 1);
                    s.setIsBracktOpen(true);
                    setIsAccepted(true);
                    countNextLoop += counter;
                }
            }
        }
    }

    private void q14() {
        //System.out.print("---------------> State 14");
        if (getCounter() < getTokens().size()) {
            //System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 27) {
                setIsAccepted(true);
            }
        }
    }

    private void q15(){
        //System.out.print("---------------> State 15");
        if (getCounter() < getTokens().size()) {
           // System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 1 || id == 27) {
                setCounter(getCounter() + 1);
                q16();
            }
        }
    }

    private void q16(){
        //System.out.print("---------------> State 16");
        if (getCounter() < getTokens().size()) {
            //System.out.println(", Token " + tokens.get(counter).getValue());
            int id = getTokens().get(getCounter()).getId();
            if (id == 23) {
                s.setIsBracktOpen(true);
                s.setOpenedBrackets(s.getOpenedBrackets() + 1);
                setIsAccepted(true);
            }
        }
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * @return the tokens
     */
    public ArrayList<Lexico> getTokens() {
        return tokens;
    }

    /**
     * @param tokens the tokens to set
     */
    public void setTokens(ArrayList<Lexico> tokens) {
        this.tokens = tokens;
    }

    /**
     * @return the isAccepted
     */
    public boolean isIsAccepted() {
        return isAccepted;
    }

    /**
     * @param isAccepted the isAccepted to set
     */
    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    /**
     * @return the numberLine
     */
    public int getNumberLine() {
        return numberLine;
    }

    /**
     * @param numberLine the numberLine to set
     */
    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }

    public Semantic getSemantic() {
        return s;
    }
}
