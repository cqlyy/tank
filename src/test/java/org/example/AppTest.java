package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        List<String> parse = parse("select * from t where a = #{name} and b = #{age}", "{", "}");
        System.out.println(parse);
    }



    public List<String> parse(String text, String openToken, String closeToken) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        // search open token
        int start = text.indexOf(openToken, 0);
        if (start == -1) {
            return new ArrayList<>();
        }
        char[] src = text.toCharArray();
        int offset = 0;
        List<String> res = new ArrayList<>();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    offset = src.length;
                } else {
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken, offset);
            res.add(expression.toString());
        }
        return res;
    }
}
