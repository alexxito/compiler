
/**
 *
 * @author Arturo Villalvazo & Alexis Moran
 */
public class PurifiedLine {

    private int numberLine;
    private String line;

    public PurifiedLine() {
        this.numberLine = -1;
        this.line = "null";
    }

    public PurifiedLine(int numberLine, String line) {
        this.numberLine = numberLine;
        this.line = line;
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
     * @return the line
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(String line) {
        this.line = line;
    }
}
