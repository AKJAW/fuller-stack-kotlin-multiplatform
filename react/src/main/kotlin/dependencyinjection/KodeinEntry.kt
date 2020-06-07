package dependencyinjection

import org.kodein.di.DI
import org.kodein.di.DIAware

object KodeinEntry : DIAware {
    override val di by DI.lazy {
        import(common)
    }
}
