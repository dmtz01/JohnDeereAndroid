package com.example.johndeere.api

import android.util.Log
import com.example.johndeere.models.employeeInfo
import com.example.johndeere.models.insertResponse
import com.example.johndeere.models.organization
import com.example.johndeere.models.progress

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
Definición de los servicios para el API.
Incluye las funciones correspondiendes a la solicitud (request) y respuesta (response)
del API para las rutas especificadas en el archivo Api.kt
 */


object ApiService {

    val retrofit = ServiceBuilder.buildService(Api::class.java)

    fun addEmployee(userData: employeeInfo, onResult: (insertResponse?) -> Unit) {

        retrofit.addEmployee(userData).enqueue(
            object : Callback<insertResponse> {
                override fun onFailure(call: Call<insertResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<insertResponse>,
                    response: Response<insertResponse>
                ) {
                    val addedEmployee = response.body()
                    onResult(addedEmployee)
                }
            }
        )
    }

    fun verifyOrg(orgData: organization, onResult: (List<organization>?) -> Unit) {
        retrofit.verifyOrg(orgData).enqueue(
            object : Callback<List<organization>> {
                override fun onFailure(call: Call<List<organization>>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<organization>>,
                    response: Response<List<organization>>
                ) {
                    val org = response.body()
                    onResult(org)
                }
            }
        )
    }

    fun authEmployee(employeeData: employeeInfo, onResult: (List<employeeInfo>?) -> Unit) {
        retrofit.authEmployee(employeeData).enqueue(
            object : Callback<List<employeeInfo>> {
                override fun onFailure(call: Call<List<employeeInfo>>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<employeeInfo>>,
                    response: Response<List<employeeInfo>>
                ) {
                    val employee = response.body()
                    onResult(employee)
                }
            }
        )
    }

    fun createProgress(progressData: progress, onResult: (insertResponse?) -> Unit) {
        retrofit.createProgress(progressData).enqueue(
            object : Callback<insertResponse> {
                override fun onFailure(call: Call<insertResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<insertResponse>,
                    response: Response<insertResponse>
                ) {
                    val createdProgress = response.body()
                    onResult(createdProgress)
                }
            }
        )
    }
}