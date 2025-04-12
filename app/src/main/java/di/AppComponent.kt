package di

import dagger.Component
import viewModel.MainActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NoteModule::class])
interface AppComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
}