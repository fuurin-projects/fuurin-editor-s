package fuurineditor.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GWorldJson(

    /**
     * Worldの名前
     */
    @SerialName("world_name")
    val worldName: String,

    /**
     * Worldのデータ
     */
    @SerialName("image_data")
    val imageData: Array<Array<Int>>,

    /**
     * Worldの画像マッピング
     */
    @SerialName("image_mapping")
    val imageMapping: Map<String, String>


)