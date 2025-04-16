package di

import dagger.Component
import viewModel.MainActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NoteModule::class])
interface NoteComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
}