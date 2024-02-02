package com.github.hamlet_rt.workoutapp.biz.general

import com.github.hamlet_rt.workoutapp.common.WrkContext
import com.github.hamlet_rt.workoutapp.common.helpers.errorAdministration
import com.github.hamlet_rt.workoutapp.common.helpers.fail
import com.github.hamlet_rt.workoutapp.common.models.WrkWorkMode
import com.github.hamlet_rt.workoutapp.common.repo.ITngRepository
import com.github.hamlet_rt.workoutapp.cor.ICorChainDsl
import com.github.hamlet_rt.workoutapp.cor.worker

fun ICorChainDsl<WrkContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        tngRepo = when {
            workMode == WrkWorkMode.TEST -> settings.repoTest
            workMode == WrkWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != WrkWorkMode.STUB && tngRepo == ITngRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
