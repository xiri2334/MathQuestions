package bean;
/**
 * 分数类
 */
public class Fraction {
    private int inter;//整数部分
    private int numerator;//分子
    private int denominator;//分母


    public Fraction() {
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public Fraction(int inter, int numerator, int denominator) {
        this.inter = inter;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * 获取
     * @return inter
     */
    public int getInter() {
        return inter;
    }

    /**
     * 设置
     * @param inter
     */
    public void setInter(int inter) {
        this.inter = inter;
    }

    /**
     * 获取
     * @return numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * 设置
     * @param numerator
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    /**
     * 获取
     * @return denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * 设置
     * @param denominator
     */
    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }
    @Override
    public String toString() {
        return "Fraction{inter = " + inter + ", numerator = " + numerator + ", denominator = " + denominator + "}";
    }
}
