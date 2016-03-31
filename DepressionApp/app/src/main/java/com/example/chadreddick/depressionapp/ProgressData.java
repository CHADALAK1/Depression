package com.example.chadreddick.depressionapp;

import java.util.ArrayList;

/**
 * Created by chadreddick on 3/31/16.
 * Object that holds the overtime progress from User
 */
public class ProgressData
{
    private ArrayList<Integer> Feeling;
    private ArrayList<String> Journals;

    //CONSTRUCTOR
    public ProgressData(ArrayList<Integer> Feels, ArrayList<String> Jour)
    {
        Feeling = Feels;
        Journals = Jour;
    }


    public ArrayList<Integer> GetFeeling()
    {
        return Feeling;
    }

    public ArrayList<String> GetJournals()
    {
        return Journals;
    }

    public void SetFeeling(int newFeel)
    {
        Feeling.add(newFeel);
    }

    public void SetJournals(String newJournal)
    {
        Journals.add(newJournal);
    }


}
