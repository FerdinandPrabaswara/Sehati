package com.example.sehati.model;

public class Medicine {
    String _nameMedicine, _priceMedicine,  _jumlahObat;

    public Medicine(String _nameMedicine, String _priceMedicine) {
        this._nameMedicine = _nameMedicine;
        this._priceMedicine = _priceMedicine;
    }

    public String get_jumlahObat() {
        return _jumlahObat;
    }

    public String get_nameMedicine() {
        return _nameMedicine;
    }

    public String get_priceMedicine() {
        return _priceMedicine;
    }
}
