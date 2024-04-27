# <p align="center">School Project</p>

## School and Course
<img src="https://epg.ulisboa.pt/sites/ulisboa.pt/files/styles/logos_80px_vert/public/uo/logos/logo_ist.jpg?itok=2NCqbcIP" width="100" height="50">

[Instituto Superior Técnico](https://tecnico.ulisboa.pt/)

[Engenharia Informática e de Computadores](https://tecnico.ulisboa.pt/en/education/courses/undergraduate-programmes/computer-science-and-engineering/)

## Class Subject and Goals
### Class: [OOP](https://fenix.tecnico.ulisboa.pt/cursos/leic-t/disciplina-curricular/1971853845332783) - Object-Oriented Programming

### Goals

 - Use of the OOP paradigm (concepts: encapsulation, abstraction, inheritance, and polymorphism); 
 - Use of an OOP language: Java; 
 - use of design patterns to solve application structuring problems; 
 - writing test cases for applications.

### Grade: 16/20 ![Grade](https://img.shields.io/badge/Grade-B%2B-brightgreen)


## Problem Specification

The aim of the project is to develop an application that will act as the manager of a spreadsheet. This document is organized as follows. 
Section 1 presents the entities in the domain of the application to be developed. 
Section 2 describes the design requirements that the application developed must offer.

[Section1](section1.md) <br>
[Section2](section2.md)

## Requirements

java 17

```bash
sudo apt update 
```
```bash
sudo apt install -y openjdk-17-jdk 
```

## Compilation
To compile the program, use the following command:

```bash
javac -cp po-uilib.jar:. `find xxl -name "*.java"`
```
## Run
Run the program using the following command:

```bash
java -cp po-uilib.jar:. xxl.app.App
```

## Testing
To run all tests:
```bash
./runtests_both.sh
``` 

<h2>Disclaimer</h2>

Folder 'pt' is a framework provided by the school, it was not developed by me nor do I have any intellectual prority over it.
 The structure in the xxl folder and the app subfolder was also provided by the school.

<h2>Credits</h2>

- Author: <a href="https://github.com/iribeirocampos" target="_blank">Iuri Campos</a>

<h2>Copyright</h2>
This project is licensed under the terms of the MIT license and protected by IST Honor Code and Community Code of Conduct. 

<img src="https://img.shields.io/badge/java-00599C?style=for-the-badge&logo=java&logoColor=white">
