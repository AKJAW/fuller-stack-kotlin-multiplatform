package dependency_injection

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import sample.Counter
import sample.CounterImpl

val common = Kodein.Module("Common") {
    bind<Counter>() with singleton { CounterImpl() }
}