/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package fuzz.ssml.rest

import kotlin.test.*
import fr.xgouchet.elmyr.Forge
import java.io.File

class AppTest {
    @Test fun testSSML() {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Female\" name=\"en-US-JessaNeural\">1: 23456. 789? 0!</voice></speak>",
                ssml())
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Female\" name=\"en-US-ZiraRUS\">1: 23456. 789? 0!</voice></speak>",
                ssml(name = "en-US-ZiraRUS"))
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Female\" name=\"en-US-JessaNeural\">abcdefg</voice></speak>",
                ssml(text = "abcdefg"))
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"pt-br\"><voice xml:lang=\"pt-br\" xml:gender=\"Female\" name=\"pt-BR-FranciscaNeural\">1: 23456. 789? 0!</voice></speak>",
                ssml(lang = Language.PTB.value, name = "pt-BR-FranciscaNeural"))
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Male\" name=\"en-US-GuyNeural\">1: 23456. 789? 0!</voice></speak>",
                ssml(gender = Gender.MALE.value, name = "en-US-GuyNeural"))
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"zh-cn\"><voice xml:lang=\"zh-cn\" xml:gender=\"Male\" name=\"zh-CN-YunyangNeural\">并非所有服务都适用于所有市场。</voice></speak>",
                ssml(lang = Language.CHS.value, gender = Gender.MALE.value, name = "zh-CN-YunyangNeural", text = "并非所有服务都适用于所有市场。"))
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Female\" name=\"en-US-JessaNeural\"></voice></speak>",
                ssml(text = ""))
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Female\" name=\"en-US-JessaNeural\">&amp;&lt;&gt;\t\n" +
                "\"'</voice></speak>",
                ssml(text = "&<>\t\n\"'"))
    }

    @Test fun testFuzzText() {
        var text: String = ""
        var forger = Forge()
        forger.seed = System.nanoTime()
        val length: Int = forger.aTinyInt()
        for (i in 1..length) {
            val word: String = forger.aString()
            val space: String = forger.aWhitespaceChar().toString()
            text = text.plus(word).plus(space)
        }
        val ssml = ssml(lang = Language.CHS.value, gender = Gender.MALE.value, name = "zh-CN-YunyangNeural", text = text.trim())
        assertNotNull(ssml)
        File(forger.seed.toString()).writeText(ssml)
    }
}
