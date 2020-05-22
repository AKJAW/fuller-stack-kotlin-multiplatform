package dependencyinjection

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

object KodeinEntry : KodeinAware {
    override val kodein by Kodein.lazy {
        import(common)
    }
}
