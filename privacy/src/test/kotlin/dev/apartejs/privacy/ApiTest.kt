// API tests on the bundled model. Made-up personal data only: no real name, number or address lives in
// this repository. The text-by-text equality test against the Python reader is ParityTest.

package dev.apartejs.privacy

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApiTest {
    private val privacy: Privacy by lazy { Privacy.load() }

    @Test
    fun readsTheModelHeader() {
        assertEquals("privacy", privacy.header.task)
        assertEquals("1.0.0", privacy.header.version)
        assertEquals("int8", privacy.header.precision)
        assertEquals(
            listOf("private_person", "private_address", "private_email", "private_phone", "private_url", "private_date", "account_number"),
            privacy.header.subtypes,
        )
    }

    @Test
    fun findsANameWithItsKindAndOffsets() {
        val text = "Appelle Marie Lambert au 06 12 34 56 78 demain"
        val s = privacy.detect(text).firstOrNull { it.text.contains("Lambert") }
        assertNotNull(s, privacy.detect(text).toString())
        assertEquals("private_person", s.subtype)
        assertEquals(s.text, text.substring(s.start, s.end))
        assertTrue(s.score > 0 && s.score <= 1)
    }

    @Test
    fun anEmptyOrPrivacyFreeTextGivesNothing() {
        assertEquals(emptyList(), privacy.detect(""))
        assertEquals(emptyList(), privacy.detect("Can you explain how photosynthesis works?"))
    }

    @Test
    fun ignoreAndMinScoreAreAppliedAfterTheModel() {
        val text = "Appelle Marie Lambert au 06 12 34 56 78 demain"
        val value = privacy.detect(text).first { it.subtype == "private_person" }.text
        assertTrue(privacy.detect(text, ignore = listOf(value)).none { it.text == value })
        assertEquals(emptyList(), privacy.detect(text, minScore = 1.01))
    }

    @Test
    fun rejectsAFileThatIsNotAModel() {
        assertFailsWith<PrivacyException> { Privacy("not a model".toByteArray()) }
    }
}
