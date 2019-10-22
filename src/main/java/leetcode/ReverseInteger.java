package leetcode;

public class ReverseInteger {

    public static void main(String[] args){
        ReverseInteger demo = new ReverseInteger();
        int x = 34236469;
        System.out.println(demo.reverse2(x));
    }

    public int reverse(int x) {
        int rt=0;
        int y = x;
        int z = 0;
        StringBuilder sb = new StringBuilder();
        if(x<0){
            sb.append("-");
            x= Math.abs(x);
        }
        if(x==0){
            sb.append(x);
        }
        while(x>0){
            z = x%10;
            x = x/10;
            sb.append(z);
        }
        try {
            rt = Integer.parseInt(sb.toString());
        }catch (NumberFormatException e){
            rt=0;
        }
        return rt;
    }

    public int reverse2(int x) {
        String str2 = "";
        if(x<0){
            str2 += "-";
            x= Math.abs(x);
        }
        String str = Integer.toString(x);

        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            str2+=c;
        }

        int rt = 0;
        try {
            rt = Integer.valueOf(str2);
        }catch (NumberFormatException e){
            rt=0;
        }
        return rt;
    }

    /*
    * Approach 1: Pop and Push Digits & Check before Overflow
    Intuition
        We can build up the reverse integer one digit at a time. While doing so, we can check
        beforehand whether or not appending another digit would cause overflow.
    Algorithm
        Reversing an integer can be done similarly to reversing a string.
        We want to repeatedly "pop" the last digit off of x and "push" it to the back of
        the rev. In the end, rev will be the reverse of the x.

        To "pop" and "push" digits without the help of some auxiliary stack/array, we can use math.

        //pop operation:
        pop = x % 10;
        x /= 10;

        //push operation:
        temp = rev * 10 + pop;
        rev = temp;

        However, this approach is dangerous, because the statement temp = rev * 10 + pop can cause overflow.
        Luckily, it is easy to check beforehand whether or this statement would cause an overflow.
    */
    public int reverse3(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    public int reverse4(int x) {
        boolean isNegative = x < 0;
        x = isNegative ? x * -1 : x;

        String y = String.valueOf(x);

        char[] chars = y.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i <= chars.length; i++) {
            stringBuilder.append(chars[chars.length - i]);
        }

        try {
            return isNegative ? -1 * Integer.valueOf(stringBuilder.toString()) : Integer.valueOf(stringBuilder.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
