package com.example.johndeere.fragments

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.Words
import com.example.johndeere.databinding.FragmentQuizPageBinding

/*
Fragmento para crear los quizzes.
 */

class QuizPage : Fragment() {
    private var _binding: FragmentQuizPageBinding? = null
    private val binding get() = _binding!!
    private var mediaController: MediaController? = null
    private var rightAnswer: String? = null
    private var difficulty: String? = null
    private var level = 0
    private var rightAnswerCount = 0
    private var questionCount = 1
    private var maxQuestionCount = 0
    lateinit var answer1: Button
    lateinit var answer2: Button
    lateinit var answer3: Button
    lateinit var answer4: Button

    private lateinit var quizData: ArrayList<Words>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Se buscan y deshabilitan todos los botones para poder configurarlos por dificultad
        answer1 = view.findViewById<Button>(R.id.answer1)
        answer2 = view.findViewById<Button>(R.id.answer2)
        answer3 = view.findViewById<Button>(R.id.answer3)
        answer4 = view.findViewById<Button>(R.id.answer4)

        answer1.setEnabled(false)
        answer2.setEnabled(false)
        answer3.setEnabled(false)
        answer4.setEnabled(false)

        answer1.setVisibility(View.GONE)
        answer2.setVisibility(View.GONE)
        answer3.setVisibility(View.GONE)
        answer4.setVisibility(View.GONE)

        // Display del video para el quiz
        mediaController = MediaController(context)
        mediaController!!.setAnchorView(binding.quizVideoView)
        quizData = ArrayList()

        // Asignacion de los valores del bundle enviado del fragmento anterior
        arguments?.let {
            maxQuestionCount = it.getInt("questions")
            difficulty = it.getString("difficulty") as String
            quizData = it.getParcelableArrayList("words")!!
            level = it.getInt("level")
        }

        // Llamado a la funcion para revisar la respuesta al hacer click en los botones
        answer1.setOnClickListener{
            checkAnswer(answer1.text.toString())
        }

        answer2.setOnClickListener{
            checkAnswer(answer2.text.toString())
        }

        answer3.setOnClickListener{
            checkAnswer(answer3.text.toString())
        }

        answer4.setOnClickListener{
            checkAnswer(answer4.text.toString())
        }

        // Llamado a la funcion para crear la siguiente pregunta
        nextQuestion()
    }

    // Funcion para crear la siguiente pregunta
    private fun nextQuestion() {
        // Asignacion del numero de pregunta al text view del fragmento
        binding.quizPageTextView.text = getString(R.string.countLabel, questionCount)

        // Mezcla del array de palabras para que siempre cambie
        quizData.shuffle()

        // Asignacion del url del video y la respuesta correcta de la pregunta
        val videoId = quizData[0].localVideoURL
        rightAnswer = quizData[0].name

        // Muestra del video en el videoview utilizando la variable con la url
        if(videoId != null) {
            val offlineUrl = Uri.parse("android.resource://${requireContext().packageName}/${videoId}")
            binding.quizVideoView.setMediaController(mediaController)
            binding.quizVideoView.setVideoURI(offlineUrl)
            binding.quizVideoView.requestFocus()
            binding.quizVideoView.start()
        }
        else {
            binding.quizVideoView.isVisible = false
            Toast.makeText(context, "Video no disponible", Toast.LENGTH_SHORT).show()
        }

        // Eliminacion de la palabra actual de la lista para evitar preguntas repetidas
        quizData.removeAt(0)

        // Asignacion de un indice aleatorio a la respuesta correcta y seleccion de indices aleatorios para el resto de las respuestas
        // La configuracion se hace por nivel de dificultad
        var randomRight: Int
        if (difficulty == "Dificultad básica"){
            randomRight = (0 until 2).random()
            answer1.setEnabled(true)
            answer2.setEnabled(true)
            answer1.setVisibility(View.VISIBLE)
            answer2.setVisibility(View.VISIBLE)
        }

        else if (difficulty == "Dificultad intermedia"){
            randomRight = (0 until 3).random()
            answer1.setEnabled(true)
            answer2.setEnabled(true)
            answer3.setEnabled(true)
            answer1.setVisibility(View.VISIBLE)
            answer2.setVisibility(View.VISIBLE)
            answer3.setVisibility(View.VISIBLE)
        }

        else {
            randomRight = (0 until 4).random()
            answer1.setEnabled(true)
            answer2.setEnabled(true)
            answer3.setEnabled(true)
            answer4.setEnabled(true)
            answer1.setVisibility(View.VISIBLE)
            answer2.setVisibility(View.VISIBLE)
            answer3.setVisibility(View.VISIBLE)
            answer4.setVisibility(View.VISIBLE)
        }

        val randomAnswers: MutableSet<Int> = mutableSetOf()
        while (randomAnswers.size < 4) { randomAnswers.add((0 until quizData.size).random()) }
        val randomAnswerList = randomAnswers.toList()

        // Asignacion de los textos de los botones utilizando los indices aleatorios
        if (randomRight == 0) {
            binding.answer1.text = rightAnswer
            binding.answer2.text = quizData[randomAnswerList[0]].name
            binding.answer3.text = quizData[randomAnswerList[1]].name
            binding.answer4.text = quizData[randomAnswerList[2]].name
        }
        else if (randomRight == 1) {
            binding.answer2.text = rightAnswer
            binding.answer1.text = quizData[randomAnswerList[0]].name
            binding.answer3.text = quizData[randomAnswerList[1]].name
            binding.answer4.text = quizData[randomAnswerList[2]].name
        }
        else if (randomRight == 2) {
            binding.answer3.text = rightAnswer
            binding.answer1.text = quizData[randomAnswerList[0]].name
            binding.answer2.text = quizData[randomAnswerList[1]].name
            binding.answer4.text = quizData[randomAnswerList[2]].name
        }
        else if (randomRight == 3) {
            binding.answer4.text = rightAnswer
            binding.answer1.text = quizData[randomAnswerList[0]].name
            binding.answer2.text = quizData[randomAnswerList[1]].name
            binding.answer3.text = quizData[randomAnswerList[2]].name
        }
    }

    // Funcion para revisar si la respuesta es correcta
    private fun checkAnswer(submittedAnswer: String) {
        val answerFeedback: String
        if(submittedAnswer == rightAnswer){
            answerFeedback = "Correct"
            rightAnswerCount++
        } else {
            answerFeedback = "Incorrect"
        }

        // Creacion de alerta para mostrar los resultados
        AlertDialog.Builder(requireActivity())
            .setTitle(answerFeedback)
            .setMessage("Answer: $rightAnswer")
            .setPositiveButton("Ok"){ _, _ ->
                view?.let { questionCount(it) }
            }
            .setCancelable(false)
            .show()
    }

    // Funcion para la cuenta de preguntas y el puntaje
    private fun questionCount(view: View) {
        // Llamado a la accion para pasar al siguiente fragmento si termina el quiz y asignacion del bundle con las variables requeridas
        if (questionCount == maxQuestionCount){
            val bundle = Bundle()
            bundle.putInt("correct",rightAnswerCount)
            bundle.putInt("questions",maxQuestionCount)
            bundle.putInt("level",level)
            bundle.putString("difficulty",difficulty)
            view.findNavController().navigate(R.id.action_quizPage_frag_to_quizEndPage_frag, bundle)
        } // Continuar el quiz
        else {
            questionCount++
            nextQuestion()
        }
    }
}