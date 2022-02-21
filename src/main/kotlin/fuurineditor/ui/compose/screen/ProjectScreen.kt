package fuurineditor.ui.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material.icons.sharp.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.File
import fuurineditor.ui.compose.parts.FunctionPanel
import fuurineditor.ui.compose.parts.FunctionSubPanel
import fuurineditor.ui.compose.parts.ToolButton
import fuurineditor.ui.compose.parts.VerticalDivider
import fuurineditor.ui.data.FunctionType
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border
import fuurineditor.ui.theme.IconGreenColor
import fuurineditor.ui.theme.IconRedColor

data class ProjectScreenUIState(
    val functionType: FunctionType = FunctionType.General
)

@Composable
fun ProjectScreen(
    usState: ProjectScreenUIState,
    tiletipList: File?,
    onClickFunctionButton: (type: FunctionType) -> Unit = {}
) {

    Column {
        //上の段
        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {
            ToolButton(imageVector = Icons.Sharp.PlayArrow, tint = IconGreenColor)
            ToolButton(imageVector = Icons.Sharp.Stop, tint = IconRedColor)
        }
        Divider(color = Border, thickness = 1.dp)
        //真ん中の段
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {

            FunctionPanel(
                modifier = Modifier.fillMaxHeight().width(80.dp).background(Background),
                functionType = usState.functionType,
                onClickFunctionButton = onClickFunctionButton
            )

            VerticalDivider(color = Border, thickness = 1.dp)

            FunctionSubPanel(
                modifier = Modifier.fillMaxHeight().width(160.dp),
                functionType = usState.functionType,
                tiletipList = tiletipList
            )

            VerticalDivider(color = Border, thickness = 1.dp)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
                ) {
                    Text(text = "s")
                }
                Divider(color = Border, thickness = 1.dp)
                Column {
                    Text(text = "s")
                }
            }
        }
        Divider(color = Border, thickness = 1.dp)
        //下の段
        Row(
            modifier = Modifier.height(20.dp).fillMaxWidth().background(Background)
        ) {
            Text(text = "s")
        }
    }

}