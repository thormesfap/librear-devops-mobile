package br.com.librear

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class AulaActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aula)

        val webView: WebView = findViewById(R.id.youtube_webview)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()


        val botaoConcluido = findViewById<Button>(R.id.botaoConcluido)


        val aulaId = intent.getIntExtra("aulaId", -1)
        botaoConcluido.setOnClickListener {
            RetrofitInstance.apiInterface.marcarAulaVistA(aulaId).enqueue(object : Callback<MsgResponse>{
                override fun onResponse(
                    call: Call<MsgResponse?>,
                    response: Response<MsgResponse?>
                ) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@AulaActivity, "Não foi possível marcar aula como vista", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Toast.makeText(this@AulaActivity, "Aula marcada como vista", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<MsgResponse?>, t: Throwable) {
                    Toast.makeText(this@AulaActivity, "Não foi possível marcar aula como vista", Toast.LENGTH_SHORT).show()
                }
            })
        }

        RetrofitInstance.apiInterface.showAula(aulaId).enqueue(object : Callback<AulaResponse> {
            override fun onResponse(call: Call<AulaResponse?>, response: Response<AulaResponse?>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@AulaActivity, "Erro ao carregar aula", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                val videoUrl = response.body()?.videoUrl
                val videoId = extractVideoId(videoUrl)
                val htmlContent = """
                    <!DOCTYPE html>
                    <html>
                    <body>
                        <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->
                        <div id="player"></div>

                        <script>
                        // 2. This code loads the IFrame Player API code asynchronously.
                        var tag = document.createElement('script');
                        tag.src = "https://www.youtube.com/iframe_api";
                        var firstScriptTag = document.getElementsByTagName('script')[0];
                        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                        // 3. This function creates an <iframe> (and YouTube player)
                        //    after the API code downloads.
                        var player;
                        function onYouTubeIframeAPIReady() {
                            player = new YT.Player('player', {
                            height: '100%', // Or fixed dimensions
                            width: '100%',  // Or fixed dimensions
                            videoId: '$videoId',
                            playerVars: {
                                'playsinline': 1 // Important for mobile playback
                            },
                            events: {
                                'onReady': onPlayerReady,
                                // 'onStateChange': onPlayerStateChange // Add other event handlers as needed
                            }
                            });
                        }

                        // 4. The API will call this function when the video player is ready.
                        function onPlayerReady(event) {
                            // You can autoplay or perform other actions here
                               event.target.playVideo();
                        }

                        // Example: To control playback from Android (Kotlin/Java)
                            function playVideo() {
                               if (player && typeof player.playVideo === 'function') {
                                 player.playVideo();
                               }
                             }
                             function pauseVideo() {
                               if (player && typeof player.pauseVideo === 'function') {
                                 player.pauseVideo();
                               }
                             }
                        </script>
                    </body>
                    </html>
                """.trimIndent()
                webView.loadDataWithBaseURL(
                    "https://www.youtube.com", htmlContent, "text/html", "utf-8", null
                )
            }

            override fun onFailure(call: Call<AulaResponse?>, t: Throwable) {
                Toast.makeText(this@AulaActivity, "Erro ao carregar aula", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    fun extractVideoId(youtubeUrl: String?): String? {
        if (youtubeUrl.isNullOrEmpty()) {
            return null
        }
        val videoIdPatterns = arrayOf(
            // Standard watch URLs: https://www.youtube.com/watch?v=VIDEO_ID
            Pattern.compile("""youtube\.com/watch\?v=([a-zA-Z0-9_-]{11})"""),
            // Shortened URLs: https://youtu.be/VIDEO_ID
            Pattern.compile("""youtu\.be/([a-zA-Z0-9_-]{11})"""),
            // Embed URLs: https://www.youtube.com/embed/VIDEO_ID
            Pattern.compile("""youtube\.com/embed/([a-zA-Z0-9_-]{11})"""),
            // Live URLs: https://www.youtube.com/live/VIDEO_ID
            Pattern.compile("""youtube\.com/live/([a-zA-Z0-9_-]{11})"""),
            // Shorts URLs: https://www.youtube.com/shorts/VIDEO_ID
            Pattern.compile("""youtube\.com/shorts/([a-zA-Z0-9_-]{11})"""),
            // URLs with additional parameters: https://www.youtube.com/watch?v=VIDEO_ID&feature=youtu.be
            Pattern.compile("""youtube\.com/watch\?.*v=([a-zA-Z0-9_-]{11})""")
            // Add more patterns if you encounter other formats
        )

        for (pattern in videoIdPatterns) {
            val matcher = pattern.matcher(youtubeUrl)
            if (matcher.find()) {
                val potentialId = matcher.group(1)
                if (potentialId != null && potentialId.length == 11) {
                    return potentialId
                }
            }
        }
        return null

    }
}
