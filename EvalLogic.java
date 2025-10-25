import java.util.HashMap;
import java.util.ArrayList;

public class EvalLogic {
    @FunctionalInterface
    private interface DoubleOperator {
        public Double apply(Double a, Double b);
    }

    private ArrayList<String> decodeInput(ArrayList<String> expr){
        // Decodes strings to handle negatives
        for (int i = 0; i < expr.size(); i++){
            if (expr.get(i).equals("-")){
                expr.set(i, "+");
                expr.set(i + 1, "n" + expr.get(i + 1));
            }
        }
        
        return expr;
    }

    private Double negChecker(String num){
        if (num.equals("")){return 0.0;}
        
        if (num.startsWith("n")){
            return (-1 * Double.parseDouble(num.substring(1)));
        }
        return Double.parseDouble(num);
    }

    private boolean opr_in_expr(String input){
        String[] oprs = {"+", "-","*","/"};
        for (String opr : oprs){
            if (input.contains(opr)){
                return true;
            }
        }
        return false;
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
        ArrayList<String> expr = decodeInput(parseExpr(input));
        //System.out.println(expr); // DEBUG
        
        Double num;
        String numstr;
        Double lhs;
        Double rhs;
        //While there are still operators in the expression
        while (opr_in_expr(String.join("",expr))) {
            for (int j = 0; j < expr.size(); j++){
                DoubleOperator func = oper_map.get(expr.get(j));
                if (func != null){
                    //If j is an operation
                    lhs = negChecker(expr.get(j - 1));
                    rhs = negChecker(expr.get(j + 1));
                    
                    num = oper_map.get(expr.get(j)).apply(lhs,rhs);
                    if (num < 0){numstr = "n" + num.toString().substring(1);}
                    else{numstr = num.toString();}
                    
                    expr.set(j , numstr);
                    expr.remove(j + 1);
                    if (j != 0){ expr.remove(j - 1);}
                }
            }
        }
        
        if (expr.size() > 1){throw new Exception();}
        expr.set(0,negChecker(expr.get(0)).toString());
        
        return Double.parseDouble(expr.get(0));
    }
};