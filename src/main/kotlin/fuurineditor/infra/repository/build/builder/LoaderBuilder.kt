package fuurineditor.repository.build.builder

import fuurineditor.repository.build.data.LoaderJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class LoaderBuilder {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(rowBasePath: Path) {

        val loaderJsonPath = rowBasePath.resolve("loader.json")

        loaderJsonPath.toFile().writeText(
            json.encodeToString(
                LoaderJson()
            )
        )

    }

}