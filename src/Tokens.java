/**
 *
 * @author Arturo Villalvazo & Alexis Moran
 */
enum Tokens {

//    DataTypes Values -- 4th parameter
//    1 - numeros
//    2 - cadenas
//    3 - booleanos
//    4 - any
//    5 - cadenas y numeros
    
//    IsAssemblyFunction -- 5th parameter -- 0 isn't -- other value is function id
//    1 - condicional
//    2 - impresion
//    3 - ciclo
//    4 - entrada
    
    
//        Values
    NUMBER_VALUE(1, "Entero", "[0-9]+", 1, 0),
    STRING_VALUE(2, "Cadena", "(\")((?:[^\"]|\"\")*)\"", 2, 0),
    BOOLEAN_VALUE(3, "Booleano", "TRUE|FALSE", 3, 0),
    //        variables types
    NUMBER_VAR(4, "Numérica", "\\binteger\\b", 1, 0),
    STRING_VAR(5, "De Cadena", "\\bstring\\b", 2, 0),
    BOOLEAN_VAR(6, "Booleana", "\\bboolean\\b", 3, 0),
    //        operators
    ADD_OPERATOR(7, "Suma", "[+]", 5, 0),
    MINUS_OPERATOR(8, "Resta", "[-]", 1, 0),
    TIMES_OPERATOR(9, "Multiplicación", "[*]", 1, 0),
    DIVIDE_OPERATOR(10, "Division", "[/]", 1, 0),
    ASSIGN_OPERATOR(11, "Asignacion", "[=]", 4, 0),
    AND_OPERATOR(12, "AND", "[&]", 3, 0),
    OR_OPERATOR(13, "OR", "[|]", 3, 0),
    NOT_OPERATOR(14, "NOT", "[!]", 3, 0),
    LESS_OPERATOR(15, "Menor", "[<]", 5, 0),
    GREAT_OPERATOR(16, "Mayor", "[>]", 5, 0),
    LESSQ_OPERATOR(17, "Menor Igual", "<=", 5, 0),
    GREATQ_OPERATOR(18, "Mayor Igual", ">=", 5, 0),
    EQUALS_OPERATOR(19, "Igual", "==", 3, 0),
    NEQUALS_OPERATOR(20, "Diferente", "\\b!=\\b", 3, 0),
    //        reserved words
    IF(21, "Condicional", "\\bif\\b", 4, 1),
    PRINT(22, "Impresion", "\\bprint\\b", 2, 2),
    //        specials
    OBRACKET(23, "Apertura", "[{]", 4, 0),
    CBRACKET(24, "Cierre", "[}]", 4, 0),
    //        identifiers
    LOOP(25, "Ciclo", "\\bdo\\b", 4, 3),
    INPUT(26, "Entrada", "\\binput\\b", 2, 4),
    IDENTIFIER(27, "ID", "[a-z][A-Za-z0-9]*{1,16}$", 4, 0),
    UNKNOWN(28, "Desconocido", ".*", 4, 0);

    public final int id;
    public final String name;
    public final String regex;
    public final int dataType;
    public final int functionId;

    Tokens(int id, String name, String regex, int dataType, int functionId) {
        this.id = id;
        this.name = name;
        this.regex = regex;
        this.dataType = dataType;
        this.functionId = functionId;
    }
}
