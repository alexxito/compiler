
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Arturo Villalvazo & Alexis Moran
 */
public class Purifier {

    private BufferedReader br;
    private boolean band;
    private int counter;
    private String purifiedDoc;

    public Purifier() throws IOException{
        this.br = new BufferedReader(new FileReader("C:\\Users\\PC\\IdeaProjects\\CompiladorOriginal\\src\\ejemplo.ana"));
        this.band = false;
        this.counter = 1;
        this.purifiedDoc = "";
    }

    public String purify() throws IOException{
        try {
            String sCurrentLine;
            String liAEscribir = "";
            while ((sCurrentLine = br.readLine()) != null) {
                if (!sCurrentLine.trim().equals("")) {
                    if (sCurrentLine.trim().startsWith("//")) {
                        continue;
                    } else if (sCurrentLine.trim().startsWith("/*") | sCurrentLine.trim().startsWith("*")) {
                        while ((sCurrentLine = br.readLine()) != null) {
                            counter++;
                            if (sCurrentLine.trim().endsWith("*/")) {
                                break;
                            }
                        }
                    } else {
                        StringTokenizer st = new StringTokenizer(sCurrentLine);
                        String tokenActual = "";
                        while (st.hasMoreTokens()) {
                            tokenActual = st.nextToken();
                            if (tokenActual.length() == 0) {
                                tokenActual = st.nextToken();
                            }
                            for (int i = 0; i < tokenActual.length(); i++) {
                                if (tokenActual.charAt(i) == ';') {
                                    band = true;
                                    tokenActual = tokenActual.substring(0, i + 1);
                                    break;
                                }
                            }
                            liAEscribir = liAEscribir + " " + tokenActual;
                            if (band == true) {
                                break;
                            }
                        }
                        band = false;
                        if (liAEscribir != "") {
                            liAEscribir = liAEscribir.substring(1);
                        }
                        purifiedDoc += (counter + " " + liAEscribir + "\n");
                        liAEscribir = "";
                    }
                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return purifiedDoc;
    }
    
    public ArrayList<PurifiedLine> purifyDoc() throws IOException{
        StringTokenizer st = new StringTokenizer( purify(), "\n" );
        ArrayList<PurifiedLine> lines = new ArrayList<>();
        StringTokenizer stLines;
        
        while(st.hasMoreTokens()){
            stLines = new StringTokenizer(st.nextToken());
            int numberLine = Integer.parseInt(stLines.nextToken());
            String line = "";
            while(stLines.hasMoreTokens()){
                line += (stLines.nextToken() + " ");
            }
            line = line.trim();
            lines.add(
                    new PurifiedLine(
                            numberLine,
                            line
                    )
            );
        }
        
        return lines;
    }

}
