package core.model

enum class SolverType(val label: String) {
    LEFT_SQUARE("Метод левых прямоугольников"),
    MIDDLE_SQUARE("Метод средних прямоугольников"),
    RIGHT_SQUARE("Метод правых прямоугольников"),
    SIMPSON("Метод Симпсона"),
    TRAPEZE("Метод трапеций")
}
