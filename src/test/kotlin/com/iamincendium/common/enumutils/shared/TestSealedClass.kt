package com.iamincendium.common.enumutils.shared

sealed class TestSealedClass(val name: String) {
    data object SealedValueA : TestSealedClass("A")
    data object SealedValueB : TestSealedClass("B")
    data object SealedValueC : TestSealedClass("C")
    data object SealedValueD : TestSealedClass("D")
}
