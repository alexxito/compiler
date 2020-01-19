import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Arturo Villalvazo & Alexis Moran
 */
public class Semantic {
    private ArrayList<Lexico> variables;
    private ArrayList<Lexico> values;
    private int openedBrackets;
    private int closedBrackets;
    private boolean isBracktOpen;
    private boolean isBracketClosed;
    private int variableDataType;
    private Stack<Integer> dataTypes;
    
    
    public Semantic(){
        this.variables = new ArrayList<>();
        this.values = new ArrayList<>();
        this.openedBrackets = 0;
        this.closedBrackets = 0;
        this.isBracktOpen = false;
        this.variableDataType = -1;
        this.dataTypes = new Stack<>();
    }
    
    public void checkDeclaredVariable(Lexico lex, int numberLine){
        if(this.getVariables().contains(lex)){
            throw new RuntimeException("Variable \"" + lex.getValue() + "\" ya declarada ::: Línea -> " + numberLine);
        }
        this.getVariables().add(lex);
    }
    
    public void checkVariableInMemory(Lexico lex, int numberLine){
        if(!this.getVariables().contains(lex)){
            throw new RuntimeException("Variable \"" + lex.getValue() + "\" NO ha sido declarada ::: Línea -> " + numberLine);
        }
    }
    
    public void checkDataType(Lexico fromVal, Lexico withVal, int numberLine){
        if(!fromVal.getType().equals(withVal.getType())){
            throw new RuntimeException("Tipo de " + (withVal.getType().equals("ID") ? "Identificador" : "Dato") + " no válido en la expresión ::: Línea -> " + numberLine);
        }
    }
    
    public void checkGeneralBrackets(){
        if(openedBrackets != closedBrackets){
            throw new RuntimeException("No hay un emparejamiento con segmentos de codigo \"{}\"");
        }
    }
    
    public void isBracketOpen(){
        if(!isIsBracktOpen())
            throw new RuntimeException("No hay un emparejamiento con segmentos de codigo \"{}\"");
    }
    
    public void isBracketClosed(){
        if(!isIsBracketClosed())
            throw new RuntimeException("No hay un emparejamiento con segmentos de codigo \"{}\"");
    }
    
    public void checkDataTypeWithStack(Lexico lex, int numberLine){
//        System.out.println("====> " + lex.getValue() + " - " + lex.getDataType() + " - " + this.dataTypes.peek());
//        if(this.dataTypes.peek() == 3 && (lex.getDataType() == 2) ){
//            throw new RuntimeException("*Valor " + lex.getValue() + " inválido en la expresión ::: Linea -> " + numberLine);
//        }
        if(lex.getDataType() != this.dataTypes.peek() && this.dataTypes.peek() != 4){
            throw new RuntimeException("Valor " + lex.getValue() + " inválido en la expresión ::: Linea -> " + numberLine);
        }
    }

    /**
     * @return the variables
     */
    public ArrayList<Lexico> getVariables() {
        return variables;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(ArrayList<Lexico> variables) {
        this.variables = variables;
    }

    /**
     * @return the values
     */
    public ArrayList<Lexico> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(ArrayList<Lexico> values) {
        this.values = values;
    }

    /**
     * @return the openedBrackets
     */
    public int getOpenedBrackets() {
        return openedBrackets;
    }

    /**
     * @param openedBrackets the openedBrackets to set
     */
    public void setOpenedBrackets(int openedBrackets) {
        this.openedBrackets = openedBrackets;
    }

    /**
     * @return the closedBrackets
     */
    public int getClosedBrackets() {
        return closedBrackets;
    }

    /**
     * @param closedBrackets the closedBrackets to set
     */
    public void setClosedBrackets(int closedBrackets) {
        this.closedBrackets = closedBrackets;
    }

    /**
     * @return the isBracktOpen
     */
    public boolean isIsBracktOpen() {
        return isBracktOpen;
    }

    /**
     * @param isBracktOpen the isBracktOpen to set
     */
    public void setIsBracktOpen(boolean isBracktOpen) {
        this.isBracktOpen = isBracktOpen;
    }

    /**
     * @return the variableDataType
     */
    public int getVariableDataType() {
        return variableDataType;
    }

    /**
     * @param variableDataType the variableDataType to set
     */
    public void setVariableDataType(int variableDataType) {
        this.variableDataType = variableDataType;
    }

    /**
     * @return the dataTypes
     */
    public Stack<Integer> getDataTypes() {
        return dataTypes;
    }

    /**
     * @param dataTypes the dataTypes to set
     */
    public void setDataTypes(Stack<Integer> dataTypes) {
        this.dataTypes = dataTypes;
    }

    /**
     * @return the isBracketClosed
     */
    public boolean isIsBracketClosed() {
        return isBracketClosed;
    }

    /**
     * @param isBracketClosed the isBracketClosed to set
     */
    public void setIsBracketClosed(boolean isBracketClosed) {
        this.isBracketClosed = isBracketClosed;
    }
    
}
