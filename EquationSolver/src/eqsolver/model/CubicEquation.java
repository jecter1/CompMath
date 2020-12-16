package eqsolver.model;

import org.w3c.dom.ls.LSOutput;

public class CubicEquation {
    // ax^3 + bx^2 + cx + d = 0
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double eps;

    public CubicEquation(double a, double b, double c, double d, double eps) throws IncorrectEpsilonException {
        if (a != 0.0 && eps <= MIN_EPSILON) {
            throw new IncorrectEpsilonException();
        }

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.eps = eps;
    }

    @Override
    public String toString() {
        String str = "";
        str += (a == 0) ? "" : String.format("%.6f", a) + "x³";
        if (b != 0) {
            str += (str.isEmpty()) ? "" : (b > 0.0) ? " + " : " - ";
            str += String.format("%.6f", Math.abs(b)) + "x²";
        }
        if (c != 0) {
            str += (str.isEmpty()) ? "" : (c > 0.0) ? " + " : " - ";
            str += String.format("%.6f", Math.abs(c)) + "x";
        }
        if (d != 0) {
            str += (str.isEmpty()) ? "" : (d > 0.0) ? " + " : " - ";
            str += String.format("%.6f", Math.abs(d));
        }
        str += (str.isEmpty()) ? d : "";
        str += " = 0";
        str += (a == 0) ? "" : "\nε = " + eps;
        return str;
    }

    public String getAnswer() {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return answer0();
                } else {
                    return answer1();
                }
            } else {
                return answer2();
            }
        } else {
            return answer3();
        }
    }

    // a = b = c = 0
    private String answer0() {
        return (d == 0.0) ? "x ∈ (-∞; +∞)" : "x ∈ ø";
    }

    // a = b = 0
    // c != 0
    private String answer1() {
        double res = -d / c;
        return "x ≈ " + String.format("%.6f", res);
    }

    // a = 0
    // b != 0
    private String answer2() {
        // x^2 + Ax + B = 0
        double A = c / b;
        double B = d / b;

        double Disc = A * A - 4 * B;
        if (Disc < 0.0) {
            return "x ∈ ø";
        } else if (Disc == 0.0) {
            double root = -A / 2;
            return "x ≈ " + String.format("%.6f", root);
        } else {
            double root1 = (-A + Math.sqrt(Disc)) / 2;
            double root2 = (-A - Math.sqrt(Disc)) / 2;
            return "x₁ ≈ " + String.format("%.6f", root1) + "\n" + "x₂ ≈ " + String.format("%.6f", root2);
        }
    }

    // a != 0
    private String answer3() {
        // x^3 + Ax^2 + Bx + C = 0
        double A = b / a;
        double B = c / a;
        double C = d / a;

        // derivative: 3x^2 + 2Ax + B = 0
        // if 0 or 1 root then only 1 root of equation
        double Disc_half = A * A - 3 * B;
        if (Disc_half <= 0.0) { // 1 root
            double start_x = 0.0;
            return "x ≈ " + String.format("%.6f", findRootInf(start_x));
        } else {
            double der_root1 = (-A + Math.sqrt(Disc_half)) / 3;
            double der_root2 = (-A - Math.sqrt(Disc_half)) / 3;
            double func_der_root1 = function3(der_root1);
            double func_der_root2 = function3(der_root2);
            if (func_der_root1 < -eps && func_der_root2 < -eps) { // 1 root
                double start_x = Math.max(der_root1, der_root2);
                return "x ≈ " + String.format("%.6f", findRootInf(start_x));
            } else if (func_der_root1 > eps && func_der_root2 > eps) { // 1 root
                double start_x = Math.min(der_root1, der_root2);
                return "x ≈ " + String.format("%.6f", findRootInf(start_x));
            } else if (Math.abs(func_der_root1) > eps && Math.abs(func_der_root2) > eps) { // 3 roots
                return "x₁ ≈ " + String.format("%.6f", findRoot(der_root2, der_root1)) +
                        "\nx₂ ≈ " + String.format("%.6f", findRootInf(der_root1)) +
                        "\nx₃ ≈ " + String.format("%.6f", findRootInf(der_root2));
            } else if (Math.abs(func_der_root1) < eps) { // 2 roots
                return "x₁ ≈ " + String.format("%.6f", der_root1) +
                        "\nx₂ ≈ " + String.format("%.6f", findRootInf(der_root2));
            } else {
                return "x₁ ≈ " + String.format("%.6f", der_root2) +
                        "\nx₂ ≈ " + String.format("%.6f", findRootInf(der_root1));
            }
        }
    }

    // function3(a) < -eps => (a -> +inf)
    // function3(a) > eps => (-inf -> a)
    private double findRootInf(double a) {
        double res = a;
        double step;
        if (function3(res) > eps) {
            step = -1;
        } else if (function3(res) < -eps) {
            step = 1;
        } else {
            return res;
        }

        do {
            step *= 2;
            res += step;
        } while (function3(res) * step < 0);


        double a0 = Math.min(res, res - step);
        double b0 = Math.max(res, res - step);
        return findRoot(a0, b0);
    }

    private double findRoot(double a, double b) {
        double fa, fb, fc;
        double c;

        if (Math.abs(function3(a)) < eps) {
            return a;
        }
        if (Math.abs(function3(b)) < eps) {
            return b;
        }

        do {
            c = (a + b) / 2;
            fa = function3(a);
            fb = function3(b);
            fc = function3(c);
            if ((fc > eps && fa > eps) || (fc < -eps && fa < -eps)) {
                a = c;
            } else if ((fc > eps && fb > eps) || (fc < -eps && fb < -eps)) {
                b = c;
            }
        } while (Math.abs(fc) > eps);
        return c;
    }

    // x^3 + Ax^2 + Bx + C
    private double function3(double x) {
        double A = b / a;
        double B = c / a;
        double C = d / a;

        return x * x * x + A * x * x + B * x + C;
    }

    private static final double MIN_EPSILON = 0.0;
}