package com.cebs.pingfood;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * Created by Dhiraj.Pathak on 8/29/2016.
 */
public class FragmentStack
{
    private static Stack<Fragment> activities=new Stack<>();
    public static void push(Fragment fragment)
    {
        activities.push(fragment);
    }
    public static Fragment pop()
    {
        return activities.pop();
    }
}
