import com.github.hamlet_rt.workoutapp.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = TngCreateResponse(
        requestId = "123",
        tng = TngResponseObject(
            title = "tng title",
            description = "tng description",
            tngType = TngType.POWER,
            visibility = TngVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"tng title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as TngCreateResponse

        assertEquals(response, obj)
    }
}
