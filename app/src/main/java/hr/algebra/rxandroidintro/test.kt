package hr.algebra.rxandroidintro

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun getProgrammingLanguagesObserverable( ) : Observable< String > {
    return Observable.just( "Kotlin", "Java", "Javascript", "C", "Scala", "Python" )
}

fun getProgrammingLanguagesObserver( ) : Observer< String > {
    return object : Observer< String > {
        override fun onSubscribe( d : Disposable ) {
            Log.i( "test.onSubscribe", d.toString( ) )
        }

        override fun onNext( t : String ) {
            Log.i( "test.onNext", t )
        }

        override fun onError( e: Throwable ) {
            Log.i( "test.onNext", e.toString( ) )
        }

        override fun onComplete( ) {
            Log.i( "test.onComplete", "DONE!!" )
        }
    }
}