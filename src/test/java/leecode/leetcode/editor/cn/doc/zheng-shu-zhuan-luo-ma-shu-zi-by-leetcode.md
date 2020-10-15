####  概述
在许多国家，罗马数字在小学就就有教学。这对于它们来说是一个轻松的面试问题。然而并不是每个人都在学校里面学过，因此学过的人就获得了很大的优势。这是不公平也令人沮丧的，但是你要记住你能够做的就是把这个问题和相关的问题从头到尾解决，这样你就不会在面试中遇到麻烦。

**先从罗马数到整数开始**

把罗马数字转换成整数的问题比较简单。因此，如果你觉得这个问题很难，我们建议你先解决这个问题。这将使您能够更加熟悉罗马数字的概念，而不会出现将整数转换为罗马数字时出现的“歧义”问题。当将罗马数字转换为整数时，只有一个合理的转换。

**罗马数字符号**

罗马数字由 7 个单字母符号组成，每个符号都有自己的价值。此外，减法规则（如问题描述中所述）给出了额外的 6 个符号。这给了我们总共 13 个独特的符号（每个符号由 1 个字母或 2 个字母组成）。

 [在这里插入图片描述](https://img-blog.csdnimg.cn/2020041410522079.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zOTEzOTUwNQ==,size_16,color_FFFFFF,t_70)
一个整数被表示为一个罗马数字，通过查找符号来增加它的值。

**歧义处理**

如果你不熟悉罗马数字，有一件事会让你有点困惑，那就是知道哪种表示法是一个特定整数的“正确”表示法。例如，考虑 140 表示的可能方法。哪一个是正确的？

 [在这里插入图片描述](https://img-blog.csdnimg.cn/20200414105438254.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zOTEzOTUwNQ==,size_16,color_FFFFFF,t_70)

我们用来决定的规则是从左到右选择尽可能大的符号表示。例如，上面以最大符号开头的表示法是以 C 开头的表示法。

 [在这里插入图片描述](https://img-blog.csdnimg.cn/20200414105536370.png)

为了决定使用哪一个表示法，我们看下一个符号。其中两个为 X 值 10，一个 XL 值 40。因为 XL 更大，所以我们采用这种表示法。因此，140 的表示是 CXL。

现在，罗马数字的这个定义是 “最被接受的”。有趣的是，它仍然不是一个绝对的标准，纵观历史，已经有很多变种。如果你对数学和历史感兴趣，我们建议你自己去看看维基百科的文章。

####  方法一：贪心
将给定的整数转换为罗马数字需要找到上述 13 个符号的序列，这些符号的对应值加起来就是整数。根据符号值，此序列必须按从大到小的顺序排列。符号值如下。

 [在这里插入图片描述](https://img-blog.csdnimg.cn/20200414105909472.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zOTEzOTUwNQ==,size_16,color_FFFFFF,t_70)

如概述中所述，表示应该使用尽可能大的符号，从左侧开始工作。因此，使用贪心算法是有意义的。贪心算法是一种在当前时间做出最佳可能决策的算法；在这种情况下，它会取出最大可能的符号。

为了表示一个给定的整数，我们寻找适合它的最大符号。我们减去它，然后寻找适合余数的最大符号，依此类推，直到余数为0。我们取出的每个符号都附加到输出的罗马数字字符串上。

例如，假设我们需要将数字设为 `671`。

`671` 中最大的符号是 `D`（值 `500`）。

```
Roman Numeral so far: D
Integer remainder: 671 - 500 = 171
```

我们在 `171` 的基础重复以上步骤，最大的符号是 `C`（值 `100`）。

```
Roman Numeral so far: DC
Integer remainder: 171 - 100 = 71
```

在 `71` 的基础重复以上步骤，最大的符号是 `L` （值 `50`）。

```
Roman Numeral so far: DCL
Integer remainder: 71 - 50 = 21
```

在 `21` 的基础重复以上步骤，最大的符号是 `X` （值 `10`）。

```
Roman Numeral so far: DCLX
Integer remainder: 21 - 10 = 11
```

在 `11` 的基础重复以上步骤，最大的符号是 `X` （值 `10`）。

```
Roman Numeral so far: DCLXX
Integer remainder: 11 - 10 = 1
```

最后，用 `I` 表示 1，完成转换。

```
Roman Numeral so far: DCLXXI
Integer remainder: 1 - 1 = 0
```

在伪代码中，该算法如下：

```
define function to_roman(integer):
    roman_numeral = ""
    while integer is non-zero:
        symbol = biggest valued symbol that fits into integer
        roman_numeral = concat roman_numeral and symbol
        integer = integer - value of symbol
    return roman_numeral
```

在代码中实现这一点的最简单的方法是从最大到最小循环遍历每个符号，检查当前符号的有多少个副本适合剩余的整数。

```
define function to_roman(integer):
    roman_numeral = ""
    for each symbol from largest to smallest:
        if value of symbol is greater than integer:
            continue
        symbol_count = number of times symbol value fits into integer
        repeat symbol_count times:
            roman_numeral = concat roman_numeral and symbol
        integer = integer - (value of symbol * symbol_count)

    return roman_numeral
```

以下动画显示了算法在 478 上运行的情况：

  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTEuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTIuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTMuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTQuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTUuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTYuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTcuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTguUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTkuUE5H?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTEwLlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTExLlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTEyLlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTEzLlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTE0LlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTE1LlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTE2LlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTE3LlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTE4LlBORw?x-oss-process=image/format png)  [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZ3JlZWR5X2FuaW1hdGlvbi9TbGlkZTE5LlBORw?x-oss-process=image/format png) 

**算法：**

```python [solution1-Python]
digits = [(1000, "M"), (900, "CM"), (500, "D"), (400, "CD"), (100, "C"), (90, "XC"), 
          (50, "L"), (40, "XL"), (10, "X"), (9, "IX"), (5, "V"), (4, "IV"), (1, "I")]

def intToRoman(self, num: int) -> str:
    roman_digits = []
    # Loop through each symbol.
    for value, symbol in digits:
        # We don't want to continue looping if we're done.
        if num == 0: break
        count, num = divmod(num, value)
        # Append "count" copies of "symbol" to roman_digits.
        roman_digits.append(symbol * count)
    return "".join(roman_digits)
```

```java [solution1-Java]
int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};    
String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

public String intToRoman(int num) {
    StringBuilder sb = new StringBuilder();
    // Loop through each symbol, stopping if num becomes 0.
    for (int i = 0; i < values.length && num >= 0; i++) {
        // Repeat while the current symbol still fits into num.
        while (values[i] <= num) {
            num -= values[i];
            sb.append(symbols[i]);
        }
    }
    return sb.toString();
}
```

**复杂度分析**

* 时间复杂度：*O(1)*。由于有一组有限的罗马数字，循环可以迭代多少次有一个硬上限。因此，我们说时间复杂度是常数的，即 *O(1)*。
* 空间复杂度：*O(1)*，使用的内存量不会随输入整数的大小而改变，因此是常数的。


####  方法二：硬编码数字
你会发现，当把整数转换成罗马数字时，整数的十进制表示中的每一个数字都可以单独处理。所有的符号可以根据在 1000，100，10 和 1 的最大因子分成多个组。

 [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvcm9tYW5fbnVtZXJhbF9zeW1ib2xfdmFsdWVzLnBuZw?x-oss-process=image/format,png)
当数字至少为 `1000` 时，将向输出追加 `M(1000）`，并从整数中减去 `1000`。其他符号在数字低于 `1000` 之前都不会被考虑。此外，`M(1000)` 不能表示数字的任何较低的部分。因此，我们可以用 `M(1000)` 来表示整数的千位。

现在，假设我们有 `100` 到 `999` 之间的余数。接下来考虑在此范围的符号。最高的符号是 `CM(900)`，最低的是 `C(100)`。此范围内的任何符号都不可能修改成十或一。只要余数仍在100以上，我们就至少可以取 `C(100)`。这意味着只要数字至少是 `100`，我们就只能从余数中减去符号。

同样的方法也适用于十，然后是一。

因此，我们可以计算出每个数字在每个地方的表示形式。总共有 34 个，千列是 `0、1、2、3、4`，百、十、一列是 `0、1、2、3、4、5、6、7、8、9`。因此，计算出它们的表示情况，并对它们进行硬编码。然后，将整数转换为罗马数字将需要将整数分解为并将每个数字的表示追加到结果。

 [在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9waWMubGVldGNvZGUtY24uY29tL0ZpZ3VyZXMvMTIvZGlnaXRfcm9tYW5fbnVtZXJhbF92YWx1ZXMucG5n?x-oss-process=image/format,png)
使用模运算和除法运算，可以得到我们数字中每个位上的数字。

```
thousands_digit = integer / 1000
hundreds_digit = (integer % 1000) / 100
tens_digit = (integer % 100) / 10
ones_digit = integer % 10
```

然后，我们可以简单地在硬编码表中查找这些结果，并将结果附加在一起！

**算法：**

在代码中实现它最简单的方法是使用 4 个独立的数组；每个位置值对应一个数组。然后，在输入数字中提取每个位置的数字，在相关数组中查找它们的符号，并将它们全部附加在一起。

```python [solution2-Python]
def intToRoman(self, num: int) -> str:
    thousands = ["", "M", "MM", "MMM"]
    hundreds = ["", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"]
    tens = ["", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"]
    ones = ["", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"]
    return thousands[num // 1000] + hundreds[num % 1000 // 100] + tens[num % 100 // 10] + ones[num % 10]
```

```java [solution2-Java]
public String intToRoman(int num) {
    
    String[] thousands = {"", "M", "MM", "MMM"};
    String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; 
    String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    
    return thousands[num / 1000] + hundreds[num % 1000 / 100] + tens[num % 100 / 10] + ones[num % 10];
}
```

**复杂度分析**

* 时间复杂度：*O(1)*。无论输入的大小，都会执行相同数量的操作。因此，时间复杂度是常数的。
* 空间复杂度：*O(1)*，虽然我们使用数组，但不管输入的大小，它们都是相同的大小。因此，它们是常数级空间。

这种方法的缺点是，如果要扩展罗马数字，它是不灵活的（这是一个有趣的后续问题）。例如，如果我们说符号 `H` 现在表示 `5000`，而 `P` 现在表示 `10000`，允许我们表示多达 `39999` 的数字，会怎么样？方法 1 修改起来要快得多，因为您只需要将这两个值添加到代码中，而不需要进行任何计算。但是对于方法 2，您需要计算并硬编码 `10` 个新的表示。如果我们再加上一些符号就能达到 `39999999` 呢？方法2变得越来越难管理，我们添加的符号越多。