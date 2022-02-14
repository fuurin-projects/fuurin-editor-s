package fuurineditor.repository

import fuurineditor.FuurinEditorSpring
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(value = [FuurinEditorSpring::class])
internal class ProjectRepositoryTest {

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    fun getProjectData() {


    }

    @Test
    fun getProjectList() = runTest {

        //val projectList = projectRepository.getProjectInfoList()

        //projectList.first()


    }
}