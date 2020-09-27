package com.akjaw.fullerstack.composition.modules

import com.akjaw.fullerstack.notes.database.AppDatabase
import database.NoteDao
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val databaseModule = DI.Module("databaseModule") {
    bind<AppDatabase>() with singleton { AppDatabase.create(instance("ApplicationContext")) }
    bind<NoteDao>() with singleton { instance<AppDatabase>().noteDao() }
}
