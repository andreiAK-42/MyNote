import android.app.Application
import di.AppComponent
import di.DaggerAppComponent
//import di.DaggerAppComponent
import di.NoteModule

class MyApp: Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .noteModule(NoteModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}