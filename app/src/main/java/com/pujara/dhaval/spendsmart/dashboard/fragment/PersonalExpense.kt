package com.pujara.dhaval.spendsmart.dashboard.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.DashboardActivity
import com.pujara.dhaval.spendsmart.dashboard.adapter.ExpenseListAdapterJava
import com.pujara.dhaval.spendsmart.dashboard.model.PersonalExpenseData
import com.pujara.dhaval.spendsmart.dashboard.presenter.personalexpense.IPersonalExpensePresenter
import com.pujara.dhaval.spendsmart.dashboard.presenter.personalexpense.PersonalExpensePresenter
import com.pujara.dhaval.spendsmart.dashboard.view.IPersonalExpenseView
import java.util.ArrayList

class PersonalExpense : Fragment(), IPersonalExpenseView, ExpenseListAdapterJava.onNoteListerner {

    private var mFirebaseAuth = FirebaseAuth.getInstance()
    val user: String? = mFirebaseAuth.currentUser?.uid
    private var recyclerViewPersonalExpense: RecyclerView? = null
    private lateinit var personalExpensePresenter: IPersonalExpensePresenter
    private var totalIncome: Double = 0.0
    private var totalExpense: Double = 0.0
    private var textviewtotalExpense: TextView? = null
    private var textviewtotalIncome: TextView? = null
    private lateinit var expenseListDataSorted: ArrayList<PersonalExpenseData>
    override fun setExpenseList(expenseList: ArrayList<PersonalExpenseData>) {

        expenseListDataSorted = expenseList
        totalIncome = 0.0
        totalExpense = 0.0
        for (eData in expenseListDataSorted) {
            if (eData.expense == "Expense") {
                totalExpense += eData.amount!!.toDouble()
            } else {
                totalIncome += eData.amount!!.toDouble()
            }
        }
        showData(totalExpense, totalIncome)
        recyclerViewPersonalExpense = view?.findViewById(R.id.recycler_view_personal_expense) as RecyclerView?
        recyclerViewPersonalExpense?.layoutManager = activity?.let { LinearLayoutManager(it) }
        if (!expenseListDataSorted.isEmpty())
            recyclerViewPersonalExpense?.adapter = ExpenseListAdapterJava(expenseListDataSorted, this, context)
    }

    override fun onNoteClick(position: Int) {
        val fullscreenDialog = FullScreenDialogPersonal()
        val args = Bundle()
        args.putSerializable("personalData", expenseListDataSorted[position])
        args.putString("edit", "Yes")
        fullscreenDialog.arguments = args
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val transaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        val fullScreenDialogPersonal = FullScreenDialogPersonal()
        fullScreenDialogPersonal.arguments = args
        transaction?.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_in_bottom)
        transaction?.replace(R.id.container_drawer, fullScreenDialogPersonal)
        transaction?.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun showData(totalExpense: Double, totalIncome: Double) {
        textviewtotalExpense = view?.findViewById(R.id.TextviewtotalExpense)
        textviewtotalExpense?.text = "$$totalExpense"
        context?.let { ContextCompat.getColor(it, R.color.red) }?.let { textviewtotalExpense?.setTextColor(it) }
        textviewtotalIncome = view?.findViewById(R.id.TextviewtotalIncome)
        textviewtotalIncome?.text = "$$totalIncome"
        context?.let { ContextCompat.getColor(it, R.color.colorSecondaryDark) }
            ?.let { textviewtotalIncome?.setTextColor(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_personal_expense, container, false)
        personalExpensePresenter = PersonalExpensePresenter(this)
        personalExpensePresenter.fetchExpense(user)
        textviewtotalExpense?.findViewById<TextView>(R.id.TextviewtotalExpense)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isAdded) {
            return
        }
    }

    fun addExpense(dashboardActivity: DashboardActivity) {
        (dashboardActivity as NavigationHost).navigateTo(
            FullScreenDialogPersonal(),
            true,
            true,
            R.anim.slide_in_bottom,
            R.anim.fade_out,
            0,
            0
        )
    }
}
