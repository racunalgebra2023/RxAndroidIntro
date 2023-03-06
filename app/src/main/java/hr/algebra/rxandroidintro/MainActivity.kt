package hr.algebra.rxandroidintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.rxandroidintro.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val observable = getProgrammingLanguagesObserverable( )
    private val observer   = getProgrammingLanguagesObserver( )

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        observable
            .subscribeOn( Schedulers.io( ) )
            .observeOn( AndroidSchedulers.mainThread( ) )
            .subscribe( observer )

        binding.bShowDialog.setOnClickListener {
            DialogExample( ).show( supportFragmentManager, "DF" )
        }

        binding.bShowOperatorDialog.setOnClickListener {
            OperatorDialog( ).show( supportFragmentManager, "OD" )
        }
    }

    override fun onDestroy( ) {
        super.onDestroy( )
        ( observer as Disposable ).dispose( )
    }
}