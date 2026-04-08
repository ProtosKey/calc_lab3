package presentation.model

enum class IntegralType(val label: String) {
    SQUARE("x^{2}"),
    CUBE_PLUS("x^3 + 2x"),
    EXPONENT("e^x"),
    LINEAR("x"),
    SIN("\\sin(x)"),
    COS("\\cos(x)"),
    POLYNOMIAL("x^4 - 3x^2 + 5"),
    SIN_SQUARE("\\sin^2(x)"),
    X_SIN("x \\cdot \\sin(x)"),
    ABS("|x|"),
    REMOVABLE_FRAC("\\frac{x^2 - 1}{x - 1}"),
    EXP_SUM("e^x + e^{-x}"),
    SQRT("\\sqrt{x}"),
    LN("\\ln(x)"),
}
