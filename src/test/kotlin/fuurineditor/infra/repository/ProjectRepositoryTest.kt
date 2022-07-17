package fuurineditor.infra.repository

import fuurineditor.FuurinEditorSpring
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(value = [FuurinEditorSpring::class])
internal class ProjectRepositoryTest {

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    fun getProjectData() {

        println("aa")
        val text = Json.encodeToString(
            WorldSceneJson1.serializer(),
            WorldSceneJson1(name = "aa")
        )
        println(text)

    }

    @Test
    fun getProjectList() = runTest {

        //val projectList = projectRepository.getProjectInfoList()

        //projectList.first()


    }
}

@Serializable
data class WorldSceneJson1(
    @SerialName("name")
    val name: String = "test",

    //@SerialName("width")
    //val width: JsonPrimitive = 1.toJsonPrimitive(),
)
