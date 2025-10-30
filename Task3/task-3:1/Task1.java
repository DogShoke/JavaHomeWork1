//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Task1 {
    public static void main(String[] args) {
        int x = (new java.util.Random()).nextInt(100, 999);
        System.out.println(x);
        String s = Integer.toString(x);
        int max = 0;
        for(int i = 0; i < s.length(); i++){
           int y =s.charAt(i) -'0';
           if(y > max)
               max = y;
        }
        System.out.println(max);

        System.out.println();
        int x1 = (new java.util.Random()).nextInt(100, 999);
        int x2 = (new java.util.Random()).nextInt(100, 999);
        int x3 = (new java.util.Random()).nextInt(100, 999);
        System.out.println(x1);
        System.out.println(x2);
        System.out.println(x3);
        int sum1 = 0;
        String s1 = Integer.toString(x1);
        String s2 = Integer.toString(x2);
        String s3 = Integer.toString(x3);

        sum1 += s1.charAt(0) -'0';
        sum1 += s2.charAt(0) -'0';
        sum1 += s3.charAt(0) -'0';
        System.out.println(sum1);

        System.out.println();
        int diff = x1 + x2 - x3;
        System.out.println(diff);

        System.out.println();
        s = Integer.toString(x);
        int sum = 0;
        for(int i = 0; i < s.length(); i++){
            int y = s.charAt(i) -'0';
            sum += y;
        }
        System.out.println(s);
        System.out.println(sum);
    }
}