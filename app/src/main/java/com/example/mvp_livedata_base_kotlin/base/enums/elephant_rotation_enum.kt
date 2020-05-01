package com.example.mvp_livedata_base_kotlin.base.enums

enum class ElephantRotationEnum{
    DEGREES_0, DEGREES_45, DEGREES_60, DEGREES_90, DEGREES_120, DEGREES_135, DEGREES_180, DEGREES_270,
}

fun Int.toDegrees(): ElephantRotationEnum{
    return when(this) {
        0 -> ElephantRotationEnum.DEGREES_0
        45 -> ElephantRotationEnum.DEGREES_45
        60 -> ElephantRotationEnum.DEGREES_60
        90 -> ElephantRotationEnum.DEGREES_90
        120 -> ElephantRotationEnum.DEGREES_120
        135 -> ElephantRotationEnum.DEGREES_135
        180 -> ElephantRotationEnum.DEGREES_180
        270 -> ElephantRotationEnum.DEGREES_270
        else -> ElephantRotationEnum.DEGREES_0
    }
}