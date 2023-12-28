package com.iamincendium.common.enumutils.shared

sealed interface TestSealedInterface {
    val name: String

    data object SealedValueA : TestSealedInterface {
        override val name: String = "A"
    }
    data object SealedValueB : TestSealedInterface {
        override val name: String = "B"
    }
    data object SealedValueC : TestSealedInterface {
        override val name: String = "C"
    }
    data object SealedValueD : TestSealedInterface {
        override val name: String = "D"
    }
    data class UnknownValue(override val name: String) : TestSealedInterface
}
