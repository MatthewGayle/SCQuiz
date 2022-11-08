package edu.uga.cs.scquiz;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class RecycleAdapter
        extends RecyclerView.Adapter<RecycleAdapter.PastQuizHolder> {

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private final Context context;

    private List<Quiz> values;
    private List<Quiz> originalValues;

    public RecycleAdapter( Context context, List<Quiz> quizList ) {
        this.context = context;
        this.values = quizList;
        this.originalValues = new ArrayList<Quiz>( quizList );
    }

    // reset the originalValues to the current contents of values
    public void sync()
    {
        originalValues = new ArrayList<Quiz>( values );
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class PastQuizHolder extends RecyclerView.ViewHolder {

        TextView quizName;
        TextView dateText;
        TextView scoreText;
        Button seeQuizButton;

        public PastQuizHolder( View itemView ) {
            super( itemView );

            quizName = itemView.findViewById( R.id.quizText );
            dateText = itemView.findViewById( R.id.dateText );
            scoreText = itemView.findViewById( R.id.scoreText );
            seeQuizButton = itemView.findViewById( R.id.button );
        }
    }

    @NonNull
    @Override
    public PastQuizHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.past_quiz_view, parent, false );
        return new PastQuizHolder( view );
    }

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( PastQuizHolder holder, int position ) {

        Quiz quiz= values.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + quiz );

        holder.quizName.setText("Quiz " + position);
        holder.dateText.setText( quiz.getDate() );
        holder.scoreText.setText( String.valueOf(quiz.getQuizResult()) );
        holder.seeQuizButton.setText( "See quiz" );
    }

    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    }


}
