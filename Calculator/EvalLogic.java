import java.util.HashMap;
import java.util.ArrayList;

public class EvalLogic {
    @FunctionalInterface
    private interface DoubleOperator {
        public Double apply(Double a, Double b);
    }

    private Double negChecker(String str){
        if (str.startsWith("n")){
            return -1 * Double.parseDouble(str.substring(1));
        }
        return Double.parseDouble(str);
    }
    
    private static HashMap<String,DoubleOperator> getOperMap(){
        HashMap<String,DoubleOperator> oper_map = new HashMap<String,DoubleOperator>();
        oper_map.put("+", (n1,n2)->{return n1 + n2;});
        oper_map.put("-", (n1,n2)->{return n1 - n2;});
        oper_map.put("*", (n1,n2)->{return n1 * n2;});
        oper_map.put("/", (n1,n2)->{return n1 / n2;});
        
        return oper_map;
    }
    
    private ArrayList<String> parseExpr(String input){
        HashMap<String,DoubleOperator> oper_map = getOperMap();
        ArrayList<String> expr = new ArrayList<String>();
        String cur = "";
        String c;
        //Turns expr into an array of full numbers and operations
        for (int i = 0; i < input.length(); i++){
            c = "" + input.charAt(i);
            if (oper_map.get(c) == null){
                // If i is an integer and not an operation
                cur += c;
            }
            else{
                
                if (c.equals("-")) {
                    if (cur.length() > 0){
                        //Used during subtraction
                        expr.add(cur);
                        expr.add("+");
                        cur = "";
                    }
                    //Used when there's an operation before (e.g mulitplying by a negative number)
                    cur += "n";
                    
                    continue;
                }
                expr.add(cur);
                cur = "";
                expr.add(c);

            }
        }

        expr.add(cur); 
        return expr;
    }
    
    public Double eval(String input) throws Exception{
        HashMap<String,DoubleOperator> oper_map = getOperMap();
        ArrayList<String> expr = parseExpr(input);

        for (int j = 0; j < expr.size(); j++){
            String str = expr.get(j);
            if (oper_map.get(str) != null) {
                // str is an operation and not an integer
                Double lhs = 0.0;
                Double rhs = 0.0;
                try{
                    lhs = negChecker(expr.get(j - 1));
                    rhs = negChecker(expr.get(j + 1));

                    expr.remove(j + 1);
                    expr.remove(j - 1);
                }
                catch (IndexOutOfBoundsException exception) {
                    if (str.equals("*") || str.equals("/")){
                        if (lhs == 0.0) {
                            lhs = 1.0;
                        }
                        if (rhs == 0.0) {
                            rhs = 1.0;
                        }
                    }
                }

                expr.set(j - 1, String.valueOf(oper_map.get(str).apply(lhs, rhs)));
            }
        }
        
        if (expr.get(0).equals("-Infinity") || expr.get(0).equals("Infinity")){throw new Exception("Divide by zero error");}
        Double num = Double.parseDouble(expr.get(0));
        num *= Math.pow(10, 6);
        num = Double.valueOf(Math.round(num));
        num /= Math.pow(10,6); 
        return num;
    }
};