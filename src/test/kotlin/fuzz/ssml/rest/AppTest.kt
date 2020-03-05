/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package fuzz.ssml.rest

import kotlin.test.*
import fr.xgouchet.elmyr.Forge
import java.io.File
import kotlin.random.Random

val KnownUnsupported = mapOf(
        "&#11;" to " ",
        "&#12;" to " "
)

class AppTest {
    @Test fun testBasicSsml() {
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
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"en-us\"><voice xml:lang=\"en-us\" xml:gender=\"Female\" name=\"en-US-JessaNeural\"></voice></speak>",
                ssml(text = ""))
    }

    @Test fun testFuzzText() {
        val seed = System.nanoTime()
        val forger = Forge()
        forger.seed = System.nanoTime()
        var text: String = ""
        val rand = Random(seed)
        val length = rand.nextInt(1, 8)
        for (i in 1..length) {
            val word: String = forger.aString()
            val space: String = forger.aWhitespaceChar().toString()
            text = text.plus(word).plus(space)
        }
        val ssml = ssml(lang = Language.CHS.value, gender = Gender.MALE.value, name = "zh-CN-YunyangNeural", text = text.trim())
        assertNotNull(ssml)
        //File(forger.seed.toString()).writeText(ssml)
    }

    @Test fun testFuzzSynthesize() {
        // seed = 174000494876383
        val body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<speak version=\"1.0\" xml:lang=\"zh-cn\"><voice xml:lang=\"zh-cn\" xml:gender=\"Male\" name=\"zh-CN-YunyangNeural\">먔ᛎ듮欓瑶⭃쇱悩짒㕓뒸拋蜏Ꮒ</voice></speak>"
        val synth = synthesize(body)
        assertNotNull(synth)
        //File("174000494876383.bin").writeBytes(synth)
    }

    @Test fun testFuzzSsmlSynth() {
        val seed = System.nanoTime()
        val forger = Forge()
        forger.seed = seed
        val name = forger.aKeyFrom(Voices)
        val gender = Voices[name].toString()
        val lang = name.substring(0, 5).toLowerCase()
        var text: String = ""
        val rand = Random(seed)
        val length = rand.nextInt(1, 8)
        for (i in 1..length) {
            val word: String = forger.aString()
            val space: String = forger.aWhitespaceChar().toString()
            text = text.plus(word).plus(space)
        }
        var body = ssml(lang = lang, gender = gender, name = name, text = text.trim())
        for (s in KnownUnsupported.keys) {
            body = body.replace(s, KnownUnsupported[s].toString())
        }
        assertNotNull(body)
        val synth = synthesize(body)
        assertNotNull(synth)
        //File(forger.seed.toString() + ".bin").writeBytes(synth)
    }
}
