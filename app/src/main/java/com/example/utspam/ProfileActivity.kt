class ProfileActivity : AppCompatActivity() {

    private lateinit var ivProfilePicture: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvGithubUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button

    // Replace with your logic to get user data
    private val user = User(1, "John Doe", "johndoe", "johndoe@example.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ivProfilePicture = findViewById(R.id.iv_profile_picture)
        tvUsername = findViewById(R.id.tv_username)
        tvGithubUsername = findViewById(R.id.tv_github_username)
        tvEmail = findViewById(R.id.tv_email)
        btnLogout = findViewById(R.id.btn_logout)

        // Set user data
        tvUsername.text = user.username
        tvGithubUsername.text = user.githubUsername
        tvEmail.text = user.email

        // Implement logout button click (Optional)
        btnLogout.setOnClickListener {
            // Implement logout logic (clear user data, navigate to LoginActivity)
        }
    }
}

// Data class to represent user data (optional)
data class User(val id: Int, val username: String, val githubUsername: String, val email: String)
