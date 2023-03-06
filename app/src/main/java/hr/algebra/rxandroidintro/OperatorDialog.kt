package hr.algebra.rxandroidintro

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hr.algebra.rxandroidintro.databinding.DialogReactiveBinding
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.*

class OperatorDialog : DialogFragment( ) {

    private var _binding: DialogReactiveBinding? = null
    private val binding get() = _binding!!

    private var result = StringBuilder( "" )
    private lateinit var disposable: Disposable

    override fun onCreateDialog( savedInstanceState: Bundle? ): Dialog {
        _binding = DialogReactiveBinding.inflate(LayoutInflater.from(context))

        val observable = getProgrammingLanguagesObserverable( )
        val observer   = getProgrammingLanguagesObserver( )

        observable
            .subscribeOn( Schedulers.io( ) )
            .observeOn( AndroidSchedulers.mainThread( ) )
            .filter { it.lowercase( ).startsWith( "c" ) }
            .subscribe( observer )

        return AlertDialog
            .Builder( requireActivity( ) )
            .setView( binding.root )
            .create()
    }

    private fun getProgrammingLanguagesObserverable(): Observable< String > {
        val programmingLanguagesArray = arrayOf("Java", "Kotlin",
            "C", "C++", "C#", "Python", "Javascript",
            "Go", "TypeScript", "NodeJS", "React",
            "Spring", "Angular", "Objective C",
            "VUE", "Groovy", "Ruby", "Scala", "Ruby on Rails",
            "React Native", "Ionic", "Cordova", "T-SQL",
            "Sql", "Bash", "F#", "PSQL","Mongo", "Django")
        return Observable.fromArray( *programmingLanguagesArray )
    }

    fun getProgrammingLanguagesObserver( ) : Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe( d : Disposable ) {
                Log.i( "test.onSubscribe", d.toString( ) )
                disposable = d
            }

            override fun onNext( t : String ) {
                //if( t.lowercase( ).startsWith( "c" ) )
                result.append( "$t, " )
                Log.i( "test.onNext", t )
            }

            override fun onError( e: Throwable ) {
                Log.i( "test.onNext", e.toString( ) )
            }

            override fun onComplete( ) {
                Log.i( "test.onComplete", "DONE!!" )
                binding.tvResult.text = result.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}