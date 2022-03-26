package fuurineditor.repository.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable()
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class IconBaseJson(

    /**
     * 横方向に何分割するか
     */
    @SerialName("x_size")
    val xSize: Int = 1,

    /**
     * 縦方向に何分割するか
     */
    @SerialName("y_size")
    val ySize: Int = 1

) {


}
