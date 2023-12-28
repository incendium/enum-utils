package com.iamincendium.kotlin.enumutils

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsOnly
import com.iamincendium.kotlin.enumutils.shared.TestSealedClass
import com.iamincendium.kotlin.enumutils.shared.TestSealedInterface
import io.kotest.core.spec.style.DescribeSpec

class SealedClassUtilsTest : DescribeSpec({
    describe("sealedValues") {
        describe("using TestSealedClass") {
            it("should generate a list with all objects") {
                assertThat(sealedValues<TestSealedClass>()).containsExactly(
                    TestSealedClass.SealedValueA,
                    TestSealedClass.SealedValueB,
                    TestSealedClass.SealedValueC,
                    TestSealedClass.SealedValueD,
                )
            }
        }

        describe("using TestSealedInterface") {
            it("should generate a list with all objects") {
                assertThat(sealedValues<TestSealedInterface>()).containsExactly(
                    TestSealedInterface.SealedValueA,
                    TestSealedInterface.SealedValueB,
                    TestSealedInterface.SealedValueC,
                    TestSealedInterface.SealedValueD,
                )
            }
        }
    }

    describe("sealedObjectLookupMap") {
        describe("using TestSealedClass") {
            val lookupMap = sealedObjectLookupMap<String, TestSealedClass> { it.name }

            it("should contain all objects") {
                assertThat(lookupMap).containsOnly(
                    "A" to TestSealedClass.SealedValueA,
                    "B" to TestSealedClass.SealedValueB,
                    "C" to TestSealedClass.SealedValueC,
                    "D" to TestSealedClass.SealedValueD,
                )
            }
        }

        describe("using TestSealedInterface") {
            val lookupMap = sealedObjectLookupMap<String, TestSealedInterface> { it.name }

            it("should contain all objects") {
                assertThat(lookupMap).containsOnly(
                    "A" to TestSealedInterface.SealedValueA,
                    "B" to TestSealedInterface.SealedValueB,
                    "C" to TestSealedInterface.SealedValueC,
                    "D" to TestSealedInterface.SealedValueD,
                )
            }
        }
    }

    describe("sealedObjectLookupMapFromClassName") {
        describe("using TestSealedClass") {
            val lookupMap = sealedObjectLookupMapFromClassName<TestSealedClass>()

            it("should contain all objects") {
                assertThat(lookupMap).containsOnly(
                    "SealedValueA" to TestSealedClass.SealedValueA,
                    "SealedValueB" to TestSealedClass.SealedValueB,
                    "SealedValueC" to TestSealedClass.SealedValueC,
                    "SealedValueD" to TestSealedClass.SealedValueD,
                )
            }
        }

        describe("using TestSealedInterface") {
            val lookupMap = sealedObjectLookupMapFromClassName<TestSealedInterface>()

            it("should contain all objects") {
                assertThat(lookupMap).containsOnly(
                    "SealedValueA" to TestSealedInterface.SealedValueA,
                    "SealedValueB" to TestSealedInterface.SealedValueB,
                    "SealedValueC" to TestSealedInterface.SealedValueC,
                    "SealedValueD" to TestSealedInterface.SealedValueD,
                )
            }
        }
    }
})
