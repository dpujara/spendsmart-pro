package com.pujara.dhaval.spendsmart.dashboard.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.model.PersonalExpenseData
import com.pujara.dhaval.spendsmart.dashboard.presenter.dialog.AddExpensePresenter
import com.pujara.dhaval.spendsmart.dashboard.presenter.dialog.IAddExpensePresenter
import com.pujara.dhaval.spendsmart.dashboard.view.IAddExpenseView
import kotlinx.android.synthetic.main.full_screen_dialog_personal_expense.view.*
import kotlinx.android.synthetic.main.full_screen_dialog_personal_expense_body.*
import kotlinx.android.synthetic.main.full_screen_dialog_personal_expense_body.view.*
import java.util.*
import android.support.v7.app.AlertDialog
import com.pujara.dhaval.spendsmart.dashboard.ButtonChangeVisibility


class FullScreenDialogPersonal : DialogFragment(), IAddExpenseView {
    override fun updatedData(message: String) {
        view?.let { hideSoftKeyboard(it) }
        displayMessage(message)
        fragmentManager?.popBackStack()
        dismiss()
    }

    override fun expenseAdded(str: String) {
        view?.let { hideSoftKeyboard(it) }
        displayMessage(str)
        view?.edittext_amount_fullscreen_dialog_personal?.text = null
        view?.edittext_descr_fullscreen_dialog_personal?.text = null
        view?.dateTextview?.text = null
        view?.edittext_descr_fullscreen_dialog_personal?.clearFocus()
        view?.edittext_amount_fullscreen_dialog_personal?.clearFocus()
        view?.dateTextview?.clearFocus()
    }

    private lateinit var addExpensePresenter: IAddExpensePresenter
    private var snackbar: Snackbar? = null
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    val user: String? = mFirebaseAuth.currentUser?.uid
    private val userEmail: String = mFirebaseAuth.currentUser?.email.toString()
    private var cal = Calendar.getInstance()
    var edit = ""
    var uniqueKey = ""
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.full_screen_dialog_personal_expense, container, false)

        addExpensePresenter = AddExpensePresenter(this)
        rootView.button_close_personal_expense.setOnClickListener {
            fullScreenDialogPersonal.updateVisibility()
            view?.let { hideSoftKeyboard(it) }
            fragmentManager?.popBackStack()
            dismiss()
        }

        val spinner: Spinner? = view?.findViewById(R.id.spinner)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
            }
        }

        rootView?.dateTextview?.setOnClickListener {
            val yearI = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpd =
                DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val d: String = if (dayOfMonth < 10) {
                        "0$dayOfMonth"
                    } else {
                        dayOfMonth.toString() + ""
                    }
                    val m: String
                    var monthInc: Int = monthOfYear
                    monthInc += 1
                    m = if (monthInc < 10) {
                        "0$monthInc"
                    } else {
                        monthInc.toString() + ""
                    }
                    val formatDate = "$m/$d/$year"
                    dateTextview.text = formatDate
                }, yearI, month, day)
            dpd.show()
        }

        rootView?.button_action_personal_expense?.setOnClickListener {
            if(edit == "Yes"){
                fullScreenDialogPersonal.updateVisibility()
                addExpensePresenter.updateExpenseData(
                    uniqueKey,
                    view?.edittext_descr_fullscreen_dialog_personal?.text.toString(),
                    view?.edittext_amount_fullscreen_dialog_personal?.text.toString(), view?.dateTextview?.text.toString(),
                    view?.spinner?.selectedItem.toString(), user
                )

            }else{
                d("clicked no", edit)
                addExpensePresenter.insertExpenseData(
                    view?.edittext_descr_fullscreen_dialog_personal?.text.toString(),
                    view?.edittext_amount_fullscreen_dialog_personal?.text.toString(), view?.dateTextview?.text.toString(),
                    view?.spinner?.selectedItem.toString(), user
                )
            }
        }


        rootView.button_delete_personal_expense.setOnClickListener{
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    .setPositiveButton(android.R.string.yes) { dialog, which ->
                        fullScreenDialogPersonal.updateVisibility()
                        addExpensePresenter.deleteData(uniqueKey,user)
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .show()
            }
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        val bundle: Bundle? = arguments
        edit = arguments?.getString("edit").toString()
        val editTextDescr: TextInputEditText = view?.findViewById(R.id.edittext_descr_fullscreen_dialog_personal)!!
        val editTextAmount: TextInputEditText = view?.findViewById(R.id.edittext_amount_fullscreen_dialog_personal)!!
        val textViewDate: TextView = view?.findViewById(R.id.dateTextview)!!
        val spinner : Spinner = view?.findViewById(R.id.spinner)!!
        val textViewTitle :TextView = view?.findViewById(R.id.appbar_title_textview)!!
        val imageButtonDelete : ImageButton = view?.findViewById(R.id.button_delete_personal_expense)!!
        if (bundle != null) {
            val personalExpenseData: PersonalExpenseData = bundle.getSerializable("personalData") as PersonalExpenseData
            uniqueKey = personalExpenseData.uniqueKey.toString()
            d("amount1", personalExpenseData.amount.toString())
            editTextDescr.setText(personalExpenseData.description, TextView.BufferType.EDITABLE)
            editTextAmount.setText(personalExpenseData.amount, TextView.BufferType.EDITABLE)
            textViewDate.setText(personalExpenseData.month + "/" + personalExpenseData.day + "/" + personalExpenseData.year, TextView.BufferType.EDITABLE)
            textViewTitle.text = "Edit Expense"
            for (i in 0 until spinner.count){
                if(spinner.getItemAtPosition(i).toString().equals(personalExpenseData.expense)){
                    spinner.setSelection(i)
                    break
                }
            }
            imageButtonDelete.visibility = View.VISIBLE
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun displayMessage(str: String) {
        snackbar = view?.let { Snackbar.make(it, str, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fullScreenDialogPersonal = activity as ButtonChangeVisibility
    }

    private lateinit var fullScreenDialogPersonal: ButtonChangeVisibility
    companion object {
        fun newInstance() = FullScreenDialogPersonal()
    }
}