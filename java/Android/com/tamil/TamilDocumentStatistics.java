package com.tamil;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by muthu on 3/20/2016.
 *
 * Find out things like,
 *  - sorted words by occurrence/frequency (descending only)
 *  - number of chars, words, lines (WC)
 *  - (TBD) english/tamil ratio
 *
 *  Usage:
 *  TamilDocumentStatistics calc = new TamilDocumentStatistics();
 *  calc.begin().add(buffer).add(more_buffer).end();
 *  calc.maxWord();
 *  calc.allWordsByFrequency();
 *  calc.totalWords()
 *  calc.totalLetters()
 *  calc.totalLines()
 *
*/
public class TamilDocumentStatistics {
    private boolean m_complete = false;
    private long m_nLines = 0;
    private long m_nWords = 0;
    private long m_nLetters = 0;
    private long m_nEnglishLetters = 0;
    private long m_max_word_freq;
    private String m_max_word;
    private HashMap<String,Long> m_wordFrequency;

    public TamilDocumentStatistics() {
        m_wordFrequency = new HashMap<String, Long>();
    }

    public long getTotalWords() {
        return m_nWords;
    }

    long getTotalLetters() {
        return m_nLetters;
    }

    long getTotalLines() {
        return m_nLines;
    }

    public String toString() {
        return "Letters = "+getTotalLetters()+" Words = "+getTotalWords()+" Lines = "+getTotalLines();
    }

    public String getWordWithMaxFrequency() {
        return m_max_word;
    }

    TamilDocumentStatistics begin() {
        m_complete = false;
        m_nLetters = 0;
        m_nLines =0 ;
        m_wordFrequency.clear();
        m_nEnglishLetters = 0;
        m_nWords = 0;
        m_max_word_freq = 0;
        m_max_word = "";
        return this;
    }

    private void update_word(String w) {
        Long prev_count_long = m_wordFrequency.get(w);
        long prev_count = prev_count_long == null ? 0 : prev_count_long.longValue();
        m_wordFrequency.put(w,prev_count+1);

        //keep reference to top word
        if ( prev_count > m_max_word_freq ) {
            m_max_word = w;
            m_max_word_freq += 1;
        }
    }
    public class WordFrequency  {
        public String word;
        public long frequency;

        WordFrequency(String in_word) {
            word = in_word;
            frequency = 0;
        }
    }

    public class WordFrequencyComparator implements java.util.Comparator<WordFrequency> {
        @Override
        public int compare(WordFrequency a, WordFrequency b) {
            long freq_a = m_wordFrequency.get(a.word);
            long freq_b = m_wordFrequency.get(b.word);

            // update frequency - unrelated to sorting but required
            a.frequency = freq_a;
            b.frequency = freq_b;

            if ( freq_a != freq_b) {
                return ( freq_a > freq_b) ? 1: -1;
            }
            return 0;
        }
    }

    public ArrayList<WordFrequency> getAllWordsByFrequency() {
        Iterator<String> all_words = m_wordFrequency.keySet().iterator();
        ArrayList<WordFrequency> wordfreq = new ArrayList<WordFrequency>();

        while(all_words.hasNext()) {
            wordfreq.add( new WordFrequency(all_words.next()));
        }

        Collections.sort(wordfreq, new WordFrequencyComparator());
        return wordfreq;
    }

    public TamilDocumentStatistics add(String input) {
        List<String> letters = utf8.get_length(input).getLetters();
        Iterator<String> itr = letters.iterator();

        boolean word_boundary = false;
        StringBuilder curr_word = new StringBuilder();
        int codept;
        String val;
        while(itr.hasNext()) {
            val = itr.next();
            if (val.isEmpty())
                continue;

            //count as char for anything
            m_nLetters++;
            codept = val.codePointAt(0);

            //see if it could be English letters
            if (codept <= 'z' && codept >= 'A') {
                m_nEnglishLetters++;
            }

            if (val.equalsIgnoreCase("\n") || val.equalsIgnoreCase("\r\n")) {
                m_nLines++;
                word_boundary = true;
            } else if (val.matches("\\s+")) {
                word_boundary = true;
            } else {
                curr_word.append(val);
            }

            if (word_boundary) {
                word_boundary = false;

                String _w = curr_word.toString();
                if (_w.isEmpty())
                    continue;

                update_word(_w);
                m_nWords++;
                curr_word.delete(0,_w.length()-1);
            }
        }

        return this;
    }
}
