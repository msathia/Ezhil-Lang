open-tamil [![Build Status](https://travis-ci.org/arcturusannamalai/open-tamil.png)](https://travis-ci.org/arcturusannamalai/open-tamil)
==========

Open Source Tamil Tools

Software
========

*tamil*
open-tamil rovides Python package 'tamil' with ability to,

1. map unicode code-points to Tamil letters - basic but important parsing - in a routine called get_letters from a Tamil word
2. work with vowels (uyir) and consonants (mei), compound, uyir-mei letters
3. reverse letters in Tamil word

*transliterate*
the python package 'transliterate' provides for commonly used transliteration
phonetic schemes like,
1. Azhagi
2. Jaffna Library

where you can supply English text, which phonetically encodes Tamil, and then receive Unicode encoded, in a best-effort algorithm for the longest phonetic match.
*C-tamil*
the package under C-tamil provides some of the same functionality as Python 'tamil' but in ISO-C for C/C++ use.

Goals
=====
Goal of this package is to collect and develop open-source licensed Tamil tools, in one location that provide the following,

1. Unicode standard tools for Tamil - provide various tools for Tamil Unicode development
2. Access Unicode Tamil letters, vowels and consonants.
3. Breakdown Tamil glyphs and unicode code-points into Tamil letter representations - collation
4. Tools for navigating a corpus of data, build word frequency, prediction tables etc.
5. Conversion from various encodings. e.g. TSCII to Unicode etc.

While most of tools in this package will be in Python 2.6. or later, I am open to other open-source language source code contributions.

About
=====
Tamil is classical language primarily spoken in South India.
   
