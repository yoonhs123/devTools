package yoon.hyeon.sang.translator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Languages {
    @JsonProperty("language")
    private String language;

    @JsonProperty("name")
    private String name;

    @JsonProperty("supports_formality")
    private boolean supports_formality;

    public Languages() {
    }

    public Languages(String code, String name){
        this.language = code;
        this.name = name;
    }

    public Languages(String code, String name, boolean supports_formality) {
        this.language = code;
        this.name = name;
        this.supports_formality = supports_formality;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSupports_formality() {
        return supports_formality;
    }

    public void setSupports_formality(boolean supports_formality) {
        this.supports_formality = supports_formality;
    }
}
