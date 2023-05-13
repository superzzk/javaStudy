package zzk.study.java.core.basic.number;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryNumbersUnitTest {

    private BinaryNumbers binaryNumbers = new BinaryNumbers();

    @Test
    public void given_decimalNumber_then_returnBinaryNumber() {
        assertEquals(Integer.valueOf(1000), binaryNumbers.convertDecimalToBinary(8));
        assertEquals(Integer.valueOf(10100), binaryNumbers.convertDecimalToBinary(20));
    }

    @Test
    public void given_decimalNumber_then_convertToBinaryNumber() {
        assertEquals("1000", Integer.toBinaryString(8));
        assertEquals("10100", Integer.toBinaryString(20));
    }

    @Test
    public void given_binaryNumber_then_ConvertToDecimalNumber() {
        assertEquals(8, Integer.parseInt("1000", 2));
        assertEquals(20, Integer.parseInt("10100", 2));
    }

    @Test
    public void given_binaryNumber_then_returnDecimalNumber() {
        assertEquals(Integer.valueOf(8), binaryNumbers.convertBinaryToDecimal(1000));
        assertEquals(Integer.valueOf(20), binaryNumbers.convertBinaryToDecimal(10100));
    }

    @Test
    public void given_twoBinaryNumber_then_returnAddition() {
        // adding 4 and 10
        assertEquals(Integer.valueOf(1110), binaryNumbers.addBinaryNumber(100, 1010));

        // adding 26 and 14
        assertEquals(Integer.valueOf(101000), binaryNumbers.addBinaryNumber(11010, 1110));
    }

    @Test
    public void given_twoBinaryNumber_then_returnSubtraction() {
        // subtracting 16 from 25
        assertEquals(Integer.valueOf(1001), binaryNumbers.substractBinaryNumber(11001, 10000));

        // subtracting 29 from 16, the output here is negative
        assertEquals(Integer.valueOf(1101), binaryNumbers.substractBinaryNumber(10000, 11101));
    }

    @Test
    public void given_binaryLiteral_thenReturnDecimalValue() {

        byte five = 0b101;
        assertEquals((byte) 5, five);

        short three = 0b11;
        assertEquals((short) 3, three);

        int nine = 0B1001;
        assertEquals(9, nine);

        long twentyNine = 0B11101;
        assertEquals(29, twentyNine);

        int minusThirtySeven = -0B100101;
        assertEquals(-37, minusThirtySeven);

    }

    public class BinaryNumbers {

        /**
         * This method takes a decimal number and convert it into a binary number.
         * example:- input:10, output:1010
         *
         * @param decimalNumber
         * @return binary number
         */
        public Integer convertDecimalToBinary(Integer decimalNumber) {

            if (decimalNumber == 0) {
                return decimalNumber;
            }

            StringBuilder binaryNumber = new StringBuilder();
            Integer quotient = decimalNumber;

            while (quotient > 0) {

                int remainder = quotient % 2;
                binaryNumber.append(remainder);
                quotient /= 2;
            }

            binaryNumber = binaryNumber.reverse();
            return Integer.valueOf(binaryNumber.toString());
        }

        /**
         * This method takes a binary number and convert it into a decimal number.
         * example:- input:101, output:5
         *
         * @param binary number
         * @return decimal Number
         */
        public Integer convertBinaryToDecimal(Integer binaryNumber) {

            Integer decimalNumber = 0;
            Integer base = 1;

            while (binaryNumber > 0) {

                int lastDigit = binaryNumber % 10;
                binaryNumber = binaryNumber / 10;

                decimalNumber += lastDigit * base;
                base = base * 2;
            }
            return decimalNumber;
        }

        /**
         * This method accepts two binary numbers and returns sum of input numbers.
         * Example:- firstNum: 101, secondNum: 100, output: 1001
         *
         * @param firstNum
         * @param secondNum
         * @return addition of input numbers
         */
        public Integer addBinaryNumber(Integer firstNum, Integer secondNum) {

            StringBuilder output = new StringBuilder();

            int carry = 0;
            int temp;

            while (firstNum != 0 || secondNum != 0) {

                temp = (firstNum % 10 + secondNum % 10 + carry) % 2;
                output.append(temp);

                carry = (firstNum % 10 + secondNum % 10 + carry) / 2;

                firstNum = firstNum / 10;
                secondNum = secondNum / 10;
            }

            if (carry != 0) {
                output.append(carry);
            }

            return Integer.valueOf(output.reverse()
                    .toString());
        }

        /**
         * This method takes two binary number as input and subtract second number from the first number.
         * example:- firstNum: 1000, secondNum: 11, output: 101
         * @param firstNum
         * @param secondNum
         * @return Result of subtraction of secondNum from first
         */
        public Integer substractBinaryNumber(Integer firstNum, Integer secondNum) {

            int onesComplement = Integer.valueOf(getOnesComplement(secondNum));
            StringBuilder output = new StringBuilder();
            int carry = 0;
            int temp;

            while (firstNum != 0 || onesComplement != 0) {

                temp = (firstNum % 10 + onesComplement % 10 + carry) % 2;
                output.append(temp);

                carry = (firstNum % 10 + onesComplement % 10 + carry) / 2;

                firstNum = firstNum / 10;
                onesComplement = onesComplement / 10;
            }

            String additionOfFirstNumAndOnesComplement = output.reverse()
                    .toString();

            if (carry == 1) {
                return addBinaryNumber(Integer.valueOf(additionOfFirstNumAndOnesComplement), carry);
            } else {
                return getOnesComplement(Integer.valueOf(additionOfFirstNumAndOnesComplement));
            }
        }

        public Integer getOnesComplement(Integer num) {

            StringBuilder onesComplement = new StringBuilder();
            while (num > 0) {
                int lastDigit = num % 10;
                if (lastDigit == 0) {
                    onesComplement.append(1);
                } else {
                    onesComplement.append(0);
                }
                num = num / 10;
            }
            return Integer.valueOf(onesComplement.reverse()
                    .toString());
        }

    }
}
