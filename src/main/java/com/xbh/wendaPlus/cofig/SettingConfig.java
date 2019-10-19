package com.xbh.wendaPlus.cofig;

public class SettingConfig {
    public static String issueWordsLengthMin = "0";
    public static String issueWordsLengthMax = "99999999";

    public static String resultWordsLengthMin = "0";
    public static String resultWordsLengthMax = "99999999";


    public static String getIssueWordsLengthMin() {
        return issueWordsLengthMin;
    }

    public static void setIssueWordsLengthMin(String issueWordsLengthMin) {
        SettingConfig.issueWordsLengthMin = issueWordsLengthMin;
    }

    public static String getIssueWordsLengthMax() {
        return issueWordsLengthMax;
    }

    public static void setIssueWordsLengthMax(String issueWordsLengthMax) {
        SettingConfig.issueWordsLengthMax = issueWordsLengthMax;
    }

    public static String getResultWordsLengthMin() {
        return resultWordsLengthMin;
    }

    public static void setResultWordsLengthMin(String resultWordsLengthMin) {
        SettingConfig.resultWordsLengthMin = resultWordsLengthMin;
    }

    public static String getResultWordsLengthMax() {
        return resultWordsLengthMax;
    }

    public static void setResultWordsLengthMax(String resultWordsLengthMax) {
        SettingConfig.resultWordsLengthMax = resultWordsLengthMax;
    }

    public static void setAnsWordLengthMax(String min) {
        resultWordsLengthMax = min;
    }

    public static void setAnsWordLengthMaxMin(String max) {
        resultWordsLengthMin = max;
    }
}
