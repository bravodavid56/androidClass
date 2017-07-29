package com.example.bravodavid56.newsapp;

/**
 * Created by bravodavid56 on 7/28/2017.
 */
import android.content.Context;
import android.support.annotation.NonNull;


import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;


public class ScheduleUtils {
    private static final int SCHEDULE_INTERVAL_MINUTES = 1;
    private static final int SYNC_FLEXTIME_SECONDS = 60;
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sInitialized;

    /*
        This method uses firebase's job dispatcher to schedule a job for completion.
        After creating an instance of a GooglePlayDriver and a FirebaseJobDispatcher,
        we create what we want the job to be. The first method after creating a job
        builder sets the service that we define in NewsJob, which refreshes the
        database and alerts the user that the database has been refreshed.
        The other notable methods are the setRecurring() method which defines whether
        the service runs continuously, or just once. Furthermore, we define how often
        the service recurs using setTrigger(), and pass in the various arguments
        for determining time.
        Then we schedule the defined job using the FirebaseJobDispatcher, and the
        service is set.
        Note: The service must be defined in the manifest file, and firebase must be added to
        the gradle configuration file.

     */
    synchronized public static void scheduleRefresh(@NonNull final Context context){
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;

    }
}
