package com.example.receipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class FirstFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val edit=view.findViewById<EditText>(R.id.editfoodtext)
        val button=view.findViewById<Button>(R.id.foodgetbutton)



        fun JSONObject.toMap(): Map<String, String> {
            val map = mutableMapOf<String, String>()
            val keysItr: Iterator<String> = this.keys()
            while (keysItr.hasNext()) {
                val key = keysItr.next()
                var value: Any = this.get(key)
                when (value) {
                    is JSONObject -> value = value.toMap()
                }
                map[key] = value.toString()
            }
            return map
        }


        fun parseApiResponse(response: String): ApiResponse {
            val jsonObject = org.json.JSONObject(response)

            val s = jsonObject.getInt("s")
            val d = jsonObject.getJSONArray("d")

            val recipes = mutableListOf<Recipe>()
            for (i in 0 until d.length()) {
                val recipeObject = d.getJSONObject(i)
                val id = recipeObject.getString("id")
                val title = recipeObject.getString("Title")
                val instructions = recipeObject.getString("Instructions")
                val image = recipeObject.getString("Image")

                val ingredientsObject = recipeObject.getJSONObject("Ingredients")
                val ingList = mutableListOf<String>()
                val ingmap=ingredientsObject.toMap()
                ingmap.forEach { s -> ingList.add(s.value) }



                val recipe = Recipe(id, title, ingmap, instructions, image)
                recipes.add(recipe)
            }
            val pagesTotal = if (jsonObject.has("pagesTotal")) {
                jsonObject.getInt("pagesTotal")
            } else {

                0
            }

            return ApiResponse(s, recipes, 0, Any(), 0, 0, 0, 0, 0,0,pagesTotal)
        }

       button.setOnClickListener {

           val userInput = edit.text.toString()
           val formattedinput=userInput.replace(" ","%20")

           val editurl="https://food-recipes-with-images.p.rapidapi.com/?q=${formattedinput}"
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(editurl)
                .get()
                .addHeader("X-RapidAPI-Key", "4676d6db05msh5a86808a602fe1dp177e55jsn3c38c37d7b03")
                .addHeader("X-RapidAPI-Host", "food-recipes-with-images.p.rapidapi.com")
                .build()


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val receipedata = parseApiResponse(responseBody.toString().trimIndent())

                        Log.i("ing", receipedata.d[0].ingredients.toString())

                }
            }
            }
            )

           val secondfragment=SecondFragment()
           val transaction=fragmentManager?.beginTransaction()
           transaction?.replace(R.id.fragmentcont,secondfragment)?.commit()

        }


        return view


    }


}