![wiz](https://github.com/user-attachments/assets/ec6a29ea-fcfd-4775-a31d-bc0d6e0fd34a)

<div style="text-align: center;">
    <h1>QuizWiz - A Quiz App with API Integration(POE)</h1>
</div>

<ul><li><strong>Description:</strong></li></ul>
<p>QuizWiz is a dynamic mobile quiz application that allows users to play quizzes across various categories. It utilizes an API to store user data, fetch questions, and track user performance in real-time. The app supports both single and group play modes, with the group play mode being implemented in the final POE. This application provides AI-generated explanations.</p>

<ul><li><strong style="font-size: 20px;">Key Features:</strong></li></ul>
<p>Feature 1 - Our first key feature of the app is the creation of various categories along with several questions attached to each. We have selected 6 different categories to suit a range of users, each category has up to 10 questions which will be displayed to the user during the quiz. The question types range from Multiple Choice, with three options for each question, to True or False.</p>

<p>Feature 2 - The second feature is our Game Mode. The game mode entails the ability for the user to play our game. Once they select a category the user will be able to play the game by answering the multiple choice questions or the true or false questions. They will be answered with reactive emojis and correct or incorrect prompts.</p>

<p>Feature 3 - Our third feature is a Results page whereby the user is displayed their total along with a list of all the questions they were asked. They then can select a question and will be taken to a different page where the AI Gemini will give them an in-depth explanation of the questions answer.</p>

<p>Feature 4 - Our fourth feature, Multiplayer Mode, allows users to compete against each other by answering questions in real-time with instant emoji feedback for each answer. Players can choose a category, track their progress on a live leaderboard, and enjoy a competitive, interactive experience as they race to get the highest score.</p>

<p>Feature 5 - Our fifth feature is the Leaderboard feature showcases players' rankings based on their performance in the game. It displays scores from multiplayer mode, allowing users to see how they compare against each other. This feature adds motivation for players to improve their scores and encourages friendly competition. It’s a great way for users to track their progress, challenge themselves, and celebrate their achievements!</p>

<ul><li><strong style="font-size: 20px;">Functional Requirements:</strong></li></ul>




<p>The registration and login functionality in QuizWiz is powered by Firebase Authentication. Firebase handles user authentication, which allows users to securely create accounts, log in, and access their personalized data.</p>

<p>When users register, they are prompted to provide their name, email, and password, then click the signup button. Once the user is registered, they can log in to the application by providing their email and the registered password.</p>

<p>For added security, Biometric Authentication is also available in QuizWiz. Once users have registered, they can enable biometric login such as fingerprint or facial recognition through their device settings. This feature provides a quick, secure way to access their account without needing to re-enter their password, making the login process both convenient and safe.</p>


![Splash Screen 2024-11-01 at 11 03 17 AM](https://github.com/user-attachments/assets/206d6dce-50db-4009-8157-863e80b165ec)
![registerscreen (1)](https://github.com/user-attachments/assets/4c9f4b35-38f6-4986-911d-6ffa3e6aa0e2)
![Loginscreen (1)](https://github.com/user-attachments/assets/95ad86a8-091e-475f-a5f5-339660faeb15)

<li>This is the game mode selection screen, this allows the user to choose Singleplayer or Multiplayer </li>

![dashboard screen](https://github.com/user-attachments/assets/3854f31f-298d-4958-bf19-27ac08e19869)


  <li>allow users to select from 6 different categories from the dashboard: Animals, Disney, Music, History, Food, TV Shows.</li>

 
![Categoriesscreen](https://github.com/user-attachments/assets/d06e8256-3f16-43c7-b31e-1e0c301d8adf)


  <li>Our quiz templates include: multiple choice and true/false questions.</li>
  
![true or false](https://github.com/user-attachments/assets/75e350c1-5a39-4481-a399-3a8560c3f375)
  ![quiz correct screen (1)](https://github.com/user-attachments/assets/b043e677-9e36-4d42-b178-8cc65437133c)

  <li>Real-time quiz data fetching and submission using an API.</li>

  <li>When choosing MultiPlayer mode the users will be required to enter their names to continue playing the game.</li>

  ![add players screen (1)](https://github.com/user-attachments/assets/aade02a8-da05-417f-a491-414b45ec370f)
  ![both players ready screen (1)](https://github.com/user-attachments/assets/4d29593e-ab8c-43de-bfad-916276e3c568)

 <li>This is showing Sam and Josh playing in MultiPlayer Mode.</li>

 ![first player plays screen (1)](https://github.com/user-attachments/assets/383a221d-d19e-47ca-a3e7-19543378b69e)
 ![second player plays screen (1)](https://github.com/user-attachments/assets/53d1a14b-1daa-47c2-b7a7-b306b7f11711)

  <li>We also implemented a LeaderBoard for users playing in MultiPlayer Mode.</li>
  
 ![leaderboard draw screen (1)](https://github.com/user-attachments/assets/0f645fab-da49-4d16-b1f1-875d69b19fd1)
 ![player wins screen (1)](https://github.com/user-attachments/assets/75d8cf1e-d0f4-4743-9753-84a7e005f8ea)


  <li>We also implemented an app settings page.</li>
  <li>These settings include profile settings, game settings, help and support, as well as the "About" section.</li>
  <li>When the user clicks the help and support button, they will be redirected to a page where they can ask questions related to the app and provide feedback on their experience.</li>
  <li>When the user clicks the game settings button, they will be redirected to the game settings page where they can customize their game settings, including turning notifications on or off, toggling music and sound, and switching between light and dark modes.</li>
  <li>when the user clicks on the about button  they wil be redirected to the about gage and read what this application is and its privacy policy. </li>
  
![profile screen (1)](https://github.com/user-attachments/assets/448f1151-2963-4cb0-b207-1db6d83de846)
![about screen (1)](https://github.com/user-attachments/assets/a39fbcc4-d2f3-4451-a145-58b4155f7830)
![help and support screen  (1)](https://github.com/user-attachments/assets/f6673f0c-088b-4003-bbbd-8b00377eef83)
![game setting screen (1)](https://github.com/user-attachments/assets/ef5af34b-8425-4c73-af8d-9b0dfe3fcf4b)
![main settings screen (1)](https://github.com/user-attachments/assets/c3862a09-c859-42f1-93a9-44ddd753f6ad)



  
  <li>The game also has a results page where users can view their scores on the quizzes they have taken. As well as view all the questions of the quiz they just completed.</li>
  <li>We have integrated AI/Gemini, allowing users to select the  questions and receive AI-generated answers displayed in the app.</li>
  
  ![results1](https://github.com/user-attachments/assets/c53a47f0-10fe-4857-9f6c-0808211d9996)
![toolbarMenu1](https://github.com/user-attachments/assets/f661d8a2-3dc0-45ea-a08e-310d388a1bb0)

</ul>
<p> </p>
<ul><li><strong style="font-size: 20px;">Non-Functional Requirements:</strong></li></ul>
<p>Security: We have implemented Single Sign-On (SSO) and used Firebase for authentication to secure the login process</p>
<p>security: we have Used JWT JSON web token for session management, with encrypted data storage and transmission.</p>
<p>Usability: our application Provides an intuitive and user-friendly interface.</p>
<p>Usability: our application Provides instructions when the quiz loads on how to play the game.</p>
<p>Performance: we have ensured that the app is able Load quiz questions within 3 seconds.</p>
<p>Offline Mode: The app supports offline functionality, allowing users to access previously loaded content and continue certain activities even without an internet connection. This ensures a smooth experience anytime, anywhere.</p>
<p>Real-Time Notifications: Users receive instant updates on game events, challenges, and friend activity, keeping them engaged and informed without delay.</p>
<p>Multi-Language Support: The app includes support for multiple languages, featuring at least two South African languages—Afrikaans and Zulu—to ensure accessibility and inclusivity for a diverse user base.</p>

<ul><li><strong>Usage:</strong></li></ul>
<p>Upon opening the application you will briefly see a splash screen without apps logo, thereafter you will be taken to our Login page. If you do not already have an account simply click the signup button and you will be redirected to the signup page. Once in the signup page you will be required to enter information such as your name, email address and password. Alternatively, you can select the “Google” button and sign up using your google account. 

If you do already have an account you will remain on the Login and enter your email and password to login or alternatively, you can use the google button to login. 

Once logged in you will be redirected to our colourful Dashboard. This dashboard contains all the possible categories that you can select. Once you select a category you will be taken to the game. 

The game consists of either Multiple Choice or True or False questions. Regardless of your choice you will select the answer and then select the “next” button to proceed to the next question. You will also be told whether your selection is right or wrong.

Once the game is finished, you will be redirected to our Results page. This page will display your score as well as display all the questions you were asked throughout the quiz. If you select a question, you will be taken to a page that uses the AI Gemini to give you an in depth explanation of the question. 

Throughout the app you will see a menu located at the top of the screen, this menu has several options: 
•⁠  ⁠About: this will take you to a page that explains a little bit about our app.
•⁠  ⁠Settings: This will take you to the settings page which contains all the options mentioned in this list. 
•⁠  ⁠Help Support: This allows users to request help if they are having any issues related to the app or their account.
•⁠  Profile: This section displays your profile data such as name and email (icons to be developed in later release)
•⁠  Dashboard: This button will simply redirect you back to the dashboard.
•⁠  Logout: The ability to logout of the app.
</p>

<p>AI Report</p>

The Open Source Coding POE presented us with many challenges. Despite our knowledge and available resources, we often found it difficult to achieve our desired end goals. Thankfully, we had access to AI, which proved to be a constructive and helpful tool throughout the process. 

During the first, second, and final parts of the POE, we utilized AI in the following wise, constructive, and non-abusive ways:

- Aiding in our comprehension of complex ideas.
- Helping us figure out how to pull from Git.
- Assisting us in determining effective color combinations.
- Providing advice on modifying our designs.
- Aiding in the creation of our ideal logo.
- Helping us understand log outputs in Logcat.
- Explaining the complexities of hosting and Azure.
- Aiding us in error-finding and understanding issues in our code.
- Explaining how to set an image icon.
- Clarifying the errors encountered on the Google Play Store when attempting to publish.
- Comparing RoomDB and SQLite to assist with our offline mode.
- Explaining how to create a `values-af` directory to enable multi-language support for users switching their device's language.
- Helping us figure out how to conduct automated testing in Git. 

Through these various applications, AI has significantly enhanced our development process and contributed to our overall success.


<ul><li><strong>Technology Used:</strong></li></ul>
<p>QuizWiz follows a client-server architecture where the Android app (client) communicates with the backend API (server) to fetch quiz data, manage user accounts, and store results. Here’s an overview of the architecture:</p>

<ul><strong>Frontend: Android Studio Application</strong></ul>
<ul>
  <li>Built with Android Studio using Java/Kotlin.</li>
  <li>The app provides a user interface to interact with quizzes, show questions, submit answers, and display performance.</li>
  <li>The app sends and receives data from the backend API using HTTP requests, handled through Retrofit.</li>
  <li>User interactions like registration, login, quiz selection, and quiz submission are facilitated by API requests.</li>
</ul>

<ul><strong>Backend: ASP.NET Core in Visual Studio</strong></ul>
<ul>
  <li>Provides RESTful API endpoints for user registration, login, and fetching quizzes.</li>
  <li>Handles user authentication, quiz logic, and secure communication between the app and the database.</li>
  <li>Uses JWT (JSON Web Token) for secure session management, allowing only authenticated users to interact with the quiz system.</li>
</ul>

<ul><strong>Database: Cloud Firestore</strong></ul>
<ul>
  <li>Cloud Firestore is the NoSQL database used to store and retrieve user data and quiz content.</li>
  <li>Cloud Firestore is integrated into the backend API, where the server communicates with the database to store and fetch data.</li>
</ul>

<ul><strong>Data Structure (Cloud Firestore):</strong></ul>
<ul>
  <li>Users Collection: Stores user profile information such as username, email, password.</li>
  <li>Quiz Collection: Stores quiz questions, options, answers, and categories.</li>
</ul>

<ul><strong>Hosting:</strong></ul>
<ul>
  <li>We hosted the application on azure .</li>
  <li>We created an API App on the azure platform, the API project (Visual Studio) is hosted in the API app on azure  </li>
     <li>we used the API app link in the retrofit client to connect the client to the API  </li>
</ul>

![Hosting1](https://github.com/user-attachments/assets/c5aca820-63a5-4656-9f4f-66bd31997c94)


<ul><strong>Demonstration Video:</strong></ul>
<ul>
 YouTube Link: https://youtu.be/4zoGkb-jtm4
</ul>

