package com.thedeanda.javajson.parser;

import java.util.HashMap;
import java.util.Map;

public class ParserUtils {
    private static Map<Character, String> escapeMap = new HashMap<Character, String>();
    static {
        escapeMap.put('\\', "\\");
        escapeMap.put('b', "\b");
        escapeMap.put('f', "\f");
        escapeMap.put('n', "\n");
        escapeMap.put('r', "\r");
        escapeMap.put('t', "\t");
        escapeMap.put('/', "/");
        escapeMap.put('\"', "\"");
        escapeMap.put('\'', "\'");
    }

    /*
    @Override
    protected void interpret() {
        // ((SimpleNode) parent).push(ASTstring.fixString(val));
        ((SimpleNode) parent).push(new JsonValue(val, true));
    }
    //*/

    /** Removes escapes from string and returns normal string */
    public static String fixString(String s) {
        s = s.substring(1, s.length() - 1);
        StringBuffer ret = new StringBuffer();
        boolean escape = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!escape) {
                if (c == '\\')
                    escape = true;
                else
                    ret.append(c);
            } else {
                if (escapeMap.containsKey(c))
                    ret.append(escapeMap.get(c));
                else if (c == 'u' && s.length() >= i + 4) {
                    // handle unicode numbers... read 4 more chars (number)
                    String ucode = s.substring(i + 1, i + 5);
                    int ucoden = Integer.parseInt(ucode, 16);
                    char uc = (char) ucoden;
                    i += 4;
                    ret.append(uc);
                } else {
                    // put escape back in, error, but parser missed it(?)
                    ret.append("\\");
                    ret.append(c);
                }
                escape = false;
            }
        }
        return ret.toString();
    }

    protected static String fixString_(String s) {
        String ret = s.substring(1, s.length() - 1);
        // String t = ret;
        ret = ret.replace("\\\"", "\"");
        ret = ret.replace("\\'", "'");

        ret = ret.replace("\\b", "\b");
        ret = ret.replace("\\f", "\f");
        ret = ret.replace("\\n", "\n");
        ret = ret.replace("\\r", "\r");
        ret = ret.replace("\\t", "\t");
        ret = ret.replace("\\/", "/");
        ret = ret.replace("\\\\", "\\");

        // System.out.println(s + " " + ret);
        return ret;
    }

    public static String cleanStringLiteral(String str) {
        if (str == null || str.length() < 2) return str;
        return str.substring(1, str.length() - 1);
    }

    public static String cleanStringLiteralApos(String str) {
        if (str == null || str.length() < 2) return str;
        return str.substring(1, str.length() - 1);
    }
}