package fuurineditor.service.data.event

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.util.*

interface EventNode {

    val id: UUID

    val nodeTypeName: String

    val windowColor: Color

    val offsetX: Float

    val offsetY: Float

    fun copyWithOffset(offset: Offset): EventNode

    val screenValue: String?

}