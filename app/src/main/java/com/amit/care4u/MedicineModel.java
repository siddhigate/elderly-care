package com.amit.care4u;

public class MedicineModel {
    String med_name, med_timing, med_desc;

    public MedicineModel(String med_name, String med_timing, String med_desc) {
        this.med_name = med_name;
        this.med_timing = med_timing;
        this.med_desc = med_desc;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_timing() {
        return med_timing;
    }

    public void setMed_timing(String med_timing) {
        this.med_timing = med_timing;
    }

    public String getMed_desc() {
        return med_desc;
    }

    public void setMed_desc(String med_desc) {
        this.med_desc = med_desc;
    }
}
