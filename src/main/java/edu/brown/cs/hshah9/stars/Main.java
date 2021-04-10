package edu.brown.cs.hshah9.stars;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import edu.brown.cs.hshah9.REPL.REPL;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   * @param args An array of command line arguments
   * @throws Exception thrown exception if there's one
   */
  public static void main(String[] args) throws Exception {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws Exception {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
    .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }


    // REPL
    REPL repl = new REPL();
    repl.runREPL();

  }



  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.get("/neighbors_cor", new NeighborsCorHandler(), freeMarker);
    Spark.get("/neighbors_name", new NeighborsNameHandler(), freeMarker);
    Spark.get("/radius_cor", new RadiusCorHandler(), freeMarker);
    Spark.get("/radius_name", new RadiusNameHandler(), freeMarker);
  }

  /**
   * Handle requests to the front page of our Stars website.
   *
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
          "The Endgame: KD's Gambit", "n_cor_results", "", "n_name_results", "",
              "r_cor_results", "", "r_name_results", "");

      return new ModelAndView(variables, "query.ftl");
    }
  }


  /**
   * RadiusNameHandler.
   */
  private static class RadiusNameHandler implements TemplateViewRoute {

    /**
     * RadiusNameHandler handle method.
     * @param req request
     * @param res response
     * @return modelViewHandler
     */
    @Override
    public ModelAndView handle(Request req, Response res) {

      QueryParamsMap qm = req.queryMap();

      String searchType = qm.value("searchType");
      String r = qm.value("r");
      String starname = qm.value("r_starname");


      String[] input = new String[3];
      input[0] = searchType;
      input[1] = r;
      input[2] = starname;

      String output = "";

      if (searchType == null) {
        output = "ERROR: please select naive or optimized";
      } else if (searchType.equals("naive_radius")) {
        NaiveRadiusCommand naiveRCommand = new NaiveRadiusCommand();
        int err = naiveRCommand.execute(input); // what about errors

        if (err == -1) {
          output = naiveRCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(naiveRCommand.getFinalList());
        }
      } else if (searchType.equals("radius")) {
        RadiusCommand rCommand = new RadiusCommand();
        int err = rCommand.execute(input); // what about errors

        if (err == -1) {
          output = rCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(rCommand.getFinalList());
        }
      }

      Map<String, Object> variables = ImmutableMap.of("title",
              "The Endgame: KD's Gambit", "n_cor_results", "", "n_name_results", "",
              "r_cor_results", "", "r_name_results", output);

      return new ModelAndView(variables, "query.ftl");
    }
  }


  /**
   * RadiusCorHandler.
   */
  private static class RadiusCorHandler implements TemplateViewRoute {

    /**
     * RadiusCorHandler handle method.
     * @param req request
     * @param res response
     * @return modelViewHandler
     */
    @Override
    public ModelAndView handle(Request req, Response res) {

      QueryParamsMap qm = req.queryMap();

      String searchType = qm.value("searchType");
      String r = qm.value("r");
      String xcor = qm.value("x");
      String ycor = qm.value("y");
      String zcor = qm.value("z");

      String[] input = new String[5];
      input[0] = searchType;
      input[1] = r;
      input[2] = xcor;
      input[3] = ycor;
      input[4] = zcor;

      String output = "";

      if (searchType == null) {
        output = "ERROR: please select naive or optimized";
      } else if (searchType.equals("naive_radius")) {
        NaiveRadiusCommand naiveRCommand = new NaiveRadiusCommand();
        int err = naiveRCommand.execute(input); // what about errors

        if (err == -1) {
          output = naiveRCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(naiveRCommand.getFinalList());
        }

      } else if (searchType.equals("radius")) {
        RadiusCommand rCommand = new RadiusCommand();
        int err = rCommand.execute(input); // what about errors

        if (err == -1) {
          output = rCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(rCommand.getFinalList());
        }
      }


      Map<String, Object> variables = ImmutableMap.of("title",
              "The Endgame: KD's Gambit", "n_cor_results", "", "n_name_results", "",
              "r_cor_results", output, "r_name_results", "");

      return new ModelAndView(variables, "query.ftl");
    }
  }


  /**
   * NeighborsNameHandler.
   */
  private static class NeighborsNameHandler implements TemplateViewRoute {

    /**
     * NeighborsNameHandler handle method.
     * @param req request
     * @param res response
     * @return modelViewHandler
     */
    @Override
    public ModelAndView handle(Request req, Response res) {

      QueryParamsMap qm = req.queryMap();

      String searchType = qm.value("searchType");
      String k = qm.value("k");
      String starname = qm.value("starname");


      String[] input = new String[3];
      input[0] = searchType;
      input[1] = k;
      input[2] = starname;

      String output = "";

      if (searchType == null) {
        output = "ERROR: please select naive or optimized";
      } else if (searchType.equals("naive_neighbors")) {
        NaiveNeighborsCommand naiveNCommand = new NaiveNeighborsCommand();
        int err = naiveNCommand.execute(input); // what about errors

        if (err == -1) {
          output = naiveNCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(naiveNCommand.getFinalList());
        }

      } else if (searchType.equals("neighbors")) {
        NeighborsCommand nCommand = new NeighborsCommand();
        int err = nCommand.execute(input); // what about errors

        if (err == -1) {
          output = nCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(nCommand.getFinalList());
        }
      }


      Map<String, Object> variables = ImmutableMap.of("title",
              "The Endgame: KD's Gambit", "n_cor_results", "", "n_name_results", output,
              "r_cor_results", "", "r_name_results", "");

      return new ModelAndView(variables, "query.ftl");
    }
  }


  /**
   * NeighborsCorHandler.
   */
  private static class NeighborsCorHandler implements TemplateViewRoute {

    /**
     * NeighborsCorHandler handle method.
     * @param req request
     * @param res response
     * @return modelViewHandler
     */
    @Override
    public ModelAndView handle(Request req, Response res) {

      QueryParamsMap qm = req.queryMap();

      String searchType = qm.value("searchType");
      String k = qm.value("k");
      String xcor = qm.value("x");
      String ycor = qm.value("y");
      String zcor = qm.value("z");

      String[] input = new String[5];
      input[0] = searchType;
      input[1] = k;
      input[2] = xcor;
      input[3] = ycor;
      input[4] = zcor;

      String output = "";

      if (searchType == null) {
        output = "ERROR: please select naive or optimized";
      } else if (searchType.equals("naive_neighbors")) {
        NaiveNeighborsCommand naiveNCommand = new NaiveNeighborsCommand();
        int err = naiveNCommand.execute(input); // what about errors

        if (err == -1) {
          output = naiveNCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(naiveNCommand.getFinalList());
        }
      } else if (searchType.equals("neighbors")) {
        NeighborsCommand nCommand = new NeighborsCommand();
        int err = nCommand.execute(input); // what about errors

        if (err == -1) {
          output = nCommand.getErrorMessage();
        } else {
          output = new StarsApplication().getOutput(nCommand.getFinalList());
        }
      }


      Map<String, Object> variables = ImmutableMap.of("title",
              "The Endgame: KD's Gambit", "n_cor_results", output, "n_name_results", "",
              "r_name_results", "", "r_cor_results", "");

      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}

