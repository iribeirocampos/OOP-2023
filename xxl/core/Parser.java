package xxl.core;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import xxl.core.exception.RangeOutOfBounds;
import xxl.core.exception.UnrecognizedCoordsException;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.exception.UnrecognizedFunctionException;
import xxl.core.intervalFunction.Average;
import xxl.core.intervalFunction.Coalesce;
import xxl.core.intervalFunction.Concat;
import xxl.core.intervalFunction.Product;
import xxl.core.binary.Add;
import xxl.core.binary.Div;
import xxl.core.binary.Mul;
import xxl.core.binary.Sub;

class Parser {

  private Spreadsheet _spreadsheet;

  Parser() {
  }

  Parser(Spreadsheet spreadsheet) {
    _spreadsheet = spreadsheet;
  }

  Spreadsheet parseFile(String filename)
      throws IOException, UnrecognizedEntryException, UnrecognizedCoordsException, RangeOutOfBounds,
      UnrecognizedFunctionException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      parseDimensions(reader);

      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }

    return _spreadsheet;
  }

  private void parseDimensions(BufferedReader reader) throws UnrecognizedEntryException {
    int rows = -1;
    int columns = -1;
    int i;
    for (i = 0; i < 2; i++) {
      try {
        String[] dimension = reader.readLine().split("=");
        if (dimension[0].equals("linhas"))
          rows = Integer.parseInt(dimension[1]);
        else if (dimension[0].equals("colunas"))
          columns = Integer.parseInt(dimension[1]);
        else
          throw new UnrecognizedEntryException(reader.readLine());
      } catch (IOException e) {
        throw new UnrecognizedEntryException("Não foi possivel ler o conteudo do ficheiro.");
      }
    }

    if (rows <= 0 || columns <= 0)
      throw new UnrecognizedEntryException("Dimensões inválidas para a folha");

    _spreadsheet = new Spreadsheet(rows, columns);

    if (rows <= 0 || columns <= 0)
      throw new UnrecognizedEntryException("Dimensões inválidas para a folha");
    _spreadsheet = new Spreadsheet(rows, columns);

  }

  private void parseLine(String line)
      throws UnrecognizedEntryException, UnrecognizedCoordsException, RangeOutOfBounds, UnrecognizedFunctionException {
    String[] components = line.split("\\|");

    if (components.length == 1) // do nothing
      return;

    if (components.length == 2) {
      String[] address = components[0].split(";");
      Content content = null;
      content = parseContent(components[1]);
      _spreadsheet.getCell(Integer.parseInt(address[0]), Integer.parseInt(address[1])).insertConteudo(content);
    } else
      throw new UnrecognizedEntryException("Wrong format in line: " + line);
  }

  // // parse the begining of an expression
  Content parseContent(String contentSpecification)
      throws UnrecognizedEntryException, UnrecognizedFunctionException, RangeOutOfBounds, UnrecognizedCoordsException {
    try {
      char c = contentSpecification.charAt(0);

      if (c == '=')
        return parseContentExpression(contentSpecification.substring(1));
      else
        return parseLiteral(contentSpecification);
    } catch (StringIndexOutOfBoundsException e) {
      throw new UnrecognizedEntryException(contentSpecification);
    }
  }

  private Content parseLiteral(String literalExpression) throws UnrecognizedEntryException {
    if (literalExpression.charAt(0) == '\'')
      return new LiteralString(literalExpression.substring(1));
    else {
      try {
        int val = Integer.parseInt(literalExpression);
        return new LiteralInteger(val);
      } catch (NumberFormatException nfe) {
        throw new UnrecognizedEntryException("Número inválido: " +
            literalExpression);
      }
    }
  }

  // // contentSpecification is what comes after '='
  private Content parseContentExpression(String contentSpecification)
      throws UnrecognizedEntryException, UnrecognizedFunctionException, RangeOutOfBounds {
    try {
      if (contentSpecification.contains("("))
        return parseFunction(contentSpecification);
      // It is a reference
      String[] address = contentSpecification.split(";");
      return new Reference(Integer.parseInt(address[0].trim()), Integer.parseInt(address[1]), _spreadsheet);
    } catch (UnrecognizedCoordsException | ArrayIndexOutOfBoundsException e) {
      throw new UnrecognizedEntryException(contentSpecification);
    }
  }

  private Content parseFunction(String functionSpecification)
      throws UnrecognizedEntryException, UnrecognizedFunctionException, RangeOutOfBounds, UnrecognizedCoordsException {
    String[] components = functionSpecification.split("[()]");
    if (components[1].contains(","))
      return parseBinaryFunction(components[0], components[1]);
    return parseIntervalFunction(components[0], components[1]);
  }

  private Content parseBinaryFunction(String functionName, String args)
      throws UnrecognizedEntryException, UnrecognizedFunctionException, UnrecognizedCoordsException {
    String[] arguments = args.split(",");
    Content arg0 = parseArgumentExpression(arguments[0]);
    Content arg1 = parseArgumentExpression(arguments[1]);
    return switch (functionName) {
      case "ADD" -> new Add(arg0, arg1);
      case "SUB" -> new Sub(arg0, arg1);
      case "MUL" -> new Mul(arg0, arg1);
      case "DIV" -> new Div(arg0, arg1);
      default -> throw new UnrecognizedFunctionException(functionName);
    };
  }

  private Content parseArgumentExpression(String argExpression)
      throws UnrecognizedEntryException, UnrecognizedCoordsException {
    if (argExpression.contains(";") && argExpression.charAt(0) != '\'') {
      String[] address = argExpression.split(";");
      return new Reference(Integer.parseInt(address[0].trim()), Integer.parseInt(address[1].trim()), _spreadsheet);
    } else {
      return parseLiteral(argExpression);
    }
  }

  private Content parseIntervalFunction(String functionName, String rangeDescription)
      throws UnrecognizedEntryException, UnrecognizedFunctionException, RangeOutOfBounds, UnrecognizedCoordsException {
    Range range = _spreadsheet.createRange(rangeDescription);
    return switch (functionName) {
      case "CONCAT" -> new Concat(range);
      case "COALESCE" -> new Coalesce(range);
      case "PRODUCT" -> new Product(range);
      case "AVERAGE" -> new Average(range);
      default -> throw new UnrecognizedFunctionException(functionName);
    };
  }

}
