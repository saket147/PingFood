package com.cebs.pingfood;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * Created by Dhiraj.Pathak on 8/29/2016.
 */
public class ActivityStack
{
    private static Stack<AppCompatActivity> activities=new Stack<>();
    public static void push(AppCompatActivity activity)
    {
        activities.push(activity);
    }
    public static AppCompatActivity pop()
    {
        return activities.pop();
    }
}
