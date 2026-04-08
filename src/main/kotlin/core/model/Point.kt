package core.model

import java.math.BigDecimal

sealed class Point(val x: BigDecimal, val type: PointType) {
    class Removable(x: BigDecimal, val fixed: BigDecimal) : Point(x, PointType.REMOVABLE)
    class Second(x: BigDecimal) : Point(x, PointType.SECOND)
    class Essential(x: BigDecimal) : Point(x, PointType.ESSENTIAL)
}
