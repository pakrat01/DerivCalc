/**
 * Creates tokens from a given formula.
 * 
 * @author Paul Marks
 * @version March 2022
 */

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CreateTokens {

    /** Regex for int literal */
    private String INTLIT;

    /** Regex for symbols */
    final private String SYM;

    /** Exponent */
    final private String EXP = "^";

    /** Divide */
    final private String DIV = "/";

    /** Multiply */
    final private String MUL = "*";

    /** Add */
    final private String ADD = "+";

    /** Subtract */
    final private String SUB = "-";

    /** Parenthesis */
    final private String OPAR = "(";

    /** Parenthesis */
    final private String CPAR = ")";

    /** Regex for variables */
    private String VAR;

    /** The formula */
    private String formula;

    /** HashMap to hold found tokens and relate them to their token type and position */
    private HashMap<String, String> found;

    public CreateTokens() {

        INTLIT = "[0-9]";
        SYM = "[+^*/()-]";
        VAR = "[a-zA-Z]";

        String tmp = "(";
        System.out.println(tmp.matches(SYM));

        found = new LinkedHashMap<>(); //Linked so that things stay in order
                                       
        formula = "5x+67";
    }

    public CreateTokens(String formula) {

        this();
        this.formula = formula;
    }

    /**
     * Analyze the formula and break it down into tokens.
     * Looks at each character in the formula, assigns it a token, and adds it to the hashmap.
     */
    public void analyze() {

        StringBuilder intlit = new StringBuilder(""); //Used to get int literals longer than one 
                                                      //character

        boolean isint = false; //Used to determine if program should look ahead for more characters
                               //in the case of an int literal being found
        int pos = 0; //The position of the current token

        //iterate through formula
        for (int i = 0; i < formula.length(); i++) {
            //If a number is found, look ahead for more numbers
            while (i < formula.length() && 
                    ((Character)formula.charAt(i)).toString().matches(INTLIT)) {
                isint = true;
                intlit.append(formula.charAt(i));
                i++;
            }

            //if int was found
            if (isint) {
                found.put("INTLIT" + pos, intlit.toString());
                pos++;
                //System.out.println(intlit);
                intlit = new StringBuilder(""); //clear the stringbuilder
                isint = false;
            }

            if (i < formula.length()) {
                String car = ((Character)formula.charAt(i)).toString();

                //check if letter
                if (car.matches(VAR)) {
                    found.put("VAR" + pos, car);
                    pos++;
                    //System.out.println(car);
                //check if symbol
                } else if (car.matches(SYM)) {

                    switch(car) {
                        case EXP:
                            found.put("EXP" + pos, car);
                            pos++;
                            break;
                        case DIV:
                            found.put("DIV" + pos, car);
                            pos++;
                            break;
                        case MUL:
                            found.put("MUL" + pos, car);
                            pos++;
                            break;
                        case ADD:
                            found.put("ADD" + pos, car);
                            pos++;
                            break;
                        case SUB:
                            found.put("SUB" + pos, car);
                            pos++;
                            break;
                        case OPAR:
                            found.put("OPAR" + pos, car);
                            pos++;
                            break;
                        case CPAR:
                            found.put("CPAR" + pos, car);
                            pos++;
                            break;
                    }
                    //System.out.println(car);
                }
            }
        }
    }

    /**
     * Shows the contents of the hashmap after analyzation.
     */
    public void showTokens() {

        System.out.println();

        System.out.println("Input: " + formula);

        //checking values
        for (String find : found.values()) {
            System.out.print(find + "  ");
        }
        //checking keys
        System.out.println();
        for (String find : found.keySet()) {
            System.out.print(find + " ");
        }
        System.out.println();
    }

    /**
     * Returns the hashmap with the created tokens.
     * @return the hashmap with the tokens.
     */
    public HashMap<String, String> getTokens() {

        return found;
    }

    public static void main(String[] args) {
        CreateTokens ct = new CreateTokens();
        ct.analyze();
        ct.showTokens();

        ct = new CreateTokens("5+2 * 65^x         - 9/     3");
        ct.analyze();
        ct.showTokens();

        ct = new CreateTokens("5+2 (65^x)        - 9/     ( 3)");
        ct.analyze();
        ct.showTokens();

        HashMap<String, String> get = ct.getTokens();

        System.out.println();
        //checking that order stays through the getter method
        System.out.println();
        for (String find : get.values()) {
            System.out.print(find + " ");
        }
        System.out.println();
    }
}
