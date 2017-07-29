package com.example.bravodavid56.newsapp;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


/**
 * Created by bravodavid56 on 7/28/2017.
 */

/* This class defines what ONE job actually does
    It defines the background task (the job) as an Async Task that notifies the user
    via Toast that the database has been refreshed with new information.
    Then it refreshes the database with the new information, and finally it asserts that the job
    has been finished.
    This is just ONE job that can be defined, which is called by the ScheduledUtils class. If other jobs are defined,
    the ScheduledUtils class can also call those, but for this example we only have one.

*/
public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        // define an asynchronous task along with required methods
        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "News refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }
            // make the toast to alert the user that the database has been refreshed


            @Override
            protected Object doInBackground(Object[] params) {
                RefreshTasks.refreshArticles(NewsJob.this);
                return null;
            }
            // refresh the database with new information



            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                // assert job has been finished


                super.onPostExecute(o);

            }
        };
        // execute the asynchronous task
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        // stops the job from stopping unless the task is finished
        if (mBackgroundTask != null)
            mBackgroundTask.cancel(false);
        return true;
    }
}