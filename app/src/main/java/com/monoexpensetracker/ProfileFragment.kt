package com.monoexpensetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monoexpensetracker.databinding.FragmentProfileBinding
import com.monoexpensetracker.dataclass.JokeResponse
import com.monoexpensetracker.objectpkg.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchRandomJoke()
    }

    private fun fetchRandomJoke()
    {
        RetrofitInstance.api.getRandomJoke().enqueue(object : Callback<JokeResponse>{
            override fun onResponse(call: Call<JokeResponse>, response: Response<JokeResponse>) {
                if (response.isSuccessful)
                {
                    val joke = response.body()?.joke
                    binding.jokeTextView.text = joke ?:"No Joke Found! "
                }else
                {
                    binding.jokeTextView.text = "Failed to load joke"
                }
            }

            override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                binding.jokeTextView.text = "Damn! Something went's Wrong"
            }
        })
    }

}