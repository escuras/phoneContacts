package com.jumia.utilities;

public enum Country {

    Cameroon("237", "\\(237\\)\\ ?[2368]\\d{7,8}$"),
    Ethiopia("251", "\\(251\\)\\ ?[1-59]\\d{8}$"),
    Morocco("212", "\\(212\\)\\ ?[5-9]\\d{8}$"),
    Mozambique("258", "\\(258\\)\\ ?[28]\\d{7,8}$"),
    Uganda("256", "\\(256\\)\\ ?\\d{9}$");

    public final static int COUNTRY_START_CODE_INDEX = 1;
    public final static int COUNTRY_END_CODE_INDEX = 4;

    private String code;
    private String validRegex;

    Country(String code, String validRegex){
        this.code = code;
        this.validRegex = validRegex;
    }

    public String getCode(){
        return code;
    }

    public String getValidRegex(){
        return validRegex;
    }

    public static Country findCountry(String name){
        for(Country country : Country.values()){
            if(country.name().equalsIgnoreCase(name)){
                return country;
            }
        }
        return null;
    }
}
