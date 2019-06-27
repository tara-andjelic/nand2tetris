// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
// KBD 1 register, SCREEN 8192 registers

// LOOP:  
//   if(KBD=0)
//      color=0  (white)   000000000
//   else
//      color=-1 (black)   111111111  
//   while(i<8192)
//      SCREEN[i]=color 

(LOOP)
@8192
D=A
@n
M=D

@SCREEN
D=A
@address
M=D

@KBD
D=M
@color
D=D-M
@LOOP
D,JEQ

@KBD
D=M
@WHITE
D,JEQ

@color
M=-1
@PAINT
0,JMP

(WHITE)
@color
M=0
@PAINT
0,JMP

(PAINT)
@n
D=M
@LOOP
D,JLE
@color
D=M
@address
A=M
M=D
@address
M=M+1
@n
M=M-1
@PAINT
0,JMP

@LOOP
0,JMP
