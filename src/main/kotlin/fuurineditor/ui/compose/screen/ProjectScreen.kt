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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.ui.compose.parts.FunctionPanel
import fuurineditor.ui.compose.parts.FunctionSubPanel
import fuurineditor.ui.compose.parts.VerticalDivider
import fuurineditor.ui.data.FunctionType
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border

data class ProjectScreenUIState(
    val functionType: FunctionType = FunctionType.General
)

@Composable
fun ProjectScreen(
    usState: ProjectScreenUIState,
    onClickFunctionButton: (type: FunctionType) -> Unit = {}
) {

    Column {
        //上の段
        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {
            Text(text = "s")
        }
        Divider(color = Border, thickness = 1.dp)
        //真ん中の段
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {

            FunctionPanel(
                modifier = Modifier.fillMaxHeight().width(80.dp).background(Background),
                onClickFunctionButton = onClickFunctionButton
            )

            VerticalDivider(color = Border, thickness = 1.dp)

            FunctionSubPanel(
                modifier = Modifier.fillMaxHeight().width(160.dp).background(Background),
                functionType = usState.functionType
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