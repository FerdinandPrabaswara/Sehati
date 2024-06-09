package com.example.sehati.model;

public class Medicine {
    String _nameMedicine, _priceMedicine,  _jumlahObat;

    String key;

    public Medicine(String _nameMedicine, String _priceMedicine) {
        this._nameMedicine = _nameMedicine;
        this._priceMedicine = _priceMedicine;
    }

    public void set_nameMedicine(String _nameMedicine) {
        this._nameMedicine = _nameMedicine;
    }

    public void set_priceMedicine(String _priceMedicine) {
        this._priceMedicine = _priceMedicine;
    }

    public void set_jumlahObat(String _jumlahObat) {
        this._jumlahObat = _jumlahObat;
    }

    public Medicine(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
