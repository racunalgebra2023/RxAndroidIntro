package hr.algebra.rxandroidintro

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hr.algebra.rxandroidintro.databinding.DialogReactiveBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class DialogExample : DialogFragment( ) {

    private var _binding : DialogReactiveBinding? = null
    private val binding get( ) = _binding!!

    private var result = StringBuilder( "" )
    private lateinit var disposable : Disposable

    override fun onCreateDialog( savedInstanceState: Bundle? ) : Dialog {
        _binding = DialogReactiveBinding.inflate( LayoutInflater.from( context ) )

        disposable = getAnyObserverable( )
            .subscribeOn( Schedulers.io( ) )
            .observeOn( AndroidSchedulers.mainThread( ) )
            .subscribeBy(
                onNext = {
                    result.append( "$it, \n\n" )
                }, onComplete = {
                    binding.tvResult.text = result.toString( )
                }
            )

        return  AlertDialog
                    .Builder( requireActivity( ) )
                    .setView( binding.root )
                    .create( )
    }

    private fun getAnyObserverable( ) : Observable< Any > {
        return listOf( true, 1, 2, "Three", 4.0f, 4.5, "Five", false ).toObservable( )
    }

    override fun onDestroyView( ) {
        super.onDestroyView( )
        _binding = null
    }

    override fun onDestroy( ) {
        super.onDestroy( )
        disposable.dispose( )
    }
}