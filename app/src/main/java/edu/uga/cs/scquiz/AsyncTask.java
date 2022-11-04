package edu.uga.cs.scquiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;


/** This is a replacement class for the Android's deprecated AsyncTask class.
 * However, handling of the task's progress has not been implemented.
 * It uses Java's standard concurrency framework.
 * @param <Param> type of the input parameter for doInBackground
 * @param <Result> type of the result value returned by doInBackground
 */
public abstract class AsyncTask<Param,Result> {

    // An internal method to execute something in background
    private void executeInBackground( Param... params ) {

        // Get en executor service -- it will serve to run the task
        // in the background, i.e., in this executor.  This executor service
        // has its own thread, which is different than the main UI thread.
        // As a result, anything running on this thread will not block the UI.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Execute a Runnable with the task on the executor service.
        // Runnable is a Java interface to be implemented by a class to execute in
        // a thread.  The run() method is called automatically when a Thread is given
        // a Runnable object.
        //
        // The lambda expression below effectively creates an anonymous
        // Runnable class that will execute the method's body (doInBackground)
        // in the executor service, which uses a different thread than the main UI thread.
        // Once the result is obtained, the Runnable class will add another Runnable
        // with the call to onPostExecute with the Result argument to be added to the
        // main UI thread.  The main UI thread will update the UI accordingly.
        // Since the Runnable below will execute in a different thread, the main UI thread
        // will not be blocked.
        executor.execute( () -> {

            // Run the method body (doInBackground)
            Result result = doInBackground( params );

            // Now, pass the result to the main UI thread
            //
            // Get the looper of the UI thread (the main UI event dispatcher's loop)
            // A Looper is simply a message queue within the Android OS.
            // If you are curious, you can examine the source code of the Looper class here:
            // https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/os/Looper.java
            Looper looper = Looper.getMainLooper();

            // Create a Handler using the main UI's looper.
            // A Handler is used to interact with a Looper, for example,
            // for posting messages on the main Looper's queue.
            // If you are curious, you can examine the source code of the Handler class here:
            // https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/os/Handler.java
            Handler handler = new Handler( looper );

            // Post the processing of the result of the doInBackground method
            // on the main UI thread's looper.
            handler.post( new Runnable() {
                @Override
                public void run() {
                    // handle the method result in the main UI thread
                    onPostExecute( result );
                }
            });
        });
    }

    // This method is just like in the AsyncTask class
    public void execute( Param... arguments ){
        executeInBackground( arguments );
    }

    // These abstract methods are just like in the AsyncTask class
    protected abstract Result doInBackground( Param... arguments );
    protected abstract void onPostExecute( Result result );
}