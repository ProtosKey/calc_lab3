package core.solver

import core.basic.CanSolve
import core.basic.Factory
import core.model.SolverType
import text.solver.MiddleSquareSolver

object SolverFactory : Factory<SolverType, CanSolve> {
    private val SOLVERS = mapOf(
        SolverType.LEFT_SQUARE to LeftSquareSolver(),
        SolverType.MIDDLE_SQUARE to MiddleSquareSolver(),
        SolverType.RIGHT_SQUARE to RightSquareSolver(),
        SolverType.SIMPSON to SimpsonSolver(),
        SolverType.TRAPEZE to TrapezeSolver()
    )

    override fun create(type: SolverType): CanSolve {
        return SOLVERS[type]!!
    }
}
