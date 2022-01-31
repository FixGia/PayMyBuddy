package com.project.paymybuddy.Util;

public class GeneralConstraints {

    public GeneralConstraints() {
    }


    /** The Constant VARIABLE_LENGTH_125. */
    public static final int VARIABLE_LENGTH_125 = 125;

    /** The Constant VARIABLE_LENGTH_30. */
    public static final int VARIABLE_LENGTH_30 = 30;


    /** The Constant PATTERN_ALPHANUMERIC. */
    public static final String PATTERN_ALPHANUMERIC = "[-A-Za-z0-9\\s]+${2}";

    /** The Constant PATTERN_ALPHABET_CHARACTERS. */
    public static final String PATTERN_ALPHABET_CHARACTERS = "[-A-Za-z]+${2}";

    /** The Constant PATTERN_USERNAME. */
    public static final String PATTERN_USERNAME = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /** The Constant PATTERN_PASSWORD. */
    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&~#@=*-+!€^$£µ%])(?=\\S+$).{8,}$";

}