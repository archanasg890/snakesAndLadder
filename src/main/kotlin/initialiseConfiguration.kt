import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object Mapper {

    val module = KotlinModule()
    val mapperDash = ObjectMapper().registerModule(module)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .enable(JsonParser.Feature.ALLOW_COMMENTS)

}

data class BoardObjectConfig(val top: Int, val bottom: Int)
data class Configuration(
    val players: Int,
    val snakes: List<BoardObjectConfig>,
    val ladders: List<BoardObjectConfig>,
    val crookedDice: Boolean
)