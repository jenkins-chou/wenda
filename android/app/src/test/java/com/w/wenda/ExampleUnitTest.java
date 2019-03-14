package com.w.wenda;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        String str = "你好啊，jack";
        System.out.println(ToAnalysis.parse(str));
    }
}