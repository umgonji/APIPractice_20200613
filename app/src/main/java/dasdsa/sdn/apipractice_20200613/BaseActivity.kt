package dasdsa.sdn.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()


}