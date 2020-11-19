package ru.faizovr.todo.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import ru.faizovr.todo.R
import ru.faizovr.todo.presentation.fragments.TaskListFragment


class ToDoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(TaskListFragment())
        }
    }

    fun goToFragment(fragment: Fragment) {
        replaceFragment(fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFragmentManager.popBackStack(FRAGMENT_TAG, POP_BACK_STACK_INCLUSIVE)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, fragment)
                .commit()
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .addToBackStack(FRAGMENT_TAG)
                .commit()
    }

    companion object {
        private const val FRAGMENT_TAG = "TaskListFragment"
    }

}


