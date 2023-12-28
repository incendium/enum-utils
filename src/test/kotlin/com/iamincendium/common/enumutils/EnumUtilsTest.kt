package com.iamincendium.common.enumutils

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import com.iamincendium.common.enumutils.shared.TestEnum
import io.kotest.core.spec.style.DescribeSpec

class EnumUtilsTest : DescribeSpec({
    describe("enumValueOfOrNull") {
        it("should return null for null value") {
            assertThat(enumValueOfOrNull<TestEnum>(null)).isNull()
        }
        it("should return null for an invalid enum value") {
            assertThat(enumValueOfOrNull<TestEnum>("NOT_VALID")).isNull()
        }
        it("should return an enum instance for a valid enum value") {
            assertThat(enumValueOfOrNull<TestEnum>("A")).isEqualTo(TestEnum.A)
        }
    }

    describe("enumLookupMap") {
        val lookupMap = enumLookupMap<String, TestEnum> { it.name }

        it("should contain all enum values") {
            assertThat(lookupMap).containsOnly(
                "A" to TestEnum.A,
                "B" to TestEnum.B,
                "C" to TestEnum.C,
                "D" to TestEnum.D,
            )
        }

        it("should not contain an invalid value") {
            assertThat("E" in lookupMap).isFalse()
            assertThat("F" in lookupMap).isFalse()
            assertThat("INVALID_VALUE" in lookupMap).isFalse()
            assertThat(lookupMap["BAD_VALUE"]).isNull()
        }
    }
})
