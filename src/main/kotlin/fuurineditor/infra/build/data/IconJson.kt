package fuurineditor.infra.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IconJson(

    /**
     * spriteの名前
     */
    @SerialName("sprite")
    val sprite: String,

    /**
     * Sprite内での起点(x)
     */
    @SerialName("sx")
    val sx: Int,

    /**
     * Sprite内での起点(y)
     */
    @SerialName("sy")
    val sy: Int,

    /**
     * Sprite内でのサイズ(x)
     */
    @SerialName("sw")
    val sw: Int,

    /**
     * Sprite内でのサイズ(y)
     */
    @SerialName("sh")
    val sh: Int

)
