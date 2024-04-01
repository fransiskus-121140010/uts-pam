package com.example.utspam

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private val usersAdapter = UserAdapter(emptyList()) // Adapter untuk menampilkan user

    // Retrofit service interface for API calls
    private interface UserService {
        @GET("/api/users")
        suspend fun getUsers(): List<User>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        searchView = findViewById(R.id.search_view)
        recyclerView = findViewById(R.id.rv_users)

        recyclerView.adapter = usersAdapter

        // Implement Retrofit to call API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/") // Base URL for API
            .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON data
            .build()

        val userService = retrofit.create(UserService::class.java)

        // Coroutine to call API in background
        CoroutineScope(Dispatchers.IO).launch {
            val users = try {
                userService.getUsers()
            } catch (e: Exception) {
                emptyList() // Handle API call error
            }
            runOnUiThread {
                usersAdapter.updateUsers(users) // Update adapter with user data on UI thread
            }
        }

        // Implement search functionality (Optional)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filter users based on search query (implement logic)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter users based on search query as user types (implement logic)
                return false
            }
        })
    }
}

// Data class to represent user data
data class User(val id: Int, val email: String, val first_name: String, val last_name: String)

// Recycler view adapter to display user list
class UserAdapter(private val users: List<User>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun updateUsers(newUsers: List<User>) {
        this.users = newUsers
        notifyDataSetChanged()
    }
}

// User view holder class for RecyclerView
class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
    private val tvEmail = itemView.findViewById<TextView>(R.id.tv_email)

    fun bind(user: User) {
        tvName.text = user.first_name + " " + user.last_name
        tvEmail.text = user.email
    }
}
