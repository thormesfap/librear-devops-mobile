package br.com.librear

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load

class CourseAdapter(private var courses: List<CourseResponse>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.course_detail_card, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int = courses.size

    fun updateCourses(newCourses: List<CourseResponse>) {
        courses = newCourses
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.bind(course)
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseImageView: ImageView =
            itemView.findViewById<ImageView>(R.id.courseImageView)
        private val courseTitleTextView: TextView =
            itemView.findViewById<TextView>(R.id.courseTitleTextView)
        private val courseDescriptionTextView: TextView =
            itemView.findViewById<TextView>(R.id.courseDescriptionTextView)
        private val lessonAmountTextView: TextView =
            itemView.findViewById<TextView>(R.id.lessonsAmountTextView)
        private val readingsAmountTextView: TextView =
            itemView.findViewById<TextView>(R.id.readingsAmountTextView)
        fun bind(course: CourseResponse) {
            courseTitleTextView.text = course.titulo
            courseDescriptionTextView.text= course.descricao
            lessonAmountTextView.text = course.aulas?.size.toString() + " aulas"
            readingsAmountTextView.text = course.leituras?.size.toString() + " leituras"
            courseImageView.load(course.capaUrl)
        }


    }

}