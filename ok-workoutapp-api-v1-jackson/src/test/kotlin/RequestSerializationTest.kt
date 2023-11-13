import com.github.hamlet_rt.workoutapp.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = TngCreateRequest(
        requestId = "123",
        debug = TngDebug(
            mode = TngRequestDebugMode.STUB,
            stub = TngRequestDebugStubs.BAD_TITLE
        ),
        tng = TngCreateObject(
            title = "tng title",
            description = "tng description",
            tngType = TngType.POWER,
            visibility = TngVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"tng title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as TngCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, TngCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
