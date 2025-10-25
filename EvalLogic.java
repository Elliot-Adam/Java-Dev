import java.util.HashMap;
import java.util.ArrayList;

public class EvalLogic {
    @FunctionalInterface
    public interface DoubleOperator {
        public Double apply(Double a, Double b);
    }
    
    public boolean opr_in_expr(String input){
        String[] oprs = {"+", "-","*","/"};
        for (String opr : oprs){
            if (input.contains(opr)){
                return true;
            }
        }
        return false;
    }
    
    public static HashMap<String,DoubleOperator> getOperMap(){
        HashMap<String,DoubleOperator> oper_map = new HashMap<String,DoubleOperator>();
        oper_map.put("+", (n1,n2)->{return n1 + n2;});
        oper_map.put("-", (n1,n2)->{return n1 - n2;});
        oper_map.put("*", (n1,n2)->{return n1 * n2;});
        oper_map.put("/", (n1,n2)->{return n1 / n2;});
        
        return oper_map;
    }
    
    public ArrayList<String> parseExpr(String input){
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
        ArrayList<String> expr = parseExpr(input);
        //System.out.println(expr); // DEBUG
        
        Double num;
        String numstr;
        //While there are still operators in the expression
        while (opr_in_expr(String.join("",expr))) {
            for (int j = 0; j < expr.size(); j++){
                if (oper_map.get(expr.get(j)) != null){
                    //If j is an operation
                    num = oper_map.get(expr.get(j)).apply(Double.parseDouble(expr.get(j-1)),Double.parseDouble(expr.get(j+1)));
                    if (num < 0){numstr = "n" + num.toString().substring(1);}
                    else{numstr = num.toString();}
                    
                    expr.set(j - 1, numstr);
                    expr.remove(j + 1);
                    expr.remove(j);
                }
            }
        }
        
        if (expr.size() > 1){throw new Exception();}
        if (("" + expr.get(0).charAt(0)).equals("n")) {
            expr.set(0, String.valueOf(-1 * Double.parseDouble(expr.get(0).substring(1))));
        }
        
        return Double.parseDouble(expr.get(0));
    }
};