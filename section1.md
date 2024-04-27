# <p align="center">Section 1</p>


## Domain Entities
This section describes the various entities that will be manipulated in the context of the application to be developed. There are several important concepts in this context: spreadsheet, cells, content, ranges and user.
The application to be developed is capable of manipulating integers, strings, references between cells and functions on cells. It also allows cut, copy and paste operations. The application only manages one sheet at a time. The number of rows and columns of a sheet is fixed and is defined at the time of creation. In order to implement this application, it may be necessary to consider concepts other than those that have already been explicitly identified in this section.

### 1.1 Cells and Range
The addresses of a cell (positions on the sheet: row and column) start at 1 (one). A cell is defined based on its position on the sheet: CELULA ::= ROW;COLUMN 

An address range (they are always closed) is defined between two cells in the same row or column: RANGE ::= CELL:CELL. The term range is used to indiscriminately specify a single cell or a range of cells. Example:

Cell: ' 1;2 (row 1, column 2); or 23;4 (row 23, column 4)
Range: 1;2:1;20 (row 1, between columns 2 and 20); or 23;4:57;4 (column 4, between lines 23 and 57)

### 1.2 Content: Literals, References, Functions
By default, cells are empty (no content). The allowed contents are: literals (integers and strings of strings), references to cells and functions. References are indicated with the symbol “=” followed by the address of the cell referenced. Functions are indicated with the symbol = (“equal”), the name of the function and the (possibly empty) list of arguments in parentheses (separated by commas). Each function has a given value which corresponds to the evaluation of the given expression. The type of the returned value depends on the function. The following functions are predefined:

- Binary functions on integer values, whose arguments can be references to cells or literal values: ADD (addition),SUB (subtraction), MUL (multiplication), and DIV (integer division). All arguments to these functions must be passable to produce integer values.

- Functions applicable to a range of cells, i.e. with a single argument that is a range of cells:
    - AVERAGE (average of all the values in the range; the division is integer) and PRODUCT (product of all the values in the range). All the values in the range must be integers (they cannot contain other values).
    - CONCAT which returns a string representing the concatenation of the values of several cells in the range whose value is a string. Cells in the range that have values that are not strings of strings are ignored by the function. If the range has only one cell whose value is a character string, then this function should return that string. If no string exists in the range, then it must output the empty string. 
    - COALESCE (accepts a range of cells which may or may not contain strings and returns the first value value found in the given range that is a string). If the range does not contain any strings of characters, it outputs the empty string.

It is assumed that there are no direct or indirect circular dependencies between a function and the cells referred to by their arguments. Functions with invalid arguments (e.g., a range with empty cells in the case of the AVERAGE function), has an invalid value (displayed as #VALUE).

A cell can have contents of different types: 
- Integers (numbers with an optional sign at the beginning): -25, 48
- Strings (always start with plica): 'string
- Strings (empty): '(only one plica)
- References: ˆ =1;2
- Functions: ˜ =ADD(2;3,1), =SUB(6;2,22;1), =MUL(1,2), =DIV(1,5;2), =AVERAGE(1;2:1;19), =PRODUCT(2;33:5;33), =CONCAT(1;12:1;17), =COALESCE(1;12:1;17)

### 1.3 Cut Buffer
Each spreadsheet can have a cut buffer. The cut buffer is unique to the spreadsheet it is associated with. This entity allows the usual operations (copy, cut, paste) between cells in a sheet. The operations have the following semantics: 

 - Copy - The content of the source is copied to the cut buffer and becomes independent of the source, i.e. changes to the original objects are not propagated to the objects in the cut buffer. The previous contents of the cut buffer are destroyed. The address of the first cell of the cut buffer always starts in the 1st row and 1st column, regardless of the address of the first cell to be copied into the cut buffer. However, the cut buffer must preserve the orientation of the range to be copied, if it is a line, the different cells of the cut buffer should all be in row 1; if it is a column, the different cells of the cut buffer should all be in column 1.
 - Cut - This operation works like the copy operation, but the original content is destroyed.
 - Paste - This operation inserts the contents of the cut buffer into a range on the target sheet. If the cut buffer is empty, no any operation is performed. If the range is a single cell, the entire  cut buffer must be inserted from the specified cell, until the limit of the spreadsheet is reached. Otherwise, if the size of the cut buffer is different from that of the destination range, does not insert any value. The contents of the cut buffer are not altered by the operation. The objects inserted into the destination are independent of those in the cut buffer.

### 1.4 User
The application must support the concept of user. Each user has a unique identifier in the application (their name) and has a set of spreadsheets. Due to future needs that have not yet been fully specified, there must be a two-way many-to-many relationship between users and spreadsheets. The application always has an active user. Each time a spreadsheet is created in the application, it must be associated with the active user. The user with the name root must always exist. By default, the active user is the user with the name root.