Java Grading Tools
===========================================================

Authors
===========================================================
Steven Karas
Idan Felix
The entire Intro2CS team


Description
===========================================================
This project is the result of several years of work by various TAs at the Interdisciplinary Center
of Herzliyah, Israel. The project currently has several components:

1. Reflection - used to test API compliance. Purely academic applications.
2. I/O Redirection - used to script tests that use stdin, stdout.
3. ClassLoader - used to hotswap students' classes with school solution
4. TestUtils - Used to disable System.exit(), etc.
5. GeneralTest - An abstract base class that enables common features for JUnit tests. 


Usage
===========================================================
See javadoc.


Building from source
===========================================================
From within eclipse or from the command line using Ant+Ivy.
Note: you may need to configure eclipse's ant to recognize ivy.

Final caveat: I have a bug in my eclipse workspace that prevents me from properly testing this,
and it failed when I tried from the command line. Good luck building it yourself!