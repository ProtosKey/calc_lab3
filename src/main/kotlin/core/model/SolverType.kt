package core.model

enum class SolverType(val label: String) {
    LEFT_SQUARE("Метод левый прямоугольников"),
    MIDDLE_SQUARE("Метод средний прямоугольников"),
    RIGHT_SQUARE("Метод правый прямоугольников"),
    SIMPSON("Метод Симпсона"),
    TRAPEZE("Метод трапеций")
}
