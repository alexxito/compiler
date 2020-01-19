
/**
 *
 * @author Arturo Villalvazo & Alexis Moran
 */
public class Lexico {

    private int numberLine;
    private int id;
    private String type;
    private String value;
    private int dataType;
    private String variableValue;

    public Lexico() {
        this.numberLine = -1;
        this.id = -1;
        this.type = "Undefined";
        this.value = "Null";
        this.dataType = 0;
        this.variableValue = null;
    }

    public Lexico(int numberLine, int id, String type, String value, int dataType, String variableValue) {
        this.numberLine = numberLine;
        this.id = id;
        this.type = type;
        this.value = value;
        this.dataType = dataType;
        this.variableValue = variableValue;
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

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Lexico){
            Lexico lex = (Lexico) o;
            return this.id == lex.id && this.type.equals(lex.type) && this.value.equals(lex.value);
        }else{
            throw new RuntimeException("Comparando objeto diferente a Lexico");
        }
    }
    
    @Override
    public int hashCode(){
        return (new Integer(this.id)).hashCode();
    }

    /**
     * @return the dataType
     */
    public int getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    
    /**
     * @return the variableValue
     */
    public String getVariableValue(){
        return variableValue;
    }
    
    /**
     * @param variableValue the variableValue to set
     */
    public void setVariableValue(String variableValue){
        this.variableValue = variableValue;
    }

}
