package com.example.weatherwear;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private String globalVariable;

    public String getGlobalVariable() {
        return globalVariable;
    }

    public void setGlobalVariable(String value) {
        globalVariable = value;
    }
}
