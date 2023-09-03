# oleksandr.kharchenko.encoder
The application implements the ability to encrypt and decrypt using a key with the Caesar cipher.
There is also a method that allows for text decryption through brute force key selection.
If arguments are not specified when launching the JAR file, they can be provided through a dialogue prompted by the application.
The core functions of the application, such as text encryption, text decryption, brute-force decryption, input and data reading via the console,
a list of and execution of used commands, and determining the user interaction method, are implemented through separate classes for more flexible usage.
For text analysis decrypted using the BruteForce method, comparison with statistical data of commonly used English letters in English texts is used.
If, when comparing with this data, taking into account the specified margin of error, 4 or more matches are obtained, such text is presented for final analysis to the user.
