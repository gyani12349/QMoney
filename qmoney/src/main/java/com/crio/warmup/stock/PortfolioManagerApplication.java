
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {

    File file = resolveFileFromResources (args[0]);
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioTrade[] trades = objectMapper.readValue(file, PortfolioTrade[].class);
    List<String> symbols = new ArrayList<String>();
    for (PortfolioTrade t: trades) {
      symbols.add(t.getSymbol());
    }

     return symbols;
  }


  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.






  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>

  public static final Comparator<TotalReturnsDto> closingComparator = new Comparator<TotalReturnsDto>() {
    public int compare(TotalReturnsDto t1, TotalReturnsDto t2) {
    return (int) (t1.getClosingPrice().compareTo(t2.getClosingPrice()));
    }
  };

  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "File@56 /home/crio-user/workspace/bhadraabhigyan-ME_QMONEY_V2/qmoney/bin/test/assessments/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@815b41f";
     String functionNameFromTestFileInStackTrace = "PortfolioManagerApplicationTest.mainReadFile()";
     String lineNumberFromTestFileInStackTrace = "24:1";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.










  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.


  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
    String file = args[0];
    LocalDate endDate = LocalDate.parse(args[1]);
    String contents = readFileAsString(file);
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioManager portfolioManager =
        PortfolioManagerFactory.getPortfolioManager(new RestTemplate());
        List<PortfolioTrade> portfolioTrades = objectMapper.readValue(contents, new TypeReference<List<PortfolioTrade>>() {});
    return portfolioManager.calculateAnnualizedReturn(portfolioTrades, endDate);
  }

  private static String readFileAsString(String fileName) throws IOException, URISyntaxException {
    return new String(Files.readAllBytes(resolveFileFromResources(fileName).toPath()), "UTF-8");
  }


  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    printJsonObject(mainReadFile(args));

    printJsonObject(mainCalculateReturnsAfterRefactor(args));



  }


  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    final String tiingoToken = "1695f08005d84a2982483c36572461cb36f936c1";
    List<PortfolioTrade> portfolioTrades = readTradesFromJson(args[0]);
    LocalDate endDate = LocalDate.parse(args[1]);
    RestTemplate restTemplate = new RestTemplate();
    List<TotalReturnsDto> totalReturnsDtos = new ArrayList<>();
    List<String> listOfSortSymbolsOnClosingPrice = new ArrayList<>();
    for (PortfolioTrade portfolioTrade : portfolioTrades) {
      String tiingoURL = prepareUrl(portfolioTrade, endDate, tiingoToken);
      TiingoCandle[] tiingoCandleArray = restTemplate.getForObject(tiingoURL, TiingoCandle[].class);
      totalReturnsDtos.add(new TotalReturnsDto(portfolioTrade.getSymbol(),
          tiingoCandleArray[tiingoCandleArray.length - 1].getClose()));
    }
    Collections.sort(totalReturnsDtos,
        (a, b) -> Double.compare(a.getClosingPrice(), b.getClosingPrice()));
    for (TotalReturnsDto totalReturnsDto : totalReturnsDtos) {
      listOfSortSymbolsOnClosingPrice.add(totalReturnsDto.getSymbol());
      }
    return listOfSortSymbolsOnClosingPrice;
}

public static List<TotalReturnsDto> mainReadQuotesHelper(String[] args, List<PortfolioTrade> trades) throws IOException, URISyntaxException {
  RestTemplate restTemplate = new RestTemplate();
  List<TotalReturnsDto> tests = new ArrayList<TotalReturnsDto>();
  for (PortfolioTrade t: trades) {
  String url= "https://api.tiingo.com/tiingo/daily/"+t.getSymbol() + "/prices?startDate=" +t.getPurchaseDate().toString() + "&endDate=" + args[1]
  + "&token=1695f08005d84a2982483c36572461cb36f936c1";
  TiingoCandle[] results = restTemplate.getForObject(url, TiingoCandle[].class);
  if (results != null) {
  tests.add(new TotalReturnsDto(t.getSymbol(), results [results.length - 1].getClose())); }
  }
  return tests;
}

public static List<PortfolioTrade> readTradesFromJson(String filename) 
      throws IOException, URISyntaxException {
    File inpFile = resolveFileFromResources(filename);
    ObjectMapper mapper = getObjectMapper();
    PortfolioTrade[] trade = mapper.readValue(inpFile, PortfolioTrade[].class);
    List<PortfolioTrade> tradelst = Arrays.asList(trade);
    return tradelst;
  }


public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    String endpoint = "https://api.tiingo.com/tiingo/daily/";
    String path = "/prices?";
    StringBuilder str = new StringBuilder(endpoint);
    str.append(trade.getSymbol());
    str.append(path);
    str.append("startDate=" + trade.getPurchaseDate().toString() + "&");
    str.append("endDate=" + endDate.toString() + "&");
    str.append("token=" + token);
    return str.toString();
    // String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?"+
    // "startDate=%s&endDate=%s&token=%s", trade.getSymbol(),
    // trade.getPurchaseDate().toString(), endDate.toString(), getToken());
  }

  public static String getToken() {
    return "1695f08005d84a2982483c36572461cb36f936c1";
  }

  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
    return candles.get(0).getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
    return candles.get(candles.size() - 1).getClose();
  }

  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
    RestTemplate restTemplate = new RestTemplate();
    String tiingoRestURL = prepareUrl(trade, endDate, token);
    TiingoCandle[] tiingoCandleArray =
        restTemplate.getForObject(tiingoRestURL, TiingoCandle[].class);
    return Arrays.stream(tiingoCandleArray).collect(Collectors.toList());
  }

public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate, PortfolioTrade trade, Double buyPrice, Double sellPrice) {
    Double absReturn = (sellPrice - buyPrice) / buyPrice;
    String symbol = trade.getSymbol();
    LocalDate purchaseDate = trade.getPurchaseDate();
    Double numYears = (double) ChronoUnit.DAYS.between(purchaseDate, endDate) / 365;
    Double annualizedReturns = Math.pow((1+ absReturn), (1/numYears)) - 1;
    return new AnnualizedReturn(symbol, annualizedReturns, absReturn);
  }
  
  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args) throws IOException, URISyntaxException, DateTimeParseException { 
    List<AnnualizedReturn> annualizedReturns = new ArrayList<>();
    LocalDate endLocalDate = LocalDate.parse(args[1]);
    File trades = resolveFileFromResources (args[0]);
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioTrade[] tradeJsons = objectMapper.readValue(trades, PortfolioTrade[].class);
    for (int i = 0; i < tradeJsons.length; i++) {
      annualizedReturns.add(getAnnualizedReturn(tradeJsons[i], endLocalDate));
    }
    // Sort in Descending order 
    Comparator<AnnualizedReturn> SortByAnnReturn = Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed(); 
    Collections.sort(annualizedReturns, SortByAnnReturn);
    return annualizedReturns;
  }

  public static AnnualizedReturn getAnnualizedReturn(PortfolioTrade trade, LocalDate endLocalDate) {
    String ticker = trade.getSymbol();
    LocalDate startLocalDate = trade.getPurchaseDate();
    if (startLocalDate.compareTo(endLocalDate) >= 0) {
    throw new RuntimeException();
    }
    String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?" + "startDate=%s&endDate=%s&token=%s", ticker, startLocalDate.toString(), endLocalDate.toString(), getToken());
    RestTemplate restTemplate = new RestTemplate();
    TiingoCandle[] stocksStartToEndDate = restTemplate.getForObject(url, TiingoCandle[].class);
    if (stocksStartToEndDate != null) {
      TiingoCandle stockStartDate = stocksStartToEndDate[0];
      TiingoCandle stockLatest = stocksStartToEndDate [stocksStartToEndDate.length - 1];
      Double buyPrice = stockStartDate.getOpen();
      Double sellPrice = stockLatest.getClose();
      AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(endLocalDate, trade, buyPrice, sellPrice);
      return annualizedReturn;
    } else {
    return new AnnualizedReturn(ticker, Double. NaN, Double.NaN);
    }
  }

};


